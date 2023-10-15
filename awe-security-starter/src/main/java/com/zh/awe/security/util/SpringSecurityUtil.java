package com.zh.awe.security.util;

import com.zh.awe.common.utils.JsonUtils;
import com.zh.awe.common.utils.StringUtils;
import com.zh.awe.security.config.WebSecurityProperties;
import com.zh.awe.security.enums.SecureModel;
import com.zh.awe.security.model.JwtUser;
import com.zh.awe.web.exception.LoginTimeOutException;
import com.zh.awe.web.factory.WebBeanFactory;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

/**
 * springSecurity工具类
 * @author zh 2023/7/16 16:37
 */
public class SpringSecurityUtil {
    /**
     * token 登录
     *
     * @param username    用户名
     * @param password    密码
     * @param authorities 权限
     */
    public static void login(String username, String password, List<String> authorities) {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        List<GrantedAuthority> authoritiesList = AuthorityUtils.createAuthorityList(authorities.toArray(new String[]{}));
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password, authoritiesList);
        SecurityContextHolder.getContext().setAuthentication(token);
        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
    }

    /**
     * 获取当前登录用户名
     */
    public static String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

    /**
     * 获取当前登录用户
     *
     * @return 当前登录用户
     */
    public static <T> T getCurrentUser(Class<T> clazz) {
        String userDetail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        if (StringUtils.isBlank(userDetail) || !isLogin()){
            throw new LoginTimeOutException();
        }
        WebSecurityProperties securityProperties = WebBeanFactory.getBean(WebSecurityProperties.class);
        if (SecureModel.JWT.getCode().equals(securityProperties.getModel().toUpperCase())){
            JwtUser jwtUser = JsonUtils.jsonToPojo(userDetail, JwtUser.class);
            assert jwtUser != null;
            String user = JwtTokenUtil.getUser(securityProperties, jwtUser.getToken());
            return JsonUtils.jsonToPojo(user, clazz);
        } else {
            return JsonUtils.jsonToPojo(userDetail, clazz);
        }
    }

    /**
     * 判断是否已登录
     *
     * @return 是否已登录
     */
    public static boolean isLogin() {
        return !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
    }
}
