package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import model.User;
import model.UserDetails;
import utility.ConnectionManager;

public class UserFunctions {

	public boolean activeCheck(User user) throws ClassNotFoundException, SQLException, IOException
	{
		ConnectionManager ob=new ConnectionManager();
		Statement st=ob.getConnection().createStatement();
		ResultSet rs=st.executeQuery("select * from user_details where user_name='"+user.getUserName()+"'");
		boolean flag=false;
		while(rs.next())
		{    	  
			if(rs.getInt("active")==1)
				flag=true;
			break;
		}
		ob.getConnection().close();
		return flag;
	}
	public long getIdusingName(UserDetails ud) throws ClassNotFoundException, SQLException, IOException
	{
		String sql="select * from user_details where user_name='"+ud.getName()+"'";
		ConnectionManager ob=new ConnectionManager();
		Statement st=ob.getConnection().createStatement();
		ResultSet rs=st.executeQuery(sql);long id=0;
		while(rs.next())
		{
			id=rs.getLong("id");
			break;
		}
		ob.getConnection().close();
		return id;
	}
	public UserDetails searchDetails(UserDetails ud) throws ClassNotFoundException, SQLException, IOException
	{
		String sql="select * from user_details where id="+ud.getId();
		ConnectionManager ob=new ConnectionManager();
		Statement st=ob.getConnection().createStatement();
		ResultSet rs=st.executeQuery(sql);
		while(rs.next())
		{
			ud.setName(rs.getString("user_name"));
			ud.setEmail(rs.getString("email"));
			ud.setAddress(rs.getString("address"));
			ud.setZip(rs.getLong("zip_code"));
			break;
		}
		ob.getConnection().close();
		sql="select * from zip where code="+ud.getZip();
		ob=new ConnectionManager();
		st=ob.getConnection().createStatement();
		rs=st.executeQuery(sql);
		while(rs.next())
		{
			ud.setCity(rs.getString("city_name"));
			ud.setState(rs.getString("state_name"));
			break;
		}
		ob.getConnection().close();
		return ud;
	}
	public UserDetails ShowBalance(UserDetails ud) throws ClassNotFoundException, SQLException, IOException
	{
		String sql="select * from money where user_id="+ud.getId();
		ConnectionManager ob=new ConnectionManager();
		Statement st=ob.getConnection().createStatement();
		ResultSet rs=st.executeQuery(sql);
		while(rs.next())
		{
			ud.setBalance(rs.getDouble("balance"));
			break;
		}
		ob.getConnection().close();
		return ud;
	}
	public void changeDetails(UserDetails ud)throws ClassNotFoundException, SQLException, IOException
	{
		UserDetails ud1=ud;
		BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
		int kill=0;
		while(kill!=1)
		{
			System.out.println("..............................................................");
		System.out.println("1.Change Name");
		System.out.println("2.Change Email");
		System.out.println("3.Change Address");
		System.out.println("4.Back to Main");
		System.out.println("..............................................................");
		System.out.println("Enter your choice");
		int c=Integer.parseInt(in.readLine());
		ChangeUserDao cud=new ChangeUserDao();
		switch(c)
		{
		case 1:
		{//to change name
			System.out.println("Enter New name");
			String name=in.readLine();
			ud.setName(name);
			cud.changeUserData(ud, "user_name");
			System.out.println("..............................DONE............................");
			break;
		}
		case 2:
		{// to change email
			System.out.println("Enter new Email");
			String email=in.readLine();
			System.out.println("Confirm email");
			String cemail=in.readLine();
			if(email.equals(cemail))
			{
				ud.setEmail(email);
				cud.changeUserData(ud, "email");
				System.out.println("..............................DONE............................");
			}
			else
				System.out.println("Enter properly");
			break;
		}
		case 3:
		{//to chage address
			System.out.println("Enter new address");
			String add=in.readLine();
			ud.setAddress(add);
			cud.changeUserData(ud, "address");
			System.out.println("..............................DONE............................");
			break;
		}
		case 4:
		{
			kill=1;
			break;
		}
		default:
			System.out.println("Wrong choice");
		}
		}
		ud=ud1;
	}
	public void changePassword(UserDetails ud) throws ClassNotFoundException, SQLException, IOException
	{
		BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter old password");
		String opass=in.readLine();
		ud.setPassword(opass);
		ChangeUserDao cud=new ChangeUserDao();
		if(cud.checkPassword(ud))
		{
			System.out.println("Enter new password");
			String pass=in.readLine();
			System.out.println("Confirm password");
			String cpass=in.readLine();
			if(cpass.equals(pass))
			{
				ud.setPassword(cpass);
				cud.changeUserData(ud, "password");
				System.out.println("..............................DONE............................");
			}
		}
		else
			System.out.println("Enter valid old password");
	}
	public void detailslip(UserDetails ud) throws ClassNotFoundException, SQLException, IOException, DocumentException
	{
		ConnectionManager ob=new ConnectionManager();
	    Statement st=ob.getConnection().createStatement();
	    ResultSet rs=st.executeQuery("select * from user_details where id="+ud.getId());
	    long zip=0;String name="",email="",add="",city="",state="";double balance=0;
	    while(rs.next())
	    {//to get user details
	    	name=rs.getString("user_name");
	    	email=rs.getString("email");
	    	add=rs.getString("address");
	    	zip=rs.getLong("zip_code");
	    	break;
	    }
	    ob.getConnection().close();
	    ob=new ConnectionManager();
	    st=ob.getConnection().createStatement();
	    rs=st.executeQuery("select * from zip where code="+zip);
	    while(rs.next())
	    {//to get city and state from zip
	    	city=rs.getString("city_name");
	    	state=rs.getString("state_name");
	    	break;
	    }
	    ob.getConnection().close();
	    ob=new ConnectionManager();
	    st=ob.getConnection().createStatement();
	    rs=st.executeQuery("select * from money where user_id="+ud.getId());
	    while(rs.next())
	    {// to get balance amount
	    	balance=rs.getDouble("balance");
	    	break;
	    }
	    ob.getConnection().close();
	    Document d=new Document();
		try {
	    PdfWriter pdf=PdfWriter.getInstance(d, new FileOutputStream("Slip.pdf"));
	    d.open();
	    d.addTitle("BANK SLIP");
	    d.add(new Paragraph("  Name: "+name+"\n  Email: "+email+"\n  Address: "+add+"\n  City: "+city+"\n  State: "+state+
	    		"\n  PinCode: "+zip+"\n\n\n\n"+"--------------------------------------------------------------------------------------------------\n"));
	    d.add(new Paragraph("Current Balance: "+balance));
        d.close();
        pdf.close();
	}
	catch(DocumentException e)
	{
		e.printStackTrace();
	}
	catch(FileNotFoundException e)
	{
		e.printStackTrace();
	}
		System.out.println("..............................................................");
		System.out.println(".............................DONE.............................");
		System.out.println("..............................................................");
	}
	public void addMoney(UserDetails ud) throws ClassNotFoundException, SQLException, IOException
	{
		ConnectionManager obj=new ConnectionManager();
	    PreparedStatement pst=obj.getConnection().prepareStatement("update money set balance=? where user_id=?");
	    pst.setDouble(1, ud.getBalance());
	    pst.setLong(2, ud.getId());
	    pst.executeUpdate();
	    obj.getConnection().close();
	}
}
