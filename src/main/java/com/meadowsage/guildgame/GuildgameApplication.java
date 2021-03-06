package com.meadowsage.guildgame;

import com.meadowsage.guildgame.interceptor.ApiInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class GuildgameApplication {

    @Autowired
    ApiInterceptor apiInterceptor;

    public static void main(String[] args) {
        SpringApplication.run(GuildgameApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins(
                                "http://localhost:3000",
                                "https://guild-game-front.herokuapp.com",
                                "https://inspiring-blackwell-fb88a8.netlify.app"
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(apiInterceptor)
                        .addPathPatterns("/api/**");
            }
        };
    }
}
