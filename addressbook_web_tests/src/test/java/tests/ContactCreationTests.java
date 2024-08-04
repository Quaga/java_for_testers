package tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.CommonFunctions;
import generator.Generator;
import model.ContactData;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase{

    public static List<ContactData> contactProvider() throws IOException {
        var contacts = new ArrayList<ContactData>();
        var contactJson = Files.readString(Paths.get("contacts.json"));
        ObjectMapper contactMapper = new ObjectMapper();
        var myContactValue = contactMapper.readValue(contactJson, new TypeReference<List<ContactData>>() {});
        contacts.addAll(myContactValue);
        return contacts;
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
        app.contacts().createContact(new ContactData().withMobile("somemobile"));
    }
    
    @ParameterizedTest
    @MethodSource("contactProvider")
    public void canCreateContact(ContactData contact) {
        var oldContacts = app.jdbc().getContactList();
        app.contacts().createContact(contact);
        var newContacts = app.jdbc().getContactList();
        Comparator<ContactData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };
        newContacts.sort(compareById);
        int lastNdx = newContacts.size() - 1;
        var lastId = newContacts.get(lastNdx).id();
        var lastPhoto = newContacts.get(lastNdx).photo();
        var expectedList = new ArrayList<>(oldContacts);
        expectedList.add(contact.withId(lastId).withPhoto(lastPhoto));
        expectedList.sort(compareById);
        Assertions.assertEquals(newContacts, expectedList);
    }

    @Test
    void canCreateContactInGroup() {
        var contact = new ContactData().withRandomData(2, app.properties().getProperty("file.photoDir"));
        if (app.hbm().getGroupsCount() == 0) {
            app.hbm().createGroup(new GroupData().withRandomData(2));
        }
        var group = app.hbm().getGroupList().get(0);
        var oldRelated = app.hbm().getContactsInGroup(group);
        app.contacts().createContact(contact, group);
        var newRelated = app.hbm().getContactsInGroup(group);
        Assertions.assertEquals(oldRelated.size() + 1, newRelated.size());
    }

}
