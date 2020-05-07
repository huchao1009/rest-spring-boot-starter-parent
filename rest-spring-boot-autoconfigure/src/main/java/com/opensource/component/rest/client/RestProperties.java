package com.opensource.component.rest.client;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "component.rest-template")
public class RestProperties {
    /**
     * 指从连接池获取连接的timeout
     */
    private int connectionRequestTimeout = 500;

    /**
     * 指客户端和服务器建立连接的timeout，就是http请求的三个阶段，
     * 一：建立连接；二：数据传送；三，断开连接。
     * 超时后会ConnectionTimeOutException
     */
    private int connectTimeout = 1000;

    /**
     * 指客户端从服务器读取数据的timeout，超出后会抛出SocketTimeOutException
     */
    private int readTimeout = 10000;

    /**
     * 连接池最大连接数
     */
    private int maxTotal = 500;

    /**
     * 每一个路由的最大连接数
     * 这里的路由是指IP+PORT，
     * 例如连接池大小(MaxTotal)设置为300，路由连接数设置为200(DefaultMaxPerRoute)，
     * 对于www.a.com与www.b.com两个路由来说，发起服务的主机连接到每个路由的最大连接数（并发数）不能超过200，两个路由的总连接数不能超过300。
     */
    private int defaultMaxPerRoute = 300;
}
