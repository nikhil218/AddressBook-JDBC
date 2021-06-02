package com.AddressJDBC;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

class AddressBookTest {

    @Test
    public void givenSelectStatement_shouldReturnList() throws SQLException {
        AddressBook addressBook = new AddressBook();
        List<AddressBookData> addressBookDataList = addressBook.readData();
        Assertions.assertEquals(5, addressBookDataList.size());
    }

    @Test
    public void givenNewDataShouldBeUpdatedInDatabase() throws SQLException {
        AddressBook addressBook = new AddressBook();
        int result = addressBook.updateData(1, "Mehra");
        Assertions.assertEquals(1, result);
    }

    @Test
    void givenDateRange_WhenRetrieved_ShouldMatchAddressBook_Count() throws SQLException {
        AddressBook addressBook = new AddressBook();
        List<AddressBookData> addressBookDataList = addressBook.return_Values_between_Particular_DateRange("2016-01-01", "2018-01-01");
        Assertions.assertEquals(3, addressBookDataList.size());
    }

    @Test
    public void count_Contacts_in_a_City() throws SQLException {
        AddressBook addressBook = new AddressBook();
        String result = addressBook.countByCity("Kalyan");
        Assertions.assertEquals("3", result);
    }

    @Test
    public void count_Contacts_in_a_State() throws SQLException {
        AddressBook addressBook = new AddressBook();
        String result = addressBook.countByState("Maha");
        Assertions.assertEquals("5", result);
    }

    @Test
    public void CheckIfNewContactIsInsertedIntoDatabase() throws SQLException {
        AddressBook addressBook = new AddressBook();
        addressBook.addANewRowInDB("Aditya", "Annaldasula", "11E, XYZ colony", "Worli", "Maha", "543678", "Adi@gmail.com", "2019-06-01");
        List<AddressBookData> addressBookDataList = addressBook.readData();
        Assertions.assertEquals(6, addressBookDataList.size());
    }

    @Test
    public void insert_into_addressBook_using_Threads() throws SQLException {
        AddressBook addressBook = new AddressBook();
        List<AddressBookData> addressBookList = new ArrayList<>();
        addressBookList.add(new AddressBookData(9, "nihal","reddy", "1007 abc", "nagpur", "Maha",  "7351857301", "nihal@wayne.com", "2018-02-01"));
        addressBookList.add(new AddressBookData(10, "saurabh","shinde", "1008 abc", "Brooklyn", "New York",  "6781367092", "saurabh@avenger.com", "2018-10-01"));
        Instant start = Instant.now();
        addressBook.addContactsToAddressBookWithThreads(addressBookList);
        Instant end = Instant.now();
        System.out.println("Duration of non thread process is : " + Duration.between(start, end));
        List<AddressBookData> bookData = addressBook.readData();
        Assertions.assertEquals(7, bookData.size());
    }

}