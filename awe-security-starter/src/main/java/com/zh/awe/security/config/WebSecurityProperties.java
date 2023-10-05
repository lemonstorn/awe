package com.zh.awe.security.config;

import com.zh.awe.common.constants.WebConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zh 2023/7/2 16:16
 */
@Data
@ConfigurationProperties(prefix = "system.security")
public class WebSecurityProperties {
    private Boolean need = false;// 是否需要认证登陆
    private Boolean single = false;// 是否开启单点登陆
    private Integer time = 3600;// 会话有效时间 s
    private String loginUri = "/login";// 登陆uri
    private String loginUsername= "username";// 登陆用户名
    private String loginPassword = "password";// 登陆密码
    private String logoutUri = "/logout";
    private String use = "session";// 使用会话方式
    private String notFindUri = "/404";// 404页面uri
    private String staticPaths;// 静态地址
    private String staticOutPaths;// 外部静态资源（公网项目勿用）
    private String tokenHeader = WebConstants.TOKEN_HEADER;// JWT 请求头
    private String tokenPrefix = WebConstants.TOKEN_PREFIX;// JWT 默认前缀
    private String jwtSecret = "Jwt_Login_Secret_Key";// JWT 秘钥
    private String jwtRsaPath = "/app/";// JWT Rsa公钥存储地址
    private String jwtSignWith = "HS512";// JWT 加密方式
    private List<String> whitelist = new ArrayList<>();
    private List<String> ignoreList = new ArrayList<>();
    private Boolean csrf = false;
}
