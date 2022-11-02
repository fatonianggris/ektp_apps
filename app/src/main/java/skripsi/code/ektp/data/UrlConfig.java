package skripsi.code.ektp.data;

public class UrlConfig {
	// MAIN URL SERVER
	//public static String MAIN_URL = "https://ektp.acityatech.com/";
	public static String MAIN_URL = "http://192.168.56.1/ktp/";
	// LOGIN URL PETUGAS
	public static String URL_LOGIN_PETUGAS = MAIN_URL+"api/auth/login_petugas";
	// LOGIN URL ADMIN
	public static String URL_LOGIN_ADMIN = MAIN_URL+"api/auth/login_admin";
	// SET REG ID
	public static String REG_ID = MAIN_URL+"api/auth/setregid";

	//JSON UPDATE USER
	public static String URL_UPDATE_PROFILE = MAIN_URL+"api/petugas/updatepetugas";
	//JSON GET DATA PROFILE
	public static String URL_DETAIL_PROFILE = MAIN_URL+"api/petugas/petugasktp/id_petugas/";

	//JSON UPDATE BERANDA PETUGAS
	public static String URL_GET_BERANDA_PETUGAS = MAIN_URL+"api/beranda/berandapetugas/id_petugas/";
	//JSON UPDATE BERANDA ADMIN
	public static String URL_GET_BERANDA_ADMIN = MAIN_URL+"api/beranda/berandaadmin/id_admin/";

	//JSON GET MUTASI MASUK
	public static String URL_GET_MUTASI_MASUK = MAIN_URL+"api/mutasi/getmutasimasuk/id_region_tujuan/";
	//JSON GET MUTASI KELUAR
	public static String URL_GET_MUTASI_KELUAR = MAIN_URL+"api/mutasi/getmutasikeluar/id_region_asal/";

	//JSON GET MUTASI KONFIRM
	public static String URL_GET_KONFIRM_MUTASI = MAIN_URL+"api/mutasi/konfirmasimutasi";

	//JSON GET MUTASI DELETE
	public static String URL_GET_DELETE_MUTASI = MAIN_URL+"api/mutasi/deletemutasi";

	//JSON GET MUTASI DETAIL
	public static String URL_GET_DETAIL_MUTASI = MAIN_URL+"api/mutasi/detailmutasi/id_mutasi/";

	//JSON GET PEKERJAAN
	public static String URL_GET_PEKERJAAN = MAIN_URL+"api/pekerjaan/getpekerjaan";

	//JSON GET PROVINSI
	public static String URL_GET_PROVINSI = MAIN_URL+"api/wilayah/getprovinsi";
	//JSON GET KABUPATEN
	public static String URL_GET_KABUPATEN = MAIN_URL+"api/wilayah/getkabupaten/id_provinsi/";
	//JSON GET KECAMATAN
	public static String URL_GET_KECAMATAN = MAIN_URL+"api/wilayah/getkecamatan/id_provinsi/";
	//JSON GET KELURAHAN
	public static String URL_GET_KELURAHAN = MAIN_URL+"api/wilayah/getkelurahan/id_provinsi/";

	//JSON GET NAMA PROVINSI
	public static String URL_GET_NAMA_PROVINSI = MAIN_URL+"api/wilayahktpscan/getnamaprovinsi/nama_provinsi/";
	//JSON GET NAMA KABUPATEN
	public static String URL_GET_NAMA_KABUPATEN = MAIN_URL+"api/wilayahktpscan/getnamakabupaten/id_provinsi/";
	//JSON GET NAMA KECAMATAN
	public static String URL_GET_NAMA_KECAMATAN = MAIN_URL+"api/wilayahktpscan/getnamakecamatan/id_provinsi/";
	//JSON GET NAMA KELURAHAN
	public static String URL_GET_NAMA_KELURAHAN = MAIN_URL+"api/wilayahktpscan/getnamakelurahan/id_provinsi/";

	//JSON GET NAMA PEKERJAAN
	public static String URL_GET_NAMA_PEKERJAAN = MAIN_URL+"api/pekerjaan/getnamapekerjaan/nama_pekerjaan/";

	//JSON GET LIST KTP ALL PETUGAS
	public static String URL_LIST_KTP_ALL_PETUGAS = MAIN_URL+"api/ktp/ktpallpetugas/id_petugas/";
	//JSON GET LIST KTP ALL ADMIN
	public static String URL_LIST_KTP_ALL_ADMIN = MAIN_URL+"api/ktp/ktpalladmin/id_admin/";

	//JSON ADD KTP
	public static String URL_ADD_KTP = MAIN_URL+"api/ktp/addktp";
	//JSON UPDATE KTP
	public static String URL_UPDATE_KTP = MAIN_URL+"api/ktp/updatektp";
	//JSON DELETE KTP
	public static String URL_DELETE_KTP = MAIN_URL+"api/ktp/deletektp";
	//JSON DETAIL KTP
	public static String URL_DETAIL_KTP = MAIN_URL+"api/ktp/detailktp/id_ktp/";
	//JSON EDIT KTP
	public static String URL_EDIT_KTP = MAIN_URL+"api/ktp/detailktp/id_ktp/";

	// broadcast receiver intent filters
	public static final String REGISTRATION_COMPLETE = "registrationComplete";
	public static final String PUSH_NOTIFICATION = "pushNotification";
	// id to handle the notification in the notification tray
	public static final int NOTIFICATION_ID = 100;
	public static final String SHARED_PREF = "ah_firebase";
	// Directory name to store captured images
	public static final String IMAGE_DIRECTORY_NAME = "eKtpAppsImage";
}
