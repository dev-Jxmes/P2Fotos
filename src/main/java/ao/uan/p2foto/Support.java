package ao.uan.p2foto;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Support {
    static FirebaseDatabase db = FirebaseDatabase.getInstance();
    static DatabaseReference UsersRef = db.getReference().child("Users");

    // prints a standard toast
    static void printToast (Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    // prints a longer toast based in 'time'
    static void printToast (Context context, String message, int time) {
        for(int i =  0;i <=time;i++){
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    static CollectionReference getCollectionRef(String path) {
        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection(path);
    }

    static String getUID () {
        return FirebaseAuth.getInstance().getUid();
    }

    static String getUsername (String UID) {
        String username;
        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshots) {
                username = snapshots.getChildren("s").getValue(UID.getClass());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return username;
    }

    static ArrayList<cUser> getUsersFomDb() {
        ArrayList<cUser> Users = new ArrayList<>();

        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users.clear();

                for (DataSnapshot snap : snapshot.getChildren()) {
                    Users.add((cUser) (snap.getValue()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return Users;
    }

    static void showButton(MaterialButton b, boolean stat) {
        if (stat)  {
            b.setVisibility(View.VISIBLE);
        } else {
            b.setVisibility(View.GONE);
        }
    }

}
