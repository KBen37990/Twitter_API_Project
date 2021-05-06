package Twitter_Page_Objects;

import ReusableLibrary.Abstract_Class;

public class Twitter_BaseClass extends Abstract_Class {

    public static Twitter_HomePage_Object Twitter_HomePage(){
        Twitter_HomePage_Object Twitter_HomePage = new Twitter_HomePage_Object(driver);
        return  Twitter_HomePage;
    }//end of method

    public static Twitter_LoginPage_Object Twitter_LoginPage(){
        Twitter_LoginPage_Object Twitter_LoginPage = new Twitter_LoginPage_Object(driver);
        return  Twitter_LoginPage;
    }//end of method

    public static Twitter_ExplorePage_Object Twitter_ExplorePage(){
        Twitter_ExplorePage_Object Twitter_ExplorePage = new Twitter_ExplorePage_Object(driver);
        return  Twitter_ExplorePage;
    }//end of method


}//end of java class