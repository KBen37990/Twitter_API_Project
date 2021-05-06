package Twitter_Page_Objects;

import ReusableLibrary.Abstract_Class;
import ReusableLibrary.Reusable_Actions_PageObjects;
import com.relevantcodes.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Twitter_ExplorePage_Object extends Abstract_Class {

        ExtentTest logger;

        public Twitter_ExplorePage_Object(WebDriver driver) {
            PageFactory.initElements(driver, this);
            this.logger = Abstract_Class.logger;
        }//end of constructor class

        //define all the WebElement on page
        @FindBy(xpath = "//a[@aria-label='Tweet']")
        WebElement tweet;
        @FindBy(xpath = "//div[@role='textbox']")
        WebElement message;
        @FindBy(xpath = "//div[@data-testid='tweetButton']")
        WebElement tweetButton;
        @FindBy(xpath = "//div[@aria-label='Account menu']")
        WebElement options;
        @FindBy(xpath = "//a[@data-testid='AccountSwitcher_Logout_Button']")
        WebElement signOut;
        @FindBy(xpath = "//div[@data-testid='confirmationSheetConfirm']")
        WebElement ConfirmSignOut;



        //create a method for click
        public void tweet() {
            Reusable_Actions_PageObjects.clickOnElement(driver, tweet, logger, "tweet.");
        }//end of click method


        //create a method for sendkeys
        public void message(String userValue) {
            Reusable_Actions_PageObjects.sendKeysMethod(driver, message, userValue, logger, "message to be sent.");
        }//end of sendkeys method


        //create a method for click
        public void tweetButton() {
            Reusable_Actions_PageObjects.clickOnElement(driver, tweetButton, logger, "tweet.");
        }//end of click method


        //create a method for click
        public void option() {
            Reusable_Actions_PageObjects.clickOnElement(driver, options, logger, "user options.");
        }//end of click method


        //create a method for click
        public void signOut() {
            Reusable_Actions_PageObjects.clickOnElement(driver, signOut, logger, "log out.");
        }//end of click method

        //Beginning of click on confirm sign out method
        public void ConfirmSignOut(){
            Reusable_Actions_PageObjects.clickOnElement(driver, ConfirmSignOut, logger, "confirm log out");
        }

    }//end of java class
