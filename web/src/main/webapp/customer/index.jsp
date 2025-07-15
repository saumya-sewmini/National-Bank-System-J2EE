<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Saumya
  Date: 7/9/2025
  Time: 2:40 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Test Fund Transfer</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 40px;
            background: #f0f4f7;
        }
        form {
            background: white;
            padding: 20px;
            max-width: 400px;
            margin: auto;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.2);
        }
        h2 {
            text-align: center;
            color: #2c3e50;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 4px;
        }
        input {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        button {
            background: #3498db;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            width: 100%;
        }
        .msg {
            text-align: center;
            margin-top: 20px;
            font-weight: bold;
        }
        .success {
            color: #27ae60;
        }
        .error {
            color: #e74c3c;
        }
    </style>
</head>
<body>

    <h2>Test Fund Transfer</h2>
    <form action="${pageContext.request.contextPath}/transfer" method="POST">
        <div class="form-group">
            <label for="source">Source Account No</label>
            <input type="text" id="source" name="sourceAccount" required>
        </div>
        <div class="form-group">
            <label for="destination">Destination Account No</label>
            <input type="text" id="destination" name="toAccount" required>
        </div>
        <div class="form-group">
            <label for="amount">Amount</label>
            <input type="number" id="amount" name="amount" required>
        </div>
        <div class="form-group">
            <label for="desc">Description</label>
            <input type="text" id="desc" name="description">
        </div>
        <button type="submit">Transfer</button>
    </form>

    <c:if test="${not empty message}">
        <div class="msg ${success ? 'success' : 'error'}">${message}</div>
    </c:if>

    <h2>Scheduled Transfers</h2>
    <table class="data-table">
        <thead>
        <tr>
            <th>To Account</th>
            <th>Amount</th>
            <th>Next Execution</th>
            <th>Frequency</th>
            <th>Status</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${scheduledTransfers}" var="s">
            <tr>
                <td>${s.targetAccount.accountNumber}</td>
                <td>Rs ${s.amount}</td>
                <td>${s.nextExecutionDate}</td>
                <td>${s.frequency}</td>
                <td>${s.status}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <h2>All Transactions</h2>
    <table class="data-table">
        <thead>
        <tr>
            <th>Date</th>
            <th>Type</th>
            <th>Amount</th>
            <th>To Account</th>
            <th>Description</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${transactions}" var="txn">
            <tr>
                <td>${txn.transactionDate}</td>
                <td>${txn.type}</td>
                <td>Rs ${txn.amount}</td>
                <td>
                    <c:choose>
                        <c:when test="${txn.destinationAccount != null}">
                            ${txn.destinationAccount.accountNumber}
                        </c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </td>
                <td>${txn.description}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <form action="${pageContext.request.contextPath}/download-transactions" method="get">
        <button type="submit">Download PDF</button>
    </form>

</body>
</html>
