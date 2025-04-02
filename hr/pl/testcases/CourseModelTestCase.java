import com.thinking.machines.hr.pl.model.*;

import javax.swing.*;
import java.awt.*;

class CourseTable extends JFrame
{
private JTable table;
private Container container;
private JScrollPane jsp;
public CourseTable()
{
CourseModel courseModel = new CourseModel();
table = new JTable(courseModel);
jsp = new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
container = getContentPane();
//container.add(table); //directly add scroll pane
container.add(jsp);

setSize(500,600);
setLocation(960,540);
setVisible(true);
}
}
class CourseModelTestCase
{
public static void main(String gg[])
{
CourseTable courseTable = new CourseTable();
}
}