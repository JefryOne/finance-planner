<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Планировщик финансовых расходов</title>
</head>
<body>
    <h2>Добавить новый расход</h2>
    <form action="expenses" method="post">
        <label>Наименование:</label><br>
        <input type="text" name="item" required><br>
        <label>Сумма (руб.):</label><br>
        <input type="number" name="amount" required><br><br>
        <input type="submit" value="Добавить">
    </form>

    <h2>Список расходов</h2>
    <ul>
        <c:forEach var="expense" items="${expenses}">
            <li>${expense}</li>
        </c:forEach>
    </ul>
</body>
</html>
