package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface MyHandlerAdapter {

    // 어댑터가 해당 컨트롤러를 처리할 수 있는지 판단하는 메소드
    boolean supports(Object handler);

    /*
    어댑터는 실제 컨트롤러를 호출하고, 그 결과로 ModelView를 반환해야 한다.
    실제 컨트롤러가 ModelView를 반환하지 못하면, 어댑터가 ModelView를 직접 생성해서라도 반환해야 한다.
    프론트컨트롤러 -> 어댑터 -> 컨트롤러
    */
    ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException;


}
