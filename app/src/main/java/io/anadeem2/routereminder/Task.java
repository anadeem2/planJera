package io.anadeem2.routereminder;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

public class Task {

    private LatLng latLng;
    private int year, day, month, hour, minute;
    public Calendar c;


    public Task(LatLng latLng) {
        this.latLng = latLng;
        year = 0;
        day = 0;
        month = 0;
        hour = 0;
        minute = 0;
        c = Calendar.getInstance();
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }


    public LatLng getLatLng() {
        return latLng;
    }


}