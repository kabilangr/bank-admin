package controller;

import java.io.*;
import java.sql.SQLException;

import dao.AdminDao;
import dao.SignUpDao;
import model.UserDetails;

public class UserSignUp {
public void signUp()throws IOException, ClassNotFoundException, SQLException
{
	BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
	System.out.println("Enter User ID: ");
	long id=Long.parseLong(in.readLine());
	UserDetails ud=new UserDetails();
	ud.setId(id);
	AdminDao ad=new AdminDao();
	if(ad.findIfId(ud)!=true)
	{
		System.out.println("Enter Your Name: ");
		String name=in.readLine();
		System.out.println("Enter Your Email:");
		String email=in.readLine();
		System.out.println("Enter Your Password");
		String pass=in.readLine();
		System.out.println("Confirm Password");
		String cpass=in.readLine();
		System.out.println("Enter Your Address:");
		String add=in.readLine();
		System.out.println("Enter Your City:");
		String city=in.readLine();
		System.out.println("Enter Your State:");
		String state=in.readLine();
		System.out.println("Enter ZipCode");
		long zip=Long.parseLong(in.readLine());
		if(pass.equals(cpass))
		{
			ud.setName(name);
			ud.setEmail(email);
			ud.setPassword(pass);
			ud.setAddress(add);
			ud.setCity(city);
			ud.setState(state);
			ud.setZip(zip);
			SignUpDao sud=new SignUpDao();
			sud.signup(ud);
		}
	}
	else
	{
		System.out.println("Your ID is Already there......");
	}
}
}
