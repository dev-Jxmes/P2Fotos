package ao.uan.p2foto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText user_login, password;
    MaterialButton login_btn, back_btn, login_create_btn;
    ProgressBar load_bar;
    boolean use_mail = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user_login = findViewById(R.id.login_user_input);
        password = findViewById(R.id.login_password_input);

        load_bar = findViewById(R.id.login_load_bar);

        login_btn = findViewById(R.id.login_btn);
        back_btn = findViewById(R.id.login_go_back_btn);
        login_create_btn = findViewById(R.id.login_create_btn);

        login_btn.setOnClickListener(v -> loginUser());
        back_btn.setOnClickListener(v -> goBack());
        login_create_btn.setOnClickListener(v -> openSignUpMenu());
    }


    void loginUser() {
        String username = user_login.getText().toString().trim();
        String password = this.password.getText().toString().trim();

        boolean is_valid = validate(username, password);
        if(!(is_valid)) { return; }

        logAccountFromServer(username, password);
    }

    void logAccountFromServer(String username, String password) {
        showLoadbar(true);
        if (!(use_mail)) {
            // MATCH USERNAME TO RESPECTIVE EMAIL FROM DATABASE
        }

        FirebaseAuth lmAuth = FirebaseAuth.getInstance();
        lmAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                showLoadbar(false); // HIDES LOAD BAR AFTER LOGIN
                if(task.isSuccessful()) {

                    if (!(lmAuth.getCurrentUser().isEmailVerified())) {
                        // SEND ALERT TO USER TO GET EMAIL VERIFIED
                        Support.printToast(Login.this, "Do Not Forget To Verify Your Email");
                    }

                    // IF LOGIN IS SUCCESSFUL GOES TO MAIN ACTIVITY
                    startActivity(new Intent(Login.this, MainActivity.class));

                } else {
                    // SHOW ERROR WHEN TRYING TO LOG IN
                    Support.printToast(Login.this, task.getException().getLocalizedMessage());
                }
            }
        });
    }

    // SHOW LOAD BAR
    void showLoadbar(boolean processing) {
        if (processing) {
            load_bar.setVisibility(View.VISIBLE);
            login_btn.setVisibility(View.GONE);
        } else {
            load_bar.setVisibility(View.GONE);
            login_btn.setVisibility(View.VISIBLE);
        }
    }

    // Validates Each Field
    boolean validate(String username, String password) {
        // TAMANHO DO USERNAME
        if (username.length() < 6) {
            user_login.setError("Username must Contain at least 6 Characters");
            return false;
        }

        if (!(username.matches(".*@gmail.com"))) {
            this.use_mail = true;
        }

        // TAMANHO DA PASSWORD
        if (password.length() < 6) {
            this.password.setError("The Password Is Too Short");
            return false;
        }

        return true;
    }

    // GOES TO SIGN UP MENU
    void openSignUpMenu() {
        startActivity(new Intent(Login.this, SignUp.class));
    }

    // GOES BACK TO MAIN MENU : FIX
    void goBack() {
        startActivity(new Intent(Login.this, Loading.class));
    }
}