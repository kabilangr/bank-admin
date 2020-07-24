package service;
import java.io.IOException;
import java.sql.*;
import model.*;
import utility.ConnectionManager;

public class Validation extends UserValidation implements AdminValidation
{
	public boolean adminValidation(Admin admin) throws SQLException, ClassNotFoundException, IOException
	{
		ConnectionManager ob=new ConnectionManager();
		Statement st=ob.getConnection().createStatement();
		ResultSet rs=st.executeQuery("Select * from admin_log");
		while(rs.next())
		{
			if(((admin.getName()).equals(rs.getString("admin_name")))&&((admin.getPassword()).equals(rs.getString("password"))))
			{
				ob.getConnection().close();
				return true;
			}
		}
		ob.getConnection().close();
		return false;
	}
public long userValidation(User user) throws SQLException, ClassNotFoundException, IOException
{
	long id=0;
	ConnectionManager ob=new ConnectionManager();
	Statement st=ob.getConnection().createStatement();
	ResultSet rs=st.executeQuery("Select * from user_details");
	while(rs.next())
	{
		if(((user.getUserName()).equals(rs.getString("user_name")))&&((user.getUserPassword()).equals(rs.getString("password"))))
		{
			id=rs.getLong("id");
			ob.getConnection().close();
			return id;
		}
	}
	ob.getConnection().close();
	return id;
}
}
