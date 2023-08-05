package hello.exception.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {
    /**  
     * Object handler : 핸들러(컨트롤러) 정보
     * Exception ex : 핸들러(컨트롤러)에서 발생한 예외
     *
     * ExceptionResolver 가 ModelAndView 를 반환하는 이유는
     * try, catch 하듯이, Exception 을 처리해서 정상 흐름 처럼 변경하는 것이 목적이기 때문이다.
     * 빈 ModelAndView 를 반환하면 뷰를 렌더링 하지 않고, 정상 흐름으로 서블릿이 리턴된다.
     **/
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 발생하는 예외에 따라서 400, 404 등 다른 http 상태코드를 사용하기 위해.
        try {
            /**
             * 여기서는 IllegalArgumentException 이 발생하면 response.sendError(400) 를 호출해서
             * HTTP 상태 코드를 400으로 지정하고, 빈 ModelAndView 를 반환한다.
             **/
            if (ex instanceof IllegalArgumentException) {
                log.info("IllegalArgumentException resolver to 400");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
                return new ModelAndView();
            }
        } catch (IOException e) {
            log.error("resolver ex", e);
        }

        return null;
    }
}
