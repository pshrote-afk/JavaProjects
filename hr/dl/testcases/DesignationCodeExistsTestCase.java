import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
public class DesignationCodeExistsTestCase
{
public static void main(String gg[])
{
int code = Integer.parseInt(gg[0]);
try
{
DesignationDTOInterface designationDTO;

DesignationDAOInterface designationDAO;
designationDAO = new DesignationDAO();
System.out.println(code+ " exists: "+ designationDAO.codeExists(code));

}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}