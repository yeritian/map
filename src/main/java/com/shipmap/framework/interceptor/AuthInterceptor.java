package com.shipmap.framework.interceptor;

import com.shipmap.framework.Result;
import com.shipmap.framework.ResultCode;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.framework.service.TokenService;
import com.shipmap.framework.utils.IpUtils;
import com.shipmap.framework.utils.JwtTokenUtil;
import com.shipmap.framework.utils.ResponseUtil;
import com.shipmap.framework.utils.StringUtil;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 权限控制
 * scope:请求Controller之前拦截
 **/
public class AuthInterceptor implements HandlerInterceptor {
    /**
     * 权限控制配置
     **/
    private AuthInterceptorConfig authInterceptorConfig;
    /**
     * Token管理
     **/
    private TokenService tokenService;

    public AuthInterceptor(AuthInterceptorConfig authInterceptorConfig, TokenService tokenService) {
        this.authInterceptorConfig = authInterceptorConfig;
        this.tokenService = tokenService;
    }

    public AuthInterceptor(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    //请求方式参数约定
    private static final String REQUEST_METHOD = "_method";

    private static final String URI_INTERVAL = ":";

    private static final String TOKEN_PREFIX = "token";

    private static final int AUTH_FIAL_STATE = 400;
    private static final int AUTH_EXPIRE_STATE = 401;
    private static final int AUTH_UNAUTHORIZED_STATE = 403;

    /**
     * 前置增强
     **/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断是否携带token
        if (handler instanceof HandlerMethod) {
            String token = getRequestHeadToken(request);
            //白名单方行
            if (ignoreCheck(getInterceptUri((HandlerMethod) handler))) {
                //web容器内部使用
                request.setAttribute(JwtTokenUtil.TOKEN_HEADER, token);
                //放行
                return true;
            }
            //检查token是否合法
            if (!checkToken(request, token)) {
                //设置认证失败响应信息
                ResultCode authFail = ResultCode.AUTH_FAIL;
                ResponseUtil.responseJson(response, AUTH_FIAL_STATE, Result.failure(authFail));
                return false;
            }
            ActiveUser activeUser = tokenService.getActiveUserByToken(token);
            if (activeUser == null) {
                //设置认证过期响应信息
                ResultCode authExpire = ResultCode.AUTH_EXPIRE;
                ResponseUtil.responseJson(response, AUTH_EXPIRE_STATE, Result.failure(authExpire));
                return false;
            }
            //检查token是否具备权限
            if (checkPermission(getInterceptUri(request, (HandlerMethod) handler), activeUser)) {
                //刷新有效期
                tokenService.refreshExpirationDate(token);
                //web容器内部使用
                request.setAttribute(JwtTokenUtil.TOKEN_HEADER, token);
                //放行
                return true;
            }
            //设置拦截信息
            ResultCode unauthorized = ResultCode.UNAUTHORIZED;
            ResponseUtil.responseJson(response, AUTH_UNAUTHORIZED_STATE, Result.failure(unauthorized));
            //拦截
            return false;
        }
        //其他请求方法放行
        return true;
    }

    /**
     * 获取IP地址
     **/
    private String getIpAddress(HttpServletRequest request) {
        return IpUtils.getIpAddr(request);
    }

    private boolean checkToken(HttpServletRequest request, String token) {
        if (token != null) {
            try {
                //发起请求的ip
                String requestIPAddress = getIpAddress(request);
                //token中解析的ip
                String ipAddress = JwtTokenUtil.getIpAddress(token);
                if (ipAddress.equals(requestIPAddress)) {
                    return true;
                }
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    /**
     * 获取请求头携带的token
     **/
    private String getRequestHeadToken(HttpServletRequest request) {
        //获取token
        String token = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
        if (StringUtil.isBlank(token)) {
            token = request.getParameter(JwtTokenUtil.TOKEN_HEADER);
        }
        if (StringUtil.isNotBlank(token)) {
            //去除token前缀
            token = token.replace(JwtTokenUtil.TOKEN_PREFIX, "");
            return token;
        }
        return null;
    }

    /**
     * 获取Method
     **/
    private String getRequestMethod(HttpServletRequest request) {
        //RESTfull规范自定义Method
        String customMethod = request.getParameter(REQUEST_METHOD);
        if (StringUtil.isNotBlank(customMethod)) {
            return customMethod.toLowerCase();
        }
        System.out.println("customMethod==================>>>" + customMethod);
        System.out.println("getMethod   ==================>>>" + request.getMethod().toLowerCase());
        return request.getMethod().toLowerCase();
    }

    /**
     * 白名单的忽略
     **/
    private boolean ignoreCheck(List<String> uriList) {
        if (authInterceptorConfig != null) {
            String[] whiteList = authInterceptorConfig.getWhiteList();
            if (whiteList != null && whiteList.length > 0) {
                AntPathMatcher matcher = new AntPathMatcher();
                for (int i = 0, len = uriList.size(); i < len; i++) {
                    for (int j = 0; j < whiteList.length; j++) {
                        System.out.println("ignoreCheck=================>>>[" + whiteList[j] + "] equalsIgnoreCase [" + uriList.get(i) + "]");
                        if (matcher.match(whiteList[j], uriList.get(i))) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 解析Controller Uri
     **/
    private String[] analyticControllerUri(HandlerMethod handlerMethod) {
        Object bean = handlerMethod.getBean();
        if (bean != null) {
            RequestMapping requestMapping = bean.getClass().getAnnotation(RequestMapping.class);
            if (requestMapping != null) {
                return requestMapping.value();
            }
            RestController restController = bean.getClass().getAnnotation(RestController.class);
            if (restController != null) {
                return new String[]{restController.value()};
            }
        }
        return null;
    }

    /**
     * 解析方法 Uri
     **/
    private String[] analyticMethodUri(HandlerMethod handlerMethod) {
        Method method = handlerMethod.getMethod();
        if (method != null) {
            StringBuilder builder = new StringBuilder();
            GetMapping getMapping = method.getAnnotation(GetMapping.class);
            if (getMapping != null) {
                return getMapping.value();
            }
            PostMapping postMapping = method.getAnnotation(PostMapping.class);
            if (postMapping != null) {
                return postMapping.value();
            }
            DeleteMapping deleteMapping = method.getAnnotation(DeleteMapping.class);
            if (deleteMapping != null) {
                return deleteMapping.value();
            }
            PutMapping putMapping = method.getAnnotation(PutMapping.class);
            if (putMapping != null) {
                return putMapping.value();
            }
            RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
            if (requestMapping != null) {
                return requestMapping.value();
            }
        }
        return null;
    }

    /**
     * 获取Url路径携
     * URL:/xxxx/xxxx
     **/
    private List<String> getInterceptUri(HandlerMethod handlerMethod) {
        String[] analyticControllerArr = analyticControllerUri(handlerMethod);
        String[] analyticMethodArr = analyticMethodUri(handlerMethod);
        List<String> list = new ArrayList<String>();
        if (analyticControllerArr != null && analyticMethodArr != null) {
            for (int i = 0; i < analyticControllerArr.length; i++) {
                for (int j = 0; j < analyticMethodArr.length; j++) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(analyticControllerArr[i]).append(analyticMethodArr[j]);
                    list.add(builder.toString());
                }
            }
        } else if (analyticMethodArr != null) {
            for (int j = 0; j < analyticMethodArr.length; j++) {
                StringBuilder builder = new StringBuilder();
                builder.append(analyticMethodArr[j]);
                list.add(builder.toString());
            }
        }
        return list;
    }

    /**
     * 获取Url路径携
     * URL:post:/xxxx/xxxx
     **/
    private List<String> getInterceptUri(HttpServletRequest request, HandlerMethod handlerMethod) {
        String[] analyticControllerArr = analyticControllerUri(handlerMethod);
        String[] analyticMethodArr = analyticMethodUri(handlerMethod);
        List<String> list = new ArrayList<String>();
        String requestMethod = getRequestMethod(request);
        if (analyticControllerArr != null && analyticMethodArr != null) {
            for (int i = 0; i < analyticControllerArr.length; i++) {
                for (int j = 0; j < analyticMethodArr.length; j++) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(requestMethod).append(URI_INTERVAL).append(analyticControllerArr[i]).append(analyticMethodArr[j]);
                    list.add(builder.toString());
                }
            }
        } else if (analyticMethodArr != null) {
            for (int j = 0; j < analyticMethodArr.length; j++) {
                StringBuilder builder = new StringBuilder();
                builder.append(requestMethod).append(URI_INTERVAL).append(analyticMethodArr[j]);
                list.add(builder.toString());
            }
        }
        return list;
    }

    /**
     * 校验权限
     **/
    private boolean checkPermission(List<String> uriList, ActiveUser activeUser) {
        if (activeUser != null) {
            String[] authoritys = activeUser.getAuthorities();
            AntPathMatcher matcher = new AntPathMatcher();
            for (int i = 0; i < uriList.size(); i++) {
                for (int j = 0; j < authoritys.length; j++) {
                    System.out.println("uriList===========================>>>" + Arrays.toString(uriList.toArray()));
                    System.out.println("checkPermission=================>>>[" + authoritys[j] + "] equalsIgnoreCase [" + uriList.get(i) + "]");
                    if (matcher.match(authoritys[j], uriList.get(i))) {
                        System.out.println("checkPermission=================>>>放行");
                        return true;
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 后置增强
     **/
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
