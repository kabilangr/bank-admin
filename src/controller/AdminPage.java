package controller;
import java.io.*;
import java.sql.SQLException;

import model.Admin;
public class AdminPage 
{
public void adminPageIn(Admin admin) throws IOException, ClassNotFoundException, SQLException
{
	BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
	System.out.println("Welcome Admin");
	AdminFunctions af=new AdminFunctions();
	int kill=0;
	while(kill!=1) 
	{
	System.out.println("1.Add User");
	System.out.println("2.Accept User Request");
	System.out.println("3.Search User Details");
	System.out.println("4.Change User Details");
	System.out.println("5.Change Admin Password");
	System.out.println("6.Add Admin");
	System.out.println("7.Exit Admin");
	System.out.println("Enter your choice");
	int c=Integer.parseInt(in.readLine());
	switch(c)
	{
	case 1:
	{
		af.addUser();
		break;
	}
	case 2:
	{
		af.acceptRequest();
		break;
	}
	case 3:
	{
		af.searchUser();
		break;
	}
	case 4:
	{
		af.changeUserDetails();
		break;
	}
	case 5:
	{
		af.changeAdminPassword(admin);
		break;
	}
	case 6:
	{
		af.addAdmin();
		break;
	}
	case 7:
	{
		System.out.println("Exiting......");
		kill=1;
		break;
	}
	default:
	{
		System.out.println("Wrong choice");
	}
	}
	}
}
}
