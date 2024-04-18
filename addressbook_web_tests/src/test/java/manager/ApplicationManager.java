package manager;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Properties;

public class ApplicationManager {
    protected WebDriver driver;
    private LoginHelper session;
    private GroupHelper groups;
    private ContactHelper contacts;
    private Properties properties;

    public void init(String browser, Properties properties) {
        if (driver == null) {
            if ("firefox".equals((browser))){
                driver = new FirefoxDriver();
            } else if ("chrome".equals(browser)) {
                driver = new ChromeDriver();
            } else {
                throw new IllegalArgumentException(String.format("Unknown browser %s",browser));
            }

            Runtime.getRuntime().addShutdownHook((new Thread(driver::quit)));
            this.properties = properties;
            driver.get(properties.getProperty("web.baseUrl"));
//            driver.get("http://localhost/addressbook/");
            driver.manage().window().setSize(new Dimension(976, 1040));
            session().login(properties.getProperty("web.username"), properties.getProperty("web.password"));
        }
    }

    public LoginHelper session(){
        if (session == null){
            session = new LoginHelper(this);
        }
        return session;
    }

    public GroupHelper groups(){
        if (groups == null){
            groups = new GroupHelper(this);
        }
        return groups;
    }

    public ContactHelper contacts(){
        if (contacts == null){
            contacts = new ContactHelper(this);
        }
        return contacts;
    }

    public Properties properties() {
        return properties;
    }

    protected boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException exception){
            return false;
        }
    }

}
