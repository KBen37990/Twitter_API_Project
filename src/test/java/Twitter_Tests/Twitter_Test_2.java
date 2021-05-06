package Twitter_Tests;

import ReusableLibrary.Abstract_Class;
import Twitter_Page_Objects.Twitter_BaseClass;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.*;

import static io.restassured.RestAssured.given;

public class Twitter_Test_2 extends Abstract_Class{

    String consumerKey = "skLpskjI4lRU9VUZ5LkIRpz5A";
    String consumerSecret = "LR6RfrIh9zfDkPyrtRgf4fZujPykwfKkCq3fp4hwxcbay0Isqq";
    String accessToken = "1388536922360922115-UzuSFcTCrduJyXiCKLeY7aLsnNa9fS";
    String tokenSecret = "VhFP5t6wr2IWgrR6Vtf0tUmUdCObdU3E6EwKnndITTsbc";

    String tweetID;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://api.twitter.com/1.1/statuses/";
    }//end of set up


    @Test()
    public void TwentyTweets() throws InterruptedException {
        String postTweet = null;
        for (int i = 0; i < 5; i++) {
            postTweet = "Auto tweet post #" + (i + 1);
            Response Resp = given()
                    .auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret)
                    .queryParam("status", postTweet)
                    .when().post("update.json")
                    .then().extract().response();


            if (Resp.statusCode() == 200){
                System.out.println("Status code is 200 and successful. Tweet number: " +i+ " has been generated.");
                logger.log(LogStatus.PASS, "Status code is 200 and successful. Tweet number: " +i+ " has been generated.");
            } else {
                System.out.println("Status code is not successful: " + Resp.statusCode());
                logger.log(LogStatus.FAIL, "Status code is not successful: " + Resp.statusCode());
            }
        }//end of for loop

    }//End of twenty tweets test

    @Test(dependsOnMethods = "TwentyTweets")
    public void VerifyTweets() throws InterruptedException {


        driver.navigate().to("https://twitter.com/");
        Thread.sleep(30000);


        Twitter_BaseClass.Twitter_HomePage().signInButton();
        Thread.sleep(2000);

        Twitter_BaseClass.Twitter_LoginPage().userName("Ben53797885");
        Twitter_BaseClass.Twitter_LoginPage().userPassword("Bleron1998");
        Twitter_BaseClass.Twitter_LoginPage().loginButton();
        Thread.sleep(2000);

        driver.navigate().refresh();
        Thread.sleep(2000);

        Response Resp =
                given().auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret)
                        .queryParam("screen_name", "@Ben53797885")
                        .when()
                        .get("user_timeline.json")
                        .then()
                        .extract()
                        .response();

        String getTweet = Resp.asString();
        JsonPath js = new JsonPath(getTweet);
        String FirstID = (js.get("id[0]")).toString();
        String SecondID = (js.get("id[1]")).toString();
        String FirstTweet = js.get("text[0]").toString();
        String SecondTweet = js.get("text[1]").toString();

        System.out.println("The first most recent tweet is: " + FirstTweet + ". With the following ID: " + FirstID + ".");
        logger.log(LogStatus.INFO, "The first most recent tweet is: " + FirstTweet + ". With the following ID: " + FirstID + ".");
        System.out.println("The second most recent tweet is: " + SecondTweet + ". With the following ID: " + SecondID + ".");
        logger.log(LogStatus.INFO, "The second most recent tweet is: " + SecondTweet + ". With the following ID: " + SecondID + ".");

    }

    @Test(dependsOnMethods = "VerifyTweets")
    public void DeleteTweets() throws InterruptedException {

        //Delete all those 20 tweets using for loop as separate @Test via API
        for (int i = 0; i < 5; i++) {

            Response Resp =
                    given().auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret)
                            .queryParam("screen_name", "@Ben53797885")
                            .when()
                            .get("user_timeline.json")
                            .then()
                            .extract()
                            .response();

            String getTweet = Resp.asString();
            JsonPath js = new JsonPath(getTweet);
            tweetID = (js.get("id[0]")).toString();
            // delete recent tweet through API

            given().
                    auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret)
                    .queryParam("id", tweetID)
                    .when()
                    .post("destroy.json")
                    .then()
                    .extract()
                    .response();;

            if (Resp.statusCode() == 200){
                System.out.println("Status code is 200 and successful. Tweet number: " +i+ " has been deleted.");
                logger.log(LogStatus.PASS, "Status code is 200 and successful. Tweet number: " +i+ " has been deleted.");
            } else {
                System.out.println("Status code is not successful: " + Resp.statusCode());
                logger.log(LogStatus.FAIL, "Status code is not successful: " + Resp.statusCode());
            }


        }

        //Verify Tweet has been deleted and sign off
        Response Resp2 = given()
                .auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret)
                .queryParam("screen_name", "@sharuuukh")
                .when()
                .get("user_timeline.json")
                .then()
                .extract()
                .response();

        String getTweet2 = Resp2.asString();
        JsonPath js2 = new JsonPath(getTweet2);
        String Tweet2 = js2.get("text[3]").toString();
        String Tweet1 = js2.get("text[4]").toString();

        if (Tweet1.equals("This is auto-generated tweet number: 1")){
            System.out.println("The first tweet has not been successfully deleted.");
            logger.log(LogStatus.FAIL, "The first tweet has not been successfully deleted.");
        } else {
            System.out.println("The first tweet has been successfully deleted from timeline.");
            logger.log(LogStatus.PASS, "The first tweet has been successfully deleted from timeline.");
        }
        if (Tweet2.equals("This is auto-generated tweet number: 2")){
            System.out.println("The second tweet has not been successfully deleted.");
            logger.log(LogStatus.FAIL, "The second tweet has not been successfully deleted.");
        } else {
            System.out.println("The second tweet has been successfully deleted from timeline.");
            logger.log(LogStatus.PASS, "The second tweet has been successfully deleted from timeline.");
        }

        driver.navigate().refresh();
        Thread.sleep(2000);
        Twitter_BaseClass.Twitter_ExplorePage().option();
        Twitter_BaseClass.Twitter_ExplorePage().signOut();
        Twitter_BaseClass.Twitter_ExplorePage().ConfirmSignOut();




    }//End of verify and delete tweets test

}//End of Java Class
