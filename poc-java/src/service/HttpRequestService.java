package service;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import enums.HttpRequestMethod;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

import static utils.Constants.SERVER_ADDRESS;

public class HttpRequestService implements HttpHandler {
    private static Logger LOGGER = Logger.getLogger(String.valueOf(HttpRequestService.class));
    public static void sendHttpRequest(String messageJson) {

        try
        {
            URL url = new URL(SERVER_ADDRESS);
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection) con;
            http.setRequestMethod(HttpRequestMethod.POST.name());
            http.setDoOutput(true);
            byte[] messageJsonAsBytes = messageJson.getBytes();
            int length = messageJsonAsBytes.length;
            http.setFixedLengthStreamingMode(length);
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            http.connect();

            try(OutputStream os = http.getOutputStream())
            {
                os.write(messageJsonAsBytes);
                os.close();
            }
        }
        catch (IOException ioException)
        {
            LOGGER.warning("Unable to send message. Error: "+ioException.getMessage());
        }
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if(!exchange.getRequestMethod().equals("POST")) {
            exchange.sendResponseHeaders(405, -1);
            exchange.close();
            return;
        }

        InputStream inputStream = exchange.getRequestBody();;
        //creating an InputStreamReader object
        InputStreamReader isReader = new InputStreamReader(inputStream);
        //Creating a BufferedReader object
        BufferedReader reader = new BufferedReader(isReader);
        StringBuffer sb = new StringBuffer();
        String str;
        while((str = reader.readLine())!= null){
            sb.append(str);
        }
        System.out.println("SERVER: "+sb);
        String response = "Message sent successfully.";
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
