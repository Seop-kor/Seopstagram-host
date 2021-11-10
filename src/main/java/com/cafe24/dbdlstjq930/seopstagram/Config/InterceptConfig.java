package com.cafe24.dbdlstjq930.seopstagram.Config;

import com.cafe24.dbdlstjq930.seopstagram.Intercept.LoginIntercept;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class InterceptConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> interceptorList = new ArrayList<>();
        interceptorList.add("/");
        interceptorList.add("/post");
        interceptorList.add("/profile");
        registry.addInterceptor(new LoginIntercept())
                .addPathPatterns(interceptorList);
    }
}
