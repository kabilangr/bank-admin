package controller;
import java.io.*;
import java.sql.*;
import com.itextpdf.text.DocumentException;

import dao.UserFunctions;
import model.User;
import model.UserDetails;
public class UserPage {

	public void userPage(User user)throws IOException, ClassNotFoundException, SQLException, DocumentException
	{
		BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
		int kill=0;
		UserFunctions uf=new UserFunctions();
        boolean flag=uf.activeCheck(user);
		while(kill!=1)
		{
			System.out.println("..............................................................");
		System.out.println("Welcome User");
		System.out.println("..............................................................");
		System.out.println("1.Show details");
		System.out.println("2.Show Balance");
		System.out.println("3.Change details");
		System.out.println("4.Change password");
		System.out.println("5.Details slip");
		System.out.println("6.Exit User");
		System.out.println("..............................................................");
		System.out.println("Enter your choice");
		int c=Integer.parseInt(in.readLine());
		UserDetails ud=new UserDetails();
		ud.setName(user.getUserName());
		ud.setId(uf.getIdusingName(ud));
		switch(c)
		{
		case 1:
		{
			ud=uf.searchDetails(ud);
			System.out.println("..............................................................");
			System.out.println(" ID: "+ud.getId());
			System.out.println(" Name: "+ud.getName());
			System.out.println(" Email: "+ud.getEmail());
			System.out.println(" Address: "+ud.getAddress());
			System.out.println(" City: "+ud.getCity());
			System.out.println(" State: "+ud.getState());
			System.out.println(" ZipCode: "+ud.getZip());
			break;
		}
		case 2:
		{
			if(flag)
			{
				ud=uf.ShowBalance(ud);
				System.out.println("..............................................................");
				System.out.println(" Current Balance: "+ud.getBalance());
				System.out.println("..............................................................");
			}
			else
				System.out.println("Your Account is not Activated By Bank....");
			break;
		}
		case 3:
		{
			uf.changeDetails(ud);
			break;
		}
		case 4:
		{
			uf.changePassword(ud);
			break;
		}
		case 5:
		{
			uf.detailslip(ud);
			break;
		}
		case 6:
		{
			kill=1;
			break;
		}
		default:
			System.out.println("Choose Wisely.....");
		}
		}
	}
}
