package com.shipmap.modules.base.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author ${author}
 * @since 2019-03-12
 */
@TableName("tb_upload")
public class Upload extends Model<Upload> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String author;//作者
    private String id;
    private String source;//文件来源
    /*@JSONField(format="yyyy")
            @TableField("year")*/
    private String year;//文件年份
    private String submitter;//提交者
    //    @JSONField(format="yyyy-MM-dd HH:mm:ss")
//    @TableField("upload_time ")
    private String uploadTime;//上传时间
    private String filename;//文件名称
    private String filetype;//文件类型
    private String filesize;//文件大小


    private int downloads;//下载次数


    private String path;//文件路径
    private String nodeid;
    private String ower;//user

    public String getOwer() {
        return ower;
    }

    public void setOwer(String ower) {
        this.ower = ower;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getNodeid() {
        return nodeid;
    }

    public void setNodeid(String nodeid) {
        this.nodeid = nodeid;
    }
 /*   private String context;
    private String level;

    private String nodetitle;
    private String parentid;
    private boolean spread;
    private boolean isleaf;*/

    public String getYear() {
        return year;
    }
   /* public String getYear() {
        return new SimpleDateFormat("yyyy").format(new Date());
    }*/

    public void setYear(String year) {
        this.year = year;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    /*public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }*/

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }


    @Override
    public String toString() {
        return "Upload{" +
                "author='" + author + '\'' +
                ", id='" + id + '\'' +
                ", source='" + source + '\'' +
                ", year='" + year + '\'' +
                ", submitter='" + submitter + '\'' +
                ", uploadTime='" + uploadTime + '\'' +
                ", filename='" + filename + '\'' +
                ", filetype='" + filetype + '\'' +
                ", filesize='" + filesize + '\'' +
                ", downloads=" + downloads +
                ", path='" + path + '\'' +
                ", nodeid='" + nodeid + '\'' +
                ", ower='" + ower + '\'' +
                '}';
    }
}
