package com.example.mankirat.minesweeper;

import android.content.Context;
import android.widget.Button;

/**
 * Created by Mankirat on 16-01-2017.
 */
public class MyButton extends Button {

    private int value;
    private boolean b;
    private int row,col;
    private boolean isBomb;
    private boolean isfound;

    public boolean marker;




    public MyButton(Context context){
        super(context);
        b=true;
        setPadding(0,0,0,0);
        isBomb=false;
        isfound=false;
        marker=false;
    }

    public void setint(int data){
        this.value=data;

    }

    public int returnint(){
        return this.value;
    }


    public int getrow(){
        return this.row;
    }

    public int setrow(int r){
        return this.row=r;
    }

    public int getCol(){
        return this.col;
    }

    public int setCol(int c){
        return this.col=c;
    }

    public void changeboolean(){
        this.b=false;
    }

    public void changebooleantotrue(){
        this.b=true;
    }

    public boolean getboolean(){
        return this.b;
    }

    public void isBomb(){
        this.isBomb=true;
    }

    public void isFound(){
        if(this.isfound){
            this.isfound=false;
        }
        this.isfound = true;

    }

    public boolean returnBomb(){
        return this.isBomb;
    }

    public boolean returnisFound(){
        return this.isfound;
    }

}
