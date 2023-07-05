package technology.assessment.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import technology.assessment.interceptor.HttpTraceInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final HttpTraceInterceptor httpTraceInterceptor;

    @Autowired
    public WebMvcConfig(HttpTraceInterceptor httpTraceInterceptor) {
        this.httpTraceInterceptor = httpTraceInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(httpTraceInterceptor);
    }
}

