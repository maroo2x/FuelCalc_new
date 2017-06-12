package higheye.fuelcalculator;

import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.Nullable;
import android.widget.ListView;
import android.database.Cursor;
import android.widget.Toast;

import java.util.ArrayList;

public class ResultsActivity extends Activity {
    DbAdapter myDB;
    ArrayList<FuelDb> entries;
    ListView listView;
    FuelDb fuelDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        String no_entries = getString(R.string.no_entries);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_fuel_calc);
//  listView = (ListView) findViewById(R.id.results);
        myDB = new DbAdapter(this, null, null, 1);
        entries = new ArrayList<>();
//        FuelCalcActivity fca = new FuelCalcActivity();
        Cursor data = myDB.viewEntry(getCarID());
        int numRows = data.getCount();
        if (numRows == 0) {
            Toast.makeText(ResultsActivity.this, no_entries, Toast.LENGTH_LONG).show();
        } else {
            int i = 0;
            int prevMilage = 0;
            float tempAverage = 0;
            float lastFullQuantity = 0;
            while (data.moveToNext()) {
//                fuelDb = new FuelDb(data.getInt(0), data.getInt(1), data.getFloat(2), data.getLong(3), data.getInt(4));

                if (data.getInt(4) == 0 && prevMilage == 0){
                    tempAverage = 0;
                }
                else if (data.getInt(4) == 1 && prevMilage == 0) {
                    tempAverage = 0;
                    prevMilage = data.getInt(1);
                }

                else if (data.getInt(4) == 0 && prevMilage != 0){
                    tempAverage = 0;
                    lastFullQuantity += data.getFloat(2);
                }

                else if (data.getInt(4) == 1 && prevMilage != 0) {
                    tempAverage = (data.getFloat(2)/(data.getInt(1) - prevMilage)) * 100;
                    prevMilage = data.getInt(1);
                    lastFullQuantity = data.getFloat(2);
                }


                fuelDb = new FuelDb(data.getInt(0), data.getInt(1), data.getFloat(2), data.getLong(3), data.getInt(5), tempAverage);
                entries.add(i, fuelDb);

//                System.out.println(data.getInt(1) + " " + data.getFloat(2));
//                System.out.println(entries.get(i).getPrzebieg());
                i++;
            }
            CustomAdapter adapter = new CustomAdapter(this, R.layout.custom_row, entries);
            listView = (ListView) findViewById(R.id.results);
            listView.setAdapter(adapter);
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    //find current carID
    public int getCarID() {
        Cursor c = myDB.getCurrentCar();
        while (c.moveToNext()){
            return c.getInt(0);
        }
        return -1;
    }
}
