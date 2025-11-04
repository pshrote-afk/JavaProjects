import java.util.*;
import java.io.*;
class City implements Serializable
{
public int code;
public String name;
}
class Student implements Serializable
{
public int rollNumber;
public String name;
public char gender;
public City city;
}
class byteArray
{
public static void main(String gg[])
{
try
{
Student s1 = new Student();
s1.rollNumber = 13;
s1.name = "Rahulmani";
s1.gender = 'F';
City c1 = new City();
c1.code = 3;
c1.name = "Timbuktu";
s1.city = c1;

Student s2 = new Student();
s2.rollNumber = 14;
s2.name = "Kiran";
s2.gender = 'M';
City c2 = new City();
c2.code = 5;
c2.name = "Kyoto";
s2.city = c2;

//Part 1
ByteArrayOutputStream baos = new ByteArrayOutputStream();
ObjectOutputStream oos = new ObjectOutputStream(baos);
oos.writeObject(s1);
oos.writeObject(s2);
oos.flush();

//Part 2
byte b[];
b = baos.toByteArray();
System.out.println("Object serialized: "+b);
System.out.println("Object serialized: "+Arrays.toString(b));

//Part 3
ByteArrayInputStream bais = new ByteArrayInputStream(b);
ObjectInputStream ois = new ObjectInputStream(bais);
Student s3 = (Student)ois.readObject();
System.out.println("Object de-serialzed");
System.out.println("Roll: " + s3.rollNumber);
System.out.println("Name: " + s3.name);
System.out.println("Gender: " + s3.gender);
System.out.println("City Code: " + s3.city.code);
System.out.println("City Name: " + s3.city.name);

Student s4 = (Student) ois.readObject();
System.out.println("\nObject de-serialized");
System.out.println("Roll: " + s4.rollNumber);
System.out.println("Name: " + s4.name);
System.out.println("Gender: " + s4.gender);
System.out.println("City Code: " + s4.city.code);
System.out.println("City Name: " + s4.city.name);

}catch(Exception e)
{
System.out.println(e);
}
}
}