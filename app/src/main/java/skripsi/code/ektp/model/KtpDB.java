package skripsi.code.ektp.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class KtpDB extends SQLiteOpenHelper {

    private static final String TAG = PetugasDB.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "android_ktp";

    private static final String TABLE_KTP = "ktp";

    private static final String id_user = "id_user";
    private static final String id_petugas = "id_petugas";
    private static final String id_admin = "id_admin";
    private static final String nama_petugas = "nama_petugas";
    private static final String nomor_ktp = "nomor_ktp";
    private static final String email_petugas = "email_petugas";
    private static final String nomor_hp= "nomor_hp";
    private static final String alamat_petugas = "alamat_petugas";
    private static final String kode_petugas = "kode_petugas";
    private static final String img = "img";
    private static final String img_thumb = "img_thumb";
    private static final String tanggal_post = "tanggal_post";
    private static final String status_data= "status_data";
    private static final String license = "license";

    public KtpDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PETUGAS_TABLE = "CREATE TABLE " + TABLE_KTP + "("
                + id_user + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + id_petugas+ " TEXT,"
                + id_admin+ " TEXT,"
                + nama_petugas + " TEXT,"
                + nomor_ktp + " TEXT,"
                + email_petugas + " TEXT,"
                + nomor_hp + " TEXT,"
                + alamat_petugas + " TEXT,"
                + kode_petugas + " TEXT,"
                + img + " TEXT,"
                + img_thumb + " TEXT,"
                + tanggal_post + " TEXT,"
                + status_data + " TEXT,"
                + license + " TEXT" + ")";
        db.execSQL(CREATE_PETUGAS_TABLE);

        Log.d(TAG, "Database tables created");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KTP);
        onCreate(db);
    }


    public void addUser(Petugas user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(id_petugas, user.get_id_admin());
        values.put(id_admin, user.get_id_admin());
        values.put(nama_petugas, user.get_nama_petugas());
        values.put(nomor_ktp, user.get_nomor_hp());
        values.put(email_petugas, user.get_email_petugas());
        values.put(nomor_hp, user.get_nomor_hp());
        values.put(alamat_petugas, user.get_alamat_petugas());
        values.put(kode_petugas, user.get_kode_petugas());
        values.put(img, user.get_img());
        values.put(img_thumb, user.get_img_thumb());
        values.put(tanggal_post, user.get_tanggal_post());
        values.put(status_data, user.get_status_data());
        values.put(license, user.get_license());

        long id = db.insert(TABLE_KTP, null, values);
        db.close();

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }


    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_KTP;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {

            user.put(id_petugas, cursor.getString(1));
            user.put(id_admin, cursor.getString(2));
            user.put(nama_petugas, cursor.getString(3));
            user.put(nomor_ktp, cursor.getString(4));
            user.put(email_petugas, cursor.getString(5));
            user.put(nomor_hp, cursor.getString(6));
            user.put(alamat_petugas, cursor.getString(7));
            user.put(kode_petugas, cursor.getString(5));
            user.put(img, cursor.getString(6));
            user.put(img_thumb, cursor.getString(7));
            user.put(tanggal_post, cursor.getString(5));
            user.put(status_data, cursor.getString(6));
            user.put(license, cursor.getString(7));
        }
        cursor.close();
        db.close();

        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }


    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_KTP, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

}
