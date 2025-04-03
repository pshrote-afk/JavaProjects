import com.thinking.machines.hr.pl.model.*; //for CourseModel which extends AbstractTableModel
import java.awt.*; //for Container
import javax.swing.*; //for JFrame
class CourseModelTestCase extends JFrame
{
private Container container;
private JTable table;
private JScrollPane jsp;
private CourseModel courseModel;
CourseModelTestCase()
{
container = getContentPane();
courseModel = new CourseModel();
table = new JTable(courseModel);
jsp = new JScrollPane(this.table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
container.add(jsp);
this.setDefaultCloseOperation(EXIT_ON_CLOSE);
setSize(400,600);
setLocation(960,540);
setVisible(true);
}
public static void main(String gg[])
{
CourseModelTestCase obj1 = new CourseModelTestCase();
}
}