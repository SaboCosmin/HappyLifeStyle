package com.hls.happylifestyle.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hls.happylifestyle.Classes.UserProfile;
import com.hls.happylifestyle.R;

public class ProfileActivity extends AppCompatActivity {

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private UserProfile userProfile;
    private UserProfile test;

    RadioGroup genderRadioGroup, purposeRadioGroup;
    Spinner activityLevelSpinner;
    AutoCompleteTextView userName, userAge, userHeight, userWeight;
    LinearLayout pickedLayout, genderLayout, purposeLayout, activityLayout;
    TextView pickedGender,  pickedPurpose, pickedActivity;
    Button editButton, saveButton;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent startIntent = new Intent(getApplicationContext(), MainUserActivity.class);
        startActivity(startIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();
        mEditor.apply();

        initializeViews();

        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(ProfileActivity.this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.activity_level_spinner));

        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activityLevelSpinner.setAdapter(mAdapter);

        if (getIntent().hasExtra("pageNameActivity")){
            TextView pageName = findViewById(R.id.pageName);
            String text = getIntent().getExtras().getString("pageNameActivity");
            pageName.setText(text);
        }

        initViews();
    }

    public void saveUser(View v){

        saveLayoutView();

        String name = userName.getText().toString();
        if(TextUtils.isEmpty(name)){
            name = "DefaultName";
        }

        String age_text = userAge.getText().toString();
        int age;
        if(TextUtils.isEmpty(age_text)){
            age = 30;
        }else{
             age = Integer.parseInt(userAge.getText().toString());
        }

        String height_text = userHeight.getText().toString();
        int height;
        if(TextUtils.isEmpty(height_text)){
            height = 180;
        }else{
            height = Integer.parseInt(userHeight.getText().toString());
        }

        String weight_text = userWeight.getText().toString();
        int weight;
        if(TextUtils.isEmpty(weight_text)){
            weight = 80;
        }else{
            weight = Integer.parseInt(userWeight.getText().toString());
        }

        int selectedGenderId = genderRadioGroup.getCheckedRadioButtonId();
        int selectedPurposeId = purposeRadioGroup.getCheckedRadioButtonId();

        RadioButton genderRadioButton = findViewById(selectedGenderId);
        RadioButton purposeRadioButton = findViewById(selectedPurposeId);
        String gender = genderRadioButton.getText().toString();
        String purpose = purposeRadioButton.getText().toString();

        String activityLevelText = activityLevelSpinner.getSelectedItem().toString();
        int activityLevel = Integer.parseInt(activityLevelText.replaceAll("[\\D]", ""));

        userProfile = new UserProfile(name, gender, age, activityLevel, height, weight, purpose);
        saveSharedPreferences(userProfile);
        finish();
        startActivity(getIntent());
    }

    private void saveSharedPreferences(UserProfile userProfile){
        mEditor.putBoolean(getString(R.string.key_first_run), true);
        mEditor.commit();

        Gson gson = new Gson();
        String json = gson.toJson(userProfile);
        mEditor.putString(getString(R.string.user_profile), json);
        mEditor.commit();

        Toast.makeText(this, "Your new preferences are updating...", Toast.LENGTH_SHORT).show();
    }

    private void updateSharedPreferences(){
        Gson gson = new Gson();
        String json = mSharedPreferences.getString(getString(R.string.user_profile), "");

        userProfile = gson.fromJson(json, UserProfile.class);
    }

    public void editPressed(View v){
        editLayoutView();
    }

    public void initViews(){
        if(mSharedPreferences.getBoolean(getString(R.string.key_first_run), false)){

            updateSharedPreferences();
            saveLayoutView();
            initUserDataView(userProfile);

        }else{
           editLayoutView();
        }
    }

    private void initializeViews(){
        userName = findViewById(R.id.user_name);
        userAge = findViewById(R.id.Age);
        userHeight = findViewById(R.id.Height);
        userWeight = findViewById(R.id.Weight);
        pickedGender = findViewById(R.id.picked_gender);
        pickedActivity = findViewById(R.id.picked_activity);
        pickedPurpose = findViewById(R.id.picked_purpose);
        pickedLayout = findViewById(R.id.picked_layout);
        genderLayout = findViewById(R.id.genderLayout);
        purposeLayout = findViewById(R.id.purposeLayout);
        activityLayout = findViewById(R.id.activityLayout);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);
        purposeRadioGroup = findViewById(R.id.purposeRadioGroup);
        activityLevelSpinner = findViewById(R.id.activity_level_spinner);
        editButton = findViewById(R.id.edit_button);
        saveButton = findViewById(R.id.save_button);
    }

    public void editLayoutView(){
        userName.setEnabled(true);
        userAge.setEnabled(true);
        userWeight.setEnabled(true);
        userHeight.setEnabled(true);
        genderLayout.setVisibility(LinearLayout.VISIBLE);
        activityLayout.setVisibility(LinearLayout.VISIBLE);
        editButton.setVisibility(Button.GONE);
        saveButton.setVisibility(Button.VISIBLE);
        pickedLayout.setVisibility(LinearLayout.GONE);
        purposeLayout.setVisibility(LinearLayout.VISIBLE);
    }

    public void saveLayoutView(){
        userName.setEnabled(false);
        userAge.setEnabled(false);
        userWeight.setEnabled(false);
        userHeight.setEnabled(false);
        genderLayout.setVisibility(LinearLayout.GONE);
        activityLayout.setVisibility(LinearLayout.GONE);
        editButton.setVisibility(Button.VISIBLE);
        saveButton.setVisibility(Button.GONE);
        pickedLayout.setVisibility(LinearLayout.VISIBLE);
        purposeLayout.setVisibility(LinearLayout.GONE);
    }

    public void initUserDataView(UserProfile userProfile){
        userName.setText(userProfile.getName());
        userAge.setText(String.valueOf(userProfile.getAge()));
        userWeight.setText(String.valueOf(userProfile.getWeight()));
        userHeight.setText(String.valueOf(userProfile.getHeight()));
        pickedGender.setText(userProfile.getGender());
        pickedActivity.setText("Act lvl: " + userProfile.getActivityLevel());
        pickedPurpose.setText(userProfile.getPurpose());
    }

}
