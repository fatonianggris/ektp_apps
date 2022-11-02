package skripsi.code.ektp.view.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import java.util.HashMap;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import skripsi.code.ektp.R;
import skripsi.code.ektp.adapter.KtpAdminListAdapter;
import skripsi.code.ektp.controller.ControllerKtpAdmin;
import skripsi.code.ektp.data.UrlConfig;
import skripsi.code.ektp.model.AdminDB;


public class ListKtpAdminFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    private SwipeRefreshLayout listSwipeRefreshLayout,emptySwipeRefreshLayout;
    private ListView
            listView;
    private KtpAdminListAdapter
            adapter;
    private FloatingActionButton
            addKtp;
    private ControllerKtpAdmin
            cKtp;
    private ProgressDialog
            pDialog;
    private AdminDB
            dbAdmin;
    private static final String id_ref = "id_ref";
    private static final String role_admin = "role_admin";

    private static final int REQUEST_ACTION = 100;
    private static final int REQUEST_OK = 200;

    private OnFragmentInteractionListener
            mListener;

    public ListKtpAdminFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_ktp_admin_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        listSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.listSwipeRefreshLayout);
        emptySwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.emptySwipeRefreshLayout);
        listView = (ListView) view.findViewById(R.id.listView);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        dbAdmin = new AdminDB(getActivity());
        HashMap<String, String> user = dbAdmin.getUserDetails();
        final String idref = user.get(id_ref);
        final String roleadm = user.get(role_admin);
        final String stat = "list";

        cKtp = new ControllerKtpAdmin(getActivity(), UrlConfig.URL_LIST_KTP_ALL_ADMIN, listSwipeRefreshLayout);
        adapter = new KtpAdminListAdapter(getActivity(),pDialog, cKtp, cKtp.getList(), idref, stat);

        cKtp.setAdapter(adapter);
        listView.setAdapter(adapter);
        listView.setEmptyView(emptySwipeRefreshLayout);

        onCreateSwipeToRefresh(listSwipeRefreshLayout);
        emptySwipeRefreshLayout.setOnRefreshListener(this);

        addKtp = (FloatingActionButton) view.findViewById(R.id.fab);
        addKtp.hide();

        listSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                emptySwipeRefreshLayout.setRefreshing(false);
                cKtp.getList().clear();
                if(isNetworkAvailable(getActivity())) {
                    loadDataFromWs();
                } else {
                    loadDataFromCache();
                    Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_SHORT).show();
                }

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String idk = cKtp.getList().get(position).get_id_ktp();
                cKtp.getDetailKtp(pDialog,idk);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ACTION) {
            if (resultCode == REQUEST_OK) {
                listSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        emptySwipeRefreshLayout.setRefreshing(false);
                        cKtp.getList().clear();
                        loadDataFromWs();
                    }
                });
            }
        }
    }

    private void onCreateSwipeToRefresh(SwipeRefreshLayout refreshLayout) {
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light);

    }

    public void dialogAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Informasi...");
        alertDialog.setMessage("Mohon maaf anda tidak dapat mengubah status projek survei, mengubah status projek survei hanya dilakukan oleh petugas survei, untuk informasi selanjutnya silahkan hubungi admin, Terima kasih.");
        alertDialog.setIcon(R.drawable.ic_action_action_info);
        alertDialog.setNegativeButton("TUTUP", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }


    public void loadDataFromWs() {
        HashMap<String, String> user = dbAdmin.getUserDetails();
        String idref = user.get(id_ref);
        String roleadm = user.get(role_admin);
        cKtp.getListKtpAdminAll(idref, roleadm);
    }

    public void loadDataFromCache() {
        HashMap<String, String> user = dbAdmin.getUserDetails();
        String idp = user.get(id_ref);
        String roleadm = user.get(role_admin);
        cKtp.getListKtpAdminCacheAll(idp, roleadm);
    }

    public boolean isNetworkAvailable(Activity c) {
        boolean state;
        ConnectivityManager cmg = (ConnectivityManager) c.getSystemService(getActivity().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cmg.getActiveNetworkInfo();
        state = activeNetworkInfo != null && activeNetworkInfo.isConnected();

        if (state) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onRefresh() {
        emptySwipeRefreshLayout.setRefreshing(false);
        cKtp.getList().clear();

        if(isNetworkAvailable(getActivity())) {
            loadDataFromWs();
        } else {
            loadDataFromCache();
            Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
