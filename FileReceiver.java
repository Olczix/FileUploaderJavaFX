import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.DataInputStream;
import java.io.FileOutputStream;



public class FileReceiver {
	
	private static int portNumber=8080;

	public static void receiveFile(Socket client) throws IOException{
		
		System.out.println("\n\nReceiving a new file..");
       
		DataInputStream input = new DataInputStream(client.getInputStream());
		String fileName=input.readUTF();
        long fileSize= input.readLong();
		
		File file = new File(".", fileName);
        System.out.println("Downloading " + fileName +" ("+ fileSize + " bytes) from " + client.getInetAddress().toString() );


        file.createNewFile();
		OutputStream os=new FileOutputStream(file.getAbsolutePath());
		  try {
                int all = 0, haveRead = 0;
                byte[] buffer = new byte[512];

                while (all < fileSize) {
                    haveRead = input.read(buffer);
					all += haveRead;
                    os.write(buffer, 0, haveRead);
                }

                System.out.println("Finished downloading " + fileName + "!");
                os.close();
				input.close();
            } catch (IOException e) {
                System.out.println("Error occurred while downloading " + fileName);
                file.delete(); 
            }
		
		client.close();
	}


    public static void main(String [] args)  throws IOException {

		System.out.println("Server is available");
		ExecutorService executor = Executors.newFixedThreadPool(8);
		
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (true) {
                final Socket socket = serverSocket.accept();
							
				executor.submit(() -> {
                        try {
                            FileReceiver.receiveFile(socket);
                        } catch (IOException e) {
                            System.out.println("Problem during the download: " + e.getMessage());
                        }});
										
            }
		}
	}
}  




