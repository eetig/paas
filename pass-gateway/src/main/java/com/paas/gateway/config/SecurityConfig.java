package com.paas.gateway.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @Date 2022/4/4 周一 09:54
 * @Author xu
 * @FileName SecurityConfig
 * @Description
 */
@Configuration
@EnableOAuth2Sso
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * @Date 2022/4/4 10:02
     * @Author eetig
     * @Description Spring Security，引入了CSRF(跨域访问限制)，默认是开启。CSRF和RESTful技术有冲突。CSRF默认支持的方法： GET|HEAD|TRACE|OPTIONS，不支持POST
     * @param http
     * @Return void
     **/
    @Override
    protected void configure(HttpSecurity http)throws Exception{

        http.csrf().disable();
    }
}
