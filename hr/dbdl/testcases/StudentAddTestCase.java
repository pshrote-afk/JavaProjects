import com.thinking.machines.enums.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import java.math.*;
import java.text.*;
import java.util.*;

public class StudentAddTestCase {
  public static void main(String gg[]) {
    SimpleDateFormat sdf;
    sdf = new SimpleDateFormat("dd/MM/yyyy");
    try {
      /*
      private String rollNo;//to be generated
      private String name;
      private int courseCode;
      private Date dateOfBirth;
      private char gender;
      private boolean isIndian;
      private BigDecimal fees;
      private String enrollmentNumber;
      private String aadharCardNumber;
      */
      String name = gg[0];
      int courseCode = Integer.parseInt(gg[1]);
      Date dateOfBirth = null;
      try {
        dateOfBirth = sdf.parse(gg[2]);
      } catch (ParseException pe) {
        System.out.println(pe.getMessage());
      }
      char gender = gg[3].charAt(0);
      boolean isIndian = Boolean.parseBoolean(gg[4]);
      BigDecimal fees = new BigDecimal(gg[5]);
      String enrollmentNumber = gg[6];
      String aadharCardNumber = gg[7];

      StudentDTOInterface studentDTO;
      studentDTO = new StudentDTO();
      studentDTO.setName(name);
      studentDTO.setCourseCode(courseCode);
      studentDTO.setDateOfBirth(dateOfBirth);
      if (gender == 'M') studentDTO.setGender(GENDER.MALE);
      if (gender == 'F') studentDTO.setGender(GENDER.FEMALE);
      studentDTO.setIsIndian(isIndian);
      studentDTO.setFees(fees);
      studentDTO.setEnrollmentNumber(enrollmentNumber);
      studentDTO.setAadharCardNumber(aadharCardNumber);

      StudentDAOInterface studentDAO;
      studentDAO = new StudentDAO();
      studentDAO.add(studentDTO);
      System.out.println("Record added with roll no (" + studentDTO.getRollNo() + ")");
    } catch (DAOException daoException) {
      System.out.println(daoException.getMessage());
    }
  }
}
