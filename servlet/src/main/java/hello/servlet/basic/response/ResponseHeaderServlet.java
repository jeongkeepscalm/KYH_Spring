package hello.servlet.basic.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // [status - line]
        response.setStatus(HttpServletResponse.SC_OK); // 200

        // [response-headers]
//        response.setHeader("Content-Type", "text/plain;charset=utf-8");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache"); // 캐시 무효화'
        response.setHeader("my-header","hello"); // 임의의 값 설정 가능.

        content(response);

        /** 쿠키 값을 셋 해주고 해당 페이지를 리로드하면, request headers안 쿠키가 설정 되어 있음을 확인 가능 **/
        cookie(response);

        redirect(response);

        response.getWriter().write("OK");

    }


    // [Header - Content 편의 메소드]
    private void content(HttpServletResponse response) {
        //Content-Type: text/plain;charset=utf-8
        //Content-Length: 2
        //response.setHeader("Content-Type", "text/plain;charset=utf-8");
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        //response.setContentLength(2); //(생략시 자동 생성)
    }

    // [Header - Cookie 편의 메소드]
    private void cookie(HttpServletResponse response) {
        //Set-Cookie: myCookie=good; Max-Age=600;
        //response.setHeader("Set-Cookie", "myCookie=good; Max-Age=600");
        Cookie cookie = new Cookie("myCookie", "good");
        cookie.setMaxAge(600); //600초
        response.addCookie(cookie);
    }

    // [Header - Redirect 편의 메소드]
    private void redirect(HttpServletResponse response) throws IOException {
        //Status Code 302
        //Location: /basic/hello-form.html
        //response.setStatus(HttpServletResponse.SC_FOUND); // 302 : redirect 상태코드번호
        //response.setHeader("Location", "/basic/hello-form.html");

        response.sendRedirect("/basic/hello-form.html");
    }

}
