import java.net.*;
import java.io.*;
public class Client2
{
public static void main(String gg[])
{
int roll = Integer.parseInt(gg[0]);
String name = gg[1];
String gender = gg[2];

InputStream is;
InputStreamReader isr;
OutputStream os;
OutputStreamWriter osw;
StringBuffer sb;
String request,response;
request = roll + "," + name + "," + gender + "#";
try
{
Socket socket = new Socket("127.0.0.1",5500);
os = socket.getOutputStream();
osw = new OutputStreamWriter(os);
osw.write(request);
osw.flush();
//after this does the control wait for response?
is = socket.getInputStream();
isr = new InputStreamReader(is);

sb = new StringBuffer();
int x;
while(true)
{
x = isr.read();
if(x==-1) break;
if(x=='#') break;
sb.append((char)x);
}
response = sb.toString();
System.out.println(response);
socket.close();

}catch(Exception e)
{
System.out.println(e);
}
}
}