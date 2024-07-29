package manager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.nio.file.Paths;

public class HelperBase {

    protected final ApplicationManager manager;

    public HelperBase(ApplicationManager manager) {
        this.manager = manager;
    }

    protected void click(By locator) {
        manager.driver.findElement(locator).click();
    }

    protected void click(By locator, By list_locator) {
        WebElement list = manager.driver.findElement(locator);
        list.click();
        list.findElement(list_locator).click();
    }

    protected void type(By locator, String text) {
        click(locator);
        manager.driver.findElement(locator).clear();
        manager.driver.findElement(locator).sendKeys(text);
    }

    protected void attach(By locator, String file) {
        manager.driver.findElement(locator).sendKeys(Paths.get(file).toAbsolutePath().toString());
    }

    protected void quit() {
        manager.driver.quit();
    }
}
