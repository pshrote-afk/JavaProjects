import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import java.util.*;

public class CourseGetByTitleTestCase {
  public static void main(String gg[]) {
    try {
      String title = gg[0];
      CourseDTOInterface courseDTO;
      CourseDAOInterface courseDAO;
      courseDAO = new CourseDAO();
      courseDTO = courseDAO.getByTitle(title);
      System.out.println(courseDTO.getCode());
      System.out.println(courseDTO.getTitle());
    } catch (DAOException daoException) {
      System.out.println(daoException.getMessage());
    }
  }
}
