import java.net.*;
import java.io.*;
class serverclient extends Thread
{
	Socket sock;
	int clientNo;
	int square;
	serverclient(Socket sock, int count)
	{
		this.sock = sock;
		clientNo = count;
	}
	public void run()
	{
		try
		{
			DataInputStream inStream = new DataInputStream(sock.getInputStream());
      		DataOutputStream outStream = new DataOutputStream(sock.getOutputStream());
      		String clientMessage = "", serverMessage = "";
      		clientMessage = inStream.readUTF();
      		while(!clientMessage.equals("bye"))
      		{
	        	
	        	System.out.println("From Client " +clientNo+ ", the number is: "+clientMessage+"\n");
	        	square = Integer.parseInt(clientMessage) * Integer.parseInt(clientMessage);
	        	serverMessage="From Server to Client-" + clientNo + " Square of " + clientMessage + " is " +square;
	        	outStream.writeUTF(serverMessage);
	        	outStream.flush();
	        	clientMessage = inStream.readUTF();
            }
            serverMessage = "From Server to Client- "+clientNo+":bye";
            outStream.writeUTF(serverMessage);
	        inStream.close();
	        outStream.close();
	        sock.close();
    	}
		catch(Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			System.out.println("Client - "+clientNo+" exit!!");
		}
	}
}

class server
{
	public static void main(String args[]) throws Exception
	{
		try
		{
			ServerSocket serv = new ServerSocket(9999);
			int count =0;
			System.out.println("Server started .....");

			while(true)
			{
				count++;
				Socket sock = serv.accept();
				System.out.println("Client no :"+count+" is connected!");
				Thread t = new serverclient(sock,count);
				t.start();

			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}