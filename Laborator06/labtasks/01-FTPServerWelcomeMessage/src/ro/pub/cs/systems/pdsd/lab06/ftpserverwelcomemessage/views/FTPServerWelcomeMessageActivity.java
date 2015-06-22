package ro.pub.cs.systems.pdsd.lab06.ftpserverwelcomemessage.views;

import java.io.BufferedReader;
import java.net.Socket;

import ro.pub.cs.systems.pdsd.lab06.ftpserverwelcomemessage.R;
import ro.pub.cs.systems.pdsd.lab06.ftpserverwelcomemessage.general.Constants;
import ro.pub.cs.systems.pdsd.lab06.ftpserverwelcomemessage.general.Utilities;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FTPServerWelcomeMessageActivity extends Activity {
	
	private EditText FTPServerAddressEditText;
	private TextView welcomeMessageTextView;
	
	protected class FTPServerCommunicationThread extends Thread {
		
		@Override
		public void run() {
			try {
				
				// TODO: exercise 4
				// open socket with FTPServerAddress (taken from FTPServerAddressEditText edit text) and port (Constants.FTP_PORT = 21)
				// get the BufferedReader attached to the socket (call to the Utilities.getReader() method)
				// should the line start with Constants.FTP_MULTILINE_START_CODE, the welcome message is processed
				// read lines from server while 
				// - the value is different from Constants.FTP_MULTILINE_END_CODE1
				// - the value does not start with Constants.FTP_MULTILINE_START_CODE2
				// append the line to the welcomeMessageTextView text view content (on the UI thread!!!)
				// close the socket
				EditText text = (EditText) findViewById(R.id.ftp_server_address_edit_text);
				 Socket socket = new Socket (
				          text.getText().toString(),
				          Constants.FTP_PORT
				        );
				 BufferedReader bufferedReader = Utilities.getReader(socket);
				 String result = "";
				 String line = bufferedReader.readLine();
				 Log.e("blafgdf", line);
				 if(line.startsWith(Constants.FTP_MULTILINE_START_CODE)) {
					 result += line;
					 line = bufferedReader.readLine();
					 while(!line.contains(Constants.FTP_MULTILINE_END_CODE1) ||
							 !line.startsWith(Constants.FTP_MULTILINE_END_CODE2)) {
						 result+= line; 
						 line = bufferedReader.readLine();
					 }
				 }
				 
				 final String finaResult = result;
				 
				 findViewById(R.id.welcome_message_text_view).post(new Runnable() {
			          @Override
			          public void run() {
			        	  Log.e("blasasdasdsad", finaResult);
			        	  ((TextView)findViewById(R.id.welcome_message_text_view)).setText(finaResult);
			          }
			        });
				 Log.d("DebuggingStuff", result);
				 socket.close();
				 
			} catch (Exception exception) {
				Log.e(Constants.TAG, "An exception has occurred: "+exception.getMessage());
				if (Constants.DEBUG) {
					exception.printStackTrace();
				}
			}
		}
	}	
	
	private ButtonClickListener buttonClickListener = new ButtonClickListener();
	private class ButtonClickListener implements Button.OnClickListener {
		
		@Override
		public void onClick(View view) {
			new FTPServerCommunicationThread().start();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ftpserver_welcome_message);
		
		FTPServerAddressEditText = (EditText)findViewById(R.id.ftp_server_address_edit_text);
		welcomeMessageTextView = (TextView)findViewById(R.id.welcome_message_text_view);
		
		Button displayWelcomeMessageButton = (Button)findViewById(R.id.display_welcome_message_button);
		displayWelcomeMessageButton.setOnClickListener(buttonClickListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ftpserver_welcome_message, menu);
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
