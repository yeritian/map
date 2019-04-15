package com.shipmap.framework.config;

import com.baomidou.mybatisplus.mapper.ISqlInjector;
import com.baomidou.mybatisplus.mapper.LogicSqlInjector;
import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.shipmap.framework.utils.UUIDUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Date;

/**
 * MybatisPlus配置
 * Created by wangfan on 2018-02-22 上午 11:29.
 */
@EnableTransactionManagement
@Configuration
@MapperScan({"com.shipmap.modules.*.dao", "com.shipmap.framework.dao"})
public class MybatisPlusConfig {

    /**
     * 注入sql注入器
     */
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 自定义填充公共 name 字段
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {
            /**
             * 测试 user 表 name 字段为空自动填充
             */
            public void insertFill(MetaObject metaObject) {
                // 测试下划线
                System.out.println("*************************");
                System.out.println("insert fill");
                System.out.println("*************************");
                Object createTime = getFieldValByName("createTime", metaObject);
                if (createTime == null) {
                    setFieldValByName("createTime", new Date(), metaObject);
                }
                Object updateTime = getFieldValByName("updateTime", metaObject);
                if (updateTime == null) {
                    setFieldValByName("updateTime", new Date(), metaObject);
                }
                Object className = getFieldValByName("className", metaObject);
                if (className == null) {
                    setFieldValByName("className", UUIDUtil.randomUUID8CSS(), metaObject);
                }
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                //更新填充
                System.out.println("*************************");
                System.out.println("update fill");
                System.out.println("*************************");
                /*setFieldValByName("lastUpdatedDt", new Timestamp(System.currentTimeMillis()), metaObject);*/
                Object updateTime = getFieldValByName("updateTime", metaObject);
                if (updateTime == null) {
                    setFieldValByName("updateTime", new Date(), metaObject);
                }
            }
        };
    }
}