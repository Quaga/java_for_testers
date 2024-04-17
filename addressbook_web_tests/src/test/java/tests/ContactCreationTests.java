package tests;

import model.ContactData;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ContactCreationTests extends TestBase{

    public static List<ContactData> contactProvider() {
        var result = new ArrayList<ContactData>();
        for (int i = 0; i < 3; i++) {
            result.add(new ContactData(
                    randomString(i * 5),
                    randomString(i * 5),
                    randomString(i * 10),
                    randomString(i * 4),
                    randomString(i * 3)));
        }
        return result;
    }

    @Test
    public void canCreateContact() {
        app.contacts().createContact(new ContactData("last", "first", "address", "email", "mobile"));
    }

    @Test
    public void canCreateContactWithEmptyAttrs() {
        app.contacts().createContact(new ContactData());
    }

    @Test
    public void canCreateGroupWithLastNameOnly() {
        app.contacts().createContact(new ContactData().withLastName("some last name"));
    }

    @Test
    public void canCreateGroupWithFirstNameOnly() {
        app.contacts().createContact(new ContactData().withFirstName("some first name"));
    }

    @Test
    public void canCreateGroupWithAddressOnly() {
        app.contacts().createContact(new ContactData().withAddress("some address"));
    }

    @Test
    public void canCreateGroupWithEmailOnly() {
        app.contacts().createContact(new ContactData().withEmail("some email"));
    }

    @Test
    public void canCreateGroupWithMobileOnly() {
        app.contacts().createContact(new ContactData().withMobile("some mobile"));
    }
    
    @ParameterizedTest
    @MethodSource("contactProvider")
    public void canCreateContact(ContactData contact) {
        var contactsCount = app.contacts().getCount();
        app.contacts().createContact(contact);
        int newContactsCount = app.contacts().getCount();
        Assertions.assertEquals(contactsCount + 1, newContactsCount);
    }

}
