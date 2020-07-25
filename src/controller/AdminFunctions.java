package controller;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dao.AdminDao;
import model.Admin;
import model.UserDetails;
public class AdminFunctions {
public void addUser()throws IOException, ClassNotFoundException, SQLException
{
	//for input reader
	BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
	int kill=0;
	while(kill!=1)
	{
	System.out.println("1.Add User");
	System.out.println("2.back to Admin page");
	System.out.println("Enter your choice");
	int i=Integer.parseInt(in.readLine());
	switch(i)
	{
	case 1:
	{
		UserDetails ud=new UserDetails();
		System.out.println("Enter User ID");
		Long id=Long.parseLong(in.readLine());
        ud.setId(id);
        AdminDao acw=new AdminDao();
		if(acw.findIfId(ud))
		{
			System.out.println("User ID is Already there........");
		}
		else
		{
		System.out.println("Enter User name");
		String name=in.readLine();ud.setName(name);
		System.out.println("Enter User Password");
		String password=in.readLine();ud.setPassword(password);
		System.out.println("Enter User Email");
		String email=in.readLine();ud.setEmail(email);
		System.out.println("Enter User Address");
		String address=in.readLine();ud.setAddress(address);
		System.out.println("Enter city");
		String city=in.readLine().toUpperCase();ud.setCity(city);
		System.out.println("Enter State");
		String state=in.readLine().toUpperCase();ud.setState(state);
		System.out.println("Enter ZipCode");
		long zip=Long.parseLong(in.readLine());ud.setZip(zip);
		System.out.println("Enter balance");
		double balance=Double.parseDouble(in.readLine());ud.setBalance(balance);
		acw.addUserFunction(ud);
		}
		break;
	}
	case 2:
	{
		//to exit loop
		kill=1;
		System.out.println("Moving back......");
		break;
	}
	default:
	{
		System.out.println("Wrong choice");
	}
	}
	}
}
public void acceptRequest()throws IOException, SQLException, ClassNotFoundException
{
	BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
	AdminDao aw=new AdminDao();
	int kill=0;
	while(kill!=1)
	{
	System.out.println("1.list No.of Request");
	System.out.println("2.Accept Request");
	System.out.println("3.deactivate user");
	System.out.println("4.back to Admin page");
	System.out.println("Enter your choice");
	int c=Integer.parseInt(in.readLine());
	switch(c)
	{
	case 1:
	{
		aw.listRequest();
		break;
	}
	case 2:
	{
		List<UserDetails> list=new ArrayList<UserDetails>();
		System.out.println("ID  \t  Name  \t  Email");
		list=aw.listUser();
		for(int i=0;i<list.size();i++)
		{
			System.out.println(list.get(i).getId()+"\t"+list.get(i).getName()+"\t"+list.get(i).getEmail());
		}
		System.out.println("Enter the ID to be accepted");
		long id=Long.parseLong(in.readLine());
		UserDetails ud=new UserDetails();
		ud.setId(id);
		 ud=aw.acceptRequest(ud);
		System.out.println("The Details are:");
		System.out.println("ID: "+ud.getId());
		System.out.println("Name: "+ud.getName());
		System.out.println("Email: "+ud.getEmail());
		System.out.println("Address: "+ud.getAddress());
		System.out.println("Created Date: "+ud.getDate());
		System.out.println("Do you want to active?y/n");
		char ch=(char)(in.readLine().charAt(0));
		double balance=0;
        if(ch=='y'||ch=='Y')
        {
          aw.activateUser(ud,true);
        System.out.println("Enter the balance amount");
        balance=Double.parseDouble(in.readLine());       
        ud.setBalance(balance);    
         aw.addMoney(ud,aw.checkBalanceId(ud));
        }
		break;
	}
	case 3:
	{
		System.out.println("Enter the ID to be accepted");
		long id=Long.parseLong(in.readLine());
		UserDetails ud=new UserDetails();
		ud.setId(id);
		aw.activateUser(ud,false);
	}
	case 4:
	{
		kill=1;
		System.out.println("Moving back......");
		break;
	}
	default:
	{
		System.out.println("Wrong choice");
	}
	}
	}
}
public void searchUser()throws IOException, SQLException, ClassNotFoundException
{
	int kill=0;
	BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
	AdminDao aw=new AdminDao();
	while(kill!=1)
	{
		System.out.println("..............................................................");
	System.out.println("1.Search using ID");
	System.out.println("2.Search using Name");
	System.out.println("3.Search using City or State");
	System.out.println("4.back to Admin page");
	System.out.println("..............................................................");
	System.out.println("Enter your choice");
	int c=Integer.parseInt(in.readLine());
	switch(c)
	{
	case 1:
	{
		System.out.println("Enter the ID");
		long id=Long.parseLong(in.readLine());
		UserDetails ud=new UserDetails();
		ud.setId(id);
		if(aw.findIfId(ud)!=true)
		{
			System.out.println("The given User Id is invalid........");
		}
		else
		{
		ud=aw.searchById(ud);
		System.out.println("..............................................................");
		System.out.println("ID: "+ud.getId());
		System.out.println("Name: "+ud.getName());
		System.out.println("Email: "+ud.getEmail());
		System.out.println("Address: "+ud.getAddress());
		System.out.println("City: "+ud.getCity());
		System.out.println("State: "+ud.getState());
		System.out.println("ZipCode: "+ud.getZip());
		System.out.println("Created date: "+ud.getDate());
		System.out.println("Balance: "+ud.getBalance());
		if(ud.getActive()==1)
			System.out.println("Account: Active");
			else
			System.out.println("Account: Not Active");
		}
		System.out.println("..............................................................");
		break;
	}
	case 2:
	{
		System.out.println("Enter Name ");
		String name=in.readLine();
		UserDetails ud=new UserDetails();
		ud.setName(name);
		if(aw.findIfName(ud))
		{
			System.out.println("No User with name - "+ud.getName());
		}
		else
		{
		ud=aw.searchByName(ud);
		System.out.println("..............................................................");
		System.out.println("ID: "+ud.getId());
		System.out.println("Name: "+ud.getName());
		System.out.println("Email: "+ud.getEmail());
		System.out.println("Address: "+ud.getAddress());
		System.out.println("City: "+ud.getCity());
		System.out.println("State: "+ud.getState());
		System.out.println("ZipCode: "+ud.getZip());
		System.out.println("Created date: "+ud.getDate());
		System.out.println("Balance: "+ud.getBalance());
		if(ud.getActive()==1)
			System.out.println("Account: Active");
			else
			System.out.println("Account: Not Active");
		}
		System.out.println("..............................................................");
		break;
	}
	case 3:
	{
		System.out.println("Enter City");
		String city=in.readLine();
		System.out.println("Enter State");
		String state=in.readLine();
		List<UserDetails> list=new ArrayList<UserDetails>();
		UserDetails ud=new UserDetails();
		ud.setCity(city.toUpperCase());ud.setState(state.toUpperCase());
		list=aw.searchByCity(ud);
		for(int i=0;i<list.size();i++)
		{
			System.out.println("..............................................................");
		System.out.println("ID: "+list.get(i).getId());
		System.out.println("Name: "+list.get(i).getName());
		System.out.println("Email: "+list.get(i).getEmail());
		System.out.println("Address: "+list.get(i).getAddress());
		System.out.println("Created date: "+list.get(i).getDate());
		if(list.get(i).getActive()==1)
		System.out.println("Account: Active");
		else
		System.out.println("Account: Not Active");
		System.out.println("..............................................................");
		}
		break;
	}
	case 4:
	{
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
public void changeUserDetails() throws IOException, SQLException, ClassNotFoundException
{
	BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
	System.out.println("Search and change using ID");
		System.out.println("Enter ID");
		long id=Long.parseLong(in.readLine());
		AdminDao aw=new AdminDao();
		UserDetails ud=new UserDetails();
		ud.setId(id);
		if(aw.findIfId(ud)!=true)
		{
			System.out.println("The Given ID is not present");
		}
		else
		{
			int kill=0;
			while(kill!=1)
			{
				System.out.println("..............................................................");
			System.out.println("1.Change User Name");
			System.out.println("2.Change Email");
			System.out.println("3.Change Address");
			System.out.println("4.Change User Password");
			System.out.println("5.back to Admin page");
			System.out.println("..............................................................");
			System.out.println("Enter Your Choice");
			int c=Integer.parseInt(in.readLine());
			switch(c)
			{
			case 1:
			{
				System.out.println("Enter User Name");
				String name=in.readLine();
				ud.setName(name);
				aw.changeUserName(ud);
				System.out.println("..............................DONE............................");
				break;
			}
			case 2:
			{
				System.out.println("Enter Email");
				String email=in.readLine();
				ud.setEmail(email);
				aw.changeEmail(ud);
				System.out.println("..............................DONE............................");
				break;
			}
			case 3:
			{
				System.out.println("Enter Address");
				String add=in.readLine();
				ud.setAddress(add);
				aw.changeAddress(ud);
				System.out.println("..............................DONE............................");
				break;
			}
			case 4:
			{
				System.out.println("Enter Password");
				String pass=in.readLine();
				ud.setPassword(pass);
				aw.changePass(ud);
				System.out.println("..............................DONE............................");
				break;
			}
			case 5:
			{
				kill=1;
				break;
			}
			default:
				System.out.println("Wrong choice");
			}
			ud=aw.searchById(ud);
			System.out.println("..............................................................");
			System.out.println("ID: "+ud.getId());
			System.out.println("Name: "+ud.getName());
			System.out.println("Email: "+ud.getEmail());
			System.out.println("Address: "+ud.getAddress());
			System.out.println("City: "+ud.getCity());
			System.out.println("State: "+ud.getState());
			System.out.println("ZipCode: "+ud.getZip());
			System.out.println("Created date: "+ud.getDate());
			System.out.println("Balance: "+ud.getBalance());
			if(ud.getActive()==1)
				System.out.println("Account: Active");
				else
				System.out.println("Account: Not Active");
			System.out.println("..............................................................");
			}
		}
	}
public void changeAdminPassword(Admin admin) throws IOException, SQLException, ClassNotFoundException
{
	BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
	int kill=0;  AdminDao aw=new AdminDao();
	while(kill!=1)
	{
	System.out.println("Enter new Admin Password");
	String pass=in.readLine();
	System.out.println("Confirm Password");
	String cpass=in.readLine();
	if(pass.equals(cpass))
	{
		admin.setPassword(pass);
		aw.changeAPassword(admin);
		System.out.println("..............................DONE............................");
		kill=1;
	}
	else
		System.out.println("Wrong password try again");
	}
}
public void addAdmin()throws IOException, SQLException, ClassNotFoundException
{
	BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
	System.out.println("Enter Admin Id");
	long id=Long.parseLong(in.readLine());
	System.out.println("Enter the Admin name:");
	String name=in.readLine();
	System.out.println("Enter the Admin Password");
	String pass=in.readLine();
	System.out.println("Confirm Password");
	String cpass=in.readLine();
	AdminDao aw=new AdminDao();
	if(pass.equals(cpass))
	{
		Admin admin=new Admin(name,pass);
		aw.addAdminFunction(admin, id);
		System.out.println("..............................DONE............................");
	}
	else 
		System.out.println("Enter the details properly");
}
}


