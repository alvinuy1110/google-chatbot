package com.myproject.google.chatbot.config;

import com.myproject.google.chatbot.web.JsonBodyArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

//@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new JsonBodyArgumentResolver());
    }

//    @Override
//    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//
//        // remove the existing instance (from defaults)
//        converters.removeIf(converter -> converter instanceof GsonHttpMessageConverter);
//        // add your custom
//        converters.add(createGsonHttpMessageConverter());
//    }
//
//
//    private GsonHttpMessageConverter createGsonHttpMessageConverter() {
//        Gson gson = new Gson();
//        GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
//        converter.setGson(gson);
//        return converter;
//    }


}
