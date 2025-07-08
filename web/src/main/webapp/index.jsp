<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Saumya
  Date: 7/9/2025
  Time: 2:38 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Banking - Customer | Login</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .login-container {
            background-color: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            width: 350px;
        }
        .login-header {
            text-align: center;
            margin-bottom: 25px;
        }
        .login-header h1 {
            color: #2c3e50;
            font-size: 24px;
            margin-bottom: 5px;
        }
        .login-header p {
            color: #7f8c8d;
            font-size: 14px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        .form-group label {
            display: block;
            margin-bottom: 8px;
            color: #2c3e50;
            font-weight: 600;
            font-size: 14px;
        }
        .form-group input {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
            box-sizing: border-box;
        }
        .form-group input:focus {
            outline: none;
            border-color: #3498db;
        }
        .login-button {
            width: 100%;
            padding: 12px;
            background-color: #3498db;
            color: white;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        .login-button:hover {
            background-color: #2980b9;
        }
        .error-message {
            color: #e74c3c;
            font-size: 14px;
            margin-bottom: 15px;
            text-align: center;
        }
        .footer-links {
            margin-top: 20px;
            text-align: center;
            font-size: 13px;
        }
        .footer-links a {
            color: #3498db;
            text-decoration: none;
        }
        .footer-links a:hover {
            text-decoration: underline;
        }
        .bank-logo {
            text-align: center;
            margin-bottom: 15px;
        }
        .bank-logo img {
            height: 50px;
        }
    </style>
</head>
<body>

<div class="login-container">
    <div class="bank-logo">
        <img src="https://e7.pngegg.com/pngimages/196/115/png-clipart-bank-logo-euro-truck-simulator-2-bank-loan-finance-banks-pattern-building-investment-thumbnail.png" alt="National Bank">
    </div>
    <div class="login-header">
        <h1>Welcome to National Bank</h1>
        <p>Please sign in to access your account</p>
    </div>

    <c:if test="${not empty errorMessage}">
        <div class="error-message">
                ${errorMessage}
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/customer-login" method="POST">
        <div class="form-group">
            <label for="email">Your Email</label>
            <input type="text" id="email" name="email" required autofocus>
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required>
        </div>
        <button type="submit" class="login-button">Sign In</button>
    </form>

    <div class="footer-links">
        <a href="${pageContext.request.contextPath}/auth/forgot-password">Forgot password?</a>
    </div>
</div>

</body>
</html>
