import java.net.Socket;
import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

public class MyClient {
	private int _port;
	private String _ip;
	private Socket socket;
	public MyClient(String ip,int port) {
		_port = port;
		_ip = ip;
	}

	public void setPort(int inport) {
		_port = inport;
	}

	public void setIP(String inip) {
		_ip = inip;
	}

	public boolean connect(){
		boolean output = false;
		try{
			socket = new Socket(_ip, _port);
			System.out.println("Connected\n");
			output=true;
		}
		catch(Exception ex){
			System.out.println(ex.toString());
		}
		return output;
	}
	
	public boolean disconnect(){
		boolean output = false;
		try{
			socket.close();
			System.out.println("Disconnected");
			output=true;
		}
		catch(Exception ex){
			System.out.println(ex.toString());
		}
		return output;
	}
	
  public void startCient(String msg) throws Exception {
	String output = "";
    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
     BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
    String message="";
	out.println(msg);
	try{
		while((message = in.readLine())!=null){
			if(message.startsWith(".turn:")){
				if(message.equals(".turn:1")){
					String userInput;
					while ((userInput = stdIn.readLine()) != null) {
						out.println(userInput);
						break;
					}
				}
			}
			else{
				System.out.println(message);
			}
		}
	}
	catch(Exception ex){}
  }
}
