package tests;

import common.CommonFunctions;
import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class ContactModifyTests extends TestBase{

    @Test
    public void canModifyContact() {
        if (app.hbm().getContactsCount() == 0)
        {
            app.hbm().createContact(new ContactData("", "last", "first", "address", "email", "mobile",
                    app.properties().getProperty("file.photoDir") + "/avatar.png"));
        }
        var oldContacts = app.hbm().getContactList();
        var rnd = new Random();
        var index = rnd.nextInt(oldContacts.size());
        var testData = new ContactData()
                .withFirstName(String.format("modified firstname %s", CommonFunctions.randomString(5)))
                .withLastName(String.format("modified lastname %s", CommonFunctions.randomString(5)));
        app.contacts().modifyContact(oldContacts.get(index), testData);
        var newContacts = app.hbm().getContactList();
        var expectedList = new ArrayList<>(oldContacts);
        expectedList.set(index, testData.withId(oldContacts.get(index).id()));
        expectedList.set(index, testData
                .withId(oldContacts.get(index).id())
                .withAddress(oldContacts.get(index).address())
                .withMobile(oldContacts.get(index).mobile())
                .withEmail(oldContacts.get(index).email())
                .withPhoto(oldContacts.get(index).photo())
        );
        Comparator<ContactData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };
        newContacts.sort(compareById);
        expectedList.sort(compareById);
        Assertions.assertEquals(newContacts, expectedList);
    }

}
