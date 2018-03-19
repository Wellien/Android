package univ.acy.iut.monninj.project.random_anime;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import java.util.Random;

public class Random_Anime extends AppCompatActivity implements SensorEventListener{

    private final static String TAG=Random_Anime.class.getName();
    private SensorManager sensorManager;
    private Sensor sensor;
    private long lastUpdate = 0;
    private int id =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);
        Toast.makeText(this,"Arriver dans l'activité Random", Toast.LENGTH_LONG).show();
        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this,sensor,sensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onStart() {
        Log.i(TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onRestart() {
        Log.i(TAG, "onRestart");
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
    }

    public void onSensorChanged(SensorEvent event){

        //Log.i(TAG, "onSensorChanged");
        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER && this.id==0 ) {
            float ax, ay, az;
            ax = event.values[0];
            ay = event.values[1];
            az = event.values[2];

            //Log.i(TAG, "ax = "+ax);
            //Log.i(TAG, "ay = "+ay);
            //Log.i(TAG, "az = "+az);

            long Time = System.currentTimeMillis();
            //Log.i(TAG,"TIME = "+Time);
            //Log.i(TAG,"LastUpdate = "+lastUpdate);
            if ((Time - lastUpdate) > 100) {
                long diffTime = (Time - lastUpdate);
                lastUpdate = Time;

               // Log.i(TAG,"difftime = "+diffTime);
                float speed = Math.abs(ax+ay+az)/diffTime*1000;
                Log.i(TAG,"speed = "+speed);

                if (speed > 100 ) {
                    id_random();
                    Toast.makeText(this, "id = "+id,Toast.LENGTH_LONG).show();
                    Log.i(TAG,"ID = "+this.id);
                    Intent intent = new Intent(this, Affichage.class);
                    intent.putExtra("ID",id);
                    super.startActivity(intent);
                    super.finish();
                }


            }
        }


    }

    public void id_random(){
        Log.i(TAG, "Generate random id");
        Random random = new Random();
        this.id = random.nextInt(10000);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
