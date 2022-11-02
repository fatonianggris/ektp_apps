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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import skripsi.code.ektp.helper.AppRequest;
import skripsi.code.ektp.model.Petugas;
import skripsi.code.ektp.view.activity.EditProfileActivity;
import skripsi.code.ektp.view.activity.MainActivityPetugas;

/**
 * Created by TONY on 1/13/2017.
 */

public class ControllerPetugas {
    private Context
            mContext;
    private String
            urlProfile;
    private String getdata = "getdataprofile";
    private static final int REQUEST_EDIT_PROFILE = 100;
    private static final int REQUEST_OK = 200;

    public ControllerPetugas(Context c, String url) {
        this.mContext= c;
        this.urlProfile= url;
    }


    public void getEditProfile(final ProgressDialog pDialog, final String idp){

        pDialog.setMessage("Mengambil data...");
        pDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.GET,urlProfile+idp, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);

                    boolean error = jObj.getBoolean("status");
                    if (error) {

                        final ArrayList<Petugas> list = new ArrayList<Petugas>();
                        final Petugas prof = new Petugas();

                        JSONObject user = jObj.getJSONObject("petugas");

                        String id_petugas = user.getString("id_petugas");
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

                        prof.set_id_petugas(id_petugas);
                        prof.set_nama_petugas(nama_petugas);
                        prof.set_nomor_ktp(nomor_ktp);
                        prof.set_email_petugas(email_petugas);
                        prof.set_nomor_hp(nomor_hp);
                        prof.set_alamat_petugas(alamat_petugas);
                        prof.set_kode_petugas(kode_petugas);
                        prof.set_img(img);
                        prof.set_img_thumb(img_thumb);
                        prof.set_tanggal_post(tanggal_post);
                        prof.set_status_data(status_data);

                        list.add(prof);

                        Intent intent = new Intent(mContext, EditProfileActivity.class);
                        intent.putExtra("getprofile", list);
                        ((Activity) mContext).startActivityForResult(intent, REQUEST_EDIT_PROFILE);

                    } else {

                        String msg = jObj.getString("message");
                        Toast.makeText(mContext,msg, Toast.LENGTH_LONG).show();
                    }
                    pDialog.dismiss();
                } catch (JSONException e) {
                    Toast.makeText(mContext, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    pDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext,error.getMessage(), Toast.LENGTH_LONG).show();
                pDialog.dismiss();
            }
        });

        AppRequest.getInstance().addToRequestQueue(strReq, getdata);
    }


    public void updateProfile(final ProgressDialog pDialog, final Petugas usr){

        pDialog.setMessage("Mengubah profile ...");
        pDialog.show();
        StringRequest strReq = new StringRequest(Request.Method.POST, urlProfile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("status");
                    String msg = jObj.getString("message");
                    if (error) {
                        Toast.makeText(mContext,msg, Toast.LENGTH_LONG).show();
                        Intent addIntent = new Intent(mContext, MainActivityPetugas.class);
                        ((Activity) mContext).setResult(REQUEST_OK,addIntent);
                        ((Activity) mContext).finish();
                    } else {
                      Toast.makeText(mContext,msg, Toast.LENGTH_LONG).show();
                    }
                    pDialog.dismiss();
                }
                catch (JSONException e) {

                    Toast.makeText(mContext, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    pDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(mContext,error.getMessage(), Toast.LENGTH_LONG).show();
                pDialog.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("id_petugas", usr.get_id_petugas());
                params.put("nama_petugas", usr.get_nama_petugas());
                params.put("nomor_ktp",usr.get_nomor_ktp());
                params.put("email_petugas",usr.get_email_petugas());
                params.put("nomor_hp",usr.get_nomor_hp());
                params.put("alamat_petugas", usr.get_alamat_petugas());
                params.put("img",usr.get_img());
                params.put("img_thumb", usr.get_img_thumb());
                params.put("password", usr.get_password());
                params.put("nama_foto",usr.get_img_name());
                return params;
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
