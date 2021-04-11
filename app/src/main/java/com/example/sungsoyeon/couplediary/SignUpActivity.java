package com.example.sungsoyeon.couplediary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Handler;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class SignUpActivity extends AppCompatActivity {

    EditText name, id, pw, checkpw, couplename;
    CheckBox gender1, gender2, couplegender1, couplegender2;
    Button join, cancel;
    Realm realm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = (EditText) findViewById(R.id.name);
        id = (EditText) findViewById(R.id.id);
        pw = (EditText) findViewById(R.id.pw);
        checkpw = (EditText) findViewById(R.id.checkpw);
        couplename = (EditText) findViewById(R.id.couplename);
        gender1 = (CheckBox) findViewById(R.id.gender1);
        gender2 = (CheckBox) findViewById(R.id.gender2);
        couplegender1 = (CheckBox) findViewById(R.id.couplegender1);
        couplegender2 = (CheckBox) findViewById(R.id.couplegender2);


        join = (Button) findViewById(R.id.join);
        cancel = (Button) findViewById(R.id.cancel);

        id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Toast.makeText(getApplicationContext(), "아이디는 최대 8자 가능합니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Toast.makeText(getApplicationContext(), "비밀번호는 최대 6자 가능합니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        checkpw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = pw.getText().toString();
                String confirm = checkpw.getText().toString();

                if (password.equals(confirm)) {
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치합니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "이름을 입력하세요.", Toast.LENGTH_SHORT).show();
                    name.requestFocus();
                    return;
                }
                if (gender1.isChecked() == false && gender2.isChecked() == false) {
                    Toast.makeText(getApplicationContext(), "성별을 선택하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (gender1.isChecked() == true && gender2.isChecked() == true) {
                    Toast.makeText(getApplicationContext(), "성별을 하나만 선택하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (id.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "ID를 입력하세요.", Toast.LENGTH_SHORT).show();
                    id.requestFocus();
                    return;
                }
                if (pw.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Password를 입력하세요.", Toast.LENGTH_SHORT).show();
                    pw.requestFocus();
                    return;
                }
                if (checkpw.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Check Password를 입력하세요.", Toast.LENGTH_SHORT).show();
                    checkpw.requestFocus();
                    return;
                }
                if (!pw.getText().toString().equals(checkpw.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    pw.requestFocus();
                    return;
                }
                if (couplename.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "짝꿍 이름을 입력하세요.", Toast.LENGTH_SHORT).show();
                    couplename.requestFocus();
                    return;
                }
                if (couplegender1.isChecked() == false && couplegender2.isChecked() == false) {
                    Toast.makeText(getApplicationContext(), "짝꿍 성별을 선택하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (couplegender1.isChecked() == true && couplegender2.isChecked() == true) {
                    Toast.makeText(getApplicationContext(), "짝꿍 성별을 하나만 선택하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(getApplicationContext(), "환영합니다~ 회원가입이 완료되었습니다!!!", Toast.LENGTH_LONG).show();

//                String ID =id.getText().toString();
//                String chkPassword = checkpw.getText().toString();
//                String Name = name.getText().toString();
//
//                SharedPreferences pref= getSharedPreferences("pref", MODE_PRIVATE);
//                SharedPreferences.Editor editor = pref.edit();
//                editor.putString("id", ID);
//                editor.putString("chkpw", chkPassword);
//                editor.putString("name", Name);
//                editor.commit();
                //Realm 인스턴스를 얻는다.
                realm = Realm.getDefaultInstance();

                insertDatabase();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                setResult(MainActivity.RESULT_OK, intent);
                finish();
                startActivity(intent);
                realm.close();
                // joinGoing();

            }
        });

        cancel.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        setResult(MainActivity.RESULT_CANCELED);
                        finish();
                        startActivity(intent);
                    }
                }
        );
    }
    private void insertDatabase() {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
//                Number num = realm.where(UserData.class).max("id");
//                int nextID;
//                if(num == null) {
//                    nextID = 1;
//                } else {
//                    nextID = num.intValue() +1;
//                }
//                UserData userData = realm.createObject(UserData.class, nextID);
                UserData userData = realm.createObject(UserData.class);

                userData.setName(name.getText().toString());
                userData.setId(id.getText().toString());
                userData.setPw(pw.getText().toString());
                userData.setCheckpw(checkpw.getText().toString());
                userData.setCouplename(couplename.getText().toString());
            }
        });
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        realm.close();
//    }
}
