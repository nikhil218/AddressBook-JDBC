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
    public void givenNewDataShouldBePrintedOnConsole() throws SQLException {
        AddressBook addressBook = new AddressBook();
        addressBook.updateViaStatementData(2, "Ojha");
    }

}