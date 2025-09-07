import java.net.*;
import java.io.*;
class Server1
{
private ServerSocket serverSocket;
private Socket socket;
Server1()
{
serverSocket = new ServerSocket(5500);
startListening();
}
private void startListening()
{
InputStream is;
InputStreamReader isr;
OutputStream os;
OutputStreamWriter osw;
StringBuffer sb;
int x;
int c1,c2;
String pc1,pc2,pc3;
String request,response;
try
{
while(true)
{
System.out.println("Server is listening on port 5500");
socket = serverSocket.accept();

is = socket.getInputStream();
isr = new InputStreamReader(is);
sb = new StringBuffer();
while(true)
{
x = isr.read();
if(x==-1) break;
if(x=='#') break;
sb.append((char)x);
}
request = sb.toString();
System.out.println("Request received: "+request);
c1 = request.indexOf(",");
c2 = request.indexOf(",",c1+1);
pc1 = request.substring(0,c1);
pc2 = request.substring(c1+1,c2);
pc3 = request.substring(c2+1);

int roll = Integer.parseInt(pc1);
String name = pc2;
String gender = pc3;

System.out.printf("Roll: %d, Name: %s, Gender: %s.\n",roll,name,gender);

response = "Data Saved#";

os = socket.getOutputStream();
osw = new OutputStreamWriter(os);
osw.write(response);
osw.flush();

socket.close();



}
}catch(Exception e)
{
System.out.println(e);
}
} //end of startListening()
public static void main(String gg[])
{
Server1 server = new Server1();
}
}