import java.util.*; //for List
import javax.swing.*; //for JFrame
import javax.swing.table.*;
import java.awt.*; //for Container class
//DL
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;

class CourseTableModel extends AbstractTableModel
{
private Object data[][];
private String heading[];
CourseTableModel(Object data[][],String heading[])
{
this.data = data;
this.heading = heading;
}
public int getRowCount()
{
return data.length;
}
public int getColumnCount()
{
return heading.length;
}
public String getColumnName(int columnIndex)
{
return heading[columnIndex];
}
public Class getColumnClass(int columnIndex)
{
Class c = null;
try
{
if(columnIndex==0)
{
c = Class.forName("java.lang.Integer");
}
if(columnIndex==1)
{
c = Class.forName("java.lang.String");
}
}catch(ClassNotFoundException cnfe)
{
System.out.println(cnfe.getMessage());
}
return c;
}
public Object getValueAt(int rowIndex,int columnIndex)
{
return data[rowIndex][columnIndex];
}
public boolean isCellEditable(int rowIndex,int columnIndex)
{
return false;
}

}

class swing1 extends JFrame //cause we want label, button, etc
{
private Container container;
private JTable table;
private JScrollPane jsp;
swing1(AbstractTableModel courseTableModel)
{
table = new JTable(courseTableModel);
container = getContentPane();
container.setLayout(new FlowLayout());
container.add(table);
jsp = new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
container.add(jsp);
Font font = new Font("Papyrus",Font.ITALIC,24);
table.setFont(font);
table.setRowHeight(30);
//reorder disallowed
table.getTableHeader().setReorderingAllowed(false);
//multi select disallowed
table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//set default close
this.setDefaultCloseOperation(EXIT_ON_CLOSE);

Dimension d = Toolkit.getDefaultToolkit().getScreenSize(); //to get default toolkit of OS
int width = 700;
int height = 700;
setSize(width,height);
int x = d.width/2 - width/2;
int y = d.height/2 - height/2;
setLocation(x,y);
setVisible(true);
}


}


class psp
{
public static void main(String gg[])
{
Object data[][];
try
{
Set<CourseDTOInterface> dataLayerSet = new HashSet<>();
dataLayerSet = new CourseDAO().getAll();
data = new Object[dataLayerSet.size()][2];
int i=0;
for(CourseDTOInterface courseDTO:dataLayerSet)
{
data[i][0] = courseDTO.getCode();
data[i][1] = courseDTO.getTitle();
System.out.println("Code: "+data[i][0]+", Course: "+data[i][1]);
i++;
}
String heading[] = new String[2];
heading[0] = "Course Code";
heading[1] = "Course Title";
CourseTableModel courseTableModel = new CourseTableModel(data,heading);
swing1 s = new swing1(courseTableModel);



}catch(DAOException dao)
{
System.out.println(dao.getMessage());
}




}
}