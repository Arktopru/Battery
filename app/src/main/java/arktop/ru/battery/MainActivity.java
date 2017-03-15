package arktop.ru.battery;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private InfoReceiver infoReceiver;

    private TextView chargeStatus;
    private TextView batteryLevel;
    private TextView chargeSource;
    private TextView batteryVoltage;
    private TextView batteryTemperature;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        infoReceiver = new InfoReceiver();
        chargeStatus = (TextView) findViewById(R.id.chargeStatus);
        batteryLevel = (TextView) findViewById(R.id.batteryLevel);
        chargeSource = (TextView) findViewById(R.id.chargeSource);
        batteryVoltage = (TextView) findViewById(R.id.batteryVoltage);
        batteryTemperature = (TextView) findViewById(R.id.batteryTemperature);
    }

    public void setChargeStatus(String status) {
        chargeStatus.setText(status);
    }

    public void setLevelText(String level) {
        batteryLevel.setText(level);
    }

    public void setChargeSource(String source) {
        chargeSource.setText(source);
    }

    public void setBatteryVoltage(String voltage) {
        batteryVoltage.setText(voltage);
    }

    public void setBatteryTemperature(String temp) {
        batteryTemperature.setText(temp);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        registerReceiver(infoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    public void onStop() {
        super.onStop();
        unregisterReceiver(infoReceiver);
    }

}