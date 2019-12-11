package pl.edu.agh.qtictactoe;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.edu.agh.qtictactoe.database.QDatabase;
import pl.edu.agh.qtictactoe.database.dao.IPDao;
import pl.edu.agh.qtictactoe.database.entity.IPAdd;

public class NewMultiplayerGameFragment extends Fragment {
    @BindView(R.id.textViewIp)
    TextView textViewIp;
    private IPDao ipDao;

    @BindView(R.id.editTextHostIp)
    EditText hostIpEditText;

    @OnClick(R.id.buttonHost)
    public void hostClicked() {


        new GameServer(() -> {
            Intent intent = new Intent(getContext(), GameActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("ip", "localhost");
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    @OnClick(R.id.buttonJoin)
    public void joinClicked() {
        ipDao = QDatabase.getAppDatbase(getContext()).daoAccess();
        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... voids) {
                ipDao.insertOnlySingleIp(new IPAdd(voids[0]));
                return null;
            }
        }.execute(hostIpEditText.getText().toString());
        Intent intent = new Intent(getContext(), GameActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("ip", hostIpEditText.getText().toString());
        intent.putExtras(bundle);
        startActivity(intent);
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new_multiplayer_game, container, false);
        ButterKnife.bind(this, rootView);
        ipDao = QDatabase.getAppDatbase(getActivity()).daoAccess();
        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... voids) {
                List<IPAdd> ipList = ipDao.fetchAll();
                if (ipList.size() > 0) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            hostIpEditText.setText(ipList.get(ipList.size() - 1).getIpAdd());
                        }
                    });
                }
                return null;
            }
        }.execute();
        setRetainInstance(true);
//        ButterKnife.bind(getContext());

        textViewIp.setText(getIpAddress(true));
        return rootView;
    }


    public static String getIpAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':') < 0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim < 0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            Log.e("Functions", "getIpAddress exception: " + ex.getMessage());
        }
        return "";

    }

}
