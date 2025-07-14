<%--
  Created by IntelliJ IDEA.
  User: Saumya
  Date: 7/15/2025
  Time: 2:51 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Bank Report</title>
    <style>
        body { font-family: Arial; background: #f4f6f9; padding: 40px; }
        .report-box { max-width: 600px; margin: auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 0 10px #ccc; }
        h2 { color: #2c3e50; }
        table { width: 100%; margin-top: 20px; }
        th, td { padding: 10px; text-align: left; }
        th { background: #3498db; color: white; }
    </style>
</head>
<body>

<div class="report-box">
    <h2>Admin Report Summary</h2>
    <table>
        <tr>
            <th>Total Accounts</th>
            <td>${totalAccounts}</td>
        </tr>
        <tr>
            <th>Total Transactions</th>
            <td>${totalTransactions}</td>
        </tr>
        <tr>
            <th>Total Scheduled Transfers</th>
            <td>${totalScheduledTransfers}</td>
        </tr>
        <tr>
            <th>Total Interest Accrued</th>
            <td>Rs ${totalInterestAccrued}</td>
        </tr>
    </table>
</div>

</body>
</html>