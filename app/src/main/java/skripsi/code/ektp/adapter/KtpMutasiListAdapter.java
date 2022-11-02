package skripsi.code.ektp.adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import java.util.List;

import skripsi.code.ektp.R;
import skripsi.code.ektp.controller.ControllerKtpMutasi;
import skripsi.code.ektp.controller.ControllerKtpPetugas;
import skripsi.code.ektp.helper.BadgeDrawable;
import skripsi.code.ektp.model.Mutasi;

public class KtpMutasiListAdapter extends BaseAdapter {
    private LayoutInflater
            inflater;
    private List<Mutasi>
            mutasiList;
    private Context
            mContext;
    private ProgressDialog
            pDialog;
    private ControllerKtpMutasi
            cMutasi;
    private String
            KtpId,
            stat;

    public KtpMutasiListAdapter(Context c, ProgressDialog pDialog, ControllerKtpMutasi cMutasi, List<Mutasi> mutasiList, String KtpId, String stat) {
        this.mContext= c;
        this.pDialog = pDialog;
        this.mutasiList = mutasiList;
        this.cMutasi = cMutasi;
        this.KtpId = KtpId;
        this.stat = stat;
        notifyDataSetChanged();
   }
    @Override
    public int getCount() {
        return mutasiList.size();
    }

    @Override
    public Object getItem(int location) {
        return mutasiList.get(location);
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
            convertView = inflater.inflate(R.layout.list_row_ktp_mutasi, null);

        final ImageView foto_pas = (ImageView) convertView.findViewById(R.id.img_thumb);
        TextView nik_ktp = (TextView) convertView.findViewById(R.id.nik_ktp);
        TextView nama_ktp = (TextView) convertView.findViewById(R.id.nama_anggota);
        TextView provinsi = (TextView) convertView.findViewById(R.id.provinsi);
        TextView kabupaten = (TextView) convertView.findViewById(R.id.kabupaten);
        TextView mutasi = (TextView) convertView.findViewById(R.id.status_mutasi);
        TextView tanggal_post = (TextView) convertView.findViewById(R.id.tanggal_post);

        final Mutasi mut = mutasiList.get(position);

        nik_ktp.setText(mut.get_nik_ktp());
        nama_ktp.setText(mut.get_nama_anggota());
        tanggal_post.setText(mut.get_tgl_pengajuan_mutasi());

        foto_pas.setImageResource(R.drawable.no_user);

//        Glide.with(mContext).load(ktp.get_img_pas_thumb()).asBitmap().fitCenter().into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
//                foto_pas.setImageBitmap(bitmap);
//            }
//        });

        String prov="", kab="";

        if(stat.equals("masuk") || stat=="masuk"){

             prov = mut.get_provinsi_asal();
             kab = mut.get_kabupaten_asal();
        } else if(stat.equals("keluar") || stat=="keluar") {

             prov = mut.get_provinsi_tujuan();
             kab = mut.get_kabupaten_tujuan();
        }

        final BadgeDrawable drawable_prov =
                new BadgeDrawable.Builder()
                        .type(BadgeDrawable.TYPE_ONLY_ONE_TEXT)
                        .badgeColor(android.graphics.Color.parseColor("#20c997"))
                        .text1(prov.toUpperCase())
                        .build();

        SpannableString text_provinsi =  new SpannableString(TextUtils.concat(
                drawable_prov.toSpannable()
        ));

        provinsi.setText(text_provinsi);

        if(mut.get_nama_status_mutasi().equals("") || mut.get_nama_status_mutasi()==""){

        }else{
           String color = "#ffbc34";

           if(mut.get_nama_status_mutasi().equals("DIKIRIMKAN")){
               color = "#ffbc34";
           } else if(mut.get_nama_status_mutasi().equals("DITERIMA")){
               color = "#0D9F67";
           }

            final BadgeDrawable drawable_mut =
                    new BadgeDrawable.Builder()
                            .type(BadgeDrawable.TYPE_ONLY_ONE_TEXT)
                            .badgeColor(android.graphics.Color.parseColor(color))
                            .text1(mut.get_nama_status_mutasi())
                            .build();

            SpannableString text_mutasi =  new SpannableString(TextUtils.concat(
                    drawable_mut.toSpannable()
            ));
            mutasi.setText(text_mutasi);
        }

        if(kab.equals("") || kab==""){

        }else{

            final BadgeDrawable drawable_kab =
                    new BadgeDrawable.Builder()
                            .type(BadgeDrawable.TYPE_ONLY_ONE_TEXT)
                            .badgeColor(android.graphics.Color.parseColor("#009efb"))
                            .text1(kab.toUpperCase())
                            .build();

            SpannableString text_kabupaten =  new SpannableString(TextUtils.concat(
                    drawable_kab.toSpannable()
            ));

            kabupaten.setText(text_kabupaten);
        }

        ImageView imageClick = (ImageView) convertView.findViewById(R.id.row_click_imageView);

        if(stat.equals("masuk") || stat=="masuk"){

            try {
                imageClick.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.row_click_imageView:
                                PopupMenu popup = new PopupMenu(mContext, v);
                                popup.getMenuInflater().inflate(R.menu.menu_main_mutasi_masuk,popup.getMenu());
                                popup.show();
                                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem item) {

                                        switch (item.getItemId()) {
                                            case R.id.konfirm_mutasi:
                                                if (mut.get_status_mutasi().equals("DIKIRIMKAN")) {
                                                    konfirmasiDialog(pDialog, mut.get_id_mutasi(),mut.get_nama_anggota());
                                                } else {
                                                    diterimaDialog(mut.get_nama_anggota());
                                                }
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
        } else if(stat.equals("keluar") || stat=="keluar") {

            try {
                imageClick.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.row_click_imageView:
                                PopupMenu popup = new PopupMenu(mContext, v);
                                popup.getMenuInflater().inflate(R.menu.menu_main_mutasi_keluar,popup.getMenu());
                                popup.show();
                                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem item) {

                                        switch (item.getItemId()) {
                                            case R.id.edit_mutasi:
                                                disableDialog();
                                                break;
                                            case R.id.delete_mutasi:
                                                deleteDialog(pDialog,mut.get_id_mutasi(),mut.get_nama_anggota());
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
        }
        return convertView;
    }

    public void deleteDialog(final ProgressDialog pd, final String mut_id, final String nama_mut){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle("Konfirmasi Hapus...");
        alertDialog.setMessage("Apakah anda yakin akan menghapus mutasi anggota ( "+nama_mut+" ) ?");
        alertDialog.setIcon(R.drawable.ic_action_action_report_problem);
        alertDialog.setPositiveButton("YA", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                cMutasi.deleteKtpMutasi(pd, mut_id);
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

    public void konfirmasiDialog(final ProgressDialog pd, final String mut_id, final String nama_mut){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle("Konfirmasi Mutasi...");
        alertDialog.setMessage("Apakah anda yakin mengkonfirmasi anggota ( "+nama_mut+" ) ?");
        alertDialog.setIcon(R.drawable.ic_action_action_report_problem);
        alertDialog.setPositiveButton("YA", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                cMutasi.konfirmasiKtpMutasi(pDialog, mut_id,"list");
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

    public void diterimaDialog(final String nama_mut){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle("Pemberitahuan...");
        alertDialog.setMessage("Anggota ( "+nama_mut+" ) telah diterima/dimutasikan.");
        alertDialog.setIcon(R.drawable.ic_action_action_report_problem);
        alertDialog.setNegativeButton("TUTUP", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
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
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

}