package service;
import java.io.IOException;
import java.sql.SQLException;

import model.*;
public abstract class UserValidation 
{
public abstract long userValidation(User user) throws SQLException, ClassNotFoundException, IOException;
}
