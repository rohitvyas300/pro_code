import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MpBio extends keyactions {
	
	keyactions key = new keyactions();	
	WriteExcel excel = new WriteExcel();
	public static ArrayList<String> wordArray = new ArrayList<>();
	//HashMap<String,String> hashMap = new HashMap<String,String>(); 
	Map<String, Object[]> data = new HashMap<String, Object[]>();	
	//Map<String, Object[]> data = new TreeMap<String, Object[]>();
	public static void main(String[] args) throws Exception {
		 
		MpBio Bio = new MpBio();
		Bio.goToProduct();
		
	}//EOD of Main metod
		
	public void goToProduct() throws Exception {		
		SeleniumHandler.SwitchDriver();
		
		key.navigateToUrl("https://in.mpbio.com/life-sciences/biochemicals/antibiotics");		
		Thread.sleep(2000);
		storeAllHref();
		ExtractData();
		//System.out.println("before Extract Data..");
		//ExtractData();
		
		SeleniumHandler.CloseSeleniumDriver();
	}//Eod Method.
	
	public void storeAllHref() throws Exception {				
		WebElement element  = key.verifyElementPresence("//a[@title='Next']");							
		while(element != null)
		{				
			int count = key.getElementCount("//div[1]/div[2]/div[@class='catalog-list-item__actions-primary']//a[1]");
			//key.getattribute("//section[1]/div[1]/div[2]/div[1]/a");
			for (int i = 1; i <= count; i++) {
				
				String srcAttribute =  key.getattribute("//section["+i+"]/div[1]/div[2]/div[1]/a");
				//System.out.println(srcAttribute+"   srxattri here..");
				wordArray.add(srcAttribute);
			}//Eod FOr Loop
			
			System.out.println(wordArray.size());
			//Click next logic
			try {							
				WebDriverWait wait = new WebDriverWait(SeleniumHandler.Selenium, 15); 
			    element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='Next']")));						
				System.out.println("before click...");
				element.click();
				Thread.sleep(3000);
				element  = key.verifyElementPresence("//a[@title='Next']");
			} catch (Exception e) {				
				System.err.println("in side catch web wait..");
								
			}	
		}//EODWhile loop
		//int number = 0;
		//System.out.println(wordArray);
		//System.out.println(number++);								
	}//EOD of method
	
	
	public void storelHref() throws Exception {				
		WebElement element  = key.verifyElementPresence("//a[@title='Next']");							
		//while(element != null)
		{				
			int count = key.getElementCount("//div[1]/div[2]/div[@class='catalog-list-item__actions-primary']//a[1]");
			//String srcAttribute = key.getattribute("//section[1]/div[1]/div[2]/div[1]/a");
			//wordArray.add(srcAttribute);						
			for (int i = 1; i <= count; i++) {
				
				String srcAttribute =  key.getattribute("//section["+i+"]/div[1]/div[2]/div[1]/a");
				//System.out.println(srcAttribute+"   srxattri here..");
				wordArray.add(srcAttribute);
			}//Eod FOr Loop						
			System.out.println(wordArray.size());
			ExtractData();
//			//Click next logic
//			try {							
//				WebDriverWait wait = new WebDriverWait(SeleniumHandler.Selenium, 15); 
//			    element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='Next']")));						
//				System.out.println("before click...");
//				element.click();
//				Thread.sleep(3000);
//				element  = key.verifyElementPresence("//a[@title='Next']");
//			} catch (Exception e) {				
//				System.err.println("in side catch web wait..");
//								
//			}	
		}//EODWhile loop
		//int number = 0;
		//System.out.println(wordArray);
		//System.out.println(number++);								
	}//EOD of method
	
	public void ExtractData() throws Exception {
		
		String name;		
		String casNumber;
		String sku;
		String price;				
		for (int i=0; i< wordArray.size(); i++){			
			String getData = wordArray.get(i);			
			navigateToUrl(getData);
			//navigateToUrl("https://in.mpbio.com/0210273025-l-proline-cf");
			Thread.sleep(2000);
			
			name = getElementText("//h1[@class='heading heading--second-level product-view__wrapper-title-second']");
			excel.setCellData("Product","Product Name",i+2,name);
			
			sku =  getElementText("//span[@class='product-view__sku']");
			excel.setCellData("Product","Product Sku",i+2,sku);
			
			casNumber = getElementText("//p[contains(text(),'CAS Number:')]");
			excel.setCellData("Product","Product Cas",i+2,casNumber);	
							
			Thread.sleep(1000);
						
			WebElement packingDes = key.verifyElementPresence("//div[@class='select select--native']/select");
			if(packingDes == null)
			{
				price = getElementText("//span[@class='price']");
				excel.setCellData("Product","Product Price",i+2,price);
				//Thread.sleep(2000);	
			}
			
			System.out.println("aftr new logic..   "+packingDes);			
			if(packingDes != null)
			{	
				WebElement element = SeleniumHandler.Selenium.findElement(By.xpath("//div[@class='select select--native']/select"));									
				List<WebElement> lists = element.findElements(By.tagName("option"));
			    for(WebElement elementOption: lists)  
			    {
			        String var2 = elementOption.getText();
			        System.out.println(var2);
			        //element.click();
			        //Thread.sleep(2000);			        			        				    																	
					switch (var2) {
					
					case "1 x 250 KU":	
						//System.out.println("insisde 1 x 1 mg");
						SeleniumHandler.Selenium.findElement(By.xpath("//option[contains(text(),'1 x 250 KU')]")).click();
				        Thread.sleep(500);
						
						sku =  getElementText("//span[@class='product-view__sku']");
						excel.setCellData("Product","250KUSku",i+2,sku);				
						Thread.sleep(500);
						
						price = getElementText("//span[@class='price']");
						excel.setCellData("Product","250KUPrice",i+2,price);
						Thread.sleep(500);					
					break;
					
					case "1 x 1 MU":	
						//System.out.println("insisde 1 x 1 mg");
						SeleniumHandler.Selenium.findElement(By.xpath("//option[contains(text(),'1 x 1 MU')]")).click();
				        Thread.sleep(500);
						
						sku =  getElementText("//span[@class='product-view__sku']");
						excel.setCellData("Product","1MUSku",i+2,sku);				
						Thread.sleep(500);
						
						price = getElementText("//span[@class='price']");
						excel.setCellData("Product","1MUPrice",i+2,price);
						Thread.sleep(500);					
					break;
					
					case "1 x 5 MU":	
						//System.out.println("insisde 1 x 1 mg");
						SeleniumHandler.Selenium.findElement(By.xpath("//option[contains(text(),'1 x 5 MU')]")).click();
				        Thread.sleep(500);
						
						sku =  getElementText("//span[@class='product-view__sku']");
						excel.setCellData("Product","5MUSku",i+2,sku);				
						Thread.sleep(500);
						
						price = getElementText("//span[@class='price']");
						excel.setCellData("Product","5MUPrice",i+2,price);
						Thread.sleep(500);					
					break;
					
					case "1 x 10 MU":	
						//System.out.println("insisde 1 x 1 mg");
						SeleniumHandler.Selenium.findElement(By.xpath("//option[contains(text(),'1 x 10 MU')]")).click();
				        Thread.sleep(500);
						
						sku =  getElementText("//span[@class='product-view__sku']");
						excel.setCellData("Product","10MUSku",i+2,sku);				
						Thread.sleep(500);
						
						price = getElementText("//span[@class='price']");
						excel.setCellData("Product","10MUPrice",i+2,price);
						Thread.sleep(500);					
					break;
					
					case "1 x 25 MU":	
						//System.out.println("insisde 1 x 1 mg");
						SeleniumHandler.Selenium.findElement(By.xpath("//option[contains(text(),'1 x 25 MU')]")).click();
				        Thread.sleep(500);
						
						sku =  getElementText("//span[@class='product-view__sku']");
						excel.setCellData("Product","25MUSku",i+2,sku);				
						Thread.sleep(500);
						
						price = getElementText("//span[@class='price']");
						excel.setCellData("Product","25MUPrice",i+2,price);
						Thread.sleep(500);					
					break;
					
					case "1 x 50 MU":	
						//System.out.println("insisde 1 x 1 mg");
						SeleniumHandler.Selenium.findElement(By.xpath("//option[contains(text(),'1 x 50 MU')]")).click();
				        Thread.sleep(500);
						
						sku =  getElementText("//span[@class='product-view__sku']");
						excel.setCellData("Product","50MUSku",i+2,sku);				
						Thread.sleep(500);
						
						price = getElementText("//span[@class='price']");
						excel.setCellData("Product","50MUPrice",i+2,price);
						Thread.sleep(500);					
					break;
					
					case "1 x 100 MU":	
						//System.out.println("insisde 1 x 1 mg");
						SeleniumHandler.Selenium.findElement(By.xpath("//option[contains(text(),'1 x 100 MU')]")).click();
				        Thread.sleep(500);
						
						sku =  getElementText("//span[@class='product-view__sku']");
						excel.setCellData("Product","100MUSku",i+2,sku);				
						Thread.sleep(500);
						
						price = getElementText("//span[@class='price']");
						excel.setCellData("Product","100MUPrice",i+2,price);
						Thread.sleep(500);					
					break;
					
					case "1 x 100 μg":	
						//System.out.println("insisde 1 x 1 mg");
						SeleniumHandler.Selenium.findElement(By.xpath("//option[contains(text(),'1 x 100 μg')]")).click();
				        Thread.sleep(500);
						
						sku =  getElementText("//span[@class='product-view__sku']");
						excel.setCellData("Product","100μgSku",i+2,sku);				
						Thread.sleep(500);
						
						price = getElementText("//span[@class='price']");
						excel.setCellData("Product","100μgPrice",i+2,price);
						Thread.sleep(500);					
					break;
					
					
					case "1 x 500 μg":	
						//System.out.println("insisde 1 x 1 mg");
						SeleniumHandler.Selenium.findElement(By.xpath("//option[contains(text(),'1 x 500 μg')]")).click();
				        Thread.sleep(500);
						
						sku =  getElementText("//span[@class='product-view__sku']");
						excel.setCellData("Product","500μgSku",i+2,sku);				
						Thread.sleep(500);
						
						price = getElementText("//span[@class='price']");
						excel.setCellData("Product","500μgPrice",i+2,price);
						Thread.sleep(500);					
					break;
					
					
					case "1 x 1 mg":	
						//System.out.println("insisde 1 x 1 mg");
						SeleniumHandler.Selenium.findElement(By.xpath("//option[contains(text(),'1 x 1 mg')]")).click();
				        Thread.sleep(500);
						
						sku =  getElementText("//span[@class='product-view__sku']");
						excel.setCellData("Product","1mgSku",i+2,sku);				
						Thread.sleep(500);
						
						price = getElementText("//span[@class='price']");
						excel.setCellData("Product","1mgPrice",i+2,price);
						Thread.sleep(500);					
					break;
					
					case "1 x 2 mg":	
						//System.out.println("insisde 1 x 1 mg");
						SeleniumHandler.Selenium.findElement(By.xpath("//option[contains(text(),'1 x 2 mg')]")).click();
				        Thread.sleep(500);
						
						sku =  getElementText("//span[@class='product-view__sku']");
						excel.setCellData("Product","2mgSku",i+2,sku);				
						Thread.sleep(500);
						
						price = getElementText("//span[@class='price']");
						excel.setCellData("Product","2mgPrice",i+2,price);
						Thread.sleep(500);					
					break;										
					
					case "1 x 5 mg":	
						//System.out.println("insisde 1 x 1 mg");
						SeleniumHandler.Selenium.findElement(By.xpath("//option[contains(text(),'1 x 5 mg')]")).click();
				        Thread.sleep(500);
						
						sku =  getElementText("//span[@class='product-view__sku']");
						excel.setCellData("Product","5mgSku",i+2,sku);				
						Thread.sleep(500);
						
						price = getElementText("//span[@class='price']");
						excel.setCellData("Product","5mgPrice",i+2,price);
						Thread.sleep(500);					
					break;
																				
					case "1 x 10 mg":	
						//System.out.println("insisde 1 x 10 mg");
						SeleniumHandler.Selenium.findElement(By.xpath("//option[contains(text(),'1 x 10 mg')]")).click();
				        Thread.sleep(500);
						
						sku =  getElementText("//span[@class='product-view__sku']");
						excel.setCellData("Product","10mgSku",i+2,sku);				
						Thread.sleep(500);
						
						price = getElementText("//span[@class='price']");
						excel.setCellData("Product","10mgPrice",i+2,price);
						Thread.sleep(500);					
					break;
					
					case "1 x 20 mg":	
						//System.out.println("insisde 1 x 25 mg");
						SeleniumHandler.Selenium.findElement(By.xpath("//option[contains(text(),'1 x 20 mg')]")).click();
				        Thread.sleep(500);
						
						sku =  getElementText("//span[@class='product-view__sku']");
						excel.setCellData("Product","20mgSku",i+2,sku);				
						Thread.sleep(500);
						
						price = getElementText("//span[@class='price']");
						excel.setCellData("Product","20mgPrice",i+2,price);
						Thread.sleep(500);					
					break;
					
					case "1 x 25 mg":	
						//System.out.println("insisde 1 x 25 mg");
						SeleniumHandler.Selenium.findElement(By.xpath("//option[contains(text(),'1 x 25 mg')]")).click();
				        Thread.sleep(500);
						
						sku =  getElementText("//span[@class='product-view__sku']");
						excel.setCellData("Product","25mgSku",i+2,sku);				
						Thread.sleep(500);
						
						price = getElementText("//span[@class='price']");
						excel.setCellData("Product","25mgPrice",i+2,price);
						Thread.sleep(500);					
					break;
					
					case "1 x 50 mg":	
						//System.out.println("insisde 1 x 50 mg");
						SeleniumHandler.Selenium.findElement(By.xpath("//option[contains(text(),'1 x 50 mg')]")).click();
				        Thread.sleep(500);
						
						sku =  getElementText("//span[@class='product-view__sku']");
						excel.setCellData("Product","50mgSku",i+2,sku);				
						Thread.sleep(500);
						
						price = getElementText("//span[@class='price']");
						excel.setCellData("Product","50mgPrice",i+2,price);
						Thread.sleep(500);					
					break;
					
					case "1 x 100 mg":	
						//System.out.println("insisde 1 x 50 mg");
						SeleniumHandler.Selenium.findElement(By.xpath("//option[contains(text(),'1 x 100 mg')]")).click();
				        Thread.sleep(500);
						
						sku =  getElementText("//span[@class='product-view__sku']");
						excel.setCellData("Product","100mgSku",i+2,sku);				
						Thread.sleep(500);
						
						price = getElementText("//span[@class='price']");
						excel.setCellData("Product","100mgPrice",i+2,price);
						Thread.sleep(500);					
					break;
					
					case "1 x 250 mg":	
						//System.out.println("insisde 1 x 250 mg");
						SeleniumHandler.Selenium.findElement(By.xpath("//option[contains(text(),'1 x 250 mg')]")).click();
				        Thread.sleep(500);
						
						sku =  getElementText("//span[@class='product-view__sku']");
						excel.setCellData("Product","250mgSku",i+2,sku);				
						Thread.sleep(500);
						
						price = getElementText("//span[@class='price']");
						excel.setCellData("Product","250mgPrice",i+2,price);
						Thread.sleep(500);					
					break;
					
					case "1 x 500 mg":	
						//System.out.println("insisde 1 x 500 mg");
						SeleniumHandler.Selenium.findElement(By.xpath("//option[contains(text(),'1 x 500 mg')]")).click();
				        Thread.sleep(500);
						
						sku =  getElementText("//span[@class='product-view__sku']");
						excel.setCellData("Product","500mgSku",i+2,sku);				
						Thread.sleep(500);
						
						price = getElementText("//span[@class='price']");
						excel.setCellData("Product","500mgPrice",i+2,price);
						Thread.sleep(500);					
					break;
					
					
					case "1 x 1 g":	
						//System.out.println("insisde 1 x 1 g");
						SeleniumHandler.Selenium.findElement(By.xpath("//option[contains(text(),'1 x 1 g')]")).click();
				        Thread.sleep(500);
						
						sku =  getElementText("//span[@class='product-view__sku']");
						excel.setCellData("Product","1gSku",i+2,sku);				
						Thread.sleep(500);
						
						price = getElementText("//span[@class='price']");
						excel.setCellData("Product","1gPrice",i+2,price);
						Thread.sleep(500);					
					break;
					
					case "1 x 2 g":	
						//System.out.println("insisde 1 x 1 g");
						SeleniumHandler.Selenium.findElement(By.xpath("//option[contains(text(),'1 x 2 g')]")).click();
				        Thread.sleep(500);
						
						sku =  getElementText("//span[@class='product-view__sku']");
						excel.setCellData("Product","2gSku",i+2,sku);				
						Thread.sleep(500);
						
						price = getElementText("//span[@class='price']");
						excel.setCellData("Product","2gPrice",i+2,price);
						Thread.sleep(500);					
					break;
					
					case "1 x 5 g":	
						//System.out.println("insisde 1 x 5");
						SeleniumHandler.Selenium.findElement(By.xpath("//option[contains(text(),'1 x 5 g')]")).click();
				        Thread.sleep(500);
						
						sku =  getElementText("//span[@class='product-view__sku']");
						excel.setCellData("Product","5gSku",i+2,sku);				
						Thread.sleep(500);
						
						price = getElementText("//span[@class='price']");
						excel.setCellData("Product","5gPrice",i+2,price);
						Thread.sleep(500);					
					break;
					
					case "1 x 10 g":	
						//System.out.println("insisde 1 x 10 g");
						SeleniumHandler.Selenium.findElement(By.xpath("//option[contains(text(),'1 x 10 g')]")).click();
				        Thread.sleep(500);
						
						sku =  getElementText("//span[@class='product-view__sku']");
						excel.setCellData("Product","10gSku",i+2,sku);				
						Thread.sleep(500);
						
						price = getElementText("//span[@class='price']");
						excel.setCellData("Product","10gPrice",i+2,price);
						Thread.sleep(500);					
					break;
					
					
					case "1 x 25 g":	
							//System.out.println("insisde 1.25 gm");
							SeleniumHandler.Selenium.findElement(By.xpath("//option[contains(text(),'1 x 25 g')]")).click();
					        Thread.sleep(500);
							
							sku =  getElementText("//span[@class='product-view__sku']");
							excel.setCellData("Product","25gSku",i+2,sku);				
							Thread.sleep(500);
							
							price = getElementText("//span[@class='price']");
							excel.setCellData("Product","25gPrice",i+2,price);
							Thread.sleep(500);					
						break;
						
					case "1 x 50 g":	
						//System.out.println("insisde 1.25 gm");
						SeleniumHandler.Selenium.findElement(By.xpath("//option[contains(text(),'1 x 50 g')]")).click();
				        Thread.sleep(500);
						
						sku =  getElementText("//span[@class='product-view__sku']");
						excel.setCellData("Product","50gSku",i+2,sku);				
						Thread.sleep(500);
						
						price = getElementText("//span[@class='price']");
						excel.setCellData("Product","50gPrice",i+2,price);
						Thread.sleep(500);					
					break;
	
					case "1 x 100 g":																		
							//System.out.println("insisde 1oo gm");
							SeleniumHandler.Selenium.findElement(By.xpath("//option[contains(text(),'1 x 100 g')]")).click();
					        Thread.sleep(500);
							
							sku =  getElementText("//span[@class='product-view__sku']");
							excel.setCellData("Product","100gSku",i+2,sku);
							Thread.sleep(500);
							
							price = getElementText("//span[@class='price']");
							excel.setCellData("Product","100gPrice",i+2,price);	
							Thread.sleep(500);
						break;
						
						
					case "1 x 250 g":
						
						//System.out.println("insisde 500..");							
						SeleniumHandler.Selenium.findElement(By.xpath("//option[contains(text(),'1 x 250 g')]")).click();
				        Thread.sleep(500);
				        
				        sku =  getElementText("//span[@class='product-view__sku']");
						excel.setCellData("Product","250gSku",i+2,sku);
						Thread.sleep(500);
						
						price = getElementText("//span[@class='price']");
						excel.setCellData("Product","250gPrice",i+2,price);
						Thread.sleep(500);
					break;
						
						
					case "1 x 500 g":
						
							//System.out.println("insisde 500..");							
							SeleniumHandler.Selenium.findElement(By.xpath("//option[contains(text(),'1 x 500 g')]")).click();
					        Thread.sleep(500);
					        
					        sku =  getElementText("//span[@class='product-view__sku']");
							excel.setCellData("Product","500gSku",i+2,sku);
							Thread.sleep(500);
							
							price = getElementText("//span[@class='price']");
							excel.setCellData("Product","500gPrice",i+2,price);
							Thread.sleep(500);
						break;
						
					case "1 x 1 kg":																															
							//System.out.println("insisde 1kg..");							
							SeleniumHandler.Selenium.findElement(By.xpath("//option[contains(text(),'1 x 1 kg')]")).click();
					        Thread.sleep(500);
							
							sku =  getElementText("//span[@class='product-view__sku']");
							excel.setCellData("Product","1KgSku",i+2,sku);
							Thread.sleep(500);
							
							price = getElementText("//span[@class='price']");
							excel.setCellData("Product","1kgPrice",i+2,price);
							Thread.sleep(500);
						break;	
						
					case "1 x 2.5 kg":																															
						//System.out.println("insisde 1 x 2.5 kg..");							
						SeleniumHandler.Selenium.findElement(By.xpath("//option[contains(text(),'1 x 2.5 kg')]")).click();
				        Thread.sleep(500);
						
						sku =  getElementText("//span[@class='product-view__sku']");
						excel.setCellData("Product","2.5KgSku",i+2,sku);
						Thread.sleep(500);
						
						price = getElementText("//span[@class='price']");
						excel.setCellData("Product","2.5kgPrice",i+2,price);
						Thread.sleep(500);
					break;	
					
					case "1 x 5 kg":																															
						//System.out.println("insisde 1 x 5 kg..");							
						SeleniumHandler.Selenium.findElement(By.xpath("//option[contains(text(),'1 x 5 kg')]")).click();
				        Thread.sleep(500);
						
						sku =  getElementText("//span[@class='product-view__sku']");
						excel.setCellData("Product","5KgSku",i+2,sku);
						Thread.sleep(500);
						
						price = getElementText("//span[@class='price']");
						excel.setCellData("Product","5kgPrice",i+2,price);
						Thread.sleep(500);
					break;
					
					
					case "1 x 10 mL":																															
						//System.out.println("insisde 1 x 5 kg..");							
						SeleniumHandler.Selenium.findElement(By.xpath("//option[contains(text(),'1 x 10 mL')]")).click();
				        Thread.sleep(500);
						
						sku =  getElementText("//span[@class='product-view__sku']");
						excel.setCellData("Product","10mLSku",i+2,sku);
						Thread.sleep(500);
						
						price = getElementText("//span[@class='price']");
						excel.setCellData("Product","10mLPrice",i+2,price);
						Thread.sleep(500);
					break;	
					
					case "1 x 25 mL":																															
						//System.out.println("insisde 1 x 5 kg..");							
						SeleniumHandler.Selenium.findElement(By.xpath("//option[contains(text(),'1 x 25 mL')]")).click();
				        Thread.sleep(500);
						
						sku =  getElementText("//span[@class='product-view__sku']");
						excel.setCellData("Product","25mLSku",i+2,sku);
						Thread.sleep(500);
						
						price = getElementText("//span[@class='price']");
						excel.setCellData("Product","25mLPrice",i+2,price);
						Thread.sleep(500);
					break;
					
					case "1 x 50 mL":																															
						//System.out.println("insisde 1 x 5 kg..");							
						SeleniumHandler.Selenium.findElement(By.xpath("//option[contains(text(),'1 x 50 mL')]")).click();
				        Thread.sleep(500);
						
						sku =  getElementText("//span[@class='product-view__sku']");
						excel.setCellData("Product","50mLSku",i+2,sku);
						Thread.sleep(500);
						
						price = getElementText("//span[@class='price']");
						excel.setCellData("Product","50mLPrice",i+2,price);
						Thread.sleep(500);
					break;	
					
					case "1 x 100 mL":																															
						//System.out.println("insisde 1 x 5 kg..");							
						SeleniumHandler.Selenium.findElement(By.xpath("//option[contains(text(),'1 x 100 mL')]")).click();
				        Thread.sleep(500);
						
						sku =  getElementText("//span[@class='product-view__sku']");
						excel.setCellData("Product","100mLSku",i+2,sku);
						Thread.sleep(500);
						
						price = getElementText("//span[@class='price']");
						excel.setCellData("Product","100mLPrice",i+2,price);
						Thread.sleep(500);
					break;
					
					case "1 x 500 mL":																															
						//System.out.println("insisde 1 x 5 kg..");							
						SeleniumHandler.Selenium.findElement(By.xpath("//option[contains(text(),'1 x 500 mL')]")).click();
				        Thread.sleep(500);
						
						sku =  getElementText("//span[@class='product-view__sku']");
						excel.setCellData("Product","500mLSku",i+2,sku);
						Thread.sleep(500);
						
						price = getElementText("//span[@class='price']");
						excel.setCellData("Product","500mLPrice",i+2,price);
						Thread.sleep(500);
					break;
						
					default:
						System.out.println("in default switch..."+var2);
						break;
					}//EOD of switch															
			}//End of for for product type															
		}//If element is displayed			
																			
	}//EOD FOR LOOP array.
											     	    				
}//EOD of Method
	
	
}//EOD of Class
