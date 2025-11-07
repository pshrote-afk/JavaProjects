import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import java.util.*;

public class CourseGetByCodeTestCase {
  public static void main(String gg[]) {
    try {
      int code = Integer.parseInt(gg[0]);
      CourseDTOInterface courseDTO;
      CourseDAOInterface courseDAO;
      courseDAO = new CourseDAO();
      courseDTO = courseDAO.getByCode(code);
      System.out.println(courseDTO.getCode());
      System.out.println(courseDTO.getTitle());
    } catch (DAOException daoException) {
      System.out.println(daoException.getMessage());
    }
  }
}
