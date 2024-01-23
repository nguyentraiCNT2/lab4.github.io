package com.example.lap4;

import com.example.lap4.conn.ConnectionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/delete")
public class deleteservlet extends HttpServlet {
    public deleteservlet() {
        super();
    }
    private static final long serialVersionUID = 1L;
    private ConnectionUtils customerDAO;

    public void init() {
        customerDAO = new ConnectionUtils();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int cusID = Integer.parseInt(req.getParameter("cusID"));
        try {
            customerDAO.deleteCustomer(cusID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect(req.getContextPath() + "/customer");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
