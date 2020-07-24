package controller;
import java.io.*;
import java.sql.SQLException;

import com.itextpdf.text.DocumentException;

import model.*;
import service.Validation;
public class Main {

	public static void main(String[] args) throws  IOException, ClassNotFoundException, SQLException, DocumentException 
	{
     BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
     Validation obj=new Validation();
     int kill=0;
     while(kill!=1)//to run it until user exit
     {
    System.out.println("=============================================================");
     System.out.println("\tWelcome to Bank Administration");
     System.out.println("=============================================================");
     System.out.println("1.Admin");
     System.out.println("2.User Login");
     System.out.println("3.User SignUp");
     System.out.println("4.Exit");
     System.out.println("_____________________________________________________________");
     System.out.println("Enter your choice:");
      int c=Integer.parseInt(in.readLine());
      switch(c)
      {
      case 1:
      { //Admin lognin here
    	  System.out.println("Enter Admin Name:");
    	  String adminName=in.readLine();
    	  System.out.println("Enter Admin Password:");
    	  String adminPassword=in.readLine();
    	    System.out.println("..............................................................");
    	  Admin admin=new Admin(adminName,adminPassword);//calls the class in model
    	  if(obj.adminValidation(admin))
    	  {
    		  AdminPage ap=new AdminPage();
    		  ap.adminPageIn(admin);//calls the function in class AdminPage
    	  }
    	  else
    		  System.out.println("Enter correct admin name and password");
    	  break;
      }
      case 2:
      {
    	  System.out.println("Enter UserName");
    	  String name=in.readLine();
    	  System.out.println("Enter Password");
    	  String pass=in.readLine();
    	  System.out.println("..............................................................");
    	  User user=new User(name,pass);
    	  long id=obj.userValidation(user);
    	  if(id!=0)
    	  {
    		  UserPage up=new UserPage();
    		  up.userPage(user);
    	  }
    	  else
    		  System.out.println("Enter correct User name and password");
    	  break;
      }
      case 3:
      {
    	  UserSignUp usu=new UserSignUp();
    	  usu.signUp();
    	  break;
      }
      case 4:
      {
    	  kill=1;
    	  break;
      }
      default:
      {
    	  System.out.println("Choose wisely....");
      }
      }
	}
	}

}
