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


public class xAlbumFragment extends Fragment {
    MaterialButton add_photo_btn, create_album_btn, view_albums_btn, wifi_direct_btn;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        add_photo_btn = view.findViewById(R.id.add_photo_to_btn);
        create_album_btn = view.findViewById(R.id.new_album_btn);
        view_albums_btn = view.findViewById(R.id.list_albums_btn);
        wifi_direct_btn = view.findViewById(R.id.send_photo);

        add_photo_btn.setOnClickListener(v -> add_photo());
        create_album_btn.setOnClickListener(v -> create_album());
        view_albums_btn.setOnClickListener(v -> view_albums());
        wifi_direct_btn.setOnClickListener(v -> wifi_direct());

        return view;
    }

    void add_photo () {
        startActivity(new Intent(xAlbumFragment.this.getActivity(), AddImage.class));
    }

    private void wifi_direct() {

    }

    private void view_albums() {

    }

    private void create_album() {
        startActivity(new Intent(xAlbumFragment.this.getActivity(), a_CreateAlbum.class));
    }
}