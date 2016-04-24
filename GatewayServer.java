import gnu.io.*;
import java.io.*;
import java.net.*;
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
		    if(inputLine.equals("c3bf447eabe632720a3aa1a7ce401274")) {
		    	openGateway();
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
