package com.example.sungsoyeon.couplediary;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class LogInActivity extends AppCompatActivity {
    ImageView dday, calendar, gallery, couplestory, heartlink2;
    Button logout;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Realm 초기화
        Realm.init(this);
        //configuration 설정(중간에 설정을 다르게 하는게 들어갈 수 있다)
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfiguration);

        realm = Realm.getDefaultInstance();

        dday = (ImageView) findViewById(R.id.dday);
        dday.setImageResource(R.drawable.dday);
        calendar = (ImageView) findViewById(R.id.calendar);
        calendar.setImageResource(R.drawable.calendar);
        gallery = (ImageView) findViewById(R.id.gallery);
        gallery.setImageResource(R.drawable.gallery);
        couplestory = (ImageView) findViewById(R.id.couplestory);
        couplestory.setImageResource(R.drawable.couplestory);
        heartlink2 = (ImageView) findViewById(R.id.heartlink2);
        heartlink2.setImageResource(R.drawable.heartlink2);
        logout = (Button) findViewById(R.id.logout);

        calendar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                startActivity(intent);
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("content://media/internal/images/media"));
                startActivity(intent);
            }
        });

        dday.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Dday_Activity.class);
                startActivity(intent);
            }
        });

        couplestory.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), activity_write.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }
}
