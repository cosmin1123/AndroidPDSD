package ro.pub.cs.systems.pdsd.practicaltest02;

import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * Created by didii on 5/25/15.
 */
public class Server extends Thread{
    private boolean isRunning;

    HashMap<String, String> map = new HashMap<>();

    int SERVER_PORT;
    private ServerSocket serverSocket;

    public Server(int port) {
        SERVER_PORT = port;
    }
    public void startServer() {
        isRunning = true;
        start();
    }

    public String doGETRequest(String webPageAddress) {
        try {
            ResponseHandler<String> responseHandlerGet = new BasicResponseHandler();
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(webPageAddress);
            HttpResponse httpGetResponse = httpClient.execute(httpGet);
            HttpEntity httpGetEntity = httpGetResponse.getEntity();
            if (httpGetEntity != null) {
                // do something with the response
                return httpClient.execute(httpGet, responseHandlerGet);

            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public void stopServer() {
        isRunning = false;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (serverSocket != null) {
                        serverSocket.close();
                    }

                } catch(IOException ioException) {
                }
            }
        }).start();
    }

    @Override
    public void run() {
        try {

            serverSocket = new ServerSocket(SERVER_PORT);
            while (isRunning) {

                Socket socket = serverSocket.accept();

                PrintWriter printWriter = Utilities.getWriter(socket);
                BufferedReader reader = Utilities.getReader(socket);

                String msgType = reader.readLine();

                if(msgType.equals("set")) {
                    String id = reader.readLine();
                    String hourMin = reader.readLine();
                    map.put(id, hourMin);
                    printWriter.println("set done");
                }

                if(msgType.equals("reset")) {
                    String id = reader.readLine();
                    map.remove(id);
                    printWriter.println("reset done");
                }

                if(msgType.equals("poll")) {
                    String id = reader.readLine();
                    if(map.get(id) == null) {
                        printWriter.println("none");
                    } else {

                        String time = doGETRequest("http://www.timeapi.org/utc/now");
                        int currentTime = time.indexOf("T");
                        String minHourTime = time.substring(currentTime + 1, currentTime + 6);
                        String[] arr1 = minHourTime.split(":");
                        String[] arr2 = map.get(id).split(",");

                        Integer hour1 = Integer.parseInt(arr1[0]);
                        Integer hour2 = Integer.parseInt(arr2[0]);


                        if (hour2 < hour1) {
                            printWriter.println("active");
                        }

                        if (hour2 == hour1) {
                            Integer min1 = Integer.parseInt(arr1[1]);
                            Integer min2 = Integer.parseInt(arr2[1]);
                            if(min2 > min1) {
                                printWriter.println("active");
                            }
                            if(min2 <= min1) {
                                printWriter.println("inactive");
                            }
                        }

                        if (hour2 > hour1) {
                            printWriter.println("active");
                        }
                    }
                }

                socket.close();

            }
        } catch (IOException ioException) {
        }
    }
}
