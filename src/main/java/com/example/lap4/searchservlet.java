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
import java.util.List;

@WebServlet("/search")
public class searchservlet extends HttpServlet {
    public searchservlet() {
        super();
    }
    private static final long serialVersionUID = 1L;
    private ConnectionUtils customerDAO;

    public void init() {
        customerDAO = new ConnectionUtils();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchCusUser = request.getParameter("searchCusUser");
        List<Customer> customers = customerDAO.selectCustomerByCusUser(searchCusUser);
        request.setAttribute("customers", customers);
        RequestDispatcher dispatcher = request.getRequestDispatcher("search-cutomer.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
}
