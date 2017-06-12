package higheye.fuelcalculator;

/**
 * Created by mkurdziel on 16.01.2017.
 */

public class FuelDb {
    private int _id;
    private int _przebieg;
    private float _tankowanie;
    private long _datetime;
    private int full;
    private String car;
    private int carId;
    private float tempAverage;

    public FuelDb(int carId, String car) {
        this.carId = carId;
        this.car = car;
    }
    public FuelDb(String car) {
        this.car = car;
    }

    public FuelDb(int _przebieg, float _tankowanie, int full, int carId) {
        this._przebieg = _przebieg;
        this._tankowanie = _tankowanie;
        this.full = full;
        this.carId = carId;
    }

    public FuelDb(int _id, int _przebieg, float _tankowanie, long _datetime, int full) {
        this._id = _id;
        this._przebieg = _przebieg;
        this._tankowanie = _tankowanie;
        this._datetime = _datetime;
        this.full = full;
    }
    public FuelDb(int _id, int _przebieg, float _tankowanie, long _datetime, int full, float tempAverage) {
        this._id = _id;
        this._przebieg = _przebieg;
        this._tankowanie = _tankowanie;
        this._datetime = _datetime;
        this.tempAverage = tempAverage;
    }

    public long getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public int getPrzebieg() {
        return _przebieg;
    }

    public void setPrzebieg(int _przebieg) {
        this._przebieg = _przebieg;
    }

    public float getTankowanie() {
        return _tankowanie;
    }
    public int getFull() {return full;}
    public float getAverage() {return tempAverage;}

    public void setTankowanie(float _tankowanie) {
        this._tankowanie = _tankowanie;
    }

    public long getDatetime() {
        return _datetime;
    }
    public String getCar(){return car;}
    public int getCarId(){return carId;}
}