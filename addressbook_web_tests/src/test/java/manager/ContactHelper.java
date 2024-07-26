package manager;

import model.ContactData;
import model.GroupData;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase{

    public void createContact(ContactData contact) {
        openContactsCreationPage();
        fillContactForm(contact);
        submitContact();
        openContactsPage();
    }

    public void createContact(ContactData contact, GroupData group) {
        openContactsCreationPage();
        fillContactForm(contact);
        selectGroup(group);
        submitContact();
        openContactsPage();
    }

    private void selectGroup(GroupData group) {
        new Select(manager.driver.findElement(By.name("new_group"))).selectByValue(group.id());
    }

    private void selectNewGroup(GroupData group) {
        click(By.name("to_group"), By.cssSelector(String.format("option[value='%s']", group.id())));
    }

    public void removeContact(ContactData contact) {
        openContactsPage();
        selectContact(contact);
        removeSelectedContact();
        openContactsPage();
    }

    public void modifyContact(ContactData contact, ContactData modifiedContact) {
        openContactsPage();
        selectContact(contact);
        initContactModification(contact);
        fillContactName(modifiedContact);
        updateContact();
        openContactsPage();
    }

    public void modifyContact(ContactData contact, GroupData group) {
        openContactsPage();
        selectContact(contact);
        selectNewGroup(group);
        addContact();
        openContactsPage();
    }

    public ContactHelper(ApplicationManager manager){
        super(manager);
    }

    public void openContactsCreationPage() {
        if (! manager.isElementPresent(By.name("firstname")))
            click(By.linkText("add new"));
    }

    public boolean isContactPresent() {
        openContactsPage();
        return manager.isElementPresent(By.name("selected[]"));
    }

    private void submitContact() {
        click(By.name("submit"));
    }

    private void removeSelectedContact() {
        click(By.xpath("//input[@value=\'Delete\']"));
    }

    private void openContactsPage() {
        click(By.linkText("home"));
    }

    private void addContact() {
        click(By.name("add"));
    }

    private void updateContact() {
        click(By.name("update"));
    }

    private void fillContactForm(ContactData contact) {
        type(By.name("firstname"), contact.firstName());
        type(By.name("lastname"), contact.lastName());
        type(By.name("address"), contact.address());
        type(By.name("email"), contact.email());
        type(By.name("mobile"), contact.mobile());
        if (contact.photo().length() != 0)
            attach(By.name("photo"), contact.photo());
    }

    private void fillContactName(ContactData contact) {
        type(By.name("firstname"), contact.firstName());
        type(By.name("lastname"), contact.lastName());
    }

    private void initContactModification(ContactData contact) {
        click(By.cssSelector(String.format("a[href='edit.php?id=%s']", contact.id())));
    }

    private void selectContact(ContactData contact) {
        click(By.cssSelector(String.format("input[value='%s']", contact.id())));
    }

    public int getCount() {
        openContactsPage();
        return manager.driver.findElements(By.name("selected[]")).size();
    }

    public void removeGroupFromContact(ContactData contact, GroupData group) {
        openContactsPage();
        selectContactByGroup(group);
        selectContact(contact);
        removeContact();
        openContactsPage();
    }

    private void selectContactByGroup(GroupData group) {
        click(By.name("group"), By.cssSelector(String.format("option[value='%s']", group.id())));
    }

    private void removeContact() {
        click(By.name("remove"));
    }

    public List<ContactData> getList() {
        openContactsPage();
        var contacts = new ArrayList<ContactData>();
        var table = manager.driver.findElements(
                By.cssSelector("table[id='maintable']>tbody>tr[name='entry']"));
        for (var element : table) {
            var id = element.findElement(By.cssSelector("td.center>input")).getAttribute("value");
            var lastname = element.findElement(By.cssSelector("td:nth-child(2)")).getText();
            var firstname = element.findElement(By.cssSelector("td:nth-child(3)")).getText();
            contacts.add(new ContactData()
                    .withId(id)
                    .withFirstName(firstname)
                    .withLastName(lastname));
        }
        return contacts;
    }
}
