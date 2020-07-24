package dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.UserDetails;
import utility.ConnectionManager;

public class ChangeUserDao {

	public void changeUserData(UserDetails ud,String str) throws ClassNotFoundException, SQLException, IOException
	{
		ConnectionManager ob=new ConnectionManager();
		String sql="update user_details set "+str+"=? where id=?";
		PreparedStatement ps=ob.getConnection().prepareStatement(sql);
		if(str.equals("user_name"))
		ps.setString(1, ud.getName());
		else if(str.equals("email"))
			ps.setString(1, ud.getEmail());
		else if(str.equals("password"))
			ps.setString(1, ud.getPassword());
		else
			ps.setString(1, ud.getAddress());
		ps.setLong(2, ud.getId());
		ps.executeUpdate();
		ob.getConnection().close();
	}
	public boolean checkPassword(UserDetails ud) throws ClassNotFoundException, SQLException, IOException
	{
		ConnectionManager ob=new ConnectionManager();
		Statement st=ob.getConnection().createStatement();
		ResultSet rs=st.executeQuery("Select password from user_details where id="+ud.getId());
		while(rs.next())//to check old password
		{ 
			if(ud.getPassword().equals(rs.getString("password")))
				{ob.getConnection().close();
				return true;
				}
			break;
		}
		ob.getConnection().close();
		return false;
	}
}
