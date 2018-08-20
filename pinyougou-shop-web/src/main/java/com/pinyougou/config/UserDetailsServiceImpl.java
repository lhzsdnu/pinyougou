package com.pinyougou.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.entity.Seller;
import com.pinyougou.sellergoods.service.SellerService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Reference(version = "${demo.service.version}",
            application = "${dubbo.application.id}",
            registry = "${dubbo.registry.id}")
    private SellerService sellerService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //构建角色列表
        //List<GrantedAuthority> grantAuths = new ArrayList<GrantedAuthority>();
        //grantAuths.add(new SimpleGrantedAuthority("ROLE_SELLER"));
        List<GrantedAuthority> grantAuths = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_SELLER");

        //得到商家对象
        Seller seller = sellerService.findByUserName(username);
        if (seller != null) {
            if ("1".equals(seller.getStatus())) {
                return new User(username, seller.getPassword(), grantAuths);
            } else {
                return null;
            }
        } else {
            return null;
        }

    }
}
