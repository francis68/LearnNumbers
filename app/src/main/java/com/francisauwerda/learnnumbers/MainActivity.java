package com.francisauwerda.learnnumbers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

/**
 * Main class for the Learn Numbers app.
 */
public class MainActivity extends AppCompatActivity implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    private final static int HIGHEST_NUMBER = 100;
    private TextView mNumberView;
    private TextView mTranslation;
    private TextView mClickable;
    private Button mSeeTranslation;
    private int mCurrentNumber;
    private ImageView mTouchIcon;
    private ArrayList<String> mNumberTextArray = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNumberView = (TextView) findViewById(R.id.tv_number_view);
        mTranslation = (TextView) findViewById(R.id.tv_translation);
        mSeeTranslation = (Button) findViewById(R.id.b_unlock);
        mTouchIcon = (ImageView) findViewById(R.id.iv_touch_icon);
        mClickable = (TextView) findViewById(R.id.tv_clickable_area);

        setUpSharedPreferences();
        setUpTranslation();
        setUpClickableArea();

    }

    /**
     * Registers the shared preferences and loads the last selected language into the array.
     */
    private void setUpSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String language = sharedPreferences.getString(getString(R.string.pref_language_key),
                getString(R.string.pref_language_value_spanish));
        loadNumbersIntoArray(language);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    /**
     * Reads the asset file and adds each line to an ArrayList.
     */
    private void loadNumbersIntoArray(String language) {
        BufferedReader reader = null;
        mNumberTextArray.clear();
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open(language + ".txt")));

            String line;
            while ((line = reader.readLine()) != null) {
                mNumberTextArray.add(line);
            }

        } catch (Exception ex) {
            System.out.println("Error message is: " + ex.getMessage());
        } finally {
            String stringName = getResources().getString(R.string.see_translation) + " " + language;
            mSeeTranslation.setText(stringName);
        }
    }

    /**
     * Set up the on click listeners for the clickable area of the card.
     * This basically shows and hides everything and calls the generate random number method.
     */
    private void setUpClickableArea() {
        mClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTouchIcon.setVisibility(View.INVISIBLE);
                mTranslation.setVisibility(View.INVISIBLE);
                mSeeTranslation.setVisibility(View.VISIBLE);
                generateRandomNumber();
            }
        });
    }

    /**
     * Setting up the click listener for the See Translation button.
     * This shows and hides everything and calls the translateNumber method to find the
     * translation inside the ArrayList.
     */
    private void setUpTranslation() {
        mSeeTranslation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String translation = translateNumber(mCurrentNumber);
                mTranslation.setText(translation);
                mTranslation.setVisibility(View.VISIBLE);
                mSeeTranslation.setVisibility(View.INVISIBLE);

            }
        });
    }

    /**
     *
     * @param currentNumber The current number which is shown.
     * @return The string representation of the current number.
     */
    public String translateNumber(int currentNumber) {
        // Here, I have to make sure I don't request a number bigger than the Array.
        if (currentNumber > mNumberTextArray.size()) {
            // The number of lines in the asset file is less than the HIGHEST_NUMBER
            return "There has been an error, please pray for an update";
        }
        return mNumberTextArray.get(currentNumber);
    }

    /**
     * Creates the settings page on the main layout.
     * @param menu The current context.
     * @return true when successful.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return true;
    }

    /**
     * Handles when the settings cog has been clicked.
     * @param item which item has been selected.
     * @return returns true when successful.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Context context = this;
            Class destinationActivity = SettingsActivity.class;
            Intent intent = new Intent(context, destinationActivity);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Generates a random number from 0 to HIGHEST_NUMBER and assigns
     * it to Number View TextView.
     */
    private void generateRandomNumber() {
        Random rand = new Random();
        int i = rand.nextInt(HIGHEST_NUMBER + 1);
        // Hold a copy of the current number for when we get the translation.
        mCurrentNumber = i;
        mNumberView.setText(String.valueOf(i));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // Depending on which preference has been updated we make our changes.
        if (key.equals(getString(R.string.pref_language_key))) {
            String language = sharedPreferences.getString(key,
                    getResources().getString(R.string.pref_language_value_spanish));
            loadNumbersIntoArray(language);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}
