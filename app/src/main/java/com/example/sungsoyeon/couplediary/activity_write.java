package com.example.sungsoyeon.couplediary;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import static com.example.sungsoyeon.couplediary.R.id.date;

public class activity_write extends AppCompatActivity {
    ListView listView;
    LinearLayout linear1, linear2;
    Button btnsave, btncancel;
    EditText et;
    TextView tvCount;
    CheckBox share, everyshare, coupleshare, personal;

    ArrayList<Data> datas = new ArrayList<>();
    ArrayList<String> titles = new ArrayList<>();
    ArrayList<String> memos = new ArrayList<>();
    ArrayAdapter<String> adapter;
    DatePicker datePicker;

    int setNumber;
    Boolean exist = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        checkFunction();
        init();

    }
    public void checkFunction(){
        int permissioninfo = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permissioninfo == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,"SDCard ?????? ?????? ????????????.",Toast.LENGTH_SHORT).show();
        }else{
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                Toast.makeText(this, "?????? ??????",Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100);

            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        String str = null;
        if(requestCode == 100){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                str = "SD Card ???????????? ??????";
            else str = "SD Card ???????????? ??????";
            Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        }
    }


    void init() {
        listView = (ListView) findViewById(R.id.listview);
        linear1 = (LinearLayout) findViewById(R.id.linear1);
        linear2 = (LinearLayout) findViewById(R.id.linear2);
        datePicker = (DatePicker) findViewById(date);

        share = (CheckBox) findViewById(R.id.share);
        everyshare = (CheckBox) findViewById(R.id.everyshare);
        coupleshare = (CheckBox) findViewById(R.id.coupleshare);
        personal = (CheckBox) findViewById(R.id.personal);

        tvCount = (TextView)findViewById(R.id.tvCount);
        btnsave = (Button) findViewById(R.id.btnsave);
        btncancel = (Button) findViewById(R.id.btncancel);

        et = (EditText) findViewById(R.id.et);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titles);

        listView.setAdapter(adapter);

        listFile();

        share.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(share.isChecked() == true) {
                    everyshare.setVisibility(View.VISIBLE);
                    coupleshare.setVisibility(View.VISIBLE);
                } else {
                    everyshare.setVisibility(View.INVISIBLE);
                    coupleshare.setVisibility(View.INVISIBLE);
                }
            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(everyshare.isChecked() || coupleshare.isChecked() || personal.isChecked()) {
                    String year = (datePicker.getYear()+"").substring(2);
                    String month = (datePicker.getMonth()+1)+"";
                    String day = datePicker.getDayOfMonth()+"";

                    if(month.length() == 1) month = "0" +month;
                    if(day.length() == 1) day = "0" + day;

                    String title = year + "-" + month + "-" + day+".memo";
                    String body = et.getText().toString();

                    if(btnsave.getText().toString().equals("??????"))  {
                        if(exist){
                            if(!title.equals(titles.get(setNumber))){
                                deletefile(titles.get(setNumber));
                                titles.remove(setNumber);
                                datas.remove(setNumber);

                                titles.add(title);
                                datas.add(new Data(title, body));
                            }else{
                                datas.get(setNumber).setMemo(body);
                            }
                            btnsave.setText("??????");
                            exist = false;
                        }else{
                            titles.set(setNumber, title);
                            datas.get(setNumber).setTitle(title);
                            datas.get(setNumber).setMemo(body);
                            btnsave.setText("??????");
                        }
                    }else{
                        if(titleCheck(title)){
                            btnsave.setText("??????");
                            et.setText(datas.get(setNumber).getMemo());
                            Toast.makeText(activity_write.this, "?????? ????????? ?????? ????????? ?????? ?????? ?????? ????????? ????????????.",Toast.LENGTH_SHORT).show();
                            exist = true;
                            return;
                        }
                        titles.add(title);
                        datas.add(new Data(title,body));
                    }
                    Collections.sort(titles, titleAsc);
                    Collections.sort(datas, dataAsc);

                    adapter.notifyDataSetChanged();

                    writeFile(title,body);

                    et.setText("");
                    linear1.setVisibility(View.VISIBLE);
                    linear2.setVisibility(View.GONE);
                } else {
                    Toast.makeText(getApplicationContext(), "????????? ??????????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setVisibility(false);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                btnsave.setText("??????");
                setVisibility(true);

                String t = titles.get(i);
                String[] temp = t.split(".m");

                String[] str = (temp[0].split("-"));

                int year = Integer.parseInt("20"+str[0]);
                int month = Integer.parseInt(str[1]);
                int day = Integer.parseInt(str[2]);


                setNumber = i;

                datePicker.updateDate(year, month-1, day);
                et.setText(datas.get(i).getMemo());

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder alg = new AlertDialog.Builder(activity_write.this);

                final int position = i;
                alg.setTitle("??????")
                        .setMessage("?????????????????????????")
                        .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deletefile(titles.get(position));
                                titles.remove(position);
                                datas.remove(position);
                                adapter.notifyDataSetChanged();
                                tvCount.setText("????????? ????????? : " + titles.size() +"???");

                            }
                        })
                        .setNegativeButton("??????",null)
                        .show();
                return true;
            }
        });
    }

    public String getExternalPath(){
        String sdPath ="";
        String ext = Environment.getExternalStorageState();
        if(ext.equals(Environment.MEDIA_MOUNTED)){
            sdPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
        }else{
            sdPath  = getFilesDir() +"";

        }
        return sdPath;
    }

    public void deletefile(String title){
        String path = getExternalPath();

        File file = new File(path + "mydiary");

        File[] files = new File(path + "mydiary").listFiles();

        for(File f : files){
            if(title.equals(f.getName())){
                f.delete();
            }
        }
    }

    public void listFile() {
        String path = getExternalPath();

        File file = new File(path + "mydiary");
        if (!file.exists()) file.mkdir();
        else{
            File[] files = new File(path + "mydiary").listFiles();

            if(files != null){
                for (File f : files){
                    titles.add(f.getName());
                    String body = loadFile(f.getName());
                    datas.add(new Data(f.getName(), body));
                }

            }
            Collections.sort(titles, titleAsc);
            Collections.sort(datas, dataAsc);

            adapter.notifyDataSetChanged();
        }
        tvCount.setText("????????? ????????? : " + titles.size() + "???");


    }

    public String loadFile(String title){
        try{
            String path = getExternalPath();
            BufferedReader br = new BufferedReader(new FileReader(path+"mydiary/"+title));
            String readStr = "";
            String str = null;
            while(((str = br.readLine()) != null)){
                readStr += str +"\n";
            }
            br.close();

            return readStr;

        }catch (FileNotFoundException e){
            e.printStackTrace();
            Toast.makeText(this, "File not Found", Toast.LENGTH_SHORT).show();
        }catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public void writeFile(String title, String body){
        try{
            String path = getExternalPath();
            String filename = title;

            BufferedWriter bw = new BufferedWriter(new FileWriter(path + "mydiary/" + filename, false));
            bw.write(body);
            bw.close();
            tvCount.setText("????????? ????????? : " + titles.size() + "???");
            Toast.makeText(this,"?????? ?????????????????????.", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public boolean titleCheck(String title){
        for(int i = 0 ; i < titles.size(); i++){
            if(titles.get(i).equals(title)){
                setNumber = i;
                return true;
            }
        }
        return false;
    }

    public void onClick(View v) {
        setVisibility(true);
        et.setText("");
        btnsave.setText("??????");
        Calendar cal= Calendar.getInstance();

        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DAY_OF_MONTH);

        datePicker.updateDate(year, month, day);

    }

    public void setVisibility(Boolean t){
        if(t){
            linear1.setVisibility(View.GONE);
            linear2.setVisibility(View.VISIBLE);
        }else{
            linear1.setVisibility(View.VISIBLE);
            linear2.setVisibility(View.GONE);

        }
    }

    Comparator<Data> dataAsc = new Comparator<Data>() {
        @Override
        public int compare(Data data, Data t1) {
            return data.getTitle().compareToIgnoreCase(t1.getTitle());
        }
    };

    Comparator<String> titleAsc = new Comparator<String>() {
        @Override
        public int compare(String s, String t1) {
            return s.compareToIgnoreCase(t1);
        }
    };


}