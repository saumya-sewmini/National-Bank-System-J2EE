<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Saumya
  Date: 7/9/2025
  Time: 3:03 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bank | Customer Registration</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            padding: 20px 0;
        }
        .registration-container {
            background-color: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            width: 450px;
        }
        .registration-header {
            text-align: center;
            margin-bottom: 25px;
        }
        .registration-header h1 {
            color: #2c3e50;
            font-size: 24px;
            margin-bottom: 5px;
        }
        .registration-header p {
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
        .form-group input, .form-group select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
            box-sizing: border-box;
        }
        .form-group input:focus, .form-group select:focus {
            outline: none;
            border-color: #3498db;
        }
        .form-row {
            display: flex;
            gap: 15px;
        }
        .form-row .form-group {
            flex: 1;
        }
        .register-button {
            width: 100%;
            padding: 12px;
            background-color: #27ae60;
            color: white;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        .register-button:hover {
            background-color: #219653;
        }
        .error-message {
            color: #e74c3c;
            font-size: 14px;
            margin-bottom: 15px;
            text-align: center;
        }
        .success-message {
            color: #27ae60;
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
        .password-strength {
            margin-top: 5px;
            font-size: 12px;
            color: #7f8c8d;
        }
        .password-strength.weak {
            color: #e74c3c;
        }
        .password-strength.medium {
            color: #f39c12;
        }
        .password-strength.strong {
            color: #27ae60;
        }
    </style>
    <script>
        function validatePassword() {
            const password = document.getElementById('password').value;
            const strengthIndicator = document.getElementById('password-strength');

            if (password.length === 0) {
                strengthIndicator.textContent = '';
                strengthIndicator.className = 'password-strength';
                return;
            }

            // Simple password strength check
            let strength = 0;
            if (password.length >= 8) strength++;
            if (password.match(/[a-z]/) strength++;
            if (password.match(/[A-Z]/)) strength++;
            if (password.match(/[0-9]/)) strength++;
            if (password.match(/[^a-zA-Z0-9]/)) strength++;

            if (strength <= 2) {
                strengthIndicator.textContent = 'Weak password';
                strengthIndicator.className = 'password-strength weak';
            } else if (strength <= 4) {
                strengthIndicator.textContent = 'Medium strength password';
                strengthIndicator.className = 'password-strength medium';
            } else {
                strengthIndicator.textContent = 'Strong password';
                strengthIndicator.className = 'password-strength strong';
            }
        }

        function validateForm() {
            const password = document.getElementById('password').value;
            const confirmPassword = document.getElementById('confirmPassword').value;

            if (password !== confirmPassword) {
                alert('Passwords do not match!');
                return false;
            }

            return true;
        }
    </script>
</head>
<body>

<div class="registration-container">
    <div class="bank-logo">
        <img src="https://e7.pngegg.com/pngimages/196/115/png-clipart-bank-logo-euro-truck-simulator-2-bank-loan-finance-banks-pattern-building-investment-thumbnail.png" alt="National Bank">
    </div>
    <div class="registration-header">
        <h1>New Customer Registration</h1>
        <p>Please fill in all required details to open new customer account</p>
    </div>

    <c:if test="${not empty errorMessage}">
        <div class="error-message">
                ${errorMessage}
        </div>
    </c:if>

    <c:if test="${not empty successMessage}">
        <div class="success-message">
                ${successMessage}
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/customer-register" method="POST" onsubmit="return validateForm()">
        <div class="form-row">
            <div class="form-group">
                <label for="name">Customer Name <span style="color: #e74c3c;">*</span></label>
                <input type="text" id="name" name="name" required>
            </div>
        </div>

        <div class="form-group">
            <label for="email">Email Address <span style="color: #e74c3c;">*</span></label>
            <input type="email" id="email" name="email" required>
        </div>

        <div class="form-group">
            <label for="contact">Contact Number <span style="color: #e74c3c;">*</span></label>
            <input type="tel" id="contact" name="contact" required pattern="[0-9]{10,15}">
        </div>

        <div class="form-group">
            <label for="password">Password <span style="color: #e74c3c;">*</span></label>
            <input type="password" id="password" name="password" required onkeyup="validatePassword()">
            <div id="password-strength" class="password-strength"></div>
        </div>

        <div class="form-group">
            <label for="confirmPassword">Confirm Password <span style="color: #e74c3c;">*</span></label>
            <input type="password" id="confirmPassword" name="confirmPassword" required>
        </div>

        <button type="submit" class="register-button">Register Account</button>
    </form>
</div>

</body>
</html>