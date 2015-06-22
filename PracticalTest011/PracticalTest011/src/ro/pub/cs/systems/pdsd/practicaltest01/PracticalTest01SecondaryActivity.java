package ro.pub.cs.systems.pdsd.practicaltest01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Cosmin on 4/6/2015.
 */
public class PracticalTest01SecondaryActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_secondary);
        Intent tmp = getIntent();
        String center = tmp.getStringExtra("center");
        String left = tmp.getStringExtra("left");
        String right = tmp.getStringExtra("right");

        TextView text = (TextView) findViewById(R.id.textView2);
        Button verify = (Button) findViewById(R.id.verify);
        Button cancel = (Button) findViewById(R.id.cancel);


        text.setText("center: " + center + "right: " + right + "left: " + left);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(PracticalTest01MainActivity.VERIFY);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(PracticalTest01MainActivity.CANCEL);
                finish();
            }
        });


        setContentView(R.layout.activity_practical_test01_secondary);
    }
}
