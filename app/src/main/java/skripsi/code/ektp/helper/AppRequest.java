package skripsi.code.ektp.helper;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

public class AppRequest extends Application {

	public static final String TAG = AppRequest.class.getSimpleName();

	private RequestQueue mRequestQueue;
	private static AppRequest mInstance;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
	}

	public static synchronized AppRequest getInstance() {
		return mInstance;
	}

	public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
		ConnectivityReceiver.connectivityReceiverListener = listener;
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
			//disk cache
			Cache cache = new DiskBasedCache(getApplicationContext().getCacheDir(), 10 * 1024 * 1024); // 10mb
			// Set up the network to use HttpURLConnection as the HTTP client.
			Network network = new BasicNetwork(new HurlStack());
			// init
			mRequestQueue = new RequestQueue(cache, network);
			// Don't forget to start the volley request queue
			mRequestQueue.start();
		}

		return mRequestQueue;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		req.setRetryPolicy(new DefaultRetryPolicy(
				100000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}}