package tests;

import common.CommonFunctions;
import model.ContactData;
import model.GroupData;
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
            app.hbm().createContact(new ContactData().withRandomData(2, app.properties().getProperty("file.photoDir")));
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

    @Test
    void canModifyContactInGroup() {
        if (app.hbm().getContactsCount() == 0) {
            app.hbm().createContact(new ContactData().withRandomData(2,
                    app.properties().getProperty("file.photoDir")));
        }
        if (app.hbm().getGroupsCount() == 0) {
            app.hbm().createGroup(new GroupData().withRandomData(2));
        }
        var contact = app.hbm().getContactList().get(0);
        var group = app.hbm().getGroupList().get(0);
        if (app.hbm().getContactsInGroup(group).contains(contact)) {
            app.hbm().removeGroupFromContact(contact, group);
        }
        var oldRelated = app.hbm().getContactsInGroup(group);
        app.contacts().modifyContact(contact, group);
        var newRelated = app.hbm().getContactsInGroup(group);
        Assertions.assertEquals(oldRelated.size() + 1, newRelated.size());
    }

    @Test
    void canModifyContactOutGroup() {
        if (app.hbm().getContactsCount() == 0) {
            app.hbm().createContact(new ContactData().withRandomData(2,
                    app.properties().getProperty("file.photoDir")));
        }
        if (app.hbm().getGroupsCount() == 0) {
            app.hbm().createGroup(new GroupData().withRandomData(2));
        }
        if (app.hbm().getGroupsInContactCount() == 0) {
            app.hbm().addGroupToContact(app.hbm().getContactList().get(0),
                    app.hbm().getGroupList().get(0));
        }
        var group = app.hbm().getGroupsInContact().get(0);
        var oldRelated = app.hbm().getContactsInGroup(group);
        var contact = oldRelated.get(0);
        app.contacts().removeGroupFromContact(contact, group);
        var newRelated = app.hbm().getContactsInGroup(group);
        Assertions.assertEquals(oldRelated.size() - 1, newRelated.size());
    }

}
