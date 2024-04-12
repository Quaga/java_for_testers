package tests;

import model.ContactData;
import model.GroupData;
import org.junit.jupiter.api.Test;

public class ContactRemovalTests extends TestBase{

    @Test
    public void canRemoveContact() {
        if (!app.contacts().isContactPresent())
        {
            app.contacts().createContact(new ContactData("last", "first", "address", "email", "mobile"));
        }
        app.contacts().removeContact();
    }

}
