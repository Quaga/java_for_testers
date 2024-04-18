package manager;

import model.ContactData;
import model.GroupData;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase{

    public void createContact(ContactData contact) {
        openContactsCreationPage();
        fillContactForm(contact);
        submitContactCreation();
        openContactsPage();
    }

    public void removeContact(ContactData contact) {
        openContactsPage();
        selectContact(contact);
        removeSelectedContact();
        openContactsPage();
    }

    public void modifyContact(ContactData modifiedContact) {
        openContactsPage();
        initContactModification();
        fillContactForm(modifiedContact);
        submitContactModification();
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

    private void submitContactCreation() {
        click(By.name("submit"));
    }

    private void removeSelectedContact() {
        click(By.xpath("//input[@value=\'Delete\']"));
    }

    private void openContactsPage() {
        click(By.linkText("home"));
    }

    private void submitContactModification() {
        click(By.name("update"));
    }

    private void fillContactForm(ContactData contact) {
        type(By.name("firstname"), contact.firstName());
        type(By.name("lastname"), contact.lastName());
        type(By.name("address"), contact.address());
        type(By.name("email"), contact.email());
        type(By.name("mobile"), contact.mobile());
    }

    private void initContactModification() {
        click(By.xpath("//img[@alt=\'Edit\']"));
    }

    private void selectContact(ContactData contact) {
        click(By.cssSelector(String.format("input[value='%s']", contact.id())));
    }

    public int getCount() {
        openContactsPage();
        return manager.driver.findElements(By.name("selected[]")).size();
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
