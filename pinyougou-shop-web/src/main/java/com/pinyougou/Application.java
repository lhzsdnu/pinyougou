package com.pinyougou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
// 开启security访问授权
@EnableWebSecurity
// 开启security注解模式,只有当这个注解启动了之后，才能让权限配置生效
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
