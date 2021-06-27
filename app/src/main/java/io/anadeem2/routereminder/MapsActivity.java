package io.anadeem2.routereminder;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, TimePickerDialog.OnTimeSetListener {


    public ArrayList<Task> taskList = new ArrayList<>();
    public int currentIndex = 0;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText name, location;
    private Button save, cancel, time, date;




//    Calendar calendar = Calendar.getInstance();
//    String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());



    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);





    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;



        //locationPermission();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);


        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {



                taskList.add(new Task(latLng));
                createNewTask();
                mMap.addMarker(new MarkerOptions().position(latLng).title("Stop" + taskList.size()));

            }
        });

    }

    public void handleTime() {

        Calendar calendar = Calendar.getInstance();
        int HOUR = calendar.get(Calendar.HOUR);
        int MINUTE = calendar.get(Calendar.MINUTE);

        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(), "time picker");

//        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//
//                taskList.get(taskList.size()-1).c.set(Calendar.HOUR_OF_DAY, hourOfDay);
//                taskList.get(taskList.size()-1).c.set(Calendar.MINUTE, minute);
//                taskList.get(taskList.size()-1).c.set(Calendar.SECOND, 0);
//
//            }
//        }, HOUR, MINUTE, true);
//
//        timePickerDialog.show();


    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        taskList.get(taskList.size()-1).c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        taskList.get(taskList.size()-1).c.set(Calendar.MINUTE, minute);
        taskList.get(taskList.size()-1).c.set(Calendar.SECOND, 0);

    }

    public void handleDate(){

        Calendar calendar = Calendar.getInstance();

            int YEAR = calendar.get(Calendar.YEAR);
            int MONTH = calendar.get(Calendar.MONTH);
            int DAY = calendar.get(Calendar.DATE);



            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


                    taskList.get(taskList.size()-1).c.set(Calendar.YEAR, year);
                    taskList.get(taskList.size()-1).c.set(Calendar.MONTH, month);
                    taskList.get(taskList.size()-1).c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    setAlarm();

                }
            }, YEAR, MONTH, DAY);

            datePickerDialog.show();



dialog.hide();



//        long currentTime = System.currentTimeMillis();
//
//        int currentHOUR = calendar.get(Calendar.HOUR);
//        int currentMINUTE = calendar.get(Calendar.MINUTE);

//(((taskList.get(taskList.size()-1).getHour() - currentHOUR) * 60*60*1000) +
//                        ((taskList.get(taskList.size()-1).getMinute() - currentMINUTE) * 60 * 1000))

      //  alarmManager.set(AlarmManager.RTC_WAKEUP,taskList.get(taskList.size()-1).c.getTimeInMillis(), pendingIntent);


    }

    public void setAlarm(){

        Toast.makeText(this, "Reminder Set", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, Reminders.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);


        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP,taskList.get(taskList.size()-1).c.getTimeInMillis(), pendingIntent);

    }

    public void createNewTask(){


          dialogBuilder = new AlertDialog.Builder(this);
        final View createTaskView = getLayoutInflater().inflate(R.layout.popup,null);


        date = (Button) createTaskView.findViewById(R.id.date);
        time = (Button) createTaskView.findViewById(R.id.time);




        dialogBuilder.setView(createTaskView);
        dialog = dialogBuilder.create();
        dialog.show();

        time.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                handleTime();
            }
        });

        date.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                handleDate();
            }
        });






    }



}
