package com.andy.yaokao0717;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 类描述：
 * 创建人：yekh
 * 创建时间：2017/7/17 16:01
 */

@Table(name="data")
public class DataBean {
    /**
     * news_id : 13811
     * news_title : 深港澳台千里连线，嘉年华会今夏入川
     * news_summary : 6月17—20日，“2016成都深港澳台嘉年华会”(简称嘉年华会)将在成都世纪城国际会展中心举办。其主办方励展华博借力旗
     * pic_url : http://f.expoon.com/sub/news/2016/01/21/887844_230x162_0.jpg
     */
    //指明字段,主键,是否自增长,约束(不能为空)
    @Column(name = "id", isId = true, autoGen = true, property = "NOT NULL")
    private String news_id;
    @Column(name="title")
    private String news_title;
    private String news_summary;
    @Column(name="url")
    private String pic_url;

    @Column(name="urlPath")
    private String urlPath;

    public String getNews_id() {
        return news_id;
    }

    public void setNews_id(String news_id) {
        this.news_id = news_id;
    }

    public String getNews_title() {
        return news_title;
    }

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    public String getNews_summary() {
        return news_summary;
    }

    public void setNews_summary(String news_summary) {
        this.news_summary = news_summary;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }
}
