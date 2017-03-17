package arktop.ru.battery;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import arktop.ru.battery.draw.DrawAnalogGauge;

public class MainActivity extends AppCompatActivity {

    private InfoReceiver infoReceiver;

    private TextView chargeStatus;
    private TextView chargeSource;
    private TextView batteryTemperature;
    private TextView batteryWarnings;
    private TextView currentNow;
    private TextView currentAverage;
    private TextView chargeCount;
    private TextView energyCount;
    private TextView capacity;
    private DrawAnalogGauge batteryLevelGauge;
    private DrawAnalogGauge voltageGauge;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        infoReceiver = new InfoReceiver();
        chargeStatus = (TextView) findViewById(R.id.chargeStatus);
        chargeSource = (TextView) findViewById(R.id.chargeSource);
        batteryTemperature = (TextView) findViewById(R.id.batteryTemperature);
        batteryWarnings = (TextView) findViewById(R.id.batteryWarnings);
        currentNow = (TextView) findViewById(R.id.currentNow);
        currentAverage = (TextView) findViewById(R.id.currentAverage);
        chargeCount = (TextView) findViewById(R.id.chargeCount);
        energyCount = (TextView) findViewById(R.id.energyCount);
        capacity = (TextView) findViewById(R.id.capacity);
        float height = (float) (getResources().getDimension(R.dimen.gauge_phisical_height) * .52);
        float width = (float) (getResources().getDimension(R.dimen.gauge_phisical_width) * .52);
        batteryLevelGauge = (DrawAnalogGauge) findViewById(R.id.batLevel);
        batteryLevelGauge.setHeight(height);
        batteryLevelGauge.setWidth(width);
        batteryLevelGauge.invalidate();
        voltageGauge = (DrawAnalogGauge) findViewById(R.id.batVolts);
        voltageGauge.setHeight(height);
        voltageGauge.setWidth(width);
        voltageGauge.invalidate();
        getSupportActionBar().hide();
    }

    public void setChargeCount(String count){
        if(!count.isEmpty()) {
            chargeCount.setText(getString(R.string.charge_count_text).replace("<0>", count));
        } else {
            chargeCount.setText(count);
        }
    }

    public void setEnergyCount(String count){
        if(!count.isEmpty()) {
            energyCount.setText(getString(R.string.energy_count_text).replace("<0>", count));
        } else {
            energyCount.setText(count);
        }

    }

    public void setCapacity(String cap){

        if(!cap.isEmpty()) {
            capacity.setText(getString(R.string.capacity_text).replace("<0>", cap));
        } else {
            capacity.setText(cap);
        }
    }

    public void setCurrentNow(String current){
        if(!current.isEmpty()) {
            currentNow.setText(getString(R.string.current_now_text).replace("<0>", current));
        } else {
            currentNow.setText(current);
        }
    }

    public void setCurrentAverage(String current){

        if(!current.isEmpty()) {
            currentAverage.setText(getString(R.string.current_average_text).replace("<0>", current));
        } else {
            currentAverage.setText(current);
        }
    }

    public void setBatteryWarnings(String warning){
        batteryWarnings.setText(warning);
    }

    public void setChargeStatus(String status) {
        chargeStatus.setText(status);
    }

    public void setLevelText(String level) {
        Double levelForGauge = Double.parseDouble(level);
        double angle = (DrawAnalogGauge.MAX_ANGLE - DrawAnalogGauge.MIN_ANGLE) * levelForGauge / 100 + DrawAnalogGauge.MIN_ANGLE;
        batteryLevelGauge.setAngle(angle);
        batteryLevelGauge.setGaugeText("Заряд  " + level + "%");
        batteryLevelGauge.invalidate();

    }

    public void setChargeSource(String source) {
        chargeSource.setText(getString(R.string.charge_source_text).replace("<0>", source));
    }

    public void setBatteryVoltage(String voltage) {
        Double levelForGauge = Double.parseDouble(voltage);
        double angle = (DrawAnalogGauge.MAX_ANGLE - DrawAnalogGauge.MIN_ANGLE) * levelForGauge / 4.5 + DrawAnalogGauge.MIN_ANGLE;
        voltageGauge.setAngle(angle);
        voltageGauge.setGaugeText("Напр. бат. " + voltage + "В");
        voltageGauge.invalidate();
    }

    public void setBatteryTemperature(String temp) {

        batteryTemperature.setText(getString(R.string.battery_temperature_text).replace("<0>", temp));
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