package com.zh.awe.security.model;

import cn.hutool.core.util.ReflectUtil;
import com.zh.awe.common.utils.JsonUtils;
import com.zh.awe.security.config.WebSecurityProperties;
import com.zh.awe.security.util.JwtTokenUtil;
import com.zh.awe.web.factory.WebBeanFactory;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author zh 2023/10/15 21:43
 */
public class JwtUser implements UserDetails, CredentialsContainer {
    private final UserDetails user;
    private final String token;

    public String getToken(){
        return this.token;
    }
    public JwtUser(UserDetails user, String id, Object tokenUser) {
        this.user = user;
        WebSecurityProperties securityProperties = WebBeanFactory.getBean(WebSecurityProperties.class);
        this.token = JwtTokenUtil.createToken(id, JsonUtils.objectToJson(tokenUser), securityProperties);
    }

    @Override
    public void eraseCredentials() {
        ReflectUtil.setFieldValue(user, "password", null);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
