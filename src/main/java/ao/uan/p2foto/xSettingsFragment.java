package ao.uan.p2foto;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class xSettingsFragment extends Fragment {
    MaterialButton logout_btn;
    FirebaseAuth mauth = FirebaseAuth.getInstance();
    TextView username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_x_settings, container, false);
        logout_btn = view.findViewById(R.id.x_logout_btn);
        username = view.findViewById(R.id.app_header_logged_user);
        username.setText(mauth.getCurrentUser().getEmail());
        logout_btn.setOnClickListener(v -> logout());
        return view;
    }

    void logout () {
        mauth.signOut();
        mauth = null;
        startActivity(new Intent(xSettingsFragment.this.getActivity(), Login.class));
    }
}