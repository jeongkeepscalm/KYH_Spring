package hello.servlet.basic.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/** http://localhost:8080/request-param?username=jeonggil&age=30&username=jeonggil2 **/
@WebServlet(name = "requestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("[전체 파라미터 조회] - start");
        request.getParameterNames().asIterator().forEachRemaining(paramusername -> System.out.println(paramusername + " : " + request.getParameter(paramusername)));
        System.out.println("[전체 파라미터 조회] - end");
        System.out.println();

        System.out.println("[단일 파라미터 조회] - start");
        System.out.println("username : "+request.getParameter("username"));
        System.out.println("age : "+request.getParameter("age"));
        System.out.println("[단일 파라미터 조회] - end");
        System.out.println();

        System.out.println("[동일한 이름의 복수 파라미터 조회] - start");
        String[] usernames = request.getParameterValues("username");
        for(String username : usernames) {
            System.out.println("username : " + username);
        }
        System.out.println("[동일한 이름의 복수 파라미터 조회] - end");

        response.getWriter().write("SUCCESS");

        /**
        request.getParameter() - 파라미터 이름에 대해 단 하나의 값이 있을경우.
        request.getParameterValues() - 파라미터 이름에 대해 값이 중복일 경우.
        **/

    }
}
