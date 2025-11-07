import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;

public class CourseDeleteTestCase {
  public static void main(String gg[]) {
    try {
      int code = Integer.parseInt(gg[0]);
      CourseDAOInterface courseDAO;
      courseDAO = new CourseDAO();
      courseDAO.delete(code);
      System.out.println("Record deleted.");
    } catch (DAOException daoException) {
      System.out.println(daoException.getMessage());
    }
  }
}
