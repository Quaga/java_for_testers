package manager;

import org.openqa.selenium.By;

public class LoginHelper extends HelperBase{

    public LoginHelper(ApplicationManager manager){
        super(manager);
    }

    public void login(String user, String password) {
        type(By.name("user"), user);
        type(By.name("pass"), password);
        click(By.xpath("//input[@value=\'Login\']"));
    }

    public void close() {
        click(By.linkText("Logout"));
        quit();
    }
}
