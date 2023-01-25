package ao.uan.p2foto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.List;

public class SignUp extends AppCompatActivity {
    EditText username_input, email_input, password_input, repeat_input;
    MaterialButton create_account_btn, clean_btn;
    ProgressBar load_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // INPUTS IN SIGN UP ACTIVITY
        username_input = findViewById(R.id.username_input);
        email_input = findViewById(R.id.email_input);
        password_input = findViewById(R.id.password_input);
        repeat_input = findViewById(R.id.repeat_password_input);

        load_bar = findViewById(R.id.load_bar);
        create_account_btn = findViewById(R.id.sign_up_btn);
        clean_btn = findViewById(R.id.clear_btn);

        create_account_btn.setOnClickListener(v -> createAccount());
        clean_btn.setOnClickListener(v -> clearFields());
    }

    // CREATES AN ACCOUNT AFTER IT'S VALIDATED
    void createAccount() {
        String username = username_input.getText().toString().trim();
        String email = email_input.getText().toString().trim();
        String password = password_input.getText().toString().trim();
        String repeat = repeat_input.getText().toString().trim();

        boolean is_valid = validate(username, email, password, repeat);
        if(!(is_valid)) { return; }

        addAccountToServer(username, email, password);
    }

    // ADDS ACCOUNT TO FIREBASE
    void addAccountToServer(String username, String email, String password) {
        showLoadbar(true);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(
                SignUp.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        showLoadbar(false); // HIDE LOADBAR
                        if (task.isSuccessful()) {
                            addUserToDatabase(username, email); // CHANGE LOADCOLOR
                            showLoadbar(true);
                            Support.printToast(SignUp.this, "Registration Complete");

                            // VERIFY IF THE USER IS NOT NULL & SEND EMAIL
                            //if (mAuth.getCurrentUser() != null) {
                            //    mAuth.getCurrentUser().sendEmailVerification();
                            //}
                            // SIGN OUT TO LOG BACK IN

                            mAuth.signOut();
                            goBack();
                        } else {
                            // PRINT ERROR
                            Support.printToast(SignUp.this, task.getException().getLocalizedMessage());
                        }
                    }
                }
        );
    }

    void addUserToDatabase(String username, String email) {
        cUser new_user = new cUser(username, email);
        FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getUid())
                .setValue(new_user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            showLoadbar(false);
                            Support.printToast(SignUp.this,"ADDED !");
                        } else {
                            Support.printToast(SignUp.this, task.getException().getLocalizedMessage());
                        }
                    }
                });
    }

    // SHOW LOAD BAR
    void showLoadbar(boolean processing) {
        if (processing) {
            load_bar.setVisibility(View.VISIBLE);
            create_account_btn.setVisibility(View.GONE);
        } else {
            load_bar.setVisibility(View.GONE);
            create_account_btn.setVisibility(View.VISIBLE);
        }
    }

    // Validates Each Field
    boolean validate(String username, String email, String password, String repeat) {
        // TAMANHO DO USERNAME
        if (username.length() < 6) {
            username_input.setError("Username must Contain at least 6 Characters");
            return false;
        }

        // CHECK IF USERNAME HAS BEEN ALREADY USED
        /*if (true) {
            username_input.setError("Username Already Taken");
            return false;
        }*/

        // VER SE EMAIL SEGUE O PADRÃO: .*@MAILSERVER.TOPLEVELDOMAIN
        if (!(email.matches(".*@gmail.com"))) {
            email_input.setError("Email is Invalid !!!");
            return false;
        }

        // TAMANHO DA PASSWORD
        if (password.length() < 6) {
            password_input.setError("The Password Is Too Short");
            return false;
        }

        if (!(password.matches(repeat))) {
            password_input.setError("The Passwords Do Not Match");
            return false;
        }

        return true;
    }

    // Clears All Fields
    void clearFields() {
        username_input.setText("");
        email_input.setText("");
        password_input.setText("");
        repeat_input.setText("");
    }

    // GOES BACK TO MAIN MENU : FIX
    void goBack() {
        startActivity(new Intent(SignUp.this, Loading.class));
    }
}