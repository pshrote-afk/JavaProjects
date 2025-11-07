import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import java.util.*;

public class CourseGetAllTestCase {
  public static void main(String gg[]) {
    try {
      Set<CourseDTOInterface> treeSet1;

      CourseDAOInterface courseDAO;
      courseDAO = new CourseDAO();
      treeSet1 = courseDAO.getAll();
      System.out.println("Iterating once with \"forEach()\" lambda expression:");

      treeSet1.forEach(
          (courseDTO) -> {
            System.out.println(courseDTO.getCode());
            System.out.println(courseDTO.getTitle());
            System.out.println("*************");
          });

      Iterator<CourseDTOInterface> iterator1 = treeSet1.iterator();
      CourseDTOInterface courseDTO = new CourseDTO();
      System.out.println("\nIterating twice with \"hasNext()\":");
      while (iterator1.hasNext()) {
        courseDTO = iterator1.next();
        System.out.println(courseDTO.getCode());
        System.out.println(courseDTO.getTitle());
        System.out.println("*************");
      }
    } catch (DAOException daoException) {
      System.out.println(daoException.getMessage());
    }
  }
}
