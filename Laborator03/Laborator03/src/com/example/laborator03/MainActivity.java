package com.example.laborator03;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        View.OnClickListener myClick = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				int id = v.getId();
				EditText text = (EditText)findViewById(R.id.editText1);
				if(R.id.button1 == id) {
					text.append("1");
				}
				if(R.id.button2 == id) {
					text.append("2");
				}
				if(R.id.button3 == id) {
					text.append("3");
				}
				if(R.id.button4 == id) {
					text.append("4");
				}
				if(R.id.button5 == id) {
					text.append("5");
				}
				if(R.id.button6 == id) {
					text.append("6");
				}
				if(R.id.button7 == id) {
					text.append("7");
				}
				if(R.id.button8 == id) {
					text.append("8");
				}
				if(R.id.butto9 == id) {
					text.append("9");
				}
			
				
			}
		};
		
		findViewById(R.id.button1).setOnClickListener(myClick);
		findViewById(R.id.button2).setOnClickListener(myClick);
		findViewById(R.id.button3).setOnClickListener(myClick);
		findViewById(R.id.button4).setOnClickListener(myClick);
		findViewById(R.id.button5).setOnClickListener(myClick);
		findViewById(R.id.button6).setOnClickListener(myClick);
		findViewById(R.id.button7).setOnClickListener(myClick);
		findViewById(R.id.button8).setOnClickListener(myClick);
		findViewById(R.id.butto9).setOnClickListener(myClick);
		
		View.OnClickListener myClick2 = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText text = (EditText)findViewById(R.id.editText1);
				String s = text.getText().toString();
				text.setText(s.substring(0, s.length() - 1));
				
				
			}
		};
		
		findViewById(R.id.imageButton1).setOnClickListener(myClick2);
		
		findViewById(R.id.imageButton3).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText text = (EditText)findViewById(R.id.editText1);
				Intent intent = new Intent(Intent.ACTION_CALL);
				intent.setData(Uri.parse("tel:"+text.getText().toString()));
				startActivity(intent);
				
			}
		});
		
		findViewById(R.id.imageButton2).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				
			}
			
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
