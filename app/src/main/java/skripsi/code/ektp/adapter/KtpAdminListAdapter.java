package skripsi.code.ektp.adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;

import skripsi.code.ektp.R;
import skripsi.code.ektp.controller.ControllerKtpAdmin;
import skripsi.code.ektp.controller.ControllerKtpPetugas;
import skripsi.code.ektp.helper.BadgeDrawable;
import skripsi.code.ektp.model.Ktp;

public class KtpAdminListAdapter extends BaseAdapter {
    private LayoutInflater
            inflater;
    private List<Ktp>
            ktpList;
    private Context
            mContext;
    private ProgressDialog
            pDialog;
    private ControllerKtpAdmin
            cKtp;
    private String
            KtpId,
            stat;

    public KtpAdminListAdapter(Context c, ProgressDialog pDialog, ControllerKtpAdmin cKtp, List<Ktp> ktpList, String KtpId, String stat) {
        this.mContext= c;
        this.pDialog = pDialog;
        this.ktpList = ktpList;
        this.cKtp = cKtp;
        this.KtpId = KtpId;
        this.stat = stat;
        notifyDataSetChanged();
   }
    @Override
    public int getCount() {
        return ktpList.size();
    }

    @Override
    public Object getItem(int location) {
        return ktpList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row_ktp_admin, null);

        final ImageView foto_pas = (ImageView) convertView.findViewById(R.id.img_thumb);
        TextView nik_ktp = (TextView) convertView.findViewById(R.id.nik_ktp);
        TextView nama_ktp = (TextView) convertView.findViewById(R.id.nama_anggota);
        TextView provinsi = (TextView) convertView.findViewById(R.id.provinsi);
        TextView kabupaten = (TextView) convertView.findViewById(R.id.kabupaten);
        TextView mutasi = (TextView) convertView.findViewById(R.id.status_mutasi);
        TextView tanggal_post = (TextView) convertView.findViewById(R.id.tanggal_post);

        final Ktp ktp = ktpList.get(position);

        nik_ktp.setText(ktp.get_nik_kta_baru());
        nama_ktp.setText(ktp.get_nama_ktp());
        tanggal_post.setText(ktp.get_tanggal_post());

        foto_pas.setImageResource(R.drawable.ico_error);

        Glide.with(mContext).load(ktp.get_img_pas_thumb()).asBitmap().fitCenter().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                foto_pas.setImageBitmap(bitmap);
            }
        });


        final BadgeDrawable drawable_prov =
                new BadgeDrawable.Builder()
                        .type(BadgeDrawable.TYPE_ONLY_ONE_TEXT)
                        .badgeColor(android.graphics.Color.parseColor("#36bea6"))
                        .text1(ktp.get_provinsi_asal())
                        .build();

        SpannableString text_provinsi =  new SpannableString(TextUtils.concat(
                drawable_prov.toSpannable()
        ));

        if(ktp.get_status_mutasi().equals("") || ktp.get_status_mutasi()==""){

        }else{
            final BadgeDrawable drawable_mut =
                    new BadgeDrawable.Builder()
                            .type(BadgeDrawable.TYPE_ONLY_ONE_TEXT)
                            .badgeColor(android.graphics.Color.parseColor("#ffbc34"))
                            .text1(ktp.get_nama_status_mutasi())
                            .build();

            SpannableString text_mutasi =  new SpannableString(TextUtils.concat(
                    drawable_mut.toSpannable()
            ));
            mutasi.setText(text_mutasi);
        }


        final BadgeDrawable drawable_kab =
                new BadgeDrawable.Builder()
                        .type(BadgeDrawable.TYPE_ONLY_ONE_TEXT)
                        .badgeColor(android.graphics.Color.parseColor("#009efb"))
                        .text1(ktp.get_kabupaten_asal().toUpperCase())
                        .build();

        SpannableString text_kabupaten =  new SpannableString(TextUtils.concat(
                drawable_kab.toSpannable()
        ));

        kabupaten.setText(text_kabupaten);
        provinsi.setText(text_provinsi);

        ImageView imageClick = (ImageView) convertView.findViewById(R.id.row_click_imageView);
        try {
            imageClick.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.row_click_imageView:
                            PopupMenu popup = new PopupMenu(mContext, v);
                            popup.getMenuInflater().inflate(R.menu.menu_main_ktp_admin,popup.getMenu());
                            popup.show();
                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {

                                    switch (item.getItemId()) {
                                        case R.id.mutasi:
                                            disableDialog();
                                            break;
                                        default:
                                            break;
                                    }
                                    return true;
                                }
                            });
                            break;
                        default:
                            break;
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    public void deleteDialog(final ProgressDialog pd, final ControllerKtpPetugas cj, final String id, final String nm){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle("Konfirmasi Hapus...");
        alertDialog.setMessage("Apakah anda yakin akan menghapus tugas ( "+nm+" ) ?");
        alertDialog.setIcon(R.drawable.ic_action_action_report_problem);
        alertDialog.setPositiveButton("YA", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                cj.deleteKtp(pd,id);
            }
        });
        alertDialog.setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mContext, "Dibatalkan", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    public void disableDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle("Pemberitahuan...");
        alertDialog.setMessage("Fitur ini sedang di non-aktifkan.");
        alertDialog.setIcon(R.drawable.ic_action_action_report_problem);
        alertDialog.setNegativeButton("TUTUP", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mContext, "Dibatalkan", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

}