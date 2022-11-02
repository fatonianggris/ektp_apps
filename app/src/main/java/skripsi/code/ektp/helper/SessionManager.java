package skripsi.code.ektp.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class SessionManager {
	// LogCat tag
	private static String TAG = SessionManager.class.getSimpleName();
	// Shared Preferences
	SharedPreferences pref;
	Editor editor;
	Context mContext;
	// Shared pref mode
	int PRIVATE_MODE = 0;

	String id_status_user;
	// Shared preferences file name
	private static final String PREF_NAME = "eKTPAppsLogin";
	private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
	private static final String KEY_STATUS_USER = "user_stat";

	public SessionManager(Context context) {
		this.mContext = context;
		pref = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}

	public void setLogin(boolean isLoggedIn) {

		editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);

		// commit changes
		editor.commit();

		Log.d(TAG, "User login session modified!");
	}

	public void setRole(String status_user) {

		id_status_user = status_user;
		editor.putString(KEY_STATUS_USER, status_user);
		// commit changes
		editor.commit();

		Log.d(TAG, "User ROLE session modified!");
	}
	
	public boolean isLoggedIn(){
		return pref.getBoolean(KEY_IS_LOGGED_IN, false);
	}

	public String getRole(){
		return pref.getString(KEY_STATUS_USER, id_status_user);
	}
}
