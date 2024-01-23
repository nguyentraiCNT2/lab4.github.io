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

@WebServlet("/edit")
public class editservlet extends HttpServlet {
    public editservlet() {
        super();
    }
    private static final long serialVersionUID = 1L;
    private ConnectionUtils customerDAO;

    public void init() {
        customerDAO = new ConnectionUtils();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cusIDre = request.getParameter("cusID");
        int  cusID = Integer.parseInt(cusIDre);
        Customer existingCustomer = customerDAO.selectCustomerByCusID(cusID);
        RequestDispatcher dispatcher = request.getRequestDispatcher("edit-customer.jsp");
        request.setAttribute("customer", existingCustomer);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int cusID = Integer.parseInt(request.getParameter("cusID"));
        String cusPass = request.getParameter("cusPass");
        String cusName = request.getParameter("cusName");
        String cusPhone = request.getParameter("cusPhone");
        String cusAdd = request.getParameter("cusAdd");
        String cusEmail = request.getParameter("cusEmail");
        String cusFacebook = request.getParameter("cusFacebook");
        String cusSkyper = request.getParameter("cusSkyper");
        int cusStatus = Integer.parseInt(request.getParameter("cusStatus"));

        Customer customer = new Customer(cusID, null, cusPass, cusName, cusPhone, cusAdd, cusEmail, cusFacebook, cusSkyper, cusStatus);
        try {
            customerDAO.updateCustomer(customer);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect("list");
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
}
