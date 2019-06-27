import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class keyactions{
	
	public void navigateToUrl(String url) {				
		try {
				SeleniumHandler.Selenium.get(url);
				Thread.sleep(3000);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
	}
	
	
	public void typeText(String xpath, String text) {
		WebElement element = findElement(xpath);
		element.sendKeys(text);
		
	}
	
	
	public WebElement findElement(String xpath) {
		WebElement element = null;
		try {		
		element = SeleniumHandler.Selenium.findElement(By.xpath(xpath));
		for (int i = 0; i <= 10; i++)
		{ 	
			
			if (!element.isDisplayed())
				
					Thread.sleep(500);
					else break;							
		}
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Error unable to find element...");
			//e.printStackTrace();
		}
		return element;			
	}
	
	public void click(String xpath) {
		WebElement element = findElement(xpath);
		element.click();		
	}
	
	
	public String getattribute(String xpath)throws Exception
	{								
			//System.out.println("inside getAttribute.."+ xpath);
			WebElement element = findElement(xpath);							
			{				
				String srcAttribute = element.getAttribute("href");
				Thread.sleep(1000);
				//System.out.println(srcAttribute+"    srcAttribute is ");
				if (srcAttribute != null)
				{
					//System.out.println("inside if attribute not null ...");
					//System.out.println(srcAttribute+ "   = href is ");															
					//SeleniumHandler.Selenium.navigate().to(srcAttribute);						
					return  srcAttribute;
					
					//SeleniumHandler.Selenium.navigate().back();
					//Thread.sleep(3000);					
				}
			}
			return null;									
	}//EOD method VerifyIsElementContainsText
	
	public String getElementText(String xpath)throws Exception
	{								
		//String driverTitle = SeleniumHandler.Selenium.getTitle();
		WebElement element = findElement(xpath);				
       	WebDriverWait wait = new WebDriverWait(SeleniumHandler.Selenium, 15); 
	    element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));			
		String elementattrtext = element.getText();							
		if(elementattrtext != null)
		{
			//System.out.println("Element:  "+elementattrtext);
			return elementattrtext;
		}
		else
		{
			System.out.println("Element : Null");
			return null;
		}		
	}//EOD Method
	
	
	public int getElementCount(String xpath) {		
		//WebElement element = findElement(xpath);
		List<WebElement> totalXpath = SeleniumHandler.Selenium.findElements(By.xpath(xpath));
		int xpathCount = totalXpath.size();
		System.out.println("Total xpath: " + xpathCount);
		return xpathCount;
	}//Eod Method
	
	
	public WebElement verifyElementPresence(String xpath) {
		WebElement element = null;
		try {	
				Thread.sleep(1000);
				element = SeleniumHandler.Selenium.findElement(By.xpath(xpath));
		       	WebDriverWait wait = new WebDriverWait(SeleniumHandler.Selenium, 15); 
			    element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));	
				if (element.isDisplayed() && element.isEnabled())				
				{
					return element;
				}
				else {
					return null;
				}
			}
			catch (Exception e) {
				System.out.println("element not present exception. ");
			}
		return element;	
	}

}//EOD Class
