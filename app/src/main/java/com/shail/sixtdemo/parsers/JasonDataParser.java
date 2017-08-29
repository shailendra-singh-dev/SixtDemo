package com.shail.sixtdemo.parsers;

import com.shail.sixtdemo.BuildConfig;
import com.shail.sixtdemo.model.Car;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shailendra Singh on 28-Aug-17.
 * iTexico
 * ssingh@itexico.net
 */
public class JasonDataParser {


    private static final String ID = "id";
    private static final String MODEL_IDENTIFIER = "modelIdentifier";
    private static final String MODEL_NAME = "modelName";
    private static final String NAME = "name";
    private static final String MAKE = "make";
    private static final String GROUP = "group";
    private static final String COLOR = "color";
    private static final String SERIES = "series";
    private static final String FUEL_TYPE = "fuelType";
    private static final String FULEL_LEVEL = "fuelLevel";
    private static final String TRANSMISSION = "transmission";
    private static final String LICENSE_PLATE = "licensePlate";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String INNER_CLEANLINESS = "innerCleanliness";

    private JasonDataParser() {
    }

    public static ArrayList<Car> getCars(String response) {
        ArrayList<Car> cars = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.optString(ID);
                String modelIdentifier = jsonObject.optString(MODEL_IDENTIFIER);
                String modelName = jsonObject.optString(MODEL_NAME);
                String name = jsonObject.optString(NAME);
                String make = jsonObject.optString(MAKE);
                String group = jsonObject.optString(GROUP);
                String color = jsonObject.optString(COLOR);
                String series = jsonObject.optString(SERIES);
                String fuelType = jsonObject.optString(FUEL_TYPE);
                int fuelLevel = jsonObject.optInt(FULEL_LEVEL);
                String transmission = jsonObject.optString(TRANSMISSION);
                String licensePlate = jsonObject.optString(LICENSE_PLATE);
                double latitude = jsonObject.optDouble(LATITUDE);
                double longitude = jsonObject.optDouble(LONGITUDE);
                String innerCleanliness = jsonObject.optString(INNER_CLEANLINESS);

                Car car = new Car();
                car.setID(id);
                car.setModelIdentifier(modelIdentifier);
                car.setModelName(modelName);
                car.setName(name);
                car.setMake(make);
                car.setGroup(group);
                car.setColor(color);
                car.setSeries(series);
                car.setFuelType(fuelType);
                car.setFuelLevel(fuelLevel);
                car.setTransmission(transmission);
                car.setLicensePlate(licensePlate);
                car.setLatitude(latitude);
                car.setLongitude(longitude);
                car.setInnerCleanliness(innerCleanliness);
                String carImageUrl = BuildConfig.BASE_URL + modelIdentifier + BuildConfig.BASE_URL_SEPERATOR + color + BuildConfig.BASE_URL_SEPERATOR + BuildConfig.BASE_URL_POSTFIX;
                car.setCarImageUrl(carImageUrl);

                cars.add(car);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cars;
    }
}
