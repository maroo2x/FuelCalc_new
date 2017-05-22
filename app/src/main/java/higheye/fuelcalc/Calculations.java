package higheye.fuelcalc;

import android.database.Cursor;
import android.content.Context;

import static java.security.AccessController.getContext;


/**
 * Created by mkurdziel on 3/5/17.
 */

public class Calculations {
private DbAdapter myDB;
    public Calculations(DbAdapter myDB){
        this.myDB = myDB;
    }


    public float getAverage(int carID) {
        int ostatniPrzebieg;
        int poczatkowyPrzebieg;
        float sumaTankowan = 0;
        float average = 0;
        Cursor descDB = myDB.viewDesc(carID);
        descDB.moveToFirst();
        if (descDB.getCount() > 1) {
            ostatniPrzebieg = descDB.getInt(1);
            descDB.moveToLast();
            poczatkowyPrzebieg = descDB.getInt(1);
            Cursor ascDB = myDB.viewAsc(carID);
            ascDB.moveToFirst();
            while (ascDB.moveToNext()) {
                sumaTankowan += ascDB.getFloat(2);
            }
            average = 100 * sumaTankowan / (ostatniPrzebieg - poczatkowyPrzebieg);
        }
//        return average;
            return average;
    }
}
