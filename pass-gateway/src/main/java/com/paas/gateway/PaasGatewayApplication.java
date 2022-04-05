package com.paas.gateway;

import com.didispace.swagger.butler.EnableSwaggerButler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @Date 2022/4/4 周一 09:20
 * @Author xu
 * @FileName PaasGatewayApplication
 * @Description 网关入口
 */
@SpringBootApplication//start
@EnableDiscoveryClient//和@EnableEurekaClient在功能上是一致：写在启动类的上，开启服务注册发现功能，但是适用任何注册中心
@EnableZuulProxy//@EnableZuulServer的加强版，提供路由综合管理能力的Zuul
@EnableOAuth2Sso//sping基于oauth2的Single Sign On ：代码的思路：用户登录时，验证用户的账户和密码，生成一个Token保存在数据库中，将Token写到Cookie,将用户数据保存在Session中请求时都会带上Cookie，检查有没有登录，如果已经登录则放行
@EnableHystrix//熔断
@EnableSwaggerButler//API文档汇总管理工具Swagger Butler
public class PaasGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaasGatewayApplication.class,args);
    }

    @Bean
    public CorsFilter corsFilter(){
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        //允许跨域，credentials	资格证书; 证件; 资格; 资历; 证明书;
        config.setAllowCredentials(true);
        //允许向该服务器提交请求的URI，*表示全部允许，在SpringMVC中，如果设成*，会自动转成当前请求头中的Origin
        config.addAllowedOrigin("*");
        //允许访问的头信息,*表示全部
        config.addAllowedHeader("*");
        //预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
        config.setMaxAge(18000L);
        // 允许提交请求的方法，*表示全部允许
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        // 允许Get的请求方法
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");

        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }


}
