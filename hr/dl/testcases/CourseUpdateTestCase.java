import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
public class CourseUpdateTestCase
{
public static void main(String gg[])
{
try
{
int code = Integer.parseInt(gg[0]);
String title = gg[1];
CourseDTOInterface courseDTO = new CourseDTO();
courseDTO.setCode(code);
courseDTO.setTitle(title);

CourseDAOInterface courseDAO;
courseDAO = new CourseDAO();
courseDAO.update(courseDTO);
System.out.println("Record updated.");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}