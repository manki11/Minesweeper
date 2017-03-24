package com.example.mankirat.minesweeper;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {


    Bundle extras;
    int row;
    int col;
    int totalopen;
    int noOfBombs;
    int textSize;

    Button newgame;
    TextView score;

    LinearLayout mainLayout,top,allLayout;
    LinearLayout[] rows;
    MyButton grid[][];

    boolean gameover;

    String level;

    private static String TAG;
    int iClicks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TAG = "MyActivity";
        totalopen=0;

        extras=getIntent().getExtras();
        level=extras.getString("Level");
        Log.e(TAG,"the level is:"+level);

        allLayout = (LinearLayout) findViewById(R.id.activity_main);

        gameover = false;
        textSize = 14;

        setLevel();
        setUpBoard();





    }

    public void setLevel(){

        if (level.equalsIgnoreCase("easy")) {
            Log.e(TAG,"level 1");
            row = 9;
            col = 6;
            noOfBombs = 10;
            textSize = 14;
            setUpBoard();
        }else if(level.equalsIgnoreCase("med")){
            Log.e(TAG,"level 2");
            row = 12;
            col = 8;
            noOfBombs = 30;
            textSize = 12;
            setUpBoard();
        }else if(level.equalsIgnoreCase("hard")){
            Log.e(TAG,"level 3");
            row = 15;
            col = 10;
            noOfBombs = 40;
            textSize = 10;
            setUpBoard();
        }else{
            Log.e(TAG,"level default");
            row = 9;
            col = 6;
            noOfBombs = 10;
            textSize = 14;
            setUpBoard();

        }
    }


    public void setUpBoard() {
        gameover = false;
        allLayout.removeAllViews();


        mainLayout=new LinearLayout(this);
        top=new LinearLayout(this);
        top.removeAllViews();


        LinearLayout.LayoutParams tparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
        tparams.setMargins(0, 0, 0, 0);
        top.setLayoutParams(tparams);
        top.setOrientation(LinearLayout.HORIZONTAL);
        newgame=new Button(this);

        LinearLayout.LayoutParams newgameparams = new LinearLayout.LayoutParams( 0,ViewGroup.LayoutParams.MATCH_PARENT, 1);
        newgameparams.setMargins(0, 0, 0, 0);
        newgame.setText("NEW GAME");
        newgame.setTextSize(20);
        newgame.setLayoutParams(newgameparams);


        newgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();
                i.setClass(MainActivity.this,StartActivity.class);
                startActivity(i);
            }
        });
        score=new TextView(this);

        score.setLayoutParams(newgameparams);
        top.addView(newgame);
        top.addView(score);
        score.setTextSize(30);
        score.setGravity(Gravity.CENTER);
        score.setText("Score: "+totalopen);



        LinearLayout.LayoutParams mlparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 7);
        mlparams.setMargins(0, 0, 0, 0);
        mainLayout.setLayoutParams(mlparams);
        mainLayout.setOrientation(LinearLayout.VERTICAL);

        grid = new MyButton[row][col];
        mainLayout.removeAllViews();
        rows = new LinearLayout[row];

        for (int i = 0; i < row; i++) {
            rows[i] = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
            params.setMargins(0, 0, 0, 0);
            rows[i].setLayoutParams(params);
            rows[i].setOrientation(LinearLayout.HORIZONTAL);
            mainLayout.addView(rows[i]);
        }


        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                grid[i][j] = new MyButton(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
                params.setMargins(1, 1, 1, 1);
                grid[i][j].setLayoutParams(params);
                grid[i][j].setOnClickListener(this);
                grid[i][j].setOnLongClickListener(this);
                grid[i][j].setrow(i);
                grid[i][j].setCol(j);
                grid[i][j].setText("");
                grid[i][j].setint(0);
                grid[i][j].setBackgroundColor(Color.parseColor("#a18d2c"));
                grid[i][j].setTextSize(textSize);
                rows[i].addView(grid[i][j]);
            }
        }

        allLayout.addView(top);
        allLayout.addView(mainLayout);


        putBombs();
        countno();
    }


    public void putBombs() {


        for (int i = 0; i <= noOfBombs; i++) {
            Random rand = new Random();
            int x = rand.nextInt(row - 1) + 0;
            int y = rand.nextInt(col - 1) + 0;
            grid[x][y].setint(-1);
            grid[x][y].isBomb();
        }
        checknoofbombs();

    }


    public void countno() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {

                if (grid[i][j].returnint() != -1) {
                    int count = 0;

                    if (find(i - 1, j - 1) == -1) count++;
                    if (find(i - 1, j) == -1) count++;
                    if (find(i - 1, j + 1) == -1) count++;
                    if (find(i, j - 1) == -1) count++;
                    if (find(i, j + 1) == -1) count++;
                    if (find(i + 1, j - 1) == -1) count++;
                    if (find(i + 1, j) == -1) count++;
                    if (find(i + 1, j + 1) == -1) count++;

                    grid[i][j].setint(count);
                }

            }
        }
    }


    public int find(int x, int y) {
        if (x >= 0 && x < row && y >= 0 && y < col) {
            return grid[x][y].returnint();
        }

        return 0;
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        this.getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.newGame) {
            totalopen=0;
            resetBoard();
            setUpBoard();
        } else if (id == R.id.boardSize2) {
            row = 9;
            col = 6;
            totalopen=0;
            noOfBombs = 10;
            textSize = 14;
            setUpBoard();
        } else if (id == R.id.boardSize3) {
            row = 12;
            col = 8;
            totalopen=0;
            noOfBombs = 30;
            textSize = 12;
            setUpBoard();
        } else if (id == R.id.boardSize4) {
            row = 15;
            col = 10;
            totalopen=0;
            noOfBombs = 40;
            textSize = 10;
            setUpBoard();
        }
        return true;
    }


    private void resetBoard() {

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                grid[i][j].setText("");
                grid[i][j].setint(0);
                grid[i][j].setBackgroundColor(Color.parseColor("#a18d2c"));
            }
        }
        totalopen=0;
        gameover = false;
    }


    @Override
    public void onClick(View v) {
        MyButton b = (MyButton) v;
        int i = b.getrow();
        int j = b.getCol();





        if (gameover) {
            return;
        }

        if (b.returnint() == -1 && b.marker==false) {
            Toast.makeText(this, "Game Lost", Toast.LENGTH_LONG).show();
            Log.e(TAG, "mine");
            b.setBackgroundColor(Color.parseColor("#e32636"));
            b.setText("X");
            showallmine(2);
            gameover = true;

        } else if(!b.marker) {
            b.changeboolean();
            totalopen++;
            score.setText("Score: "+totalopen);

            grid[i][j].setBackgroundColor(Color.parseColor("#f1c22d"));
            if (b.returnint() == 0) {
                openNeighbors(b);
            } else {
                b.setText(b.returnint() + "");
            }

            Log.e(TAG,"totalopen"+totalopen);

        }

        checkForWin();

    }


    public void showallmine(int x) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j].returnint() == -1) {
                    if (x == 1) {
                        grid[i][j].setBackgroundColor(Color.parseColor("#f98791"));
                        grid[i][j].setText("X");
                    } else {
                        grid[i][j].setBackgroundColor(Color.parseColor("#e32636"));
                        grid[i][j].setText("X");
                    }


                }
            }
        }
    }

    public void checkForRecurssion(MyButton b) {
        if (b.returnint() == 0) {
            totalopen++;
            Log.e(TAG, "grid print::"+ b.getrow() +"  " +b.getCol());
            b.changeboolean();
            openNeighbors(b);

        }
    }

    public void print(int x, int y) {

        score.setText("Score: "+totalopen);

        if (grid[x][y].returnint() == 0 ) {
            return;
        } else if(grid[x][y].getboolean()) {
            totalopen++;
            Log.e(TAG, "grid print::"+ x +"  " +y);
            grid[x][y].setText(grid[x][y].returnint() + "");
        }

        grid[x][y].changeboolean();

    }


    public void openNeighbors(MyButton button) {

        int i = button.getrow();
        int j = button.getCol();


        score.setText("Score: "+totalopen);

        if (check(i - 1, j - 1) && grid[i - 1][j - 1].getboolean()) {
            print(i - 1, j - 1);
            checkForRecurssion(grid[i - 1][j - 1]);
        }
        if (check(i - 1, j) && grid[i - 1][j].getboolean()) {
            print(i - 1, j);
            checkForRecurssion(grid[i - 1][j]);
        }
        if (check(i - 1, j + 1) && grid[i - 1][j + 1].getboolean()) {
            print(i - 1, j + 1);
            checkForRecurssion(grid[i - 1][j + 1]);
        }
        if (check(i, j - 1) && grid[i][j - 1].getboolean()) {
            print(i, j - 1);
            checkForRecurssion(grid[i][j - 1]);
        }
        if (check(i, j + 1) && grid[i][j + 1].getboolean()) {
            print(i, j + 1);
            checkForRecurssion(grid[i][j + 1]);
        }
        if (check(i + 1, j - 1) && grid[i + 1][j - 1].getboolean()) {
            print(i + 1, j - 1);
            checkForRecurssion(grid[i + 1][j - 1]);
        }
        if (check(i + 1, j) && grid[i + 1][j].getboolean()) {
            print(i + 1, j);
            checkForRecurssion(grid[i + 1][j]);
        }
        if (check(i + 1, j + 1) && grid[i + 1][j + 1].getboolean()) {
            print(i + 1, j + 1);
            checkForRecurssion(grid[i + 1][j + 1]);
        }


    }


    public void checknoofbombs() {
        int count = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j].returnint() == -1) {
                    count++;
                }
            }
        }

        noOfBombs = count;
        Log.e(TAG,"noOFBombs:"+noOfBombs+"");
    }

    public boolean checkForWin() {


        if(row*col==totalopen+noOfBombs){
            showallmine(1);
            Toast.makeText(this, "GAME WON", Toast.LENGTH_LONG).show();
            gameover = true;
            return true;
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j].returnBomb() != grid[i][j].returnisFound()) {
                    return false;
                }
            }
        }



        showallmine(1);
        Toast.makeText(this, "GAME WON", Toast.LENGTH_LONG).show();
        gameover = true;
        return true;
    }


    public boolean check(int x, int y) {
        if (x >= 0 && x < row && y >= 0 && y < col) {
            grid[x][y].setBackgroundColor(Color.parseColor("#f1c22d"));
            return true;
        }

        return false;
    }


    @Override
    public boolean onLongClick(View v) {
        MyButton b = (MyButton) v;
        checkForWin();


        if(!gameover) {
            if (b.getboolean()) {
                b.setBackgroundColor(Color.parseColor("#2e86c1"));
                b.setText("!");
                b.marker=true;
                b.changeboolean();
                b.isFound();
                checkForWin();
                return true;
            } else if (b.marker) {
                b.setBackgroundColor(Color.parseColor("#a18d2c"));
                b.setText("");
                b.marker=false;
                b.changebooleantotrue();
                b.isFound();
                checkForWin();
                return true;
            }
        }

        return false;

    }
}
