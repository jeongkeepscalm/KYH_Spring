package hello.servlet.web.frontcontroller.v1;
import hello.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// 해당 서블릿이 무조건 호출되게 urlPatterns 설정.
@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet {

    private Map<String, ControllerV1> controllerMap = new HashMap<>();


    // 요청 -> FrontController(This) -> Mapping URL 확인 -> Controller
    public FrontControllerServletV1() {
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1()); // 해당 url로 요청이 오면, 해당 컨트롤러가 실행되게 끔.
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
    }


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("FrontControllerServletV1.service");

        String requestURI = request.getRequestURI(); // 요청 URI 를 얻음. ex) /front-controller/v1/members/new-form
        ControllerV1 controller = controllerMap.get(requestURI); // 다형성으로 인해 ControllerV1 으로 받음.
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404
            return;
        }
        controller.process(request, response);
    }
}