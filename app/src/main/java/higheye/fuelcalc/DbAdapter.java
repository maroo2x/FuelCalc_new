package higheye.fuelcalc;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by mkurdziel on 1/23/17.
 */
public class DbAdapter extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FuelCalc.db";
    public static final String TABLE_POJAZD = "pojazd";
    public static final String TABLE_CARS = "cars";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PRZEBIEG = "przebieg";
    public static final String COLUMN_TANKOWANIE = "tankowanie";
    public static final String COLUMN_DATETIME = "data";
    public static final String COLUMN_FULL = "full";
    public static final String COLUMN_STACJA = "stacja";
    public static final String COLUMN_POJAZD_CAR_ID = "carid";


    public static final String COLUMN_CAR_ID = "id";
    public static final String COLUMN_CARNAME = "carname";
    public static final String COLUMN_DEFAULT = "maincar";


    public DbAdapter(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_POJAZD + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY," +
                        COLUMN_PRZEBIEG + " INT," +
                        COLUMN_TANKOWANIE + " FLOAT, " +
                        COLUMN_DATETIME + " INT, " +
                        COLUMN_FULL + " INT, " +
                        COLUMN_POJAZD_CAR_ID + " INT, " +
                        COLUMN_STACJA + " VARCHAR);";
        String SQL_CREATE_CARS =
                "CREATE TABLE "+TABLE_CARS+" ("+COLUMN_CAR_ID+" INTEGER PRIMARY KEY, "+COLUMN_CARNAME+" VARCHAR, "+COLUMN_DEFAULT + " INT);";
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_CARS);

        ContentValues values = new ContentValues();
        values.put(COLUMN_CARNAME, "Domyślny pojazd");
        values.put(COLUMN_DEFAULT, "1");
        db.insert(TABLE_CARS, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_POJAZD;
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void dropTable() {
        String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_POJAZD;
        String SQL_DELETE_ENTRIES_CARS =
                "DROP TABLE IF EXISTS " + TABLE_CARS;
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_DELETE_ENTRIES_CARS);
        onCreate(db);
        db.close();
    }

    public void addEntry(FuelDb fueldb) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRZEBIEG, fueldb.getPrzebieg());
        values.put(COLUMN_TANKOWANIE, fueldb.getTankowanie());
        values.put(COLUMN_DATETIME, System.currentTimeMillis());
        values.put(COLUMN_FULL, fueldb.getFull());
        values.put(COLUMN_POJAZD_CAR_ID, fueldb.getCarId());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_POJAZD, null, values);
        db.close();
    }

    public void deleteEntry(FuelDb fuelDb) {
        String delentry = "DELETE FROM " + TABLE_POJAZD + " WHERE " + COLUMN_ID + " =\"" + fuelDb.getId() + "\"";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(delentry);
    }
    public void deleteEntries(FuelDb fuelDb) {
        String delentry = "DELETE FROM " + TABLE_POJAZD + " WHERE " + COLUMN_POJAZD_CAR_ID + " =\"" + fuelDb.getCarId() + "\"";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(delentry);
    }

    public Cursor viewEntry(int carID) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_POJAZD + " WHERE "+COLUMN_POJAZD_CAR_ID+" = "+carID+";";// why not leave out the WHERE clause?
        //Cursor points to a location in your results
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getLast(int carID){
        SQLiteDatabase db = getWritableDatabase();
//        String query = "SELECT * FROM "+TABLE_POJAZD+" ORDER BY "+COLUMN_ID+" DESC LIMIT 1";
        String query = "SELECT MAX("+COLUMN_PRZEBIEG+") FROM "+TABLE_POJAZD+" WHERE "+COLUMN_POJAZD_CAR_ID+" = "+carID+";";
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public Cursor viewDesc(int carID){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_POJAZD+" where "+COLUMN_POJAZD_CAR_ID+" = "+carID+" AND "+COLUMN_FULL+" = 1 ORDER BY "+COLUMN_PRZEBIEG+" DESC";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    public Cursor viewAsc(int carID){
        SQLiteDatabase db = getWritableDatabase();
//        String query = "SELECT * FROM "+TABLE_POJAZD+" ORDER BY "+COLUMN_ID+" ASC";
        String query = "select * from "+TABLE_POJAZD+" where "+COLUMN_POJAZD_CAR_ID+" = "+carID+" AND "+COLUMN_PRZEBIEG+" <= (select max("+COLUMN_PRZEBIEG+") from "+TABLE_POJAZD+" where "+COLUMN_FULL+" = 1) and "+COLUMN_PRZEBIEG+" >= (select min("+COLUMN_PRZEBIEG+") from "+TABLE_POJAZD+" where "+COLUMN_FULL+" = 1) ORDER BY "+COLUMN_PRZEBIEG+" ASC";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    public Cursor listCars(){
        SQLiteDatabase db = getWritableDatabase();
        String query = "select * from "+TABLE_CARS+" ORDER BY "+COLUMN_ID+" ASC";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void newCar(String carname){
        ContentValues values = new ContentValues();
        values.put(COLUMN_CARNAME, carname);
        values.put(COLUMN_DEFAULT, "0");
        SQLiteDatabase db = getWritableDatabase();
 //       db = getWritableDatabase();
        db.insert(TABLE_CARS, null, values);
        db.close();
    }
    public void deleteCar(FuelDb fuelDb) {
        String delentry = "DELETE FROM " + TABLE_CARS + " WHERE " + COLUMN_CAR_ID + " =\"" + fuelDb.getCarId() + "\"";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(delentry);
    }

    public Cursor getCurrentCar(){
        SQLiteDatabase db = getWritableDatabase();
        String query = "select * from "+TABLE_CARS+" WHERE "+COLUMN_DEFAULT+" = 1";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void removeCurrentCarFlag(){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DEFAULT, "0");
        db.update(TABLE_CARS, values, null, null);
    }
    public void setCurrentCarFlag(int id){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DEFAULT, "1");
        db.update(TABLE_CARS, values, COLUMN_CAR_ID+" = "+id, null);
        String query = "update "+TABLE_CARS+" set "+COLUMN_DEFAULT+" = 1 where "+COLUMN_CAR_ID+" = "+id+";";
    }

}