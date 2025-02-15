import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
public class StudentGetCountTestCase
{
public static void main(String gg[])
{
int count=0;
try
{
StudentDAOInterface studentDAO;
studentDAO = new StudentDAO();
count=studentDAO.getCount();
System.out.println("Number of students: "+count);
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}