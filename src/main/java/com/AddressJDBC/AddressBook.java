package com.AddressJDBC;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class AddressBook {

    private List<AddressBook> addressBookList;

    public Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/address_book_service?useSSL=false";
        String userName = "root";
        String password = "nikhil123";

        Connection connection;

        connection = DriverManager.getConnection(jdbcURL, userName, password);
        System.out.println("Successful connection" + connection);

        return connection;
    }

    public List<AddressBookData> readData() throws SQLException {
        String sql = "select * from address_book;";
        return this.getDataInDataBase(sql);
    }

    public List<AddressBookData> getDataInDataBase(String sql) throws SQLException {
        List<AddressBookData> addressBookList = new ArrayList<>();
        try (Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            addressBookList = this.addData(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addressBookList;
    }

    public List<AddressBookData> addData(ResultSet resultSet) throws SQLException {
        List<AddressBookData> addressBookList = new ArrayList<>();
        while (resultSet.next()){
            int id = resultSet.getInt("ID");
            String Firstname = resultSet.getString("FirstName");
            String Lastname = resultSet.getString("LastName");
            String address = resultSet.getString("Address");
            String City = resultSet.getString("City");
            String State = resultSet.getString("State");
            String PhoneNum = resultSet.getString("PhoneNum");
            String Email = resultSet.getString("Email");
            addressBookList.add(new AddressBookData(id, Firstname, Lastname, address, City, State, PhoneNum, Email));
        }

        return addressBookList;
    }

    private static void listDrivers() {
        Enumeration<Driver> driverList= DriverManager.getDrivers();
        while (driverList.hasMoreElements()){
            Driver driverClass = driverList.nextElement();
            System.out.println(" "+driverClass.getClass().getName());
        }
    }

}
