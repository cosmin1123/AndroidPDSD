package ro.pub.cs.systems.pdsd.practicaltest01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    static int centerVal = 0;
    static int topLeftVal = 0;
    static int topRightVal = 0;
    final static int VERIFY = 5;
    final static int CANCEL = 10;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_main);
        if(savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }

        Button bottomLeft = (Button) findViewById(R.id.bottomLeft);
        Button bottomRight = (Button) findViewById(R.id.bottomRight);
        final Button center = (Button) findViewById(R.id.center);
        Button topLeft = (Button) findViewById(R.id.topLeft);
        final Button topRight = (Button) findViewById(R.id.topRight);
        Button go = (Button) findViewById(R.id.go);

        final TextView text = (TextView) findViewById(R.id.textView);
        text.setText("");
        View.OnClickListener myClick =  new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                if(R.id.bottomLeft == id) {
                    text.append("BottomLeft;");
                }
                if(R.id.bottomRight == id) {
                    text.append("BottomRight;");
                }
                if(R.id.center == id) {
                    text.append("Center;");
                    centerVal++;
                }
                if(R.id.topLeft == id) {
                    topLeftVal++;
                    text.append("TopLeft;");
                }
                if(R.id.topRight == id) {
                    topRightVal++;
                    text.append("TopRight;");
                }
            }
        };

        bottomLeft.setOnClickListener(myClick);
        bottomRight.setOnClickListener(myClick);
        center.setOnClickListener(myClick);
        topLeft.setOnClickListener(myClick);
        topRight.setOnClickListener(myClick);

        final Intent intent = new Intent(this, PracticalTest01SecondaryActivity.class);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentText = (String) text.getText().toString();
                intent.putExtra("center", centerVal + "");
                intent.putExtra("left", topLeftVal + "");
                intent.putExtra("right", topRightVal + "");
                startActivityForResult(intent, 10);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("centerVal", centerVal);
        savedInstanceState.putInt("topLeftVal", topLeftVal);
        savedInstanceState.putInt("topRightVal", topRightVal);

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        centerVal = savedInstanceState.getInt("centerVal");
        topLeftVal = savedInstanceState.getInt("topLeftVal");
        topRightVal = savedInstanceState.getInt("topRightVal");

        Toast.makeText(this.getApplicationContext(), "topLeft: " + topLeftVal +
                "; center: " + centerVal + "; topRight: " + topRightVal, Toast.LENGTH_LONG).show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println(requestCode);

        if(resultCode == VERIFY) {
            Toast.makeText(this.getApplicationContext(), "VERIFY", Toast.LENGTH_LONG).show();
        }

        if(resultCode == CANCEL) {
            Toast.makeText(this.getApplicationContext(), "CANCEL", Toast.LENGTH_LONG).show();
        }
    }
}
