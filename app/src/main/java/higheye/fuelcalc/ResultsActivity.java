package higheye.fuelcalc;

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
            while (data.moveToNext()) {
                fuelDb = new FuelDb(data.getInt(0), data.getInt(1), data.getFloat(2), data.getLong(3), data.getInt(4));
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
    //find current carID
    public int getCarID() {
        Cursor c = myDB.getCurrentCar();
        while (c.moveToNext()){
            return c.getInt(0);
        }
        return -1;
    }
}
