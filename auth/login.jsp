<%--
  Created by IntelliJ IDEA.
  User: Dell
  Date: 06/12/2018
  Time: 15:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>Reddit: The front page of the internet</title>

        <link rel="stylesheet" href=<c:out value="${initParam.assets}/css/styles.css"/>>
    </head>
    <body class="bg-grey-light font-sans">
            <form action="/reddit/login" method="Post">
                <div class="flex flex-col w-1/3 mx-auto p-2">
                    <p class="m-2">Sign in</p>
                    <input type="text" name="username" placeholder="username" class="border rounded p-2 m-2" required>
                    <input type="password" name="password" placeholder="passsword" class="border rounded p-2 m-2" required>
                    <button class="border rounded bg-blue p-2 text-sm font-bold text-white w-1/2 mx-auto">Sign In</button>
                    <a href="" class="mx-auto">Forgot password</a>
                </div>
            </form>
    <%--TODO if not subs to anything home page signals it--%>
    </body>
</html>
