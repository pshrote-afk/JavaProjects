import java.net.*;
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
public class Client3
{
public static void main(String gg[])
{
try
{
int roll = Integer.parseInt(gg[0]);
String name = gg[1];
String gender = gg[2];
int cityCode = Integer.parseInt(gg[3]);
String cityName = gg[4];
City c1 = new City();
c1.code = cityCode;
c1.name = cityName; 
Student s1 = new Student();
s1.rollNumber=101;
s1.name="Sameer";
s1.gender='M';
s1.city=c1;

ByteArrayOutputStream baos = new ByteArrayOutputStream();
ObjectOutputStream oos = new ObjectOutputStream(baos);
oos.writeObject(s1);
oos.flush();
byte objectBytes[];
objectBytes = baos.toByteArray();

//header
int requestLength = objectBytes.length;
byte header[] = new byte[1024];
int x;
int i;
i = 1023;
x = requestLength;
while(x>0)
{
header[i] = (byte)(x%10);
x=x/10;
i--;
}
Socket socket = new Socket("localhost",5500);
OutputStream os = socket.getOutputStream();
os.write(header,0,1024);  	//source, from which index, how many
os.flush();

//receiving request data, after receiving acknowledgement
InputStream is = socket.getInputStream();
byte ack[] = new byte[1];
int bytesReadCount;
while(true)
{
bytesReadCount = is.read(ack);		//after waiting for a long time and receiving nothing, read returns -1
if(bytesReadCount==-1) continue;
break;
}
int bytesToSend = requestLength;
int chunkSize = 1024;
int j = 0;
while(j<bytesToSend)
{
if((bytesToSend - j) < chunkSize) chunkSize = bytesToSend - j;
os.write(objectBytes,j,chunkSize);
os.flush();
j = j + chunkSize;
}

//receive response
//first receive header
int bytesToReceive = 1024;
byte tmp[] = new byte[1024];
int k;
i = 0;
j = 0;
while(j<bytesToReceive)
{
bytesReadCount = is.read(tmp);
if(bytesReadCount==-1) continue;
for(k=0;k<bytesReadCount;k++)
{
header[i] = tmp[k];
i++;
}
j = j + bytesReadCount;
}
int responseLength = 0;
i = 1;
j = 1023;
while(j>=0)
{
responseLength = responseLength + (header[j] * i);
i = i*10;
j--;
}
System.out.println("Header received: " + responseLength);
ack[0] = 1;
os.write(ack,0,1);
os.flush();
System.out.println("Acknowledgement sent");

//receive response
byte response[] = new byte[responseLength];
bytesToReceive = responseLength;
i=0;
j=0;
System.out.println("Now receiving response");
while(j<bytesToReceive)
{
bytesReadCount = is.read(tmp);
if(bytesReadCount==-1) continue;
for(k=0;k<bytesReadCount;k++)
{
response[i] = tmp[k];
i++;
}
j = j + bytesReadCount;
}
System.out.println("Response received");
ack[0] = 1;
os.write(ack);
os.flush();
socket.close();

ByteArrayInputStream bais = new ByteArrayInputStream(response);
ObjectInputStream ois = new ObjectInputStream(bais);
String responseString = (String)ois.readObject();
System.out.println("Response is: "+responseString);

}catch(Exception e)
{
System.out.println(e);
}
}
}//end of Client3






































