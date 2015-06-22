package ro.pub.cs.systems.pdsd.practicaltest01;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
/**
 * Created by Cosmin on 4/5/2015.
 */
public class PracticalTest01SecondaryActivity extends  Activity{
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_secondary);

        TextView t1 = ((TextView) findViewById(R.id.textView3));
        Button cancel  = ((Button) findViewById(R.id.cancel));
        Button ok  = ((Button) findViewById(R.id.ok));

        t1.setText((PracticalTest01MainActivity.a + PracticalTest01MainActivity.b) + "");

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(PracticalTest01MainActivity.CANCEL);
                finish();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(PracticalTest01MainActivity.OK);
                finish();
            }
        });
    }
}
