package ao.uan.p2foto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Loading extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // SUBSTITUIR PARA MAIN MENU CASO NÃO ESTIVER LOGADO E MENU CASO ESTIVER
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Support.printToast(Loading.this, "USER:" + user);
                System.out.print("USER LOGGED AS: "+user);

                if (user == null) {
                    startActivity(new Intent(Loading.this, IntroMenu.class));
                } else {
                    // MAIN ACTIVITY: SE ESTIVER LOGADO MANDA PARA OPERATIONS CASO NÃO MANDA PARA SIMPLE MENU
                    startActivity(new Intent(Loading.this, MainActivity.class));
                }
                finish();
            }
        },1000);
    }
}