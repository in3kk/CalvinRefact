package Refact.CalvinRefact.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private String connectPath = "/imgPath/**";
    private String resourcePath = "file:///F:/CalvinUploadFiles/";//로컬
    public FilterRegistrationBean<OncePerRequestFilter> urlFilter(){//uri 필터
        FilterRegistrationBean<OncePerRequestFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                String uri = request.getRequestURI();
                String sanitizedUri = uri.replaceAll("[<>\\[\\]{}|`]","");//특수 문자 제거
                HttpServletRequestWrapper wrapperRequest = new HttpServletRequestWrapper(request){
                    @Override
                    public String getRequestURI(){
                        return sanitizedUri;
                    }
                };
                filterChain.doFilter(wrapperRequest,response);
            }
        });
        return registrationBean;
    }



    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(connectPath)
                .addResourceLocations(resourcePath);
    }
}

