package com.francisauwerda.learnnumbers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final static int HIGHEST_NUMBER = 100;
    private TextView mNumberView;
    private Button mGoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNumberView = (TextView) findViewById(R.id.number_view);
        mNumberView.setText("?");

        mGoButton = (Button) findViewById(R.id.go_button);
        mGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateRandomNumber();
            }
        });

    }

    private void generateRandomNumber() {
        Random rand = new Random();
        int i = rand.nextInt(HIGHEST_NUMBER) + 1;
        mNumberView.setText(String.valueOf(i));
    }


}
