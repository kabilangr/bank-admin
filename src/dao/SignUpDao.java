package dao;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import model.UserDetails;
import utility.ConnectionManager;

public class SignUpDao {
public void signup(UserDetails ud) throws ClassNotFoundException, SQLException, IOException
{
	ConnectionManager cm=new ConnectionManager();
	String sql="insert into user_details(id,user_name,password,email,address,create_at,zip_code,active)values(?,?,?,?,?,?,?,?)";
	PreparedStatement st1=cm.getConnection().prepareStatement(sql);
	LocalDate date=LocalDate.now();
	Date date1=Date.valueOf(date);
	st1.setLong(1, ud.getId());
	st1.setString(2,ud.getName());
	st1.setString(3, ud.getPassword());
	st1.setString(4, ud.getEmail());
	st1.setString(5, ud.getAddress());
	st1.setDate(6, date1);
	st1.setLong(7, ud.getZip());
	st1.setInt(8,0);
	st1.executeUpdate();
	cm.getConnection().close();
	boolean flag=false;
	ConnectionManager ob=new ConnectionManager();
	Statement sta=ob.getConnection().createStatement();
	ResultSet rs=sta.executeQuery("select * from zip");
	while(rs.next())
	{
		if(ud.getZip()==(rs.getLong("code")))
		{
			flag=true;
		}
	}
	ob.getConnection().close();
	if(flag!=true)
	{
	ConnectionManager object=new ConnectionManager();
	sql="insert into zip(code,city_name,State_name)values(?,?,?)";
	PreparedStatement st=object.getConnection().prepareStatement(sql);
	st.setLong(1, ud.getZip());
	st.setString(2, (ud.getCity()).toUpperCase());
	st.setString(3, (ud.getState()).toUpperCase());
	st.executeUpdate();
	object.getConnection().close();

	}
	System.out.println("..............................DONE............................");
}
}
