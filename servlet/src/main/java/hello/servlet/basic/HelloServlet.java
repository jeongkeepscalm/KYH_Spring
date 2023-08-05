package hello.servlet.basic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {

    // ctrl + o 오버라이딩 단축키
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("HelloServlet.service"); // soutm

        System.out.println("request = " + request); // request, response 는 interface
        System.out.println("response = " + response);

        String username = request.getParameter("username"); // 쿼리파라미터로 적용한 값이 나옴.
        System.out.println("username = " + username);

        // header 정보 안에 contentType, characterEncoding 적용.
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("hello "+ username);


    }
}
