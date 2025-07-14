<%--
  Created by IntelliJ IDEA.
  User: Saumya
  Date: 7/14/2025
  Time: 9:29 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Admin - Scheduled Transfers</title>
    <style>
        body { font-family: Arial, sans-serif; padding: 40px; background: #f0f4f7; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 10px; border: 1px solid #ccc; text-align: left; }
        th { background: #3498db; color: #fff; }
        form { background: #fff; padding: 20px; border-radius: 8px; max-width: 600px; margin-top: 40px; }
        label { display: block; margin: 10px 0 4px; }
        input, select { width: 100%; padding: 8px; }
        button { margin-top: 10px; background: #3498db; color: white; border: none; padding: 10px; border-radius: 4px; cursor: pointer; }
    </style>
</head>
<body>

<h2>Admin - Scheduled Transfers</h2>

<!-- Scheduled Transfers Table -->
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Source Account</th>
        <th>Target Account</th>
        <th>Amount</th>
        <th>Next Execution</th>
        <th>Frequency</th>
        <th>Status</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${transfers}" var="t">
        <tr>
            <td>${t.scheduleId}</td>
            <td>${t.sourceAccount.accountNumber}</td>
            <td>${t.targetAccount.accountNumber}</td>
            <td>Rs ${t.amount}</td>
            <td>${t.nextExecutionDate}</td>
            <td>${t.frequency}</td>
            <td>${t.status}</td>
            <td>
                <!-- Update Form -->
                <form action="${pageContext.request.contextPath}/admin/schedule" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="update"/>
                    <input type="hidden" name="scheduleId" value="${t.scheduleId}"/>
                    <input type="number" name="amount" value="${t.amount}" step="0.01"/>
                    <select name="frequency">
                        <option ${t.frequency == 'DAILY' ? 'selected' : ''}>DAILY</option>
                        <option ${t.frequency == 'WEEKLY' ? 'selected' : ''}>WEEKLY</option>
                        <option ${t.frequency == 'MONTHLY' ? 'selected' : ''}>MONTHLY</option>
                    </select>
                    <button type="submit">Update</button>
                </form>

                <!-- Cancel Form -->
                <form action="${pageContext.request.contextPath}/admin/schedule" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="cancel"/>
                    <input type="hidden" name="scheduleId" value="${t.scheduleId}"/>
                    <button type="submit">Cancel</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<!-- Add New Scheduled Transfer -->
<h3>Add New Scheduled Transfer</h3>
<form action="${pageContext.request.contextPath}/admin/schedule" method="post">
    <input type="hidden" name="action" value="add"/>
    <label>Source Account No</label>
    <input type="text" name="sourceAccount" required/>

    <label>Target Account No</label>
    <input type="text" name="targetAccount" required/>

    <label>Amount</label>
    <input type="number" name="amount" step="0.01" required/>

    <label>Frequency</label>
    <select name="frequency">
        <option value="DAILY">DAILY</option>
        <option value="WEEKLY">WEEKLY</option>
        <option value="MONTHLY">MONTHLY</option>
    </select>

    <label>First Execution Date</label>
    <input type="date" name="nextExecutionDate" required/>

    <button type="submit">Add Scheduled Transfer</button>
</form>

</body>
</html>
