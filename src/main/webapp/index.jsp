<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Учет расходов</title>
</head>
<body>
    <h2>Добавить расход</h2>
    <form method="post" action="expenses">
        <input type="hidden" name="action" value="add">
        <label>Наименование: <input type="text" name="item"></label><br>
        <label>Сумма: <input type="text" name="amount"></label><br>
        <input type="submit" value="Добавить">
    </form>

<h2>Список расходов</h2>
<table border="1">
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
                <form method="post" action="expenses">
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
