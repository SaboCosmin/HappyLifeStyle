package com.hls.happylifestyle;

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

public class ProfileActivity extends AppCompatActivity {

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    RadioGroup genderRadioGroup, purposeRadioGroup;
    Spinner activityLevelSpinner;
    AutoCompleteTextView userName, userAge, userHeight, userWeight;
    LinearLayout pickedLayout, genderLayout, purposeLayout, activityLayout;
    TextView pickedGender,  pickedPurpose, pickedActivity;
    Button editButton;

    private Profile userProfile;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent startIntent = new Intent(getApplicationContext(), com.hls.happylifestyle.MainUserActivity.class);
        startActivity(startIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

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

        populateViews();
    }

    public void saveUser(View v){
        genderLayout.setVisibility(LinearLayout.GONE);
        purposeLayout.setVisibility(LinearLayout.GONE);
        activityLayout.setVisibility(LinearLayout.GONE);
        editButton.setVisibility(Button.VISIBLE);
        pickedLayout.setVisibility(LinearLayout.VISIBLE);

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
            height = 1180;
        }else{
            height = Integer.parseInt(userHeight.getText().toString());
        }

        String weight_text = userWeight.getText().toString();
        float weight;
        if(TextUtils.isEmpty(weight_text)){
            weight = 80f;
        }else{
            weight = Float.parseFloat(userWeight.getText().toString());
        }

        int selectedGenderId = genderRadioGroup.getCheckedRadioButtonId();
        int selectedPurposeId = purposeRadioGroup.getCheckedRadioButtonId();

        RadioButton genderRadioButton = findViewById(selectedGenderId);
        RadioButton purposeRadioButton = findViewById(selectedPurposeId);
        String gender = genderRadioButton.getText().toString();
        String purpose = purposeRadioButton.getText().toString();

        String activity_level_text = activityLevelSpinner.getSelectedItem().toString();
        int activity_level = Integer.parseInt(activity_level_text.replaceAll("[\\D]", ""));

        userProfile = new Profile(name, gender, age, activity_level, height, weight, purpose);
        saveSharedPreferences(userProfile);
        finish();
        startActivity(getIntent());
    }

    private void saveSharedPreferences(Profile profile){
        mEditor.putString(getString(R.string.key_name), profile.getName());
        mEditor.commit();
        mEditor.putString(getString(R.string.key_gender), profile.getGender());
        mEditor.commit();
        mEditor.putString(getString(R.string.key_age), Integer.toString(profile.getAge()));
        mEditor.commit();
        mEditor.putString(getString(R.string.key_activity), Integer.toString(profile.getActivity_level()));
        mEditor.commit();
        mEditor.putString(getString(R.string.key_height), Integer.toString(profile.getHeight()));
        mEditor.commit();
        mEditor.putString(getString(R.string.key_weight), Float.toString(profile.getWeight()));
        mEditor.commit();
        mEditor.putString(getString(R.string.key_purpose), profile.getPurpose());
        mEditor.commit();
        mEditor.putBoolean(getString(R.string.key_first_run), true);
        mEditor.commit();
        Toast.makeText(this, "Your new preferences are updating...", Toast.LENGTH_SHORT).show();
    }

    private void updateSharedPreferences(){
        String name = mSharedPreferences.getString(getString(R.string.key_name), "DefaultName");
        String gender = mSharedPreferences.getString(getString(R.string.key_gender), "Female");
        int age = Integer.parseInt(mSharedPreferences.getString(getString(R.string.key_age), "30"));
        int activity_level = Integer.parseInt(mSharedPreferences.getString(getString(R.string.key_activity), "0"));
        int height = Integer.parseInt(mSharedPreferences.getString(getString(R.string.key_height), "180"));
        float weight = Float.parseFloat(mSharedPreferences.getString(getString(R.string.key_weight), "80f"));
        String purpose = mSharedPreferences.getString(getString(R.string.key_purpose), "DefaultName");

        userProfile = new Profile(name, gender, age, activity_level, height, weight, purpose);
    }

    public void editPressed(View v){
        genderLayout.setVisibility(LinearLayout.VISIBLE);
        purposeLayout.setVisibility(LinearLayout.VISIBLE);
        activityLayout.setVisibility(LinearLayout.VISIBLE);
        pickedLayout.setVisibility(LinearLayout.GONE);
        editButton.setVisibility(Button.GONE);
    }

    public void populateViews(){
        if(mSharedPreferences.getBoolean(getString(R.string.key_first_run), false)){
            updateSharedPreferences();
            userName.setText(userProfile.getName());
            userAge.setText(Integer.toString(userProfile.getAge()));
            userWeight.setText(Float.toString(userProfile.getWeight()));
            userHeight.setText(Integer.toString(userProfile.getHeight()));
            pickedLayout.setVisibility(LinearLayout.VISIBLE);
            genderLayout.setVisibility(LinearLayout.GONE);
            purposeLayout.setVisibility(LinearLayout.GONE);
            activityLayout.setVisibility(LinearLayout.GONE);
            pickedGender.setText(userProfile.getGender());
            pickedActivity.setText("Act lvl: " + userProfile.getActivity_level());
            pickedPurpose.setText(userProfile.getPurpose());

        }else{
            pickedLayout.setVisibility(LinearLayout.GONE);
            editButton.setVisibility(Button.GONE);
        }
    }

}
