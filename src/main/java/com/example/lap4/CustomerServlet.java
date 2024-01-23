package com.example.lap4;

import com.example.lap4.conn.ConnectionUtils;
import com.example.lap4.model.Customer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/customer")
public class CustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ConnectionUtils customerDAO;

    public void init() {
        customerDAO = new ConnectionUtils();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertCustomer(request, response);
                    break;
                case "/delete":
                    deleteCustomer(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateCustomer(request, response);
                    break;
                case "/search":
                    searchCustomer(request, response);
                    break;
                default:
                    listCustomer(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listCustomer(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Customer> customers = customerDAO.selectAllCustomers();
        request.setAttribute("customers", customers);
        RequestDispatcher dispatcher = request.getRequestDispatcher("customer-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("customer-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String cusIDre = request.getParameter("cusID");
         int  cusID = Integer.parseInt(cusIDre);
        Customer existingCustomer = customerDAO.selectCustomerByCusID(cusID);
        RequestDispatcher dispatcher = request.getRequestDispatcher("customer-form.jsp");
        request.setAttribute("customer", existingCustomer);
        dispatcher.forward(request, response);
    }

    private void insertCustomer(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
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
        customerDAO.insertCustomer(newCustomer);
        response.sendRedirect("list");
    }

    private void updateCustomer(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
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
        customerDAO.updateCustomer(customer);
        response.sendRedirect("list");
    }
    private void searchCustomer(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String searchCusUser = request.getParameter("searchCusUser");
        List<Customer> customers = customerDAO.selectCustomerByCusUser(searchCusUser);
        request.setAttribute("customers", customers);
        RequestDispatcher dispatcher = request.getRequestDispatcher("search-cutomer.jsp");
        dispatcher.forward(request, response);
    }
    private void deleteCustomer(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int cusID = Integer.parseInt(request.getParameter("cusID"));
        customerDAO.deleteCustomer(cusID);
        response.sendRedirect("list");
    }
}
