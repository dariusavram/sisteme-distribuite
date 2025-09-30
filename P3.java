import java.net.*;
import java.io.*;

public class P3 {
    public static void main(String[] args) {
        int serverPort = 8003;
        int clientPort = 8001;
        String nextHost = "localhost";

        ServerSocket serverSocket = null;
        Socket socketFromP2 = null;
        Socket socketToP1 = null;
        DataInputStream fromP2 = null;
        DataOutputStream toP1 = null;

        try {
            serverSocket = new ServerSocket(serverPort);
            System.out.println("P3 client: conectare la P1 pe portul " + clientPort );
            while (socketToP1 == null) {
                try {
                    socketToP1 = new Socket(nextHost, clientPort);
                } catch (ConnectException e) {
                    System.out.println("P3 client: conexiune esuata");
                    Thread.sleep(1000);
                }
            }
            System.out.println("P3 client: conectat la P1");
            toP1 = new DataOutputStream(socketToP1.getOutputStream());
            System.out.println("P3 server: astept conexiune de la P2 pe portul " + serverPort );
            socketFromP2 = serverSocket.accept();
            System.out.println("P3 server: P2 s-a conectat de la " + socketFromP2.getInetAddress());
            fromP2 = new DataInputStream(socketFromP2.getInputStream());
            System.out.println("P3: asteapta mesaj de la P2");
            String mesajPrimit = fromP2.readUTF();
            System.out.println("P3 mesaj primit:  \"" + mesajPrimit + "\"");
            System.out.println("P3: retransmite mesaj la P1 pentru a inchide inelul");
            toP1.writeUTF(mesajPrimit);

        } catch (IOException | InterruptedException e) {
            System.out.println("eroare in P3: " + e.getMessage());
        } finally {
            try {
                if (fromP2 != null) fromP2.close();
                if (toP1 != null) toP1.close();
                if (socketFromP2 != null) socketFromP2.close();
                if (socketToP1 != null) socketToP1.close();
                if (serverSocket != null) serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}