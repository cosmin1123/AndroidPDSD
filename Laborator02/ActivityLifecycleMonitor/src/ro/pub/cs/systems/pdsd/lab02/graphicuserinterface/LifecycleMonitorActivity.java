package ro.pub.cs.systems.pdsd.lab02.graphicuserinterface;

import ro.pub.cs.systems.pdsd.lab02.R;
import ro.pub.cs.systems.pdsd.lab02.general.Constants;
import ro.pub.cs.systems.pdsd.lab02.general.Utilities;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupWindow;

public class LifecycleMonitorActivity extends Activity {
	
	private ButtonClickListener buttonClickListener = new ButtonClickListener();
	
	private class ButtonClickListener implements Button.OnClickListener {

		@Override
		@SuppressWarnings("all")
		public void onClick(View view) {
			EditText usernameEditText = (EditText)findViewById(R.id.username_edit_text);
			EditText passwordEditText = (EditText)findViewById(R.id.password_edit_text);
			if (getResources().getString(R.string.ok_button_content).equals(((Button)view).getText().toString())) {

				LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);  		
				
				if (Utilities.allowAccess(getApplicationContext(), usernameEditText.getText().toString(), passwordEditText.getText().toString())) {
				    View popupContent = layoutInflater.inflate(R.layout.popup_window_authentication_success, null);  
				    final PopupWindow popupWindow = new PopupWindow(popupContent, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);  
				             
				    Button dismissButton = (Button)popupContent.findViewById(R.id.dismiss_button);
				    dismissButton.setOnClickListener(new Button.OnClickListener(){	
				    	@Override
				    	public void onClick(View view) {
				    		popupWindow.dismiss();
				    	}
				    });
					popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);	
				} else {
				    View popupContent = layoutInflater.inflate(R.layout.popup_window_authentication_fail, null);  
				    final PopupWindow popupWindow = new PopupWindow(popupContent, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);  
				             
				    Button dismissButton = (Button)popupContent.findViewById(R.id.dismiss_button);
				    dismissButton.setOnClickListener(new Button.OnClickListener(){	
				    	@Override
				    	public void onClick(View view) {
				    		popupWindow.dismiss();
				    	}
				    });
					popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);					
				}
			}
			if (getResources().getString(R.string.cancel_button_content).equals(((Button)view).getText().toString())) {
				usernameEditText.setText(getResources().getString(R.string.empty));
				passwordEditText.setText(getResources().getString(R.string.empty));
			}
		}
		
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle_monitor);  
        
        
        Button okButton = (Button)findViewById(R.id.ok_button);
        okButton.setOnClickListener(buttonClickListener);
        Button cancelButton = (Button)findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(buttonClickListener);
        
        if(savedInstanceState == null) {
        	Log.d(Constants.TAG, "Never opened before");
        	savedInstanceState = new Bundle();
        	
        } else {
        	Log.d(Constants.TAG, " opened before");
        
        }
        EditText usernameEditText = (EditText)findViewById(R.id.username_edit_text);
    	EditText passwordEditText = (EditText)findViewById(R.id.password_edit_text);
		CheckBox box = (CheckBox) findViewById(R.id.remember_me_checkbox);
		
    	savedInstanceState.putString(Constants.USERNAME_EDIT_TEXT, usernameEditText.getText().toString());
    	savedInstanceState.putString(Constants.PASSWORD_EDIT_TEXT, passwordEditText.getText().toString());
    	savedInstanceState.putBoolean(Constants.REMEMBER_ME_CHECKBOX, box.isChecked());
    	
        Log.d(Constants.TAG, "onCreate() method was invoked");
    }    

    public void onRestoreInstanceState(Bundle savedInstanceState) {
    	 Log.d(Constants.TAG, "onRestoreInstanceState() method was invoked");
    	 EditText usernameEditText = (EditText)findViewById(R.id.username_edit_text);
     	 EditText passwordEditText = (EditText)findViewById(R.id.password_edit_text);
 		 CheckBox box = (CheckBox) findViewById(R.id.remember_me_checkbox);
 		 
 		usernameEditText.setText(savedInstanceState.getString(Constants.USERNAME_EDIT_TEXT));
 		passwordEditText.setText(savedInstanceState.getString(Constants.PASSWORD_EDIT_TEXT));
 		box.setChecked(savedInstanceState.getBoolean(Constants.REMEMBER_ME_CHECKBOX));
    	
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
    

 public void onRestart() {
	 super.onRestart();
	 Log.d(Constants.TAG, "onRestart method was invoked");
 }
 public void onStart() {
	 super.onStart();
	 Log.d(Constants.TAG, "onStart method was invoked");
 }
 
 public void onResume() {
	 super.onResume();
	 Log.d(Constants.TAG, "onResume method was invoked");
 }
 
 
 public void onPause() {
	 super.onPause();
	 Log.d(Constants.TAG, "onPause method was invoked");
 }
 
 public void onStop() {
	 super.onStop();
	 Log.d(Constants.TAG, "onStop method was invoked");
  }
    
 public void onDestroy() {
	 super.onDestroy();
	 Log.d(Constants.TAG, "onDestroy method was invoked");
 }

}
