package com.AddressJDBC;

import java.security.PrivateKey;
import java.sql.Date;
import java.time.LocalDate;

public class AddressBookData {
    private int id;
    private String Firstname;
    private String LastName;
    private String Address;
    private String City;
    private String State;
    private String PhoneNum;
    private String Email;
    private Date Joining_date;

    public AddressBookData(int id, String firstname, String lastName, String address, String city, String state, String phoneNum, String email, Date Joining_date) {
        this.id = id;
        this.Firstname = firstname;
        this.LastName = lastName;
        this.Address = address;
        this.City = city;
        this.State = state;
        this.PhoneNum = phoneNum;
        this.Email = email;
        this.Joining_date = Joining_date;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        this.Firstname = firstname;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        this.LastName = lastName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        this.City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        this.State = state;
    }

    public String getPhoneNum() {
        return PhoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.PhoneNum = phoneNum;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    @Override
    public String toString() {
        return "AddressBookData{" +
                "id='" + id + '\'' +
                ", Firstname='" + Firstname + '\'' +
                ", LastName='" + LastName + '\'' +
                ", Address='" + Address + '\'' +
                ", City='" + City + '\'' +
                ", State='" + State + '\'' +
                ", PhoneNum='" + PhoneNum + '\'' +
                ", Email='" + Email + '\'' +
                ", Start_date='" + Joining_date + '\'' +
                '}';
    }
}
