package com.francisauwerda.learnnumbers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * Main class for the Learn Numbers app.
 */
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
        mNumberView.setText(String.valueOf(i));
    }


}
