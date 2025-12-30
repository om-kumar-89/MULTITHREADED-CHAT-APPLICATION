import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    static List<PrintWriter> clients = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(1234);
        System.out.println("Server running...");

        while (true) {
            Socket socket = server.accept();
            System.out.println("Client connected");
            new ClientHandler(socket).start();
        }
    }

    static class ClientHandler extends Thread {
        Socket socket;
        BufferedReader in;
        PrintWriter out;

        ClientHandler(Socket socket) throws Exception {
            this.socket = socket;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            clients.add(out);
        }

        public void run() {
            try {
                String msg;
                while ((msg = in.readLine()) != null) {
                    for (PrintWriter writer : clients)
                        writer.println(msg);
                }
            } catch (Exception e) {
                System.out.println("Client disconnected");
            }
        }
    }
}
