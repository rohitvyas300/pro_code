

import java.io.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumHandler
{
	//public static IOSDriver driver;	 
	//public static WebDriver driver;
	public static WebDriver Selenium;
	public static String CurrentDirectory= System.getProperty("user.dir");
	//System.out.println("CurrentDirectory"+ CurrentDirectory);
	//Rohit Vyas
	public static boolean isProcessRunning(String serviceName) throws Exception 
	{
		Process p = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe");
		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null) {
			if (line.contains(serviceName)) {
				return true;
			}
		}    
		return false;
	}
	
	
	/// <summary>
	/// Author            :  Rohit Vyas
	/// Description       : Closing Selenium Driver
	/// </summary>
	public static void CloseSeleniumDriver() throws Exception
    {	
		try
        {	
        	//PropertiesAndConstants.Selenium.close();
        	
           Selenium.quit();
            
            System.out.println("Close Webdriver Process - Completed");           
        }
        catch (Exception exc) 
        { 
        	System.out.println(exc+"Errorrrr");
        	System.out.println("driver Close Exception " + exc.getMessage() ); 
        }
        Selenium = null;
        	
       
    }
	
	 
	/// <summary>
	/// Author            :  Rohit Vyas
	/// Description       : Switching From one Browser To Other
	/// </summary>
	 public static void SwitchDriver() throws Exception
	    {  	       	        
	        //if (driver == null)
	        { 
	            //Firefox, InternetExplorer, Chrome
	        	String browser = "CHROME";
	            switch(browser)
	            {	                
	                case "CHROME":
                    {
                    	//File file = new File(PropertiesAndConstants.CurrentDirectory + "/macbook/Documents/iprogrammerAutomation/Drivers/chromedriver");                    	
                    	System.setProperty("webdriver.chrome.driver",CurrentDirectory + "/jars/chromedriver");
                    	//System.setProperty("webdriver.chrome.driver",PropertiesAndConstants.TestData.getProperty("chrome_driver"));
            	        //ChromeOptions options = new ChromeOptions();
            	        //options.addArguments("--test-type");
            	        WebDriver driver = new ChromeDriver();                                       
                        driver.manage().window().maximize();                                                                                                 
                        Selenium = driver;                        
                    }
	                	                	
	            }
	        }
	       
	        
	    }
	
	
}//End of class Selenium Handler

