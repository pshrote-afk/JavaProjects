import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
public class StudentDeleteTestCase
{
public static void main(String gg[])
{
try
{
String rollNo = gg[0];
StudentDAOInterface studentDAO = new StudentDAO();
studentDAO.delete(rollNo);
System.out.println("Record deleted.");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}