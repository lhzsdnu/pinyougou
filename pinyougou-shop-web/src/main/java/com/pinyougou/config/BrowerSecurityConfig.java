package com.pinyougou.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BrowerSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //对于这些静态文件，忽略拦截
        web.ignoring().antMatchers("/global/**", "/css/**", "/img/**", "/js/**", "/plugins/**", "/seller/add","/register.html");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //指定密码加密所使用的加密器为passwordEncoder()
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //  定义当需要用户登录时候，转到的登录页面(拦截重定向)
        http.formLogin()
                // 设置登录页面
                .loginPage("/shoplogin.html").permitAll()
                .loginProcessingUrl("/login")
                //设置默认登录成功跳转页面
                .defaultSuccessUrl("/admin/index.html", true).permitAll()
                //设置失败跳转页面
                .failureUrl("/shoplogin.html").permitAll()
                .failureForwardUrl("/login");

        // 定义哪些URL需要被保护、哪些不需要被保护
        http.authorizeRequests()
                // 设置所有人都可以访问登录页面
                .antMatchers("/shoplogin.html").permitAll()
                // 任何请求,登录后可以访问
                .anyRequest()
                .authenticated();

        // 关闭csrf防护
        http.csrf().disable();

        //http.httpBasic();

        //解决跨域问题
        http.headers().frameOptions().disable();
    }

}
