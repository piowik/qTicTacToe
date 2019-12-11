package pl.edu.agh.qtictactoe;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    TimeReceiver receiver = new TimeReceiver();
    String FRAGMENT_ID = "fragment_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (savedInstanceState != null) {
            if (savedInstanceState.getInt(FRAGMENT_ID, 0) == 0) {
                MainViewFragment mainViewFragment = new MainViewFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, mainViewFragment);
                fragmentTransaction.commit();

            } else {
                NewMultiplayerGameFragment newMultiplayerGameFragment = new NewMultiplayerGameFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, newMultiplayerGameFragment);
                fragmentTransaction.commit();

            }
        } else {
            MainViewFragment mainViewFragment = new MainViewFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, mainViewFragment);
            fragmentTransaction.commit();
        }
        
    }

    @Override
    protected void onResume() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        intentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        intentFilter.addAction(Intent.ACTION_TIME_CHANGED);
        registerReceiver(receiver, intentFilter);
        super.onResume();
    }

    @Override
    protected void onPause() {
        unregisterReceiver(receiver);
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(FRAGMENT_ID, getSupportFragmentManager().getBackStackEntryCount() > 0 ? 1 : 0);
    }
}
