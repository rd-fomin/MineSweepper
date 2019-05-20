package com.example.labtwo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    int fieldSize;
    int bombPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.buttonRestart);
        Spinner spinnerSize = findViewById(R.id.spinnerSize);
        Spinner spinnerCount = findViewById(R.id.spinnerCount);

        spinnerSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: {
                        fieldSize = 11;
                        break;
                    }
                    case 1: {
                        fieldSize = 10;
                        break;
                    }
                    case 2: {
                        fieldSize = 8;
                        break;
                    }
                    case 3: {
                        fieldSize = 6;
                        break;
                    }
                    case 4: {
                        fieldSize = 5;
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerCount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: {
                        bombPercent = (fieldSize * fieldSize) / 10 * 4;
                        break;
                    }
                    case 1: {
                        bombPercent = (fieldSize * fieldSize) / 10 * 3;
                        break;
                    }
                    case 2: {
                        bombPercent = (fieldSize * fieldSize) / 10 * 2;
                        break;
                    }
                    case 3: {
                        bombPercent = (fieldSize * fieldSize) / 10;
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerSize.setSelection(4);
        spinnerCount.setSelection(2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("fieldSize", fieldSize);
                intent.putExtra("bombPercent", bombPercent);
                startActivity(intent);
            }
        });
    }

}
