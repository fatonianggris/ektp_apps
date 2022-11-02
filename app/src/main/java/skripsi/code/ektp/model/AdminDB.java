package skripsi.code.ektp.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class AdminDB extends SQLiteOpenHelper {

	private static final String TAG = AdminDB.class.getSimpleName();

	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_NAME = "android_ktp";

	private static final String TABLE_USER = "user_admin";

	private static final String id_user = "id_user";
	private static final String id_admin = "id_admin";
	private static final String role_admin = "role_admin";
	private static final String id_ref = "id_ref";
	private static final String nama_admin = "nama_admin";
	private static final String email = "email";
	private static final String nomor_hp = "nomor_hp";
	private static final String img = "img";
	private static final String img_thumb = "img_thumb";
	private static final String license= "license";
	private static final String license_exp = "license_exp";
	private static final String create = "create_prev";
	private static final String read = "read_prev";
	private static final String update = "update_prev";
	private static final String delete = "delete_prev";
	private static final String provinsi = "provinsi";
	private static final String kabupaten= "kabupaten";
	private static final String tanggal_post = "tanggal_post";

	public AdminDB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
		String CREATE_ADMIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
				+ id_user + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ id_admin+ " TEXT,"
				+ role_admin+ " TEXT,"
				+ id_ref + " TEXT,"
				+ nama_admin + " TEXT,"
				+ email + " TEXT,"
				+ nomor_hp + " TEXT,"
				+ img + " TEXT,"
				+ img_thumb + " TEXT,"
				+ license + " TEXT,"
				+ license_exp + " TEXT,"
				+ create + " TEXT,"
				+ read + " TEXT,"
				+ update + " TEXT,"
				+ delete + " TEXT,"
				+ provinsi + " TEXT,"
				+ kabupaten + " TEXT,"
				+ tanggal_post + " TEXT"
				+ ")";
		db.execSQL(CREATE_ADMIN_TABLE);

		Log.d(TAG, "Database tables created");
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
		onCreate(db);
	}


	public void addUser(Admin user) {
		SQLiteDatabase db = this.getWritableDatabase();
		onCreate(db);

		ContentValues values = new ContentValues();
		values.put(id_admin, user.get_id_admin());
		values.put(role_admin, user.get_role_admin());
		values.put(id_ref, user.get_id_ref());
		values.put(nama_admin, user.get_nama_admin());
		values.put(email, user.get_email());
		values.put(nomor_hp, user.get_nomor_hp());
		values.put(img, user.get_img());
		values.put(img_thumb, user.get_img_thumb());
		values.put(license, user.get_license());
		values.put(license_exp, user.get_license_exp());
		values.put(create, user.get_create());
		values.put(read, user.get_read());
		values.put(update, user.get_update());
		values.put(delete, user.get_delete());
		values.put(provinsi, user.get_provinsi());
		values.put(kabupaten, user.get_kabupaten());
		values.put(tanggal_post, user.get_tanggal_post());

		long id = db.insert(TABLE_USER, null, values);
		db.close();

		Log.d(TAG, "New user inserted into sqlite: " + id);
	}


	public HashMap<String, String> getUserDetails() {
		HashMap<String, String> user = new HashMap<String, String>();
		String selectQuery = "SELECT  * FROM " + TABLE_USER;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		cursor.moveToFirst();
		if (cursor.getCount() > 0) {

			user.put(id_admin, cursor.getString(1));
			user.put(role_admin, cursor.getString(2));
			user.put(id_ref, cursor.getString(3));
			user.put(nama_admin, cursor.getString(4));
			user.put(email, cursor.getString(5));
			user.put(nomor_hp, cursor.getString(6));
			user.put(img, cursor.getString(7));
			user.put(img_thumb, cursor.getString(8));
			user.put(license, cursor.getString(9));
			user.put(license_exp, cursor.getString(10));
			user.put(create, cursor.getString(11));
			user.put(read, cursor.getString(12));
			user.put(update, cursor.getString(13));
			user.put(delete, cursor.getString(14));
			user.put(provinsi, cursor.getString(15));
			user.put(kabupaten, cursor.getString(16));
			user.put(tanggal_post, cursor.getString(17));
		}
		cursor.close();
		db.close();

		Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

		return user;
	}


	public void deleteUsers() {
		SQLiteDatabase db = this.getWritableDatabase();

		db.delete(TABLE_USER, null, null);

		db.close();

		Log.d(TAG, "Deleted all user info from sqlite");
	}

}
