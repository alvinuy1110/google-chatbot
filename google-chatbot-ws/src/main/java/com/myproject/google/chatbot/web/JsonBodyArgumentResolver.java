package com.myproject.google.chatbot.web;

import com.google.gson.Gson;
import com.myproject.google.chatbot.annotation.JsonBody;
import com.myproject.google.chatbot.config.WebMvcConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.ServletRequest;
import java.nio.charset.Charset;

//annotation processor for JsonBody
@Slf4j
public class JsonBodyArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(JsonBody.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        JsonBody annotation = parameter.getParameterAnnotation(JsonBody.class);
        assert annotation != null;
        ServletRequest servletRequest = webRequest.getNativeRequest(ServletRequest.class);
        if (servletRequest == null) {
            throw new Exception("can not get ServletRequest from NativeWebRequest");
        }
        String copy = StreamUtils.copyToString(servletRequest.getInputStream(), Charset.forName(annotation.encoding()));

        // TODO not working if type is of String
        Object obj =  new Gson().fromJson(copy, parameter.getGenericParameterType());
        return obj;
    }
}
