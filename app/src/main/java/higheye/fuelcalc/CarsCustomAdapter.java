package higheye.fuelcalc;

/**
 * Created by mkurdziel on 3/1/17.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.NavigableSet;

class CarsCustomAdapter extends ArrayAdapter<FuelDb> {
    private ArrayList<FuelDb> cars;
    private LayoutInflater mInflater;
    private int mViewResourceId;
    DbAdapter myDB;
    Context context;
    final String remove_entry_question = context.getString(R.string.remove_entry_question);
    final String remove_entry_nr_1 = context.getString(R.string.remove_entry_nr_1);
    final String remove_entry_nr_2 = context.getString(R.string.remove_entry_nr_2);

    public CarsCustomAdapter(Context context, int textViewResourceId, ArrayList<FuelDb> cars) {
        super(context, R.layout.cars_custom_row, cars);
        this.cars = cars;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        // default -  return super.getView(position, convertView, parent);
        convertView = mInflater.inflate(mViewResourceId, null);

        final FuelDb fuelDb = cars.get(position);
        Date dt;
        TextView text1 = (TextView) convertView.findViewById(R.id.car_rowResult1);
        TextView text2 = (TextView) convertView.findViewById(R.id.car_rowResult2);
        text1.setOnClickListener(new View.OnClickListener() {
 //           public int currentCarId;
            @Override
            public void onClick(View view) {
//                int currentCarID = fuelDb.getCarId();
                Context context =  getContext();
                myDB = new DbAdapter(getContext(), null, null, 1);
                myDB.removeCurrentCarFlag();
                myDB.setCurrentCarFlag(fuelDb.getCarId());
//                ((ChooseActivity)context).finish();
//                ((FuelCalcActivity)context).finish();
//                Intent intent = new Intent(context, FuelCalcActivity.class);
//                ((FuelCalcActivity)context).startActivity(intent);
//                onBackPressed();
                Intent intent = new Intent(context, FuelCalcActivity.class);
               context.startActivity(intent);
            }
        });

        //delete listed car when press the button
        Button delbutton = (Button) convertView.findViewById(R.id.del_button);
        delbutton.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          new AlertDialog.Builder(getContext())
                                                  // .setTitle("Potwierdź")
                                                  .setMessage(remove_entry_question)
                                                  .setIcon(android.R.drawable.ic_dialog_alert)
                                                  .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                                      public void onClick(DialogInterface dialog, int whichButton) {
                                                          myDB = new DbAdapter(getContext(), null, null, 1);
                                                          myDB.deleteEntries(fuelDb);
                                                          myDB.deleteCar(fuelDb);
                                                          Toast.makeText(getContext(), remove_entry_nr_1+" " + (position + 1) + " "+remove_entry_nr_2+".", Toast.LENGTH_SHORT).show();
                                                          cars.remove(position);
                                                          notifyDataSetChanged();
                                                      }})
                                                  .setNegativeButton(android.R.string.no, null).show();
                                      }
                                  }
        );


        if (text1 != null) {
            text1.setText((CharSequence) fuelDb.getCar());
               }
        if (text2 != null) {
            myDB = new DbAdapter(getContext(), null, null, 1);
            Calculations calculations = new Calculations(myDB);
//            int currentCarID = fuelDb.getCarId();
            text2.setText(String.format("%.2f", calculations.getAverage(fuelDb.getCarId())));
        }
            return convertView;

    }

}









