package higheye.fuelcalc;

/**
 * Created by mkurdziel on 3/1/17.
 */

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.app.AlertDialog;
import android.content.DialogInterface;

import android.app.Activity;

import android.database.Cursor;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

class CustomAdapter extends ArrayAdapter<FuelDb> {
    private ArrayList<FuelDb> entries;
    private LayoutInflater mInflater;
    private int mViewResourceId;
    DbAdapter myDB;
    Context context;
    final String remove_car_question = context.getString(R.string.remove_car_question);
    final String entry_nr_1 = context.getString(R.string.entry_nr_1);
    final String entry_nr_2 = context.getString(R.string.entry_nr_2);


    public CustomAdapter(Context context, int textViewResourceId, ArrayList<FuelDb> entries) {
        super(context, R.layout.custom_row, entries);
        this.entries = entries;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;


    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        // default -  return super.getView(position, convertView, parent);
        convertView = mInflater.inflate(mViewResourceId, null);

        final FuelDb fuelDb = entries.get(position);
        Date dt;
        TextView text1 = (TextView) convertView.findViewById(R.id.rowResult1);
        TextView text2 = (TextView) convertView.findViewById(R.id.rowResult2);
        TextView text3 = (TextView) convertView.findViewById(R.id.rowResult3);
        TextView text4 = (TextView) convertView.findViewById(R.id.full_id);
        Button button = (Button) convertView.findViewById(R.id.button_del);
  //     final CustomAdapter adapter = new CustomAdapter(this, R.layout.custom_row, entries)
 //       final View finalConvertView = convertView;
        button.setOnClickListener(new View.OnClickListener() {

                                      @Override
                                      public void onClick(View v) {
                                          //myDB = new DbAdapter(getContext(), null, null, 1);
                                          //myDB.deleteEntry(fuelDb);
                                          //Toast.makeText(getContext(), "Wpis nr " + (position + 1) + " usunięty.", Toast.LENGTH_SHORT).show();
                                          //entries.remove(position);
                                          //notifyDataSetChanged();

                                          new AlertDialog.Builder(getContext())
                                                 // .setTitle("Potwierdź")

                                                  .setMessage(remove_car_question)
                                                  .setIcon(android.R.drawable.ic_dialog_alert)
                                                  .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                                      public void onClick(DialogInterface dialog, int whichButton) {
                                                          myDB = new DbAdapter(getContext(), null, null, 1);
                                                          myDB.deleteEntry(fuelDb);
                                                          Toast.makeText(getContext(), entry_nr_1+" " + (position + 1) + " "+entry_nr_2, Toast.LENGTH_SHORT).show();
                                                          entries.remove(position);
                                                          notifyDataSetChanged();
                                                      }})
                                                  .setNegativeButton(android.R.string.no, null).show();
                                      }
                                  }
        );

        if (text1 != null) {
            dt = new Date(fuelDb.getDatetime());
            String dt2 = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault()).format(dt);
            text1.setText(dt2);
            //           text1.setText(dt.toString());
            //           text1.setText(Long.toString(fuelDb.getId()));
        }
        if (text2 != null) {
            text2.setText(Integer.toString(fuelDb.getPrzebieg())); //Integer.toString(fuelDb.getPrzebieg()));
        }
        if (text3 != null) {
            text3.setText(Float.toString(fuelDb.getTankowanie())); //Float.toString(fuelDb.getTankowanie()));
        }
/*        if (text4 != null) {
            text4.setText(Integer.toString(fuelDb.getFull())); //Float.toString(fuelDb.getTankowanie()));
        }*/
        if (text4 != null) {
            if (fuelDb.getFull() == 1) {
                text4.setText("yes");
            }
            else {text4.setText("no");}
        }

        return convertView;
    }
}


/*
 Cursor data = myDB.viewEntry();
        if(data.getCount() == 0){
            Toast.makeText(this, "There are no contents in this list!",Toast.LENGTH_LONG).show();
        }else {
            while (data.moveToNext()) {
                theList.add(data.getString(1));
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, theList);
                listView.setAdapter(listAdapter);
            }

 */






