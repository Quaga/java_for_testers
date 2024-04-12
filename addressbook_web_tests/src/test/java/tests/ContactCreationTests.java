package tests;

import model.ContactData;
import org.junit.jupiter.api.Test;

public class ContactCreationTests extends TestBase{

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

}
