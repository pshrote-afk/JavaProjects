import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import java.util.*;

public class CourseAddTestCase {
  public static void main(String gg[]) {
    try {
      String title = gg[0];

      CourseDTOInterface courseDTO = new CourseDTO();
      courseDTO.setTitle(title);

      CourseDAOInterface courseDAO;
      courseDAO = new CourseDAO();
      courseDAO.add(courseDTO);
      System.out.println("Record added.");
    } catch (DAOException daoException) {
      System.out.println(daoException.getMessage());
    }
  }
}
