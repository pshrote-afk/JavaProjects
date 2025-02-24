import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
public class StudentIsCourseAllottedTestCase
{
public static void main(String gg[])
{
try
{
int code = Integer.parseInt(gg[0]);
StudentDAOInterface studentDAO = new StudentDAO();
boolean isCourseAllotted = studentDAO.isCourseAllotted(code);
System.out.println(isCourseAllotted);
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}