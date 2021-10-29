package Main;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServerConnection {

    static String host; //хост
    static int port; //порт
    static Socket socket = null; //сокет

    /**
     * Запуск сервера в конструкторе
     */
    public ServerConnection(String host, int port) throws IOException {
        try {

            this.host = host;
            this.port = port;

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    /**
     * Метод отправки файла клиентом
     */
    public void sendRequest() throws IOException {

        InputStream in = null;
        OutputStream out = null;
        try {

            socket = new Socket(host, port);
            System.out.println("Подключение успешно!");

            System.out.print("Введите путь к файлу, который нужно загрузить на сервер:");
            File file = new File(new Scanner(System.in).nextLine());


            byte[] bytes = new byte[16 * 1024];
            in = new FileInputStream(file);
            out = socket.getOutputStream();

            int data;
            //Запись файла на сервер
            while ((data = in.read(bytes)) > 0) {
                out.write(bytes, 0, data);
            }

            out.close();
            in.close();
            System.out.println("Файл успешно помещен на сервер");
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    /**
     * Метод получения ответа от сервера
     */
    public void getAnswer() throws IOException {
        try {

            socket = new Socket(host, port);

            System.out.println("Подключение успешно!");

            File file = new File("ServerFile.txt");

            PrintStream ps = new PrintStream(socket.getOutputStream());
            FileReader fr = new FileReader(file);
            BufferedReader bf = new BufferedReader(fr);

            String ans = "";
            while (bf.ready()) {
                ans += bf.readLine() + "\n";
            }
            String[] ans2 = ans.split("\n");

            //Вывод сообщения в консоль
            System.out.println("Ответ сервера: " + ans2[new Random().nextInt(ans2.length)]);
            ps.flush();

            //закрываем сокет
            socket.close();

            System.out.println("\nОтлючение...");
        } catch (IOException e) {
            System.out.println("Error -> " + e);
        }

    }

}
