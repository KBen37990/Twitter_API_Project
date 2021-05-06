package Twitter_Page_Objects;

import ReusableLibrary.Abstract_Class;
import ReusableLibrary.Reusable_Actions_PageObjects;
import com.relevantcodes.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Twitter_LoginPage_Object extends Abstract_Class {

    ExtentTest logger;

    public Twitter_LoginPage_Object(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.logger = Abstract_Class.logger;
    }//end of constructor class

    //define all the WebElement on page
    @FindBy(xpath = "//input[@name='session[username_or_email]']")
    WebElement userName;
    @FindBy(xpath = "//input[@name='session[password]']")
    WebElement userPassword;
    @FindBy(xpath = "//div[@data-testid='LoginForm_Login_Button']")
    WebElement loginButton;


    //create a method for sendkeys
    public void userName(String userValue) {
        Reusable_Actions_PageObjects.sendKeysMethod(driver, userName, userValue, logger, "username");
    }//end of sendkeys method


    //create a method for sendkeys
    public void userPassword(String userValue) {
        Reusable_Actions_PageObjects.sendKeysMethod(driver, userPassword, userValue, logger, "password");
    }//end of sendkeys method


    //create a method for click
    public void loginButton() {
        Reusable_Actions_PageObjects.clickOnElement(driver, loginButton, logger, "login button");
    }//end of click method



}
