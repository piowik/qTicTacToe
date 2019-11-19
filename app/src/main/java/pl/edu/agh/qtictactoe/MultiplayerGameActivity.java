package pl.edu.agh.qtictactoe;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;

import butterknife.ButterKnife;
import pl.edu.agh.qtictactoe.network.Network;

public class MultiplayerGameActivity extends AppCompatActivity {
    private Client client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ButterKnife.bind(this);

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

        } else
            finish();


    }
}
