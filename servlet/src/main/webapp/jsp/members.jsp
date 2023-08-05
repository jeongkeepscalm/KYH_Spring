<%--
  Created by IntelliJ IDEA.
  User: withy
  Date: 2023-06-04
  Time: 오후 1:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="hello.servlet.domain.member.Member" %>
<%@ page import="java.util.List" %>
<%@ page import="hello.servlet.domain.member.MemberRepository" %>

<%
  MemberRepository memberRepository = MemberRepository.getInstance();
  List<Member> memberList = memberRepository.findAll();

%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<a href="/index.html">메인</a>
<table>
  <thaed>
    <th>id</th>
    <th>username</th>
    <th>age</th>
  </thaed>
  <tbody>
  <%-- out 도 request, response 처럼 jsp에서 제공 --%>
  <%
    for (Member member : memberList) {
      out.write("<tr>");
      out.write("<td>" + member.getId() + "</td>");
      out.write("<td>" + member.getUsername() + "</td>");
      out.write("<td>" + member.getAge() + "</td>");
      out.write("</tr>");
    }
  %>
  </tbody>
</table>
</body>
</html>
