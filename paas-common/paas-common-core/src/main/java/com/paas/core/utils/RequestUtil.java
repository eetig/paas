package com.paas.core.utils;

import com.paas.base.constant.GlobalConstant;
import com.paas.base.dto.LoginAuthDto;
import com.paas.base.enums.ErrorCodeEnum;
import com.paas.base.exception.BusinessException;
import com.paas.common.util.PublicUtil;
import com.paas.common.util.ThreadLocalMap;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Date 2022/4/4 周一 10:35
 * @Author xu
 * @FileName RequestUtil
 * @Description
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
//Lombok 注解 @NoArgsConstructor：注解在类上，为类提供一个无参的构造方法
public class RequestUtil {

    //todo 原理是什么
    public static HttpServletRequest getRequest(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * @Date 2022/4/4 10:42
     * @Author eetig
     * @Description 获得用户远程地址
     * @param request
     * @Return java.lang.String
     **/
    public static String getRemoteAddr(HttpServletRequest request){
        String ipAddress = request.getHeader(GlobalConstant.X_REAL_IP);
        if (StringUtils.isEmpty(ipAddress)||GlobalConstant.UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader(GlobalConstant.X_FORWARDED_FOR);
        }
        if (StringUtils.isEmpty(ipAddress) || GlobalConstant.UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader(GlobalConstant.PROXY_CLIENT_IP);
        }
        if (StringUtils.isEmpty(ipAddress) || GlobalConstant.UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader(GlobalConstant.WL_PROXY_CLIENT_IP);
        }
        if (StringUtils.isEmpty(ipAddress) || GlobalConstant.UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader(GlobalConstant.HTTP_CLIENT_IP);
        }
        if (StringUtils.isEmpty(ipAddress) || GlobalConstant.UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader(GlobalConstant.HTTP_X_FORWARDED_FOR);
        }
        if (StringUtils.isEmpty(ipAddress) || GlobalConstant.UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        if (StringUtils.isEmpty(ipAddress) || GlobalConstant.UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (GlobalConstant.LOCALHOST_IP.equals(ipAddress)||GlobalConstant.LOCALHOST_IP_16.equals(ipAddress)) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                }catch (UnknownHostException e){
                    log.error("获取IP地址, 出现异常={}", e.getMessage(), e);
                }
                assert inet != null;//assertion检查通常在开发和测试时开启。为了提高性能，在软件发布后， assertion检查通常是关闭的
                ipAddress = inet.getHostAddress();
            }
            log.info("获取IP地址 ipAddress={}", ipAddress);
        }
        // 对于通过多个代理的情况, 第一个IP为客户端真实IP,多个IP按照','分割 //"***.***.***.***".length() = 15
        if (ipAddress != null && ipAddress.length() > GlobalConstant.MAX_IP_LENGTH) {
            if (ipAddress.indexOf(GlobalConstant.Symbol.COMMA) > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(GlobalConstant.Symbol.COMMA));
            }
        }
        return ipAddress;
    }

    /**
     * @Date 2022/4/4 11:52
     * @Author eetig
     * @Description 获取login user
     * @param
     * @Return com.paas.base.dto.LoginAuthDto
     **/
    public static LoginAuthDto getLoginUser(){
        LoginAuthDto loginAuthDto = (LoginAuthDto) ThreadLocalMap.get(GlobalConstant.Sys.TOKEN_AUTH_DTO);
        if (PublicUtil.isEmpty(loginAuthDto)) {
            throw new BusinessException(ErrorCodeEnum.UAC10011039);
        }
        return loginAuthDto;
    }
    /**
     * @Date 2022/4/4 11:57
     * @Author eetig
     * @Description 获取认证用户的请求头
     * @param request
     * @Return java.lang.String
     **/
    public static String getAuthHeader(HttpServletRequest request){
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (org.apache.commons.lang.StringUtils.isEmpty(authHeader)) {
            throw new BusinessException(ErrorCodeEnum.UAC10011040);
        }
        return authHeader;
    }
    /**
     * @Date 2022/4/4 12:02
     * @Author eetig
     * @Description todo
     * @param header
     * @Return java.lang.String[]
     **/
    public static String[] extractAndDecodeHeader (String header) throws IOException{
        byte[] base64Token = header.substring(6).getBytes("UTF-8");
        byte[] decoded;
        try {
            decoded = Base64.decode(base64Token);
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }

        String token = new String(decoded, "UTF-8");

        int delim = token.indexOf(GlobalConstant.Symbol.MH);

        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        return new String[]{token.substring(0, delim), token.substring(delim + 1)};
    }


}
