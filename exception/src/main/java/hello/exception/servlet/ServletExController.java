package hello.exception.servlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
public class ServletExController {

    @GetMapping("/error-ex")
    public void errorEx() {
        throw new RuntimeException("occur Exception");
    }

    /**
     * response.sendError(HTTP 상태 코드, 오류 메시지)
     * 오류가 발생했을 때 HttpServletResponse 가 제공하는 sendError 라는 메서드를 사용해도 된다.
     * 이것을 호출한다고 당장 예외가 발생하는 것은 아니지만, 서블릿 컨테이너에게 오류가 발생했다는 점을 전달할 수 있다.
     * 이 메서드를 사용하면 HTTP 상태 코드와 오류 메시지도 추가할 수 있다.
     **/
    @GetMapping("/error-404")
    public void error404(HttpServletResponse response) throws IOException {
        response.sendError(404, "404 오류!");
    }

    @GetMapping("/error-500")
    public void error500(HttpServletResponse response) throws IOException {
        response.sendError(500);
    }


}
