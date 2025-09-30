import java.net.*;
import java.io.*;

public class P2 {
    public static void main(String[] args) {
        int serverPort = 8002;
        int clientPort = 8003;
        String nextHost = "localhost";

        ServerSocket serverSocket = null;
        Socket socketFromP1 = null;
        Socket socketToP3 = null;
        DataInputStream fromP1 = null;
        DataOutputStream toP3 = null;

        try {
            serverSocket = new ServerSocket(serverPort);
            System.out.println("P2 client: conectare la P3 pe portul " + clientPort + "...");
            while (socketToP3 == null) {
                try {
                    socketToP3 = new Socket(nextHost, clientPort);
                } catch (ConnectException e) {
                    System.out.println("P2 client: conexiune esuata");
                    Thread.sleep(1000);
                }
            }
            System.out.println("P2 client: conectat la P3.");
            toP3 = new DataOutputStream(socketToP3.getOutputStream());
            System.out.println("P2 server: astept conexiune de la  P1 pe portul " + serverPort + "...");
            socketFromP1 = serverSocket.accept();
            System.out.println("P2 server: P1 s-a conectat de la " + socketFromP1.getInetAddress());
            fromP1 = new DataInputStream(socketFromP1.getInputStream());
            System.out.println("P2: asteapta mesaj de la P1");
            String mesajPrimit = fromP1.readUTF();
            System.out.println("P2 mesaj primit:  \"" + mesajPrimit + "\"");
            System.out.println("P2: retransmite mesaj la P3");
            toP3.writeUTF(mesajPrimit);

        } catch (IOException | InterruptedException e) {
            System.out.println("A aparut o eroare in P2: " + e.getMessage());
        } finally {
            try {
                if (fromP1 != null) fromP1.close();
                if (toP3 != null) toP3.close();
                if (socketFromP1 != null) socketFromP1.close();
                if (socketToP3 != null) socketToP3.close();
                if (serverSocket != null) serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}