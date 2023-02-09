import com.sun.net.httpserver.HttpServer;
import models.Message;
import service.HttpRequestService;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Scanner;

import static utils.Constants.*;

public class Main {
    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/chat", new HttpRequestService());
        server.setExecutor(null); // creates a default executor
        server.start();

        System.out.println("*****POC Chat******\n");
        System.out.print("Type out a message below to send.\nCLIENT: ");

        while(true)
        {
            Scanner scanner = new Scanner(System.in);
            String message = scanner.nextLine();

            if(message == null || message.equals(""))
            {
                System.out.println("ERROR: Message cannot be empty.");
                continue;
            }

            Message messageToSend = new Message(CLIENT_ID, message, HOST, PORT);
            HttpRequestService.sendHttpRequest(messageToSend.toJsonString());
        }
    }

}