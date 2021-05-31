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

}