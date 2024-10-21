package com.example.finance;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/expenses")
public class ExpenseServlet extends HttpServlet {

    private String jdbcUrl;
    private String jdbcUsername;
    private String jdbcPassword;
    private String jdbcDriver;

    @Override
    public void init() throws ServletException {
        jdbcUrl = getServletContext().getInitParameter("jdbc.url");
        jdbcUsername = getServletContext().getInitParameter("jdbc.username");
        jdbcPassword = getServletContext().getInitParameter("jdbc.password");
        jdbcDriver = getServletContext().getInitParameter("jdbc.driverClassName");

        // Загрузка драйвера JDBC
        try {
            Class.forName(jdbcDriver);
        } catch (ClassNotFoundException e) {
            throw new ServletException("Не удалось загрузить JDBC драйвер", e);
        }

        // Инициализация базы данных
        try (Connection conn = getConnection()) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS expenses ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "item VARCHAR(255), "
                    + "amount DECIMAL(10, 2))");
        } catch (SQLException e) {
            throw new ServletException("Ошибка инициализации базы данных", e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Перенаправляем на doPost для обработки всех запросов в одном методе
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        try {
            if (action == null || action.isEmpty()) {
                // Действие по умолчанию: отображение списка расходов
                listExpenses(request, response);
            } else if (action.equals("add")) {
                addExpense(request, response);
            } else if (action.equals("delete")) {
                deleteExpense(request, response);
            } else {
                // Неизвестное действие
                response.sendRedirect("expenses");
            }
        } catch (SQLException e) {
            throw new ServletException("Ошибка обработки запроса", e);
        }
    }

    private void listExpenses(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Expense> expenses = new ArrayList<>();

        try (Connection conn = getConnection()) {
            String sql = "SELECT id, item, amount FROM expenses";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String item = rs.getString("item");
                BigDecimal amount = rs.getBigDecimal("amount");
                expenses.add(new Expense(id, item, amount));
            }
        }

        request.setAttribute("expenses", expenses);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    private void addExpense(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String item = request.getParameter("item");
        String amountStr = request.getParameter("amount");
        BigDecimal amount = new BigDecimal(amountStr);

        try (Connection conn = getConnection()) {
            String sql = "INSERT INTO expenses (item, amount) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, item);
            stmt.setBigDecimal(2, amount);
            stmt.executeUpdate();
        }

        response.sendRedirect("expenses");
    }

    private void deleteExpense(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        try (Connection conn = getConnection()) {
            String sql = "DELETE FROM expenses WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }

        response.sendRedirect("expenses");
    }
}