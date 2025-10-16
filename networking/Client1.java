import java.net.*;		//for Socket class
import java.io.*;
public class Client1
{
private Socket socket;
public Client1()
{
try
{
socket = new Socket("127.0.0.1",5500);
//socket = new Socket("localhost",5500);
}catch(UnknownHostException uhe)
{
System.out.println(uhe);
}
catch(IOException ioe)
{
System.out.println(ioe);
}

}

public static void main(String gg[])
{
int roll = Integer.parseInt(gg[0]);
String name = gg[1];
String city = gg[2];
Client1 client1 = new Client1();
String request = roll+","+name+","+city+"#";

OutputStream os;
OutputStreamWriter osw;
InputStream is;
InputStreamReader isr;
try
{
os = client1.socket.getOutputStream();
osw = new OutputStreamWriter(os);
osw.write(request);
osw.flush();

is = client1.socket.getInputStream();
isr = new InputStreamReader(is);
int x;
StringBuffer sb = new StringBuffer();
while(true)
{
x = isr.read();
if(x==-1) break;
if(x=='#') break;
sb.append((char)x);
}
String response = sb.toString();
System.out.println("Response arrived: "+response);
client1.socket.close();
}catch(IOException io)
{
System.out.println(io);
}
}
}