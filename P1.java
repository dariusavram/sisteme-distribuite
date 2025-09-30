import java.net.*;
import java.io.*;

public class P1 {
    public static void main(String[] args) {
        int serverPort = 8001;
        int clientPort = 8002;
        String nextHost = "localhost";

        ServerSocket serverSocket = null;
        Socket socketForP3 = null;
        Socket socketToP2 = null;
        DataInputStream fromP3 = null;
        DataOutputStream toP2 = null;

        try {
            serverSocket = new ServerSocket(serverPort);
            System.out.println("P1 client: conectare la P2 pe portul " + clientPort);
            while (socketToP2 == null) {
                try {
                    socketToP2 = new Socket(nextHost, clientPort);
                } catch (ConnectException e) {
                    System.out.println("P1 client: conexiune esuata");
                    Thread.sleep(1000);
                }
            }
            System.out.println("P1 client: conectat la P2");
            toP2 = new DataOutputStream(socketToP2.getOutputStream());
            System.out.println("P1 server: astept conexiune de la P3 pe portul " + serverPort );
            socketForP3 = serverSocket.accept();
            System.out.println("P1 server: P2 s-a conectat de la " + socketForP3.getInetAddress());
            fromP3 = new DataInputStream(socketForP3.getInputStream());
            String mesajInitial = "mesaj de la P1 care incepe inelul";
            System.out.println("P1 trimite mesajul: \"" + mesajInitial + "\"");
            toP2.writeUTF(mesajInitial);
            System.out.println("P1: asteapta mesaj de la P3");
            String mesajFinal = fromP3.readUTF();
            System.out.println("P1 am primit inapoi mesajul: \"" + mesajFinal + "\"");
            System.out.println("P1: comunicatia in inel incheiata cu succes");

        } catch (IOException | InterruptedException e) {
            System.out.println("eroare in P1: " + e.getMessage());
        } finally {
            try {
                if (fromP3 != null) fromP3.close();
                if (toP2 != null) toP2.close();
                if (socketForP3 != null) socketForP3.close();
                if (socketToP2 != null) socketToP2.close();
                if (serverSocket != null) serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}