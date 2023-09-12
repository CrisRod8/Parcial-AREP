package org.example;

import java.net.*;
import java.io.*;
import java.util.Objects;

public class HttpServer {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(36000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        Socket clientSocket = null;
        boolean running = true;
        while (running) {
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            PrintWriter out = new PrintWriter(
                    clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            Object inputLine, outputLine;
            String request = "";
            while ((inputLine = in.readLine()) != null) {
                //System.out.println("Recibí: " + inputLine);
                boolean firstLine = false;
                if (firstLine) {
                    firstLine = false;
                    request = ((String) inputLine).split("HTTP")[0];
                    request = request.split(" ")[1];
                }

                if (!in.ready()) {
                    break;
                }
                //System.out.println(request);
                if (Objects.equals(request, "/")) {
                    outputLine = index();
                } else {
                    //outputLine = getClass(request);
                }
            }
            outputLine = index();
            out.println(outputLine);
            out.close();
            in.close();
        }
        clientSocket.close();
        serverSocket.close();
    }
    public static String getClass(String clase) throws ClassNotFoundException {
        Class<?> Class = java.lang.Class.forName(clase);
        return clase;
    }


    private static String getHeader(String type) {
        return "HTTP/1.1 200 OK\r\n"
                + "Content-Type: " + type + "\r\n"
                + "\r\n";
    }

    private static String index() {
        return getHeader("text/html") +
                "<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <title>Form Example</title>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <h1>Form with GET</h1>\n" +
                "        <form action=\"/hello\">\n" +
                "            <label for=\"name\">Name:</label><br>\n" +
                "            <input type=\"text\" id=\"name\" name=\"name\" value=\"Class(java.lang.String)\"><br><br>\n" +
                "            <input type=\"button\" value=\"Submit\" onclick=\"loadGetMsg()\">\n" +
                "        </form> \n" +
                "        <div id=\"getrespmsg\"></div>\n" +
                "\n" +
                "        <script>\n" +
                "            function loadGetMsg() {\n" +
                "                let nameVar = document.getElementById(\"name\").value;\n" +
                "                const xhttp = new XMLHttpRequest();\n" +
                "                xhttp.onload = function() {\n" +
                "                    document.getElementById(\"getrespmsg\").innerHTML =\n" +
                "                    this.responseText;\n" +
                "                }\n" +
                "                xhttp.open(\"GET\", \"/hello?name=\"+nameVar);\n" +
                "                xhttp.send();\n" +
                "            }\n" +
                "        </script>\n" +
                "\n" +
                "    </body>\n" +
                "</html>";

    }
}