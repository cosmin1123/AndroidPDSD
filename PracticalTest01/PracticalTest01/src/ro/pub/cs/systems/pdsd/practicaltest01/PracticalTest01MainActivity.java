package ro.pub.cs.systems.pdsd.practicaltest01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01MainActivity extends Activity {
    static Integer a = 0;
    static Integer b = 0;
    static int OK = 5;
    static int CANCEL = 10;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_main);

        if(savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }
        final TextView text1 =  ((TextView) findViewById(R.id.textView));
        final TextView text2 =  ((TextView) findViewById(R.id.textView2));
        a = 0;
        b = 0;
        text1.setEnabled(false);
        text2.setEnabled(false);
       // text1.setSaveEnabled(false);
       // text2.setSaveEnabled(false);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text1.setText((a + 1) + "");
                a++;
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text2.setText((b + 1) + "");
                b++;
            }
        });
        final Intent intent = new Intent(this, PracticalTest01SecondaryActivity.class);

        findViewById(R.id.go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult(intent, 10);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println(requestCode);

        if(resultCode == OK) {
            Toast.makeText(this.getApplicationContext(), "OK", Toast.LENGTH_LONG).show();
        }

        if(resultCode == CANCEL) {
            Toast.makeText(this.getApplicationContext(), "CANCEL", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("a", a);
        savedInstanceState.putInt("b", b);

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        a = (int)savedInstanceState.getByte("a");
        b = (int)savedInstanceState.getByte("b");
    }
}
