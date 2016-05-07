import gnu.io.*;
import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

public class GatewayServer {
    
    
    static int port = 4444;

    public static void main(String args[]){
	ServerSocket socket = null;
	Socket clientSocket = null;
	try {
	    socket = new ServerSocket(port);
	    
	    while(true) {
		clientSocket = socket.accept();
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		
		String inputLine;

		while((inputLine = in.readLine()) != null) {
			try{
				/*Initialized MD5 object*/
				MessageDigest md = MessageDigest.getInstance("MD5");
				/*Got server current time*/
				long yourmilliseconds = System.currentTimeMillis();
				/*Format server current time*/
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
				/*Created object with formated server current time*/
				Date resultdate = new Date(yourmilliseconds);
				/*MD5 hashing*/
				byte[] digest = md.digest(sdf.format(resultdate).getBytes("UTF-8"));
				/*Retrieve string from md5 bytes sequence*/
				StringBuffer sb = new StringBuffer();
				for (byte b : digest) {
					sb.append(String.format("%02x", b & 0xff));
				}	
				/*Checking two hashes on equals if it's equal open the gate*/
				if(inputLine.equals(sb.toString())) {
                                    openGateway();
                                }		
			}catch(NoSuchAlgorithmException ex){
				System.out.println(ex.getMessage());
			}
		}
		
		out.close();
		in.close();
        clientSocket.close();
	    }
	    
	}
	catch (IOException e) {
	    System.out.println("Cannot bind "+port+" port");
	}
    }
    
    private static void openGateway(){
	Enumeration portList = null;
	SerialPort serialPort = null;
	CommPortIdentifier portId = null;
	
	
	try{
	    portList = CommPortIdentifier.getPortIdentifiers();
	    portId = (CommPortIdentifier)portList.nextElement();
	    serialPort = (SerialPort)portId.open("GatewayOpen", 2000);
	    Thread.sleep(2000);
	    serialPort.close();
	}
	catch(Exception e) {
	    System.out.println(e);
	}	
    }
}
