import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;

public class DesignationGetAllTestCase
{
public static void main(String gg[])
{
try
{
DesignationDAOInterface designationDAO = new DesignationDAO();

TreeSet<DesignationDTOInterface> set1 = new TreeSet<>();

set1 = designationDAO.getAll();

//DesignationDTOInterface designationDTO;

set1.forEach((designationDTO)->{
System.out.println("Code: " +designationDTO.getCode()+ " , Title: " +designationDTO.getTitle()+ "." );
});

}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}