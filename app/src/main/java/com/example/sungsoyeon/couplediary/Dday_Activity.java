package com.example.sungsoyeon.couplediary;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

public class Dday_Activity extends AppCompatActivity {
    /** Called when the activity is first created. */

    private TextView ddayText;
    private TextView todayText;
    private TextView resultText;
    private Button dateButton;

    private int tYear;           //오늘 연월일 변수
    private int tMonth;
    private int tDay;

    private int dYear=1;        //디데이 연월일 변수
    private int dMonth=1;
    private int dDay=1;


    private long d;
    private long t;
    private long r;

    private int resultNumber=0;

    static final int DATE_DIALOG_ID=0;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dday);

        ddayText=(TextView)findViewById(R.id.dday);
        todayText=(TextView)findViewById(R.id.today);
        resultText=(TextView)findViewById(R.id.result);
        dateButton=(Button)findViewById(R.id.dateButton);

        dateButton.setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDialog(0);//----------------
            }
        });



        Calendar calendar =Calendar.getInstance();              //현재 날짜 불러옴
        tYear = calendar.get(Calendar.YEAR);
        tMonth = calendar.get(Calendar.MONTH);
        tDay = calendar.get(Calendar.DAY_OF_MONTH);

        Calendar dCalendar =Calendar.getInstance();
        dCalendar.set(dYear,dMonth, dDay);

        t=calendar.getTimeInMillis();                 //오늘 날짜를 밀리타임으로 바꿈
        d=dCalendar.getTimeInMillis();              //디데이날짜를 밀리타임으로 바꿈
        r=(d-t)/(24*60*60*1000);                 //디데이 날짜에서 오늘 날짜를 뺀 값을 '일'단위로 바꿈

        resultNumber=(int)r+1;
        updateDisplay();

    }//OnCreate end

    private void updateDisplay(){

        todayText.setText(String.format("%d년 %d월 %d일",tYear, tMonth+1,tDay));
        ddayText.setText(String.format("%d년 %d월 %d일",dYear, dMonth+1,dDay));

        if(resultNumber>=0){
            resultText.setText(String.format("D-%d", resultNumber));
        }
        else{
            int absR=Math.abs(resultNumber);
            resultText.setText(String.format("D+%d", absR));
        }
    }//디데이 날짜가 오늘날짜보다 뒤에오면 '-', 앞에오면 '+'를 붙인다

    private DatePickerDialog.OnDateSetListener dDateSetListener=new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            dYear=year;
            dMonth=monthOfYear;
            dDay=dayOfMonth;
            final Calendar dCalendar =Calendar.getInstance();
            dCalendar.set(dYear,dMonth, dDay);

            d=dCalendar.getTimeInMillis();
            r=(d-t)/(24*60*60*1000);

            resultNumber=(int)r;
            updateDisplay();
        }
    };


    @Override
    protected Dialog onCreateDialog(int id){
        if(id==DATE_DIALOG_ID){
            return new DatePickerDialog(this,dDateSetListener,tYear,tMonth,tDay);

        }
        return null;
    }



}//DatecalActivity end
