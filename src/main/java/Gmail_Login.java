import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.io.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.HashMap;

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
    	   
    	   HashMap<String,Kappa> sisterInformation = getSisterInformation();  
    	   List<Email_Class> emailList = runSeleniumGrab(usrnm, pswd);
		   sisterInformation = attributeEmails(emailList, sisterInformation);
		   System.out.println(getPoints(sisterInformation));
		   //getDeckTeams(sisterInformation);

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
       
       public static HashMap<String,Kappa> getSisterInformation() throws IOException{
    	   
    	   String firstName;
    	   String lastName;
    	   boolean isActive;
    	   boolean inHouse;
    	   int deck;
    	   
    	   HashMap<String,Kappa> sisterInformation = new HashMap<String, Kappa>(41);
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
    			   sisterInformation.put(firstName+" "+lastName, new Kappa(isActive, inHouse, deck));
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
       
       public static HashMap<String,Kappa> attributeEmails(List<Email_Class> emailList, HashMap<String,Kappa>sisterInformation) {
    	   Scanner scan = new Scanner(System.in);
    	   for (Email_Class temp : emailList) {
    		   String name = temp.getName();
    		   if (sisterInformation.containsKey(name)) {
    			   Kappa t = sisterInformation.get(name);
    			   t.incrementCount();
    			   sisterInformation.put(name, t);
    		   } 
    		   else {
    			   System.out.println(name+" not found in directory");
    			   String manualAttempt = scan.nextLine();
        		   if (sisterInformation.containsKey(manualAttempt)) {
        			   System.out.println("Manual classification successful");
        			   Kappa t = sisterInformation.get(manualAttempt);
        			   t.incrementCount();
        			   sisterInformation.put(name, t);
        		   }
        		   else System.out.println(manualAttempt+" not found in directory, skipping");
    		   }
    	   } 
    	   scan.close();
    	   return sisterInformation;
       }
       
       public static String getPoints(HashMap<String,Kappa> sisterInformation) {
    	   double inHousePoints = 0, outOfHousePoints = 0;
    	   double activePoints = 0, pledgePoints = 0;
    	   double secondDeckPoints = 0, thirdDeckPoints = 0, firstDeckPoints = 0, basementPoints = 0;
    	   
    	   double inHouse = 0, outOfHouse = 0;
    	   double actives = 0, pledges = 0;
    	   double secondDeck = 0, thirdDeck = 0, firstDeck = 0, basement = 0;
    	   for(Kappa value : sisterInformation.values()) {
    		   if (value.getInHouse() == true) {
    			   inHousePoints += value.getCount();
    			   inHouse++;
    		   }
    		   else {
    			   outOfHousePoints += value.getCount();
    			   outOfHouse++;
    		   }
    		   
    		   if (value.getActive() == true) {
    			   activePoints += value.getCount();
    			   actives++;
    		   }
    		   else {
    			   pledgePoints += value.getCount();
    			   pledges++;
    		   }
    		   
    		   if (value.getDeck() == 0) {
    			   basementPoints += value.getCount();
    			   basement++;
    		   }
    		   else if (value.getDeck() == 1) {
    			   firstDeckPoints += value.getCount();
    			   firstDeck++;
    		   }
    		   else if (value.getDeck() == 2) {
    			   secondDeckPoints += value.getCount();
    			   secondDeck++;
    		   }
    		   else {
    			   thirdDeckPoints += value.getCount();
    			   thirdDeck++;
    		   }
    	   }

    	   return "\nSCHOLARSHIP POINT REPORT:\n\n"
    	   		+ "In House vs. Out of House:\n"
    	   		+ "In House: "+inHousePoints/inHouse+" Out of House: "+outOfHousePoints/outOfHouse+"\n\n"
    	   		+ "Actives vs. Pledges:\n"
    	   		+ "Actives: "+activePoints/actives+" Pledges: "+pledgePoints+"\n\n"
    	   		+ "Deck Wars:\n"
    	   		+ "Basement: "+basementPoints/basement+"\n"
    	   		+ "First Deck: "+firstDeckPoints/firstDeck+"\n"
    	   		+ "Second Deck: "+secondDeckPoints/secondDeck+"\n"
    	   		+ "Third Deck: "+thirdDeckPoints/thirdDeck;
       }
       
       public static void getDeckTeams(HashMap<String,Kappa> sisterInformation) {
    	   for (String name : sisterInformation.keySet()) {
    		   if (sisterInformation.get(name).getDeck() < 2) {
    			   System.out.print(name);
    			   System.out.println(" "+sisterInformation.get(name).getDeck());
    		   }
    	   }
       }
       
}
