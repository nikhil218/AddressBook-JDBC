package com.AddressJDBC;

import java.sql.*;
import java.sql.Date;
import java.util.*;

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
            String start_date = resultSet.getString("Joining_Date");
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
        String sql = String.format("select * from address_book where Joining_Date between '%s' and '%s' ;", startDate, endDate);
        return this.getDataInDataBase(sql);
    }

    public String countByCity(String city) throws SQLException {
        Connection connection = this.getConnection();
        String result = null;
        try{
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("Select count(*) from address_book where city = ?;");
            preparedStatement.setString(1, city);
            ResultSet resultSet = preparedStatement.executeQuery();
            connection.commit();
            while(resultSet.next()){
                result = resultSet.getString(1);
                System.out.println(resultSet.getString(1));
            }
            return result;
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
            connection.rollback();
        }
        return  result;
    }

    public String countByState(String state) throws SQLException {
        Connection connection = this.getConnection();
        String result = null;
        try{
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("Select count(*) from address_book where state = ?;");
            preparedStatement.setString(1, state);
            ResultSet resultSet = preparedStatement.executeQuery();
            connection.commit();
            while(resultSet.next()){
                result = resultSet.getString(1);
                System.out.println(resultSet.getString(1));
            }
            return result;
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
            connection.rollback();
        }
        return  result;
    }


    private static void listDrivers() {
        Enumeration<Driver> driverList= DriverManager.getDrivers();
        while (driverList.hasMoreElements()){
            Driver driverClass = driverList.nextElement();
            System.out.println(" "+driverClass.getClass().getName());
        }
    }

    public void addANewRowInDB(String FirstName, String LastName, String Address, String City, String State, String PhoneNum, String Email, String Joining_date) throws SQLException {
        Connection connection = this.getConnection();
        try {
            connection.setAutoCommit(false);
            String sql = "insert into address_book(FirstName, LastName, Address, City, State, PhoneNum, Email, Joining_date)\n" +
                    "values(?, ?, ?, ?, ?, ?, ?, ?);";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, FirstName);
            preparedStatement.setString(2, LastName);
            preparedStatement.setString(3, Address);
            preparedStatement.setString(4, City);
            preparedStatement.setString(5, State);
            preparedStatement.setString(6, PhoneNum);
            preparedStatement.setString(7, Email);
            preparedStatement.setString(8, Joining_date);

            preparedStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
    }

    public void addContactsToAddressBookWithThreads(List<AddressBookData> addressBookDataList) throws SQLException{
        Map<Integer, Boolean> contactAdditionStatus = new HashMap<Integer, Boolean>();
        addressBookDataList.forEach(addressBookData -> {
            Runnable task = () -> {
                contactAdditionStatus.put(addressBookData.hashCode(), false);
                System.out.println("Employee Being Added: "+Thread.currentThread().getName());
                try {
                    this.addANewRowInDB(addressBookData.getFirstname(), addressBookData.getLastName(), addressBookData.getAddress(), addressBookData.getCity(),
                            addressBookData.getState(), addressBookData.getPhoneNum(), addressBookData.getEmail(), String.valueOf(addressBookData.getJoining_date()));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                contactAdditionStatus.put(addressBookData.hashCode(), true);
                System.out.println("Employee added : " + Thread.currentThread().getName());
            };
            Thread thread = new Thread(task, addressBookData.getFirstname());
            thread.start();
        });
        while ( contactAdditionStatus.containsValue(false)) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(this.addressBookList);
    }

}
