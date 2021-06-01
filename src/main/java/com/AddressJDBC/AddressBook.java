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
            Date start_date = resultSet.getDate("Joining_Date");
            addressBookList.add(new AddressBookData(id, Firstname, Lastname, address, City, State, PhoneNum, Email, start_date));
        }
        return addressBookList;
    }

    public int updateData(int id, String Lastname) throws SQLException {
        String sql = "UPDATE address_book set LastName =? where id = ? ;";
        try(Connection connection = this.getConnection()) {
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, Lastname);
            prepareStatement.setInt(2, id);
            int result = prepareStatement.executeUpdate();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<AddressBookData> printData(ResultSet resultSet) throws SQLException {
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
            Date start_date = resultSet.getDate("Joining_date");
            System.out.println("\n");
            System.out.println("Id : " + id);
            System.out.println("Firstname : " + Firstname);
            System.out.println("Lastname : " + Lastname);
            System.out.println("address : " + address);
            System.out.println("City : " + City);
            System.out.println("State : " + State);
            System.out.println("PhoneNum : " + PhoneNum);
            System.out.println("Email : " + Email);
            System.out.println("Start_date : " + start_date);
        }
        return addressBookList;
    }

    public List<AddressBookData> return_Values_between_Particular_DateRange(String startDate, String endDate) throws SQLException{
        List<AddressBookData> addressBookList = new ArrayList<>();
        String sql = String.format("select * from address_book where Joining_Date between '%s' and '%s' ;", startDate, endDate);
        return this.getDataInDataBase(sql);
        /*
        Connection connection = this.getConnection();
        try{
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                int id = resultSet.getInt("ID");
                String Firstname = resultSet.getString("FirstName");
                String Lastname = resultSet.getString("LastName");
                String address = resultSet.getString("Address");
                String City = resultSet.getString("City");
                String State = resultSet.getString("State");
                String PhoneNum = resultSet.getString("PhoneNum");
                String Email = resultSet.getString("Email");
                Date start_date = resultSet.getDate("Joining_date");

                addressBookList.add(new AddressBookData(id, Firstname, Lastname, address, City, State, PhoneNum, Email, start_date));
            }
            System.out.println(addressBookList.toString());
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return addressBookList;
         */
    }


    private static void listDrivers() {
        Enumeration<Driver> driverList= DriverManager.getDrivers();
        while (driverList.hasMoreElements()){
            Driver driverClass = driverList.nextElement();
            System.out.println(" "+driverClass.getClass().getName());
        }
    }
}
