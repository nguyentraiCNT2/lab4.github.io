package com.example.lap4;

import com.example.lap4.conn.ConnectionUtils;
import com.example.lap4.model.Customer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/new")
public class addcustomerservlet extends HttpServlet {
    public addcustomerservlet() {
        super();
    }
    private static final long serialVersionUID = 1L;
    private ConnectionUtils  customerDAO;

    public void init() {
        customerDAO = new ConnectionUtils();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("customer-form.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        String cusUser = request.getParameter("cusUser");
        String cusPass = request.getParameter("cusPass");
        String cusName = request.getParameter("cusName");
        String cusPhone = request.getParameter("cusPhone");
        String cusAdd = request.getParameter("cusAdd");
        String cusEmail = request.getParameter("cusEmail");
        String cusFacebook = request.getParameter("cusFacebook");
        String cusSkyper = request.getParameter("cusSkyper");
        int cusStatus = Integer.parseInt(request.getParameter("cusStatus"));

        Customer newCustomer = new Customer(0,cusUser, cusPass, cusName, cusPhone, cusAdd, cusEmail, cusFacebook, cusSkyper, cusStatus);
        try {
            customerDAO.insertCustomer(newCustomer);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect("customer");
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
}
