package Main;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {

    static String host;
    static int port;

    public static void main(String[] args) {

        try {


            //Получаем хост
            getHost();
            getPort(host);

            //создаем объект класса StartClient  для запуска программы
            ServerConnection serverConnection = new ServerConnection(host, port);

            System.out.println("Подключение для отправки файла:\n");
            serverConnection.sendRequest();

            System.out.println("\nПодключение для получения ответа:\n");
            serverConnection.getAnswer();

        } catch (IOException e) {
            System.out.println(e);
        }
}

    /**
     * Метод для получения хоста.
     */
    static private void getHost() throws UnknownHostException{
        InetAddress currentIP = null;
        try {
            currentIP = InetAddress.getLocalHost();
            host = currentIP.getHostAddress();
        } catch (UnknownHostException e) {
            System.err.println("Адрес не доступен!" + e);
            host = "localhost";
        }
    }

    private static void getPort(String hostWithPort) {
        port = hostWithPort.indexOf(":", 0);
        port = (port < 0) ? 80 : Integer.parseInt(hostWithPort
                .substring(port + 1));
    }
    private static String getHostWithoutPort(String hostWithPort) {
        int portPosition = hostWithPort.indexOf(":", 0);
        if (portPosition < 0) {
            return hostWithPort;
        } else {
            return hostWithPort.substring(0, portPosition);
        }
    }

}
