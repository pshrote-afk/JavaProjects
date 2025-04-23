package com.thinking.machines.hr.pl.model;
import java.util.*; //for List
import javax.swing.*;
import javax.swing.table.*; //for AbstractTableModel
import java.io.*; //for File class
//itext
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*; //for Paragraph
import com.itextpdf.kernel.font.*;
import com.itextpdf.io.font.constants.*;
import com.itextpdf.layout.property.*;
import com.itextpdf.io.image.*;
//BL
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
public class CourseModel extends AbstractTableModel
{
private String columnTitle[];
private java.util.List<CourseInterface> plCoursesList;
private CourseManagerInterface courseManager;
public CourseModel()
{
populateDataStructure();
}
private void populateDataStructure() 
{
columnTitle = new String[2];
columnTitle[0] = "Sr.No.";
columnTitle[1] = "Course";
plCoursesList = new LinkedList<>();
try
{
courseManager = CourseManager.getCourseManager();
}catch(BLException blException)
{
//????? do what
}
Set<CourseInterface> courses = courseManager.getCourses();
Iterator<CourseInterface> iterator = courses.iterator();
CourseInterface tmpCourse;
while(iterator.hasNext())
{
tmpCourse = iterator.next();
plCoursesList.add(tmpCourse);
}
Collections.sort(this.plCoursesList,new Comparator<CourseInterface>(){
public int compare(CourseInterface left,CourseInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
}
public int getRowCount()
{
return plCoursesList.size();
}
public int getColumnCount()
{
return columnTitle.length;
}
public String getColumnName(int columnIndex)
{
if(columnIndex==0) return columnTitle[0];
if(columnIndex==1) return columnTitle[1];
return null;
}
public Class getColumnClass(int columnIndex)
{
if(columnIndex==0) return Integer.class;
if(columnIndex==1) return String.class;
return null;
}
public Object getValueAt(int rowIndex,int columnIndex)
{
if(columnIndex==0) return rowIndex+1;
else return plCoursesList.get(rowIndex).getTitle();
}
public boolean isCellEditable(int rowIndex,int columnIndex)
{
return false;
}
//application specific methods
public void add(CourseInterface course) throws BLException
{
courseManager.addCourse(course); //not in a try-catch cause method does not handle error, it throws it
this.plCoursesList.add(course);
Collections.sort(this.plCoursesList,new Comparator<CourseInterface>(){
public int compare(CourseInterface left,CourseInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
fireTableDataChanged();
}
public void update(CourseInterface course) throws BLException
{
courseManager.updateCourse(course);
plCoursesList.remove(indexOfCourse(course));
plCoursesList.add(course);
Collections.sort(this.plCoursesList,new Comparator<CourseInterface>(){
public int compare(CourseInterface left,CourseInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
fireTableDataChanged();
}
public void remove(int code) throws BLException
{
courseManager.removeCourse(code); //may throw exception like "invalid code"
Iterator<CourseInterface> iterator = this.plCoursesList.iterator();
int index = 0;
CourseInterface tmpCourse;
while(iterator.hasNext())
{
tmpCourse = iterator.next();
if(tmpCourse.getCode() == code)
{
this.plCoursesList.remove(index);
break;
}
index++;
}
fireTableDataChanged();
}

public void exportToPDF(File file) throws BLException
{
if(file.getName().endsWith(".pdf")==false)
{
String newFileName = file.getName() + ".pdf";
String path = file.getParent();
if(path==null)
{
path = ".";
}
file = new File(path,newFileName);
}
try
{
PdfWriter pdfWriter = new PdfWriter(file);
PdfDocument pdfDocument = new PdfDocument(pdfWriter);
Document document = new Document(pdfDocument);
int r = 0;
int sno = 0;
int pageSize = 5;
int pageNumber = 0;
boolean newPage = true;
Table pdfTable = null;
document.setMargins(15,15,15,15);
while(r < plCoursesList.size())
{
if(newPage==true) //create header,
{
Image companyLogo = new Image(ImageDataFactory.create("C:/JavaProjects/hr/pl/src/main/java/com/thinking/machines/hr/pl/icons/company-logo.png"));
companyLogo.setFixedPosition(0,800);
companyLogo.scaleToFit(50,50);
Paragraph companyName = new Paragraph().setTextAlignment(TextAlignment.CENTER);
companyName.add("Company Name");

pageNumber++;
Paragraph pageNumberParagraph = new Paragraph().setTextAlignment(TextAlignment.RIGHT);
pageNumberParagraph.add(String.valueOf(pageNumber));

Paragraph reportTitle = new Paragraph(); 
reportTitle.add("Courses").setTextAlignment(TextAlignment.LEFT);

pdfTable = new Table(2);
pdfTable.addHeaderCell("Sr.No.");
pdfTable.addHeaderCell("Course");

document.add(companyLogo);
document.add(companyName);
document.add(pageNumberParagraph);
document.add(reportTitle);
newPage = false;
}
sno++;
//add row
pdfTable.addCell(new Cell().add(new Paragraph(String.valueOf(sno))));
pdfTable.addCell(new Cell().add(new Paragraph(plCoursesList.get(r).getTitle())));

if(sno%pageSize==0 || sno==plCoursesList.size())
{
//create footer
document.add(pdfTable);
document.add(new Paragraph("Software by: Paras Shrote\nwww.github.com/pshrote-afk"));
//
if(sno < plCoursesList.size())
{
pdfDocument.addNewPage();
document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
}
newPage=true;
}

r++;
}
document.close();
}catch(Exception exception)
{
BLException blException = new BLException();
blException.setGenericException(exception.getMessage());
throw blException;
}
}

public int indexOfCourse(CourseInterface course) throws BLException
{
Iterator<CourseInterface> iterator = this.plCoursesList.iterator();
int index = 0;
CourseInterface tmpCourse;
while(iterator.hasNext())
{
tmpCourse = iterator.next();
if(tmpCourse.equals(course)) //deep comparison. CourseInterface implements Comparable which means we have written "equals()" method which compares on the basis of "course code" in its implementation
{
break;
}
index++;
}
if(index == plCoursesList.size()) //control will never reach here, cause if course doesnt exist then BL will indicate even before control enters this function
{
BLException blException = new BLException();
blException.setGenericException("Invalid course: "+course.getTitle());
throw blException;
}
return index;
}
public int indexOfTitle(String title,boolean leftPartialSearch) throws BLException
{
int index = 0;
CourseInterface tmpCourse;
Iterator<CourseInterface> iterator = plCoursesList.iterator();
while(iterator.hasNext())
{
tmpCourse = iterator.next();
if(leftPartialSearch)
{
if(tmpCourse.getTitle().toUpperCase().startsWith(title.toUpperCase())) //rethink //done done
{
return index;
}
}
else
{
if(title.equalsIgnoreCase(tmpCourse.getTitle()))
{
break;
}
}
index++;
}
if(index == plCoursesList.size())
{
BLException blException = new BLException();
blException.setGenericException("Invalid title: "+title);
throw blException;
}
return index;
}
public CourseInterface getCourseAt(int rowIndex) throws BLException
{
CourseInterface tmpCourse;
try
{
tmpCourse = this.plCoursesList.get(rowIndex);
}catch(IndexOutOfBoundsException indexOutOfBoundsException)
{
BLException blException = new BLException();
blException.setGenericException(indexOutOfBoundsException.getMessage());
throw blException;
}
return tmpCourse;
}

} //end of class