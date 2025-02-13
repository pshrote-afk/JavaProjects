import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
public class CourseCodeExistsTestCase
{
public static void main(String gg[])
{
try
{
int code = Integer.parseInt(gg[0]);
CourseDAOInterface courseDAO = new CourseDAO();
boolean codeExists = courseDAO.codeExists(code);
System.out.println("Code exists: "+codeExists);
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}