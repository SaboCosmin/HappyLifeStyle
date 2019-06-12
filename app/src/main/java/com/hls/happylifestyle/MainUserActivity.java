package com.hls.happylifestyle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainUserActivity extends AppCompatActivity {
    SharedPreferences mSharedPreferences;
    TextView mainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);

        final Button profileBtn = findViewById(R.id.profileButton);
        final Button seeMenuBtn = findViewById(R.id.seeMenuButton);
        final Button createNewMenuBtn = findViewById(R.id.createNewMenuButton);
        final Button calculatorBtn = findViewById(R.id.calculatorButton);
        final Button addFoodBtn = findViewById(R.id.addFood);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mainMenu = findViewById(R.id.mainMenuTextView);
        mainMenu.setText("");

        if(mSharedPreferences.getBoolean(getString(R.string.key_first_run), false) == false){
            seeMenuBtn.setEnabled(false);
            createNewMenuBtn.setEnabled(false);
            mainMenu.setText("Please fill your profile information!");
        }else if(mSharedPreferences.getBoolean(getString(R.string.key_menu_generated), false) == false){
            seeMenuBtn.setEnabled(false);
            mainMenu.setText("Please create a new menu!");
        }

        profileBtn.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              Intent startIntent = new Intent(getApplicationContext(), com.hls.happylifestyle.ProfileActivity.class);
                                              startIntent.putExtra("pageNameActivity", profileBtn.getText());
                                              finish();
                                              startActivity(startIntent);
                                          }
                                      }
        );

        seeMenuBtn.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              Intent startIntent = new Intent(getApplicationContext(), SeeMenuActivity.class);
                                              startIntent.putExtra("pageNameActivity", seeMenuBtn.getText());
                                              startActivity(startIntent);
                                          }
                                      }
        );

        createNewMenuBtn.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Intent startIntent = new Intent(getApplicationContext(), CreateNewMenuActivity.class);
                                                    startIntent.putExtra("pageNameActivity", createNewMenuBtn.getText());
                                                    finish();
                                                    startActivity(startIntent);
                                                }
                                            }
        );

        calculatorBtn.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 Intent startIntent = new Intent(getApplicationContext(), CalculatorActivity.class);
                                                 startIntent.putExtra("pageNameActivity", calculatorBtn.getText());
                                                 startActivity(startIntent);
                                             }
                                         }
        );

        addFoodBtn.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 Intent startIntent = new Intent(getApplicationContext(), AddFoodActivity.class);
                                                 startIntent.putExtra("pageNameActivity", addFoodBtn.getText());
                                                 startActivity(startIntent);
                                             }
                                         }
        );
    }
}
