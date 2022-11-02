package skripsi.code.ektp.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import skripsi.code.ektp.helper.AppRequest;
import skripsi.code.ektp.model.Admin;
import skripsi.code.ektp.model.AdminDB;
import skripsi.code.ektp.model.Petugas;
import skripsi.code.ektp.model.PetugasDB;
import skripsi.code.ektp.helper.SessionManager;
import skripsi.code.ektp.view.activity.MainActivityAdmin;
import skripsi.code.ektp.view.activity.MainActivityPetugas;

public class ControllerLogin {
    private Context mContext;
    private String urlLogin;
    private String getdata = "req_login";

    public ControllerLogin(Context c, String url) {
        this.mContext= c;
        this.urlLogin= url;
    }

    public void checkLoginPetugas(final PetugasDB db, final SessionManager session, final ProgressDialog pDialog, final String email, final String password, final String kode) {

        pDialog.setMessage("Logging in ...");
        pDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST, urlLogin, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("status");

                    if (error) {
                        pDialog.dismiss();
                        session.setLogin(true);
                        session.setRole("1");
                        final Petugas pet= new Petugas();

                        JSONObject user = jObj.getJSONObject("user");
                        String id_petugas = user.getString("id_petugas");
                        String id_admin = user.getString("id_admin");
                        String nama_petugas = user.getString("nama_petugas");
                        String nomor_ktp = user.getString("nomor_ktp");
                        String email_petugas = user.getString("email_petugas");
                        String nomor_hp = user.getString("nomor_hp");
                        String alamat_petugas = user.getString("alamat_petugas");
                        String kode_petugas = user.getString("kode_petugas");
                        String img = user.getString("img");
                        String img_thumb = user.getString("img_thumb");
                        String tanggal_post = user.getString("tanggal_post");
                        String status_data = user.getString("status_data");
                        String license = user.getString("license");
                        String provinsi = user.getString("provinsi");
                        String kabupaten = user.getString("kabupaten");
                        String path = user.getString("path");

                        pet.set_id_petugas(id_petugas);
                        pet.set_id_admin(id_admin);
                        pet.set_nama_petugas(nama_petugas);
                        pet.set_nomor_ktp(nomor_ktp);
                        pet.set_email_petugas(email_petugas);
                        pet.set_nomor_hp(nomor_hp);
                        pet.set_alamat_petugas(alamat_petugas);
                        pet.set_kode_petugas(kode_petugas);
                        pet.set_img(img);
                        pet.set_img_thumb(img_thumb);
                        pet.set_tanggal_post(tanggal_post);
                        pet.set_status_data(status_data);
                        pet.set_license(license);
                        pet.set_provinsi(provinsi);
                        pet.set_kabupaten(kabupaten);
                        pet.set_path(path);

                        db.addUser(pet);

                        Intent intent = new Intent(mContext, MainActivityPetugas.class);
                        ((Activity) mContext).startActivity(intent);
                        ((Activity) mContext).finish();

                    } else {
                        pDialog.dismiss();
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(mContext,errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                    Toast.makeText(mContext, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    pDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_LONG).show();
                pDialog.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                params.put("kode", kode);
                return params;
            }
            @Override
            public Priority getPriority() {
                return getPriority().HIGH;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
                return params;
            }
        };

        AppRequest.getInstance().addToRequestQueue(strReq, getdata);
    }

    public void checkLoginAdmin(final AdminDB db, final SessionManager session, final ProgressDialog pDialog, final String email, final String password) {

        pDialog.setMessage("Logging in ...");
        pDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST, urlLogin, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("status");

                    if (error) {
                        pDialog.dismiss();
                        session.setLogin(true);
                        session.setRole("2");
                        final Admin adm = new Admin();

                        JSONObject user = jObj.getJSONObject("user");
                        String id_admin = user.getString("id_user");
                        String role_admin = user.getString("role_admin");
                        String id_ref = user.getString("id_ref");
                        String nama_admin = user.getString("nama_admin");
                        String email = user.getString("email");
                        String nomor_hp = user.getString("nomor_hp");
                        String img = user.getString("img");
                        String img_thumb = user.getString("img_thumb");
                        String license = user.getString("license");
                        String license_exp = user.getString("license_exp");
                        String create = user.getString("create_prev");
                        String read = user.getString("read_prev");
                        String update = user.getString("update_prev");
                        String delete = user.getString("delete_prev");
                        String provinsi = user.getString("provinsi");
                        String kabupaten = user.getString("kabupaten");
                        String tanggal_post = user.getString("tanggal_post");

                        adm.set_id_admin(id_admin);
                        adm.set_role_admin(role_admin);
                        adm.set_id_ref(id_ref);
                        adm.set_nama_admin(nama_admin);
                        adm.set_email(email);
                        adm.set_nomor_hp(nomor_hp);
                        adm.set_img(img);
                        adm.set_img_thumb(img_thumb);
                        adm.set_license(license);
                        adm.set_license_exp(license_exp);
                        adm.set_create(create);
                        adm.set_read(read);
                        adm.set_update(update);
                        adm.set_delete(delete);
                        adm.set_provinsi(provinsi);
                        adm.set_kabupaten(kabupaten);
                        adm.set_tanggal_post(tanggal_post);

                        db.addUser(adm);

                        Intent intent = new Intent(mContext, MainActivityAdmin.class);
                        ((Activity) mContext).startActivity(intent);
                        ((Activity) mContext).finish();

                    } else {
                        pDialog.dismiss();
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(mContext,errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                    Toast.makeText(mContext, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    pDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_LONG).show();
                pDialog.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
            @Override
            public Priority getPriority() {
                return getPriority().HIGH;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
                return params;
            }
        };

        AppRequest.getInstance().addToRequestQueue(strReq, getdata);
    }

}
