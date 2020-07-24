package service;
import java.io.IOException;
import java.sql.SQLException;

import model.Admin;
public interface AdminValidation {
	public boolean adminValidation(Admin admin) throws SQLException, ClassNotFoundException, IOException;

}
