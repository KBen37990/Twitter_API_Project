package Twitter_Tests;

import ReusableLibrary.Abstract_Class;
import Twitter_Page_Objects.Twitter_BaseClass;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.*;
import java.util.Objects;

import static io.restassured.RestAssured.given;

public class Twitter_Test_1 extends Abstract_Class{

    String consumerKey = "skLpskjI4lRU9VUZ5LkIRpz5A";
    String consumerSecret = "LR6RfrIh9zfDkPyrtRgf4fZujPykwfKkCq3fp4hwxcbay0Isqq";
    String accessToken = "1388536922360922115-UzuSFcTCrduJyXiCKLeY7aLsnNa9fS";
    String tokenSecret = "VhFP5t6wr2IWgrR6Vtf0tUmUdCObdU3E6EwKnndITTsbc";


    String tweetID;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://api.twitter.com/1.1/statuses/";
    }//end of set up


    @Test(priority = 1)
    public void Twitter_Action_Item_1() throws InterruptedException, AWTException {

        //Post tweet through UI
        driver.navigate().to("https://twitter.com/");
        Thread.sleep(3000);


        Twitter_BaseClass.Twitter_HomePage().signInButton();
        Thread.sleep(2000);

        Twitter_BaseClass.Twitter_LoginPage().userName("Ben53797885");
        Twitter_BaseClass.Twitter_LoginPage().userPassword("Bleron1998");
        Twitter_BaseClass.Twitter_LoginPage().loginButton();
        Thread.sleep(2000);

        Twitter_BaseClass.Twitter_ExplorePage().tweet();
        Thread.sleep(1000);
        Twitter_BaseClass.Twitter_ExplorePage().message("This is a UI automation test.");
        Twitter_BaseClass.Twitter_ExplorePage().tweetButton();
        Thread.sleep(2000);



        //Get recent tweet through API
        Response Resp=
                given().auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret).
                        queryParam("screen_name", "@Ben53797885")
                        .when()
                        .get("user_timeline.json")
                        .then()
                        .extract()
                        .response();

        String getTweet=Resp.asString();
        JsonPath js=new JsonPath(getTweet);
        String tweetText= js.get("text[0]").toString();
        tweetID=(js.get("id[0]")).toString();


        if (tweetText.equals("This is a UI automation test.")) {
            System.out.println("The following tweet has been created: "+ tweetText);
            System.out.println("The ID of recently created tweet is: "+ tweetID);
            logger.log(LogStatus.PASS, "The following tweet has been created: " + tweetText + "The ID of the tweet is: "+tweetID);
        } else {
            System.out.println("The following tweet has not been created: " + tweetText);
            System.out.println("ID is not available since tweet has not been posted.");
            logger.log(LogStatus.INFO, "The following tweet has not been created: " + tweetText + " No ID available.");
        }


        //Delete recent tweet through API
        given().
                auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret).
                queryParam("id", tweetID)
                .when()
                .post("destroy.json")
                .then();
        System.out.println("Recent twitter message with id '" + tweetID + "' has been deleted");
        logger.log(LogStatus.PASS,"Recent twitter message with id '" + tweetID + "' has been deleted");




        //Refresh page and verify tweet is deleted
        driver.navigate().refresh();
        Thread.sleep(2000);

        //Verify tweet is not present with API

        Response Resp1=
        given().auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret).
                queryParam("screen_name", "@Ben53797885")
                .when()
                .get("user_timeline.json")
                .then()
                .extract()
                .response();

        String getNewTweet=Resp1.asString();
        JsonPath js1=new JsonPath(getNewTweet);
        String NewTweet= js1.get("text[0]");


        //String NewTweetID=(js1.get("id[0]"));


        Objects.toString("NewTweet", NewTweet);

        //Objects.toString("NewTweet", NewTweetID);

        if (NewTweet == null){
            System.out.println("The recently posted tweet: " + tweetText + " with ID " + tweetID + " is no longer present.");
            logger.log(LogStatus.PASS, "The recently posted tweet: " + tweetText + " with ID " + tweetID + " is no longer present.");
        }else {
            System.out.println("The recently posted tweet: " + tweetText + " is still present.");
            logger.log(LogStatus.INFO, "The recently posted tweet " + tweetText + " is still present.");

        }


        Twitter_BaseClass.Twitter_ExplorePage().option();
        Twitter_BaseClass.Twitter_ExplorePage().signOut();
        Twitter_BaseClass.Twitter_ExplorePage().ConfirmSignOut();


    }//end of test
}//end of java class
