package higheye.fuelcalculator;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChooseActivity extends Activity {
    DbAdapter myDB;
    ArrayList<FuelDb> cars;
    ListView cars_listView;
    FuelDb fuelDb;
    EditText fieldCar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        fieldCar = (EditText) findViewById(R.id.car_name);
        myDB = new DbAdapter(this, null, null, 1);
        cars = new ArrayList<>();
        Cursor data = myDB.listCars();
        int numRows = data.getCount();
        final String add_car = getString(R.string.add_car);
        if (numRows == 0) {
            Toast.makeText(ChooseActivity.this, add_car, Toast.LENGTH_LONG).show();
        } else {
            int i = 0;
            while (data.moveToNext()) {
                fuelDb = new FuelDb(data.getInt(0), data.getString(1));
                cars.add(i, fuelDb);
                i++;
            }
            CarsCustomAdapter adapter = new CarsCustomAdapter(this, R.layout.cars_custom_row, cars);
            cars_listView = (ListView) findViewById(R.id.car_results);
            cars_listView.setAdapter(adapter);

        }
    }

    public void addCar(View view){
        String enter_data =  getString(R.string.enter_data);
        String car_added = getString(R.string.car_added);
        FuelDb fuelDb = new FuelDb(fieldCar.getText().toString());
//        fieldCar.getText().toString();
//        String enter_data = this.getString(R.string.enter_data);
//        String car_added = this.getString(R.string.car_added);
        if (fieldCar.length()==0) {
//            Toast.makeText(this, enter_data, Toast.LENGTH_LONG).show();
            Toast.makeText(this, enter_data, Toast.LENGTH_LONG).show();
        }
        else {
            myDB.newCar(fuelDb.getCar());
            fieldCar.setText("");
            Toast.makeText(this, car_added, Toast.LENGTH_LONG).show();
 //           Toast.makeText(this, entry_added, Toast.LENGTH_LONG).show();
            finish();
            startActivity(getIntent());
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //    finish();
    }

}
