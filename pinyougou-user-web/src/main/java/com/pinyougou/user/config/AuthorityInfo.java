package com.pinyougou.user.config;

import org.springframework.security.core.GrantedAuthority;

/**
 * 权限信息
 */
public class AuthorityInfo implements GrantedAuthority {

    /**
     * 权限CODE
     */
    private String authority;

    public AuthorityInfo(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

}


