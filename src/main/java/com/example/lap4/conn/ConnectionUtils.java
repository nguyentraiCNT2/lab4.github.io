package com.example.lap4.conn;

import com.example.lap4.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectionUtils {
    private String url = "jdbc:sqlserver://DESKTOP-MOTUJCG\\SQLSERVER:1433;databaseName=Lab4JspServletJDBC";
    private String user = "sa";
    private String password = "1412";

    private static final String INSERT_CUSTOMER_SQL = "INSERT INTO CUSTOMER (CusUser, CusPass, CusName, CusPhone, CusAdd, CusEmail, CusFacebook, CusSkyper, CusStatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_CUSTOMERS = "SELECT * FROM CUSTOMER";
    private static final String SELECT_CUSTOMER_BY_CUSUSER = "SELECT * FROM CUSTOMER WHERE CusUser = ?";
    private static final String SELECT_CUSTOMER_BY_CUSID = "SELECT * FROM CUSTOMER WHERE CusID = ?";
    private static final String UPDATE_CUSTOMER_SQL = "UPDATE CUSTOMER SET CusPass=?, CusName=?, CusPhone=?, CusAdd=?, CusEmail=?, CusFacebook=?, CusSkyper=?, CusStatus=? WHERE CusID=?";
    private static final String DELETE_CUSTOMER_SQL = "DELETE FROM CUSTOMER WHERE CusID=?";

    public ConnectionUtils() {
    }

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void insertCustomer(Customer customer) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CUSTOMER_SQL)) {
            preparedStatement.setString(1, customer.getCusUser());
            preparedStatement.setString(2, customer.getCusPass());
            preparedStatement.setString(3, customer.getCusName());
            preparedStatement.setString(4, customer.getCusPhone());
            preparedStatement.setString(5, customer.getCusAdd());
            preparedStatement.setString(6, customer.getCusEmail());
            preparedStatement.setString(7, customer.getCusFacebook());
            preparedStatement.setString(8, customer.getCusSkyper());
            preparedStatement.setInt(9, customer.getCusStatus());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Customer> selectAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CUSTOMERS)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int cusID = rs.getInt("CusID");
                String cusUser = rs.getString("CusUser");
                String cusPass = rs.getString("CusPass");
                String cusName = rs.getString("CusName");
                String cusPhone = rs.getString("CusPhone");
                String cusAdd = rs.getString("CusAdd");
                String cusEmail = rs.getString("CusEmail");
                String cusFacebook = rs.getString("CusFacebook");
                String cusSkyper = rs.getString("CusSkyper");
                int cusStatus = rs.getInt("CusStatus");

                customers.add(new Customer(cusID, cusUser, cusPass, cusName, cusPhone, cusAdd, cusEmail, cusFacebook, cusSkyper, cusStatus));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public  Customer selectCustomerByCusID(int cusID) {
        Customer customer = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CUSTOMER_BY_CUSID)) {
            preparedStatement.setInt(1, cusID);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String cusUser = rs.getString("cusUser");
                String cusPass = rs.getString("CusPass");
                String cusName = rs.getString("CusName");
                String cusPhone = rs.getString("CusPhone");
                String cusAdd = rs.getString("CusAdd");
                String cusEmail = rs.getString("CusEmail");
                String cusFacebook = rs.getString("CusFacebook");
                String cusSkyper = rs.getString("CusSkyper");
                int cusStatus = rs.getInt("CusStatus");

                customer = new Customer(cusID, cusUser, cusPass, cusName, cusPhone, cusAdd, cusEmail, cusFacebook, cusSkyper, cusStatus);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }
    public List <Customer> selectCustomerByCusUser(String cusUser) {
        List<Customer> customers = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CUSTOMER_BY_CUSUSER)) {
            preparedStatement.setString(1, cusUser);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int cusID = rs.getInt("CusID");
                String cusPass = rs.getString("CusPass");
                String cusName = rs.getString("CusName");
                String cusPhone = rs.getString("CusPhone");
                String cusAdd = rs.getString("CusAdd");
                String cusEmail = rs.getString("CusEmail");
                String cusFacebook = rs.getString("CusFacebook");
                String cusSkyper = rs.getString("CusSkyper");
                int cusStatus = rs.getInt("CusStatus");

                customers.add( new Customer(cusID, cusUser, cusPass, cusName, cusPhone, cusAdd, cusEmail, cusFacebook, cusSkyper, cusStatus));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public boolean updateCustomer(Customer customer) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_CUSTOMER_SQL)) {
            statement.setString(1, customer.getCusPass());
            statement.setString(2, customer.getCusName());
            statement.setString(3, customer.getCusPhone());
            statement.setString(4, customer.getCusAdd());
            statement.setString(5, customer.getCusEmail());
            statement.setString(6, customer.getCusFacebook());
            statement.setString(7, customer.getCusSkyper());
            statement.setInt(8, customer.getCusStatus());
            statement.setInt(9, customer.getCusID());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    public boolean deleteCustomer(int cusID) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_CUSTOMER_SQL)) {
            statement.setInt(1, cusID);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }
    public static void main(String[] args) throws SQLException, ClassNotFoundException  {

        System.out.println("Get connection ... ");
//        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");


        System.out.println("Done!");
    }
}
