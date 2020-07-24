package utility;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;


public class ConnectionManager {
	public Connection getConnection() throws IOException, ClassNotFoundException, SQLException
	{
		Properties prop=null;
		prop=loadPropertiesFile();
		final String url=prop.getProperty("url");
		final String driver=prop.getProperty("driver");
		Class.forName(driver);
		Connection ob=null;
		ob=DriverManager.getConnection(url);
		return ob;
	}
public Properties loadPropertiesFile() throws IOException
{
	Properties prop=new Properties();
	InputStream in=ConnectionManager.class.getClassLoader().getResourceAsStream("jdbc.properties");
	prop.load(in);
	in.close();
	return prop;
}
}
