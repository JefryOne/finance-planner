<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Учет расходов</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<h2>Добавить расход</h2>
<form method="post" action="expenses" class="add-expense-form">
    <input type="hidden" name="action" value="add">
    <div>
        <label for="item">Наименование:</label>
        <input type="text" name="item" id="item">
    </div>
    <div>
        <label for="amount">Сумма:</label>
        <input type="text" name="amount" id="amount">
    </div>
    <div>
        <input type="submit" value="Добавить">
    </div>
</form>

<h2>Список расходов</h2>
<table>
    <tr>
        <th>Наименование</th>
        <th>Сумма</th>
        <th>Действия</th>
    </tr>
    <c:forEach var="expense" items="${expenses}">
        <tr>
            <td>${expense.item}</td>
            <td>${expense.amount} руб.</td>
            <td>
                <form method="post" action="expenses" class="delete-expense-form">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="id" value="${expense.id}">
                    <input type="submit" value="Удалить">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>