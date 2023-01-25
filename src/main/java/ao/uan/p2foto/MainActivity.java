package ao.uan.p2foto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    MaterialButton album_menu_btn, users_menu_to_btn, settings_menu_btn, logout_btn; // Go Back Buttons
    FirebaseAuth mauth;

    BottomNavigationView navBar;
    xAlbumFragment albumFragment = new xAlbumFragment();
    xUsersFragment usersFragment = new xUsersFragment();
    xSettingsFragment settingsFragment = new xSettingsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navBar = findViewById(R.id.navigation_bar);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,albumFragment).commit();
        navBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_album_btn:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,albumFragment).commit();
                        return true;
                    case R.id.nav_user_btn:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,usersFragment).commit();
                        return true;
                    case R.id.nav_settings_btn: // BUTTON CLICK TO TRIGGER FRAGMENT
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,settingsFragment).commit();
                        return true;
                }

                return false;
            }
        });
    }
}
