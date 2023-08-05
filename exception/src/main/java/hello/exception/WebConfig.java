package hello.exception;

import hello.exception.filter.LogFilter;
import hello.exception.interceptor.LogInterceptor;
import hello.exception.resolver.MyHandlerExceptionResolver;
import hello.exception.resolver.UserHandlerExceptionResolver;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ERROR);
     * 클라이언트 요청, 오류 페이지 요청 둘 다 적용.
     * 아무것도 넣지 않았을 경우, 기본값이 DispatcherType.REQUEST(클라이언트 요청). 클라이언트 요청이 있을 경우에만 해당 필터 적용.
     * 물론 오류 페이지 요청 전용 필터를 적용하고 싶으면 DispatcherType.ERROR 만 지정하면 된다.
     *
     **/
//    @Bean
    public FilterRegistrationBean logFilter(){
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ERROR);
        return filterRegistrationBean;
    }


    /**
     * 필터의 경우 필터를 등록 할 때, 어떤 DispatcherType인 경우에 필터를 적용할 지 선택 가능.
     * 인터셉터는 서블릿이 제공하는 기능이 아니라 스프링이 제공하는 기능이기 때문에 DispatcherType 과 무관하게 항상 호출됨.
     * 대신 인터셉터는 요청 경로에 따라서 추가하거나 제외하기 쉽게 되어 있기에 이러한 설정을 사용해서 오류 페이지 경로를 excludePathPatterns 를 사용해서 빼주면 된다.
     **/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/css/**", "/*.ico"
                        ,"/error", "/error-page/**"
                );
        // error 발생 시 error 요청은 인터셉트를 타지 않도록 하기 위해 excludePathPatterns 적용.
    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add(new MyHandlerExceptionResolver());
        resolvers.add(new UserHandlerExceptionResolver());
    }


}
