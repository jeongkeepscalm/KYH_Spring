package hello.servlet.web.springmvc.old;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("/springmvc/old-controller") // 스프링 빈의 이름으로 해당 핸들러 호출.
public class OldController implements Controller {


    /**
         뷰 리졸버 - InternalResourceViewResolver
         스프링 부트는 InternalResourceViewResolver 라는 뷰 리졸버를 자동으로 등록하는데,
         이때 reource > application.properties 에 등록한 spring.mvc.view.prefix , spring.mvc.view.suffix 설정 정보를 사용해서 등록한다.
     **/

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("OldController.handleRequest");
        return new ModelAndView("new-form");
    }

}
