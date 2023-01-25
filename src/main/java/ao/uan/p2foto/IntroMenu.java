package ao.uan.p2foto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class IntroMenu extends AppCompatActivity {
    MaterialButton login, sign_up, leave;
    FirebaseAuth mauth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_menu);
        login = findViewById(R.id.i_login_btn);
        sign_up = findViewById(R.id.i_signup_btn);
        leave = findViewById(R.id.leave);

        login.setOnClickListener(v -> openLoginMenu());

        if(mauth.getCurrentUser() != null) {
            Support.printToast(
            IntroMenu.this,
            (
                    "Logged as: " + mauth.getCurrentUser().getEmail()
            )
            );
            openMainMenu();
        }

        sign_up.setOnClickListener(v -> openSignUpMenu());
        leave.setOnClickListener(v -> leaveApp());

    }

    void openLoginMenu () {
        startActivity(new Intent(IntroMenu.this, Login.class));
    }

    void openSignUpMenu () {
        startActivity(new Intent(IntroMenu.this, SignUp.class));
    }

    void openMainMenu () {
        startActivity(new Intent(IntroMenu.this, MainActivity.class));
    }


    void leaveApp() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        System.out.print("USER LOGGED AS: "+user);
    }
}