package com.example.labtwo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private MyImageView[][] tableMineSweeper;

    private int col;
    private int row;
    private int count;

    private int seconds;
    private boolean flag;
    private String currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Button buttonRestart = findViewById(R.id.buttonRestart);
        Toast.makeText(getApplicationContext(),"Long click to flag", Toast.LENGTH_LONG).show();

        flag = false;
        final TextView textView = findViewById(R.id.textView3);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int min, sec;
                min = (seconds % 3600) / 60;
                sec = seconds % 60;

                if (!flag) {
                    currentTime = String.format(Locale.ENGLISH, "Time: %02d:%02d", min, sec);
                    textView.setText(currentTime);
                    seconds++;
                }

                handler.postDelayed(this, 1000);

            }
        });

        buttonRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        col = getIntent().getIntExtra("fieldSize", 5);
        row = getIntent().getIntExtra("fieldSize", 5);
        count = getIntent().getIntExtra("bombPercent", 5);

        tableMineSweeper = new MyImageView[col][row];

        MyImageView.allCount = row * col - count;

        createButtons();

    }

    public void createButtons() {
        createBombs();

        TableLayout tableLayout = findViewById(R.id.tableLayout);

        for (int i = 0; i < row; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT, 1));
            for (int j = 0; j < col; j++) {
                checkAroundButtons(i, j);

                tableMineSweeper[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((MyImageView) v).getValue() == 0) checkZero((MyImageView) v);
                        else checkButtons((MyImageView) v);
                        if (MyImageView.allCount == 0) gameWon();
                    }
                });

                tableMineSweeper[i][j].setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        return GameActivity.this.onLongClick((MyImageView) v);
                    }
                });

                tableMineSweeper[i][j].setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1)
                );

                tableRow.addView(tableMineSweeper[i][j], j);
            }
            tableLayout.addView(tableRow, i);
        }
    }

    public void createBombs() {
        int countBooombs = 0;
        Random random = new Random();

        while (countBooombs < count) {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (countBooombs < count) {
                        if ((random.nextInt((row * col) / count) - 1) == -1 && tableMineSweeper[i][j] == null) {
                            tableMineSweeper[i][j] = new MyImageView(this, i, j, -1);
                            countBooombs++;
                        }
                    }
                }
            }
        }
    }

    public void checkAroundButtons(int i, int j) {
        int value = 0;
        if (tableMineSweeper[i][j] == null) {
            int sSi = i - 1, sSj = j - 1, eSi = i + 1, eSj = j + 1;
            if (j == 0) sSj = j;
            if (j == col - 1) eSj = j;
            if (i == 0) sSi = i;
            if (i == row - 1) eSi = i;
            for (int k = sSi; k <= eSi; k++) {
                for (int l = sSj; l <= eSj; l++) {
                    if (tableMineSweeper[k][l] != null) {
                        if (tableMineSweeper[k][l].getValue() == -1)
                            value++;
                    }
                }
            }
            tableMineSweeper[i][j] = new MyImageView(this, i, j, value);
        }
    }

    public boolean onLongClick(MyImageView myImageView) {
        if (myImageView.getDrawable().getConstantState() != getResources().getDrawable(R.drawable.flag).getConstantState())
            myImageView.setImageResource(R.drawable.flag);
        else
            myImageView.setImageResource(R.drawable.colorful);
        return true;
    }

    public void checkButtons(MyImageView myImageView) {
        myImageView.setImageRes();
        if (myImageView.getValue() == -1) gameOver();
    }

    public void checkZero(MyImageView myImageView) {
        int i = myImageView.getRow(), j = myImageView.getCol();
        int sSi = i - 1, sSj = j - 1, eSi = i + 1, eSj = j + 1;
        if (j == 0) sSj = j;
        if (j == col - 1) eSj = j;
        if (i == 0) sSi = i;
        if (i == row - 1) eSi = i;
        for (int k = sSi; k <= eSi; k++) {
            for (int l = sSj; l <= eSj; l++) {
                if (tableMineSweeper[k][l].getValue() == 0 && tableMineSweeper[k][l].isClickable()) {
                    tableMineSweeper[k][l].setImageRes();
                    checkZero(tableMineSweeper[k][l]);
                } else if (tableMineSweeper[k][l].isClickable()) {
                    tableMineSweeper[k][l].setImageRes();
                }
            }
        }
    }

    public void gameWon() {
        flag = true;
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(GameActivity.this);
        alertDialog.setTitle("You won!!!").setMessage("Your time is " + currentTime.substring(currentTime.indexOf(" ") + 1)).setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                startActivity(intent);
                dialog.cancel();
            }
        });
        AlertDialog dialog = alertDialog.create();
        dialog.show();
        TextView textView = dialog.findViewById(android.R.id.message);
        if (textView != null) {
            textView.setTextSize(30);
        }
    }

    public void gameOver() {
        flag = true;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                tableMineSweeper[i][j].setClickable(false);
                tableMineSweeper[i][j].setLongClickable(false);
                if (tableMineSweeper[i][j].getValue() == -1 &&
                        tableMineSweeper[i][j].getDrawable().getConstantState() != getResources().getDrawable(R.drawable.bomb).getConstantState())
                    tableMineSweeper[i][j].setImageResource(R.drawable.oopsbomb);
            }
        }
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(GameActivity.this);
        alertDialog.setMessage("Game over").setCancelable(false).setNegativeButton("REPEAT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                startActivity(intent);
                dialog.cancel();
            }
        }).setPositiveButton("Show field", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = alertDialog.create();
        dialog.show();
        TextView textView = dialog.findViewById(android.R.id.message);
        if (textView != null) {
            textView.setTextSize(60);
        }
    }
}
