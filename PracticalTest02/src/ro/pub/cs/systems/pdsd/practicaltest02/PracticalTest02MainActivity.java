package ro.pub.cs.systems.pdsd.practicaltest02;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;

import java.util.LinkedList;

public class PracticalTest02MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    Integer port = 9999;

    public String getIMEI(Context context){

        TelephonyManager mngr = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        String imei = mngr.getDeviceId();
        return imei;

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);
        Activity act = this;
        String id = getIMEI(this.getApplicationContext());


        findViewById(R.id.startServer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String portString = ((EditText) findViewById(R.id.serverPort)).getText().toString();
                if (!portString.equals("port")) {

                    port = Integer.parseInt(portString);
                }
                Server instance = new Server(port);
                instance.startServer();
            }
        });




        findViewById(R.id.clientSet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinkedList<String> query = new LinkedList<String>();
                query.add("set");
                query.add(id.toString());
                String hourMin = ((EditText) findViewById(R.id.clientAlarmTime)).getText().toString();
                query.add(hourMin);
                Client client = new Client("127.0.0.1", port, act, query);
                client.start();
            }
        });

        findViewById(R.id.clientReset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinkedList<String> query = new LinkedList<String>();
                query.add("reset");
                query.add(id.toString());

                Client client = new Client("127.0.0.1", port, act, query);
                client.start();
            }
        });

        findViewById(R.id.clientPoll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinkedList<String> query = new LinkedList<String>();
                query.add("poll");
                query.add(id.toString());

                Client client = new Client("127.0.0.1", port, act, query);
                client.start();
            }
        });
    }
}
