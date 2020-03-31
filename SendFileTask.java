package sample;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javafx.concurrent.Task;

public class SendFileTask extends Task<Void> {

   private File fileToSend;
   private long size;

    public SendFileTask(File file) {
        fileToSend = file;
        size = fileToSend.length();
    }

    //Connecting to server - overriding Task method "call"
    @Override
    protected Void call() throws Exception,IOException
    {

       // System.out.println("File Name: " + fileToSend.getName()+"size: "+size);
        //System.out.println("initiating..");
        updateMessage("Initiating...");
        updateProgress(0,100);

        try {
            InetAddress address = InetAddress.getByName("localhost");
             try (Socket client = new Socket(address, 8080)) {

                 updateMessage("Uploading file...");

                 DataOutputStream dos = new DataOutputStream(client.getOutputStream());
                 dos.writeUTF(fileToSend.getName());
                 dos.writeLong(fileToSend.length());
                 InputStream is = new FileInputStream(fileToSend.getAbsolutePath());

                 byte[] buffer = new byte[2048];
                 long readBytes = 0; //Counter of already read bytes

                 while (readBytes < size) {
                     int readNow = is.read(buffer); //reads from file
                     dos.write(buffer, 0, readNow); // and passes data to the output stream
                     readBytes += readNow;
                     updateProgress(readBytes, size);
                     Thread.sleep(500);
                 }

                 updateMessage("File has been send.");

             }
        } catch (IOException e) {
            System.err.println(e);
            updateMessage("Failed to connect to the server.");
            updateProgress(100, 100);
            return null;
        }
        return null;
    }
}