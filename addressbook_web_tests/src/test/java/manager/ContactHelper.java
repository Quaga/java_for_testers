package manager;

import model.ContactData;
import org.openqa.selenium.By;

public class ContactHelper extends HelperBase{

    public void createContact(ContactData contact) {
        openContactsCreationPage();
        fillContactForm(contact);
        submitContactCreation();
        openContactsPage();
    }

    public void removeContact() {
        openContactsPage();
        selectContact();
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

    private void selectContact() {
        click(By.name("selected[]"));
    }

    public int getCount() {
        openContactsPage();
        return manager.driver.findElements(By.name("selected[]")).size();
    }
}
