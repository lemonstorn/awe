package com.zh.awe.security.config;

import com.zh.awe.common.utils.RsaUtil;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zh 2023/7/2 16:16
 */
@Data
@ConfigurationProperties(prefix = "system.security")
public class WebSecurityProperties {
    private String model = "JWT";
    private Boolean need = false;// 是否需要认证登陆
    private Boolean single = false;// 是否开启单点登陆
    private Integer time = 3600;// 会话有效时间 s
    private String loginUri = "/login";// 登陆uri
    private String logoutUri = "/logout";
    private String use = "session";// 使用会话方式
    private String tokenHeader = "Authorization";// JWT 请求头
    private String tokenPrefix = "Bearer ";// JWT 默认前缀
    //Jwt_Login_Secret_Key JWT 秘钥
    private String jwtSecret = "g4mwi8wEz199e0qlzFrHfqnYGXL1jAv10wlvbzfB3XL1F7hW0kwoOuFyS5pXVuYKZyhRMoMdk4N4wiqYRAgNBLr1IkEOGskILrYangk7WJWW2C7Uo9BHzE0r8g9GadngTv1fSSQYezGJYb2nqmU5QxdCPXy6kjOfF2ZtkTgxVVpFxnCyIKmx7FUUZkCUP6mwHbIonGUl";
    private String jwtRsaPath = "/app/";// JWT Rsa公钥存储地址
    private String jwtSignWith = "HS512";// JWT 加密方式
    private PublicKey publicKey; // 公钥
    private PrivateKey privateKey; // 私钥
    private List<String> whitelist = new ArrayList<>();
    private List<String> ignoreList = new ArrayList<>();
    private Boolean csrf = false;


    @PostConstruct
    public void init() {
        try {
            if (!"RSA".equals(jwtSignWith)) {
                return;
            }
            String pubKeyPath = jwtRsaPath + RsaUtil.RSA_PUBLIC_FILENAME;
            String priKeyPath = jwtRsaPath + RsaUtil.RSA_PRIVATE_FILENAME;
            File pubKey = new File(pubKeyPath);
            File priKey = new File(priKeyPath);
            if (!pubKey.exists() || !priKey.exists()) {
                // 生成公钥和私钥
                RsaUtil.generateKey(pubKeyPath, priKeyPath, jwtSecret);
            }
            // 获取公钥和私钥
            this.publicKey = RsaUtil.getPublicKey(pubKeyPath);
            this.privateKey = RsaUtil.getPrivateKey(priKeyPath);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
