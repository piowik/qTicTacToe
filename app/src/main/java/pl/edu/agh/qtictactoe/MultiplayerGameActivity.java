package pl.edu.agh.qtictactoe;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.edu.agh.qtictactoe.adapter.FieldAdapter;
import pl.edu.agh.qtictactoe.network.Network;

public class MultiplayerGameActivity extends AppCompatActivity {
    private Client client;

    @BindView(R.id.recyclerView1)
    RecyclerView recyclerView1;
    @BindView(R.id.recyclerView2)
    RecyclerView recyclerView2;
    @BindView(R.id.recyclerView3)
    RecyclerView recyclerView3;
    @BindView(R.id.recyclerView4)
    RecyclerView recyclerView4;
    @BindView(R.id.recyclerView5)
    RecyclerView recyclerView5;
    @BindView(R.id.recyclerView6)
    RecyclerView recyclerView6;
    @BindView(R.id.recyclerView7)
    RecyclerView recyclerView7;
    @BindView(R.id.recyclerView8)
    RecyclerView recyclerView8;
    @BindView(R.id.recyclerView9)
    RecyclerView recyclerView9;

    List<Integer> itemsList1 = new ArrayList<>();
    List<Integer> itemsList2 = new ArrayList<>();
    List<Integer> itemsList3 = new ArrayList<>();
    List<Integer> itemsList4 = new ArrayList<>();
    List<Integer> itemsList5 = new ArrayList<>();
    List<Integer> itemsList6 = new ArrayList<>();
    List<Integer> itemsList7 = new ArrayList<>();
    List<Integer> itemsList8 = new ArrayList<>();
    List<Integer> itemsList9 = new ArrayList<>();

    List<RecyclerView> recyclerViewsList;
    List<FieldAdapter> adaptersList = new ArrayList<>();
    List<List<Integer>> itemsList = Arrays.asList(itemsList1, itemsList2, itemsList3, itemsList4, itemsList5, itemsList6, itemsList7, itemsList8, itemsList9);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ButterKnife.bind(this);
        recyclerViewsList = Arrays.asList(recyclerView1, recyclerView2, recyclerView3, recyclerView4, recyclerView5, recyclerView6, recyclerView7, recyclerView8, recyclerView9);
        init();

        String hostIp = getIntent().getExtras().getString("ip");
        if (hostIp != null) {
            client = new Client();
            client.start();
            client.addListener(new Listener() {
                @Override
                public void connected(Connection connection) {
                    super.connected(connection);
                }

                @Override
                public void disconnected(Connection connection) {
                    super.disconnected(connection);
                }

                @Override
                public void received(Connection connection, Object o) {
                    super.received(connection, o);
                }
            });
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    try {
                        client.connect(5000, hostIp, Network.GAME_PORT);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

            }.execute();

        }
        else
            finish();


    }

    private void init() {
        for (int i = 0; i < 9; i++) {
            List<Integer> list = itemsList.get(i);
            RecyclerView recyclerView = recyclerViewsList.get(i);

            for (int j = 0; j < 9; j++) {
                list.add(0);
            }
            FieldAdapter adapter = new FieldAdapter(list);
            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
            recyclerView.setLayoutManager(layoutManager);
            adaptersList.add(adapter);
        }
    }
}
