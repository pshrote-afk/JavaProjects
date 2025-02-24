import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.enums.*;
import java.util.*;	//for TreeSet
import java.math.*;	//for BigDecimal class
import java.text.*;	//for SimpleDateFormat
public class StudentRollNoExistsTestCase
{
public static void main(String gg[])
{
try
{
StudentDTOInterface studentDTO;
StudentDAOInterface studentDAO;
studentDAO = new StudentDAO();
boolean rollNoExists=studentDAO.rollNoExists(gg[0]);
System.out.println(rollNoExists);
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}