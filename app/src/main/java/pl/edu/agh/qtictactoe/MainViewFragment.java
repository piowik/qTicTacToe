package pl.edu.agh.qtictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainViewFragment extends Fragment {

    @OnClick(R.id.singleplayer_button)
    public void singlePlayer() {
        Intent intent = new Intent(getContext(), GameActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.multiplayer_button)
    public void multiplayer() {
        NewMultiplayerGameFragment mainViewFragment = new NewMultiplayerGameFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, mainViewFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
