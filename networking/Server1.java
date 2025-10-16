import java.net.*;		//for SocketServer and Socket classes
import java.io.*;		//for Input/Output streams
public class Server1
{
private ServerSocket serverSocket;
public Server1()
{
try
{
serverSocket = new ServerSocket(5500);
startListening();
}catch(Exception e)
{
System.out.println(e.getMessage());
}
}

private void startListening()
{
Socket socket;
OutputStream os;
OutputStreamWriter osw;
InputStream is;
InputStreamReader isr;
StringBuffer sb;

while(true)
{
System.out.println("Server ready to listen on port 5500");
try
{
socket = serverSocket.accept();

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
String request = sb.toString();
System.out.println("Request arrived: "+request);

String pc1,pc2,pc3;
int c1,c2;
c1 = request.indexOf(",");
c2 = request.indexOf(",",c1+1);

pc1 = request.substring(0,c1);
pc2 = request.substring(c1+1,c2);
pc3 = request.substring(c2+1);

int roll = Integer.parseInt(pc1);
String name = pc2;
String city = pc3;
System.out.println("Data: Roll: "+roll+", Name: "+name+", City: "+city);

String response = "Data saved#";

os = socket.getOutputStream();
osw = new OutputStreamWriter(os);
osw.write(response);
osw.flush();

socket.close();
}catch(IOException io)
{
System.out.println(io.getMessage());
}
}

}

public static void main(String gg[])
{
Server1 server1 = new Server1();
}
}



