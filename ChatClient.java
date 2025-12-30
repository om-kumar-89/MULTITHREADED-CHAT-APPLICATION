import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 1234);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        Scanner sc = new Scanner(System.in);

        // Thread to read messages
        new Thread(() -> {
            try {
                String msg;
                while ((msg = in.readLine()) != null)
                    System.out.println(msg);
            } catch (Exception e) {}
        }).start();

        // Send messages
        while (true)
            out.println(sc.nextLine());
    }
}

