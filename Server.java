

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import java.net.ServerSocket;
import org.json.*;


public class Server {
	
	
	private ServerSocket server;
	
	private URL url=new URL("http://kitsu.io/api/oauth/token");
	private HttpURLConnection connection = null;

//Constructeur 
	public Server (InetSocketAddress bindpoint, int timeout) throws IOException{
		
		this.server = new ServerSocket();
		this.server.bind(bindpoint);
		System.out.println(bindpoint);
		while (true) {
			System.out.println("attente de connexion");
			Socket socket  = this.waitForConnection();
			byte[] request = this.read(socket);
			System.out.println(new String(request));
			byte[] response = this.handle(request);
			this.write(socket, response);
			
			socket.close();
			
		}	
	}
	
//Socket	
	public Socket waitForConnection() throws IOException  {
		Socket socket = this.server.accept();
		return socket;
		
	}

	public byte[] read(Socket socket) throws IOException{
		byte[] request=new byte [1000];
		socket.getInputStream().read(request);
		return request;
	}
	
	public byte[] handle(byte[] request){
		System.out.println(new String(request));
		String id = new String(request);
		
		
		try {
			this.url = new URL("http://api.jikan.me/anime/"+id);
			System.out.println(url);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		try {
			
			this.connection  = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			this.connection.setRequestMethod("GET");
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.connection.setRequestProperty("Accept", "application/vnd.api+json");
		this.connection.setRequestProperty("Content-Type", "application/vnd.api+json");
	    this.connection.setDoOutput(true);
	    
	/*	OutputStream out =null;
		try {
			 out = this.connection.getOutputStream();
			 out.write(("").getBytes());
			 out.flush();
			 out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		System.out.println("Réponse API");
		
		BufferedReader read = null;
		try {
			read = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
		} catch (IOException e1) {
			System.out.println("EREUR");
			e1.printStackTrace();
			try {
				read.close();
				this.connection.disconnect();
				this.url = new URL("http://api.jikan.me/anime/1");
				this.connection.setRequestProperty("Accept", "application/vnd.api+json");
				this.connection.setRequestProperty("Content-Type", "application/vnd.api+json");
			    this.connection.setDoOutput(true);
			    read = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		String line=null;
		String chaine = null;
		try {
			while ((line = read.readLine()) != null) {
				
			    System.out.println(line);
			    chaine = line;
			 
   }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			read.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.connection.disconnect();
		JSONObject anime = null;
		JSONObject envoie = null;
		try {
			anime = new JSONObject(chaine);
			envoie = new JSONObject();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			 envoie.put("image_url", anime.get("image_url"));
			 envoie.put("synopsis", anime.get("synopsis"));
			 envoie.put("link", anime.get("link_canonical"));
			 envoie.put("title", anime.get("title"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(envoie);				
		return envoie.toString().getBytes();
		
		
	}
	
	public void write(Socket socket, byte[] response) throws IOException{
		
		OutputStream outputStream= socket.getOutputStream();
		outputStream.write(response);
		outputStream.flush();
		
	}
	
	public static void main(String[] args) throws IOException {
		Inet4Address ip = (Inet4Address) InetAddress.getByName("10.102.252.10");
		int portsrv=8080;
		int timeout = 10000;
		InetSocketAddress bindpoint=new InetSocketAddress(ip, portsrv);
		Server server = new Server(bindpoint, timeout);
		
	}
}
