package ao.uan.p2foto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class a_CreateAlbum extends AppCompatActivity {
    EditText album_name_input;
    MaterialButton create_album_btn, go_gack;
    ProgressBar load_bar;
    ListView view;

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference UsersRef = db.getReference().child("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acreate_album);

        // INPUTS IN CREATE ALBUM ACTIVITY
        album_name_input = findViewById(R.id.album_name_input);
        create_album_btn = findViewById(R.id.create_album_btn);
        go_gack = findViewById(R.id.go_back_btn);
        view = findViewById(R.id.View);

        load_bar = findViewById(R.id.load_bar_c);

        create_album_btn.setOnClickListener(v -> createAlbum());
        go_gack.setOnClickListener(v -> goBack());
    }

    private void createAlbum() {
        String uid = Support.getUID();
        String album_name = album_name_input.getText().toString().trim();
        String creator = Support.getUsername(uid);
        cAlbum album = new cAlbum(album_name, creator);
        FirebaseDatabase.getInstance().getReference("Users")
                .child(uid)
                .child("/Albums/")
                .child(album_name)
                .setValue(album).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Support.printToast(a_CreateAlbum.this,"Album Created !");
                        } else {
                            Support.printToast(a_CreateAlbum.this, task.getException().getLocalizedMessage());
                        }
                    }
                });
    }

    private void getUsername() {
        String uid = Support.getUID();
        String album_name = album_name_input.getText().toString().trim();
        String creator; // GETS USERNAME FROM DB
        ArrayList <cUser> Users = new ArrayList<>();
        ArrayAdapter <cUser> adapter = new ArrayAdapter<>(this,R.layout.list_items, Users);
        view.setAdapter(adapter);

        UsersRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users.clear();

                for (DataSnapshot data : snapshot.getChildren()) {
                    Users.add(data.getValue(cUser.class));
                }

                for (cUser x : Users) {
                    System.out.println(uid);
                    System.out.println(x.username);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

    private void goBack() {
        startActivity(new Intent(a_CreateAlbum.this, MainActivity.class));
    }
}