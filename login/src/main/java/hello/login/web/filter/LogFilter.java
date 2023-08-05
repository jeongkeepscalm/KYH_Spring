package hello.login.web.filter;


import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

/**
 *
 * [ 모든 요청을 로그로 남기는 필터 ]
 *
 * Filter 를 사용하기 위해 javax.servlet 인터페이스 구현.
 *
 * doFilter()
 *      http 요청 -> doFilter() 호출.
 *      ServletRequest 는 http 요청이 아닌 경우까지 고려해서 만든 인터페이스.
 * *** chain.doFilter()
 *      다음 필터가 있으면 필터를 호출하고, 필터가 없으면 서블릿을 호출.
 *      해당 로직을 호출하지 않으면 다음 단계로 진행되지 않음.
 **/

@Slf4j
public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("log filter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        String uuid = UUID.randomUUID().toString();
        try {
            log.info("request [{}][{}]", uuid, requestURI);
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("response [{}][{}]", uuid, requestURI);
        }
    }

    @Override
    public void destroy() {
        log.info("log filter destroy");
    }
}
