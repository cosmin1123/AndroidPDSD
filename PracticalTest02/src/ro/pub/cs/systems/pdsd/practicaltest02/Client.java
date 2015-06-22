package ro.pub.cs.systems.pdsd.practicaltest02;

import android.app.Activity;
import android.widget.EditText;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;

/**
 * Created by didii on 5/25/15.
 */
public class Client extends Thread {
    String address;
    int port;
    Activity act;
    LinkedList<String> query = new LinkedList<>();

    public Client(String address, int port, Activity act, LinkedList<String> query) {
        this.address = address;
        this.port = port;
        this.act = act;
        this.query = query;
    }

    public void run() {
        try {

            Socket sock = new Socket(address, port);

            PrintWriter printWriter = Utilities.getWriter(sock);

            for(String str : query) {
                printWriter.println(str);
            }


            String ans = Utilities.getReader(sock).readLine();

            act.findViewById(R.id.clientResult).post(new Thread(){
                public void run() {
                    ((EditText)(act.findViewById(R.id.clientResult))).setText(ans);
                }
            });

            sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
