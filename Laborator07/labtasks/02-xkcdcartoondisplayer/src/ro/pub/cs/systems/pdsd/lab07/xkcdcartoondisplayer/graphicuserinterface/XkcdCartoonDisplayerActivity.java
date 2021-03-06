package ro.pub.cs.systems.pdsd.lab07.xkcdcartoondisplayer.graphicuserinterface;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import ro.pub.cs.systems.pdsd.lab07.xkcdcartoondisplayer.R;
import ro.pub.cs.systems.pdsd.lab07.xkcdcartoondisplayer.entities.XkcdCartoonInfo;
import ro.pub.cs.systems.pdsd.xkcdcartoondisplayer.general.Constants;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class XkcdCartoonDisplayerActivity extends Activity {
	
	private TextView xkcdCartoonTitleTextView;
	private ImageView xkcdCartoonImageView;
	private TextView xkcdCartoonUrlTextView;
	private Button previousButton, nextButton;
	
	private class XkcdCartoonUrlButtonClickListener implements Button.OnClickListener {
		
		String xkcdComicUrl;
		
		public XkcdCartoonUrlButtonClickListener(String xkcdComicUrl) {
			this.xkcdComicUrl = xkcdComicUrl;
		}
		
		@Override
		public void onClick(View view) {
			new XkcdCartoonDisplayerAsyncTask().execute(xkcdComicUrl);
		}
	}
	
	private class XkcdCartoonDisplayerAsyncTask extends AsyncTask<String, Void, XkcdCartoonInfo> {

		@Override
		protected XkcdCartoonInfo doInBackground(String... urls) {
			
			XkcdCartoonInfo xkcdCartoonInfo = new XkcdCartoonInfo();
			
			// TODO: exercise 5a)
			// 1. obtain the content of the web page (whose Internet address is stored in urls[0])
			// - create an instance of a HttpClient object
			// - create an instance of a HttpGet object
			// - create an instance of a ResponseHandler object
			// - execute the request, thus obtaining the web page source code
			String result = "";
			HttpClient httpClient = new DefaultHttpClient();	
				HttpGet httpGet = new HttpGet(Constants.XKCD_INTERNET_ADDRESS);
				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				try {
					result = httpClient.execute(httpGet, responseHandler);
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			// 2. parse the web page source code
			// - cartoon title: get the tag whose id equals "ctitle"
			// - cartoon url
			//   * get the first tag whose id equals "comic"
			//   * get the embedded <img> tag
			//   * get the value of the attribute "src"
			//   * prepend the protocol: "http:"
			// - cartoon content: get the input stream attached to the url and decode it into a Bitmap
			// - previous cartoon address
			//   * get the first tag whole rel attribute equals "prev"
			//   * get the href attribute of the tag
			//   * prepend the value with the base url: http://www.xkcd.com
			//   * attach the previous button a click listener with the address attached
			// - next cartoon address
			//   * get the first tag whole rel attribute equals "next"
			//   * get the href attribute of the tag
			//   * prepend the value with the base url: http://www.xkcd.com
			//   * attach the next button a click listener with the address attached
			Document document = Jsoup.parse(result);
			Element htmlTag = document.child(0);
			
			Element divTagIdCtitle = htmlTag.getElementsByAttributeValue(Constants.ID_ATTRIBUTE, Constants.CTITLE_VALUE).first();
			xkcdCartoonInfo.setCartoonTitle(divTagIdCtitle.ownText());
			
			Element divTagIdComic = htmlTag.getElementsByAttributeValue(Constants.ID_ATTRIBUTE, Constants.COMIC_VALUE).first();
			String cartoonInternetAddress = divTagIdComic.getElementsByTag(Constants.IMG_TAG).attr(Constants.SRC_ATTRIBUTE);
			xkcdCartoonInfo.setCartoonUrl(cartoonInternetAddress);
			
			Element aTagRelPrev = htmlTag.getElementsByAttributeValue(Constants.REL_ATTRIBUTE, Constants.PREVIOUS_VALUE).first();
			String previousCartoonInternetAddress = Constants.XKCD_INTERNET_ADDRESS + aTagRelPrev.attr(Constants.HREF_ATTRIBUTE);
			xkcdCartoonInfo.setPreviousCartoonUrl(previousCartoonInternetAddress);
			
			Element aTagRelNext = htmlTag.getElementsByAttributeValue(Constants.REL_ATTRIBUTE, Constants.NEXT_VALUE).first();
			String nextCartoonInternetAddress = Constants.XKCD_INTERNET_ADDRESS + aTagRelNext.attr(Constants.HREF_ATTRIBUTE);
			xkcdCartoonInfo.setNextCartoonUrl(nextCartoonInternetAddress);
			
			try {
				String cartoonUrl = Constants.HTTP_PROTOCOL + cartoonInternetAddress;
				HttpGet httpCartoonGet = new HttpGet(cartoonUrl);
				HttpResponse httpGetResponse = httpClient.execute(httpCartoonGet);
				HttpEntity httpGetEntity = httpGetResponse.getEntity();
				if (httpGetEntity != null) {
					xkcdCartoonInfo.setCartoonContent(BitmapFactory.decodeStream(httpGetEntity.getContent()));
				}
				xkcdCartoonInfo.setCartoonUrl(cartoonUrl);
			}
			catch (ClientProtocolException clientProtocolException) {
				Log.e(Constants.TAG, clientProtocolException.getMessage());
				if (Constants.DEBUG) {
					clientProtocolException.printStackTrace();
				}
			}
			catch (IOException ioException) {
				Log.e(Constants.TAG, ioException.getMessage());
				if (Constants.DEBUG) {
					ioException.printStackTrace();
				}
			}
			
			return xkcdCartoonInfo;

		}
		
		@Override
		protected void onPostExecute(XkcdCartoonInfo xkcdCartoonInfo) {
			
			// TODO: exercise 5b)
			// map each member of xkcdCartoonInfo object to the corresponding widget
			// cartoonTitle -> xkcdCartoonTitleTextView
			// cartoonContent -> xkcdCartoonImageView
			// cartoonUrl -> xkcdCartoonUrlTextView
			if (xkcdCartoonInfo != null) {
				String cartoonTitle = xkcdCartoonInfo.getCartoonTitle();
				if (cartoonTitle != null) {
					xkcdCartoonTitleTextView.setText(cartoonTitle);
				}
				Bitmap cartoonContent = xkcdCartoonInfo.getCartoonContent();
				if (cartoonContent != null) {
					xkcdCartoonImageView.setImageBitmap(cartoonContent);
				}
				String cartoonUrl = xkcdCartoonInfo.getCartoonUrl();
				if (cartoonUrl != null) {
					xkcdCartoonUrlTextView.setText(cartoonUrl);
				}
			}

		}
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xkcd_cartoon_displayer);
		
		xkcdCartoonTitleTextView = (TextView)findViewById(R.id.xkcd_cartoon_title_text_view);
		xkcdCartoonImageView = (ImageView)findViewById(R.id.xkcd_cartoon_image_view);
		xkcdCartoonUrlTextView = (TextView)findViewById(R.id.xkcd_cartoon_url_text_view);
		
		previousButton = (Button)findViewById(R.id.previous_button);
		nextButton = (Button)findViewById(R.id.next_button);
		
		new XkcdCartoonDisplayerAsyncTask().execute(Constants.XKCD_INTERNET_ADDRESS);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.xkcd_cartoon_displayer, menu);
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
