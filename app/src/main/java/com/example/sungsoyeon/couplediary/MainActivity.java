package com.example.sungsoyeon.couplediary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    EditText loginid, loginpw;
    Button login, signup;
    ImageView imgheart;
    ImageView imgtitle;
    Realm realm;
    UserData ud;
    SignUpActivity signUpActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgheart = (ImageView) findViewById(R.id.imgheart);
        imgheart.setImageResource(R.drawable.heart);
        imgtitle = (ImageView) findViewById(R.id.imgtitle);
        imgtitle.setImageResource(R.drawable.heartlink);

        loginid = (EditText) findViewById(R.id.loginid);
        loginpw = (EditText) findViewById(R.id.loginpw);

        //Realm 초기화
        Realm.init(this);
        //configuration 설정(중간에 설정을 다르게 하는게 들어갈 수 있다)
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfiguration);

        realm = Realm.getDefaultInstance();

//        final String writeid = id.getText().toString();
//        final String writepw = pw.getText().toString();

        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v) {
                        RealmQuery<UserData> query = realm.where(UserData.class);
                        RealmResults<UserData> user = query.equalTo("id", loginid.getText().toString()).findAll();
                        if(user.size() == 0) {
                            Toast.makeText(getApplicationContext(), "일치하는 아이디가 없습니다.", Toast.LENGTH_LONG).show();
                        } else {
                            if(user.get(0).getPw().equals(loginpw.getText().toString())) {
                                Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                                intent.putExtra("name", user.get(0).getName().toString());
                                intent.putExtra("id", user.get(0).getId().toString());
                                intent.putExtra("pw", user.get(0).getPw().toString());
                                setResult(LogInActivity.RESULT_OK, intent);
                                finish();
                                Toast.makeText(getApplicationContext(), user.get(0).getName()+" 님! 오늘도 사랑하는 하루되세요~", Toast.LENGTH_LONG).show();
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "일치하는 비밀번호가 없습니다.", Toast.LENGTH_LONG).show();
                            }
                        }


//                        SharedPreferences pref= getSharedPreferences("pref", MODE_PRIVATE);
//                        final String id=pref.getString("id","id"); //해당값 불러오는 것, 해당값이 없을 경우 null호출
//                        final String chkpw=pref.getString("chkpw","chkpw");
//                        final String name = pref.getString("name", "name");
//                        if(id == null && chkpw == null){
//                            Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_LONG).show();
//                            return;
//                        } else if(id.equals(loginid.getText().toString()) && chkpw.equals(loginpw.getText().toString())){
//                            Toast.makeText(getApplicationContext(), name+" 님! 오늘도 사랑하는 하루되세요~", Toast.LENGTH_LONG).show();
//                            Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
//                            startActivity(intent);
//                        } else {
//                            Toast.makeText(getApplicationContext(), "일치하는 아이디나 비밀번호가 없습니다.", Toast.LENGTH_LONG).show();
//                            return;
//                        }

                    }
                });
        signup = (Button) findViewById(R.id.signup);
        signup.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        ud = (UserData)intent.getSerializableExtra("data");
//    }

}
