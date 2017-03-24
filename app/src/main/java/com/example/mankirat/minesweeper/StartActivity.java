package com.example.mankirat.minesweeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import static android.util.Log.*;

public class StartActivity extends AppCompatActivity  {

    Button start;
    RadioButton easy,med,hard;
    RadioGroup rg;
    String str,TAG;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        rg=(RadioGroup) findViewById(R.id.mainrg);
        str="easy";

        easy=(RadioButton) findViewById(R.id.easy);
        med=(RadioButton) findViewById(R.id.med);
        hard=(RadioButton) findViewById(R.id.hard);

        TAG="mainactivity";



        start=(Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();
                i.setClass(StartActivity.this,MainActivity.class);
                Log.e(TAG,str);
                i.putExtra("Level",str);
                startActivity(i);
            }
        });
    }

    public void easy(View view){
        Log.e(TAG,"easy");
        str="easy";
    }

    public  void med(View view){
        Log.e(TAG,"med");
        str="med";
    }

    public  void hard(View view){
        Log.e(TAG,"hard");
        str="hard";
    }


}
