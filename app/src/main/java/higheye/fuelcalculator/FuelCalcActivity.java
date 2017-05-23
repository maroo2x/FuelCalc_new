package higheye.fuelcalculator;

import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import android.content.Intent;
import android.widget.Toast;


public class FuelCalcActivity extends Activity {
    EditText polePrzebieg;
    EditText poleLitry;
    TextView ResultsTextView;
    TextView TextViewCar;
    Button Zapisz;
    DbAdapter myDB;
    int full;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_calc);
        polePrzebieg = (EditText) findViewById(R.id.polePrzebieg);
        poleLitry = (EditText) findViewById(R.id.poleLitry);
        ResultsTextView = (TextView) findViewById(R.id.ResultsTextView);
        TextViewCar = (TextView) findViewById(R.id.textViewCar);
        myDB = new DbAdapter(this, null, null, 1);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.setChecked(true);
 // myDB.dropTable();  // COMMENT THIS !!!!!!!!
        Calculations calculations = new Calculations(myDB);
        ResultsTextView.setText(String.format("%.2f", calculations.getAverage(getCarID())));
        // show current car
        TextViewCar.setText(getCar());
    }

    // move to activity with results
    public void showResults(View view) {
        Intent intent = new Intent(this, ResultsActivity.class);
        startActivity(intent);
    }

    // move to activity where choosing a car
    public void chooseCar(View view) {
        Intent intent = new Intent(this, ChooseActivity.class);
        startActivity(intent);
    }

    //find current car
    public String getCar() {
        String choose_car2 = getString(R.string.choose_car2);
        Cursor c = myDB.getCurrentCar();
         while (c.moveToNext()){
             return c.getString(1);
         }
        return choose_car2;
    }
    //find current carID
    public int getCarID() {
        Cursor c = myDB.getCurrentCar();
        while (c.moveToNext()){
            return c.getInt(0);
        }
        return -1;
    }

    // add an entry to the database
    public void newEntry(View view) {
        String enter_data =  getString(R.string.enter_data);
        String lower_milage = getString(R.string.lower_milage);
        String entry_added = getString(R.string.entry_added);
        if (checkBox.isChecked()) {
            full = 1;
        } else {
            full = 0;
        }
        Cursor lastPrzebieg = myDB.getLast(getCarID());
        while (lastPrzebieg.moveToNext()) {
            int lastP = lastPrzebieg.getInt(0);
            if (poleLitry.length() == 0 || polePrzebieg.length() == 0) {
                Toast.makeText(this, enter_data, Toast.LENGTH_LONG).show();
            } else {
                if (Integer.parseInt(polePrzebieg.getText().toString()) < lastP) {
                    Toast.makeText(this, lower_milage+" (" + lastP + ")", Toast.LENGTH_LONG).show();
                }
                FuelDb fuelDb = new FuelDb(Integer.parseInt(polePrzebieg.getText().toString()), Float.parseFloat(poleLitry.getText().toString()), full, getCarID());
                myDB.addEntry(fuelDb);
                polePrzebieg.setText("");
                poleLitry.setText("");
                Toast.makeText(this, entry_added, Toast.LENGTH_LONG).show();
                finish();
                startActivity(getIntent());
            }
        }
    }
}