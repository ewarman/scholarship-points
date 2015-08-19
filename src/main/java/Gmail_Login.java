import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.io.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Gmail_Login {
/**
* @param args
 * @throws InterruptedException 
*/
       public static void main(String[] args) throws InterruptedException, IOException{
           BufferedReader br = new BufferedReader(new FileReader("LoginStrings.txt"));
           String usrnm = "", pswd = "";
           
           try {
        	   usrnm = br.readLine();
        	   pswd = br.readLine();
           }
           catch (Exception ex) {
        	   System.out.println(ex.getMessage());
        	   System.out.println("Failed to grab login strings, exiting.");
        	   System.exit(1);
           }
           finally {
        	   br.close();
           }
    	   
    	   Kappa[] sisterInformation = getSisterInformation();  
    	   List<Email_Class> emailList = runSeleniumGrab(usrnm, pswd);
		   sisterInformation = attributeEmails(emailList, sisterInformation);
		   System.out.println(getPoints(sisterInformation));

       }
       
       public static List<Email_Class> runSeleniumGrab(String usrnm, String pswd) throws InterruptedException{
    	   	// objects and variables instantiation
            WebDriver driver = new FirefoxDriver();
            String appUrl = "https://gmail.com";
              
            // launch the firefox browser and open the application url
            driver.get(appUrl);
              
            // maximize the browser window
            driver.manage().window().maximize();
              
            // enter a valid username in the email textbox
            WebElement username = driver.findElement(By.id("Email"));
            username.clear();
            username.sendKeys(usrnm);
             
            // click on the next button
            WebElement nextButton = driver.findElement(By.id("next"));
            nextButton.click();
              
            Thread.sleep(5000);
 
            // enter a valid password in the password textbox
            WebElement password = driver.findElement(By.id("Passwd"));
            password.clear();
            password.sendKeys(pswd);
              
            // click on the Sign in button
            WebElement SignInButton = driver.findElement(By.id("signIn"));
            SignInButton.click();
              
            Thread.sleep(20000);
              
            WebElement girlsEmailTable = driver.findElement(By.id(":36"));
            List<WebElement> rowCollection=girlsEmailTable.findElements(By.xpath("//*[@id=':36']/tbody/tr"));
             
            System.out.println("Emails in inbox: "+ rowCollection.size()+".");
              
            List<Email_Class> kappas = new ArrayList<Email_Class>();
            String temp, temp2 = "";
            int j;
            
            for(WebElement rowElement:rowCollection) {
                  j=0; 
            	  temp = rowElement.getText();
            
            	  while(temp.charAt(j) != '\n'){
                	  temp2 += temp.charAt(j);
                	  j++;
                  }
                  
            	  boolean found = false;
                  
            	  for (Email_Class email : kappas) {
                	  if (temp2.equals(email.getName())) {
                		  email.incrementCount();
                		  found = true;
                		  break;
                	  }
                  }
                  
                  if (found == false) {
                	  kappas.add(new Email_Class(temp2));
                  }
                  
                  temp2 = "";
            	  found = false;
            }
            
            // close the web browser
            driver.close();
       		System.out.println("Selenium grab executed successfully.");
       
            return kappas;
       }
       
       public static Kappa[] getSisterInformation() throws IOException{
    	   
    	   String firstName;
    	   String lastName;
    	   boolean isActive;
    	   boolean inHouse;
    	   int deck;
    	   int i = 0;
    	   
    	   Kappa[] sisterInformation= new Kappa[41];
    	   BufferedReader br = new BufferedReader(new FileReader("KappaConfig.txt"));
    	   try {
    		   
    		   String line = br.readLine();
    		   
    		   while (line != null) {
    			   StringTokenizer st = new StringTokenizer(line);
    			   lastName = st.nextToken();
    			   firstName = st.nextToken();
    			   isActive = Boolean.parseBoolean(st.nextToken());
    			   inHouse = Boolean.parseBoolean(st.nextToken());
    			   deck = Integer.parseInt(st.nextToken());
    			   sisterInformation[i] = new Kappa(firstName+" "+lastName, isActive, inHouse, deck);
    			   i++;
    			   line = br.readLine();
    			   if (line == null) {
    				   break;
    			   }
    		   }
    		   
    	   } 
    	   
    	   finally {
    		   System.out.println("Sister information loaded.");
    	       br.close();
    	   }
    	   return sisterInformation;
       }
       
       public static Kappa[] attributeEmails(List<Email_Class> emailList, Kappa[] sisterInformation) {
    	   int start = 0;
		   int end = sisterInformation.length - 1;
		   int middle = 21;
		   String [] part = sisterInformation[middle].getName().split(" ");
		   char middleLetter = part[1].charAt(0);
		   
    	   for (Email_Class temp : emailList) {
    		   String[] parts = temp.getName().split(" ");
    		   //if first half of alphabet
    		   if (parts[1].charAt(0) < middleLetter) {
    			   //look only in first half of alphabet
    			   for (int i = start; i < middle; i++) {
    				   //if names are the same
    				   if (temp.getName().equals(sisterInformation[i].getName())) {
    					   //set the sister's score to the number of emails found
    					   sisterInformation[i].setCount(temp.getCount());
    				   }
    			   }
    		   }
    		   //else look in second half
    		   else{
    			   for (int i = middle; i < end; i++) {
    				   if (temp.getName().equals(sisterInformation[i].getName())) {
    					   sisterInformation[i].setCount(temp.getCount());
    				   }
    			   }
    		   }
    	   }
    	   
    	   return sisterInformation;
       }
       
       public static String getPoints(Kappa[] sisterInformation) {
    	   int inHousePoints = 0, outOfHousePoints = 0;
    	   int activePoints = 0, pledgePoints = 0;
    	   int secondDeckPoints = 0, thirdDeckPoints = 0, firstDeckPoints = 0, basementPoints = 0;
    	   
    	   for(Kappa temp : sisterInformation) {
    		   if (temp.getInHouse() == true) {
    			   inHousePoints += temp.getCount();
    		   }
    		   else {
    			   outOfHousePoints += temp.getCount();
    		   }
    		   
    		   if (temp.getActive() == true) {
    			   activePoints += temp.getCount();
    		   }
    		   else {
    			   pledgePoints += temp.getCount();
    		   }
    		   
    		   if (temp.getDeck() == 0) {
    			   basementPoints += temp.getCount();
    		   }
    		   else if (temp.getDeck() == 1) {
    			   firstDeckPoints += temp.getCount();
    		   }
    		   else if (temp.getDeck() == 2) {
    			   secondDeckPoints += temp.getCount();
    		   }
    		   else {
    			   thirdDeckPoints += temp.getCount();
    		   }
    	   }
    	   
    	   return "\nSCHOLARSHIP POINT REPORT:\n\n"
    	   		+ "In House vs. Out of House:\n"
    	   		+ "In House: "+inHousePoints+" Out of House: "+outOfHousePoints+"\n\n"
    	   		+ "Actives vs. Pledges:\n"
    	   		+ "Actives: "+activePoints+" Pledges: "+pledgePoints+"\n\n"
    	   		+ "Deck Wars:\n"
    	   		+ "Basement: "+basementPoints+"\n"
    	   		+ "First Deck: "+firstDeckPoints+"\n"
    	   		+ "Second Deck: "+secondDeckPoints+"\n"
    	   		+ "Third Deck: "+thirdDeckPoints;
       }
       
}
