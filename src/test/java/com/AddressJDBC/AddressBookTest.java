package com.AddressJDBC;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        String result = addressBook.countByCity("Mumbai");
        Assertions.assertEquals("3", result);
    }

    @Test
    public void count_Contacts_in_a_State() throws SQLException {
        AddressBook addressBook = new AddressBook();
        String result = addressBook.countByState("Maharashtra");
        Assertions.assertEquals("4", result);
    }

}