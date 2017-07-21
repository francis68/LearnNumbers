package com.francisauwerda.learnnumbers;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

/**
 * Main class for the Learn Numbers app.
 */
public class MainActivity extends AppCompatActivity {

    private final static int HIGHEST_NUMBER = 100;
    private TextView mNumberView;
    private TextView mTranslation;
    private Button mShuffleButton;
    private int mCurrentNumber;
    private ArrayList<String> mSpanishTextArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadNumbersIntoArray();

        mNumberView = (TextView) findViewById(R.id.number_view);
        mNumberView.setText("?");

        mTranslation = (TextView) findViewById(R.id.tv_translation);
        setUpTranslation();

        mShuffleButton = (Button) findViewById(R.id.shuffle_button);
        mShuffleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTranslation.setText("_____");
                generateRandomNumber();
            }
        });

    }

    private void loadNumbersIntoArray() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("translations-spanish.txt")));

            String line;
            while ((line = reader.readLine()) != null) {
                mSpanishTextArray.add(line);
            }

        } catch (Exception ex) {
            System.out.println("Error message is: " + ex.getMessage());
        }
    }

    /**
     * Setting up the click listener for the Number Card.
     */
    private void setUpTranslation() {
        mTranslation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String translation = translateNumber(mCurrentNumber);
                mTranslation.setText(translation);
            }
        });
    }

    public String translateNumber(int currentNumber) {
        return mSpanishTextArray.get(currentNumber);
    }

    /**
     * Creates the options menu on the main layout.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return true;
    }

    /**
     * Handles when the settings cog has been clicked.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Context context = this;
            Class destinationActivity = Settings.class;
            Intent intent = new Intent(context, destinationActivity);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void generateRandomNumber() {
        Random rand = new Random();
        int i = rand.nextInt(HIGHEST_NUMBER) + 1;
        mCurrentNumber = i;
        mNumberView.setText(String.valueOf(i));
    }


}
