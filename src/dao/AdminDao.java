package dao;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Admin;
import model.UserDetails;
import utility.ConnectionManager;

public class AdminDao {
	public boolean findIfName(UserDetails ud) throws ClassNotFoundException, SQLException, IOException
	{
		ConnectionManager ob1=new ConnectionManager();
		Statement stat=ob1.getConnection().createStatement();
		ResultSet rs1=stat.executeQuery("select id from User_details");
		while(rs1.next())
		{
			if(ud.getName().equals(rs1.getString("User_name")))
			{
				ob1.getConnection().close();
				return true;
			}
		}
		ob1.getConnection().close();
		return false;
	}
	public boolean findIfId(UserDetails ud) throws SQLException, ClassNotFoundException, IOException
	{
		ConnectionManager ob1=new ConnectionManager();
		Statement stat=ob1.getConnection().createStatement();
		ResultSet rs1=stat.executeQuery("select id from User_details");
		while(rs1.next())
		{
			if(ud.getId()==(rs1.getLong("id")))
			{
				ob1.getConnection().close();
				return true;
			}
		}
		ob1.getConnection().close();
		return false;
	}
public void addUserFunction(UserDetails ud) throws ClassNotFoundException, SQLException, IOException
{
	ConnectionManager cm=new ConnectionManager();
	PreparedStatement ps=cm.getConnection().prepareStatement("insert into money(user_id,balance) values(?,?)");
	ps.setLong(1, ud.getId());
	ps.setDouble(2, ud.getBalance());
	ps.executeUpdate();
	cm.getConnection().close();
	String sql="insert into zip(code,city_name,State_name)values(?,?,?)";
	//to insert zipcode
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
	PreparedStatement st=object.getConnection().prepareStatement(sql);
	st.setLong(1, ud.getZip());
	st.setString(2, (ud.getCity()).toUpperCase());
	st.setString(3, (ud.getState()).toUpperCase());
	st.executeUpdate();
	object.getConnection().close();
	}
	ob=new ConnectionManager();
	sql="insert into user_details(id,user_name,password,email,address,create_at,zip_code,active)values(?,?,?,?,?,?,?,?)";
	PreparedStatement st1=ob.getConnection().prepareStatement(sql);
	LocalDate date=LocalDate.now();
	Date date1=Date.valueOf(date);
	st1.setLong(1, ud.getId());
	st1.setString(2,ud.getName());
	st1.setString(3, ud.getPassword());
	st1.setString(4, ud.getEmail());
	st1.setString(5, ud.getAddress());
	st1.setDate(6, date1);
	st1.setLong(7, ud.getZip());
	st1.setInt(8,1);
	st1.executeUpdate();
	ob.getConnection().close();
}
public void listRequest() throws ClassNotFoundException, SQLException, IOException
{
	String sql="select count(*) AS rowcount from user_details where active=0";
	ConnectionManager ob=new ConnectionManager();
	Statement st=ob.getConnection().createStatement();
	ResultSet rs=st.executeQuery(sql);
	rs.next();
	int count = rs.getInt("rowcount");
	System.out.println("NO.of request is: "+count);
	ob.getConnection().close();
}
public List<UserDetails> listUser() throws SQLException, ClassNotFoundException, IOException
{
	String sql="select * from User_details where active=0";
	List<UserDetails> list=new ArrayList<UserDetails>();
	ConnectionManager ob=new ConnectionManager();
	Statement st=ob.getConnection().createStatement();
	ResultSet rs=st.executeQuery(sql);
	UserDetails ud=null;
	while(rs.next())
	{
		ud=new UserDetails();
		ud.setId(rs.getLong("id"));
		ud.setName(rs.getString("User_name"));
		ud.setEmail(rs.getString("email"));
		list.add(ud);
	}
	ob.getConnection().close();
	return list;
}
public UserDetails acceptRequest(UserDetails ud) throws ClassNotFoundException, SQLException, IOException
{
	String sql="select * from User_details where id="+ud.getId();
	ConnectionManager ob1=new ConnectionManager();
	Statement st1=ob1.getConnection().createStatement();
	ResultSet rs1=st1.executeQuery(sql);
	while(rs1.next())
	{
	ud.setName(rs1.getString("User_name"));
	ud.setEmail(rs1.getString("email"));
	ud.setAddress(rs1.getString("address"));
    ud.setDate(rs1.getDate("create_at").toLocalDate());
	}
	ob1.getConnection().close();
	return ud;
}
public void activateUser(UserDetails ud) throws ClassNotFoundException, SQLException, IOException
{
	String sql="update User_details set active=1 where id=?";
	ConnectionManager cm=new ConnectionManager();
	PreparedStatement pst=cm.getConnection().prepareStatement(sql);
	pst.setLong(1, ud.getId());
	pst.executeUpdate();
	cm.getConnection().close();
}
public void addMoney(UserDetails ud) throws ClassNotFoundException, SQLException, IOException
{
	ConnectionManager obj=new ConnectionManager();
    PreparedStatement pst=obj.getConnection().prepareStatement("insert into money(user_id,balance) values(?,?)");
    pst.setLong(1, ud.getId());
    pst.setDouble(2, ud.getBalance());
    pst.executeUpdate();
    obj.getConnection().close();
}
public UserDetails searchById(UserDetails ud) throws SQLException, ClassNotFoundException, IOException
{
	ConnectionManager cm=new ConnectionManager();
	Statement st=cm.getConnection().createStatement();
	ResultSet rs=st.executeQuery("select * from user_details where id="+ud.getId());
	while(rs.next())
	{
	ud.setId(rs.getLong("id"));
	ud.setName(rs.getString("user_name"));
	ud.setEmail(rs.getString("email"));
	ud.setAddress(rs.getString("address"));
	ud.setDate(rs.getDate("create_at").toLocalDate());
	ud.setActive(rs.getInt("active"));
	ud.setZip(rs.getLong("zip_code"));
	break;
	}
	cm.getConnection().close();
	ConnectionManager object=new ConnectionManager();
	Statement statement=object.getConnection().createStatement();
	ResultSet rset=statement.executeQuery("select * from money where user_id="+ud.getId());
	while(rset.next())
	{
		ud.setBalance(rset.getDouble("balance"));
		break;
	}
	object.getConnection().close();
	ConnectionManager cm1=new ConnectionManager();
	Statement st1=cm1.getConnection().createStatement();
	ResultSet rs1=st1.executeQuery("select * from zip where code="+ud.getZip());
	while(rs1.next())
	{
		ud.setCity(rs1.getString("city_name"));
		ud.setState(rs1.getString("state_name"));
	break;
	}
	System.out.println("Done");
	cm1.getConnection().close();
	return ud;
}
public UserDetails searchByName(UserDetails ud) throws SQLException, ClassNotFoundException, IOException
{
	ConnectionManager cm=new ConnectionManager();
	Statement st=cm.getConnection().createStatement();
	ResultSet rs=st.executeQuery("select * from user_details where user_name='"+ud.getName()+"'");
	while(rs.next())
	{
		ud.setId(rs.getLong("id"));
		ud.setName(rs.getString("user_name"));
		ud.setEmail(rs.getString("email"));
		ud.setAddress(rs.getString("address"));
		ud.setDate(rs.getDate("create_at").toLocalDate());
		ud.setActive(rs.getInt("active"));
		ud.setZip(rs.getLong("zip_code"));
	break;
	}
	cm.getConnection().close();
	ConnectionManager object=new ConnectionManager();
	Statement statement=object.getConnection().createStatement();
	ResultSet rset=statement.executeQuery("select * from money where user_id="+ud.getId());
	while(rset.next())
	{
		ud.setBalance(rset.getDouble("balance"));
		break;
	}
	object.getConnection().close();
	ConnectionManager cm1=new ConnectionManager();
	Statement st1=cm1.getConnection().createStatement();
	ResultSet rs1=st1.executeQuery("select * from zip where code="+ud.getZip());
	while(rs1.next())
	{
		ud.setCity(rs1.getString("city_name"));
		ud.setState(rs1.getString("state_name"));
	break;
	}
	cm1.getConnection().close();
	return ud;
}
public List<UserDetails> searchByCity(UserDetails ud) throws SQLException, ClassNotFoundException, IOException
{
	String city="'"+ud.getCity()+"'";
	String state="'"+ud.getState()+"'";
	List<UserDetails> list=new ArrayList<UserDetails>();
	ConnectionManager cm=new ConnectionManager();
	ConnectionManager ob=new ConnectionManager();
	Statement st=cm.getConnection().createStatement();
	Statement s=null;		ResultSet rs1=null;
	long zip=0;
	ResultSet rs=st.executeQuery("select * from zip where city_name="+city+" OR state_name="+state);
	while(rs.next())
	{
	zip=rs.getLong("code");
	s=ob.getConnection().createStatement();
	rs1=s.executeQuery("select * from User_details where zip_code="+zip);
	UserDetails ud1=null;
	while(rs1.next())
	{
		ud1=new UserDetails();
		ud1.setId(rs1.getLong("id"));
		ud1.setName(rs1.getString("user_name"));
		ud1.setEmail(rs1.getString("email"));
		ud1.setAddress(rs1.getString("address"));
		ud1.setDate(rs1.getDate("create_at").toLocalDate());
		ud1.setActive(rs1.getInt("active"));
		list.add(ud1);
	}
	}
	ob.getConnection().close();
	cm.getConnection().close();
	return list;
}
public void changeUserName(UserDetails ud) throws ClassNotFoundException, SQLException, IOException
{
	ConnectionManager ob=new ConnectionManager();
	PreparedStatement ps1=ob.getConnection().prepareStatement("update user_details set user_name=? where id=?");
	ps1.setString(1, ud.getName());
	ps1.setLong(2, ud.getId());
	ps1.executeUpdate();
	ob.getConnection().close();
}
public void changeEmail(UserDetails ud) throws ClassNotFoundException, SQLException, IOException
{
	ConnectionManager ob=new ConnectionManager();
	PreparedStatement ps2=ob.getConnection().prepareStatement("update user_details set email=? where id=?");
	ps2.setString(1, ud.getEmail());
	ps2.setLong(2, ud.getId());
	ps2.executeUpdate();
	ob.getConnection().close();
}
public void changeAddress(UserDetails ud) throws ClassNotFoundException, SQLException, IOException
{
	ConnectionManager ob=new ConnectionManager();
	PreparedStatement ps3=ob.getConnection().prepareStatement("update user_details set address=? where id=?");
	ps3.setString(1, ud.getAddress());
	ps3.setLong(2, ud.getId());
	ps3.executeUpdate();
	ob.getConnection().close();
}
public void changePass(UserDetails ud) throws ClassNotFoundException, SQLException, IOException
{
	ConnectionManager ob=new ConnectionManager();
	PreparedStatement ps4=ob.getConnection().prepareStatement("update user_details set passwords=? where id=?");
	ps4.setString(1, ud.getPassword());
	ps4.setLong(2, ud.getId());
	ps4.executeUpdate();
	ob.getConnection().close();
}
public void changeAPassword(Admin admin) throws ClassNotFoundException, SQLException, IOException
{
	ConnectionManager cm=new ConnectionManager();
	PreparedStatement ps=cm.getConnection().prepareStatement("Update admin_log set password=? where admin_name=?");
	ps.setString(1, admin.getPassword());
	ps.setString(2, admin.getName());
	ps.executeUpdate();
	cm.getConnection().close();
}
public void addAdminFunction(Admin admin,long id) throws ClassNotFoundException, SQLException, IOException
{
	ConnectionManager cm=new ConnectionManager();
	PreparedStatement ps=cm.getConnection().prepareStatement("insert into admin_log(id,admin_name,password) values(?,?,?)");
	ps.setLong(1, id);
	ps.setString(2,admin.getName());
	ps.setString(3, admin.getPassword());
	ps.executeUpdate();
	cm.getConnection().close();
}
}
