package arktop.ru.battery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import static android.os.BatteryManager.BATTERY_PLUGGED_AC;
import static android.os.BatteryManager.BATTERY_PLUGGED_USB;

public class InfoReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent battery) {

        if (battery.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
            MainActivity activity = (MainActivity) context;

            int status = battery.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

            boolean isCharging = (status == BatteryManager.BATTERY_STATUS_CHARGING);
            boolean isDisCharging = (status == BatteryManager.BATTERY_STATUS_DISCHARGING);
            boolean isCharged = (status == BatteryManager.BATTERY_STATUS_FULL);

            int chargePlug = battery.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            boolean usbCharge = chargePlug == BATTERY_PLUGGED_USB;
            boolean acCharge = chargePlug == BATTERY_PLUGGED_AC;

            int level = battery.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = battery.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

            float batteryPct = (level / (float) scale) * 100;

            String voltage = "" + battery.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
            voltage = voltage.substring(0, 1) + "." + voltage.substring(1, 2);
            String temperature = "" + battery.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
            temperature = temperature.substring(0, 2) + "." + temperature.substring(2, 3);

            activity.setBatteryTemperature(temperature);
            activity.setBatteryVoltage(voltage);
            activity.setLevelText("" + batteryPct);

            if (usbCharge) {
                activity.setChargeSource("USB");
            }

            if (acCharge) {
                activity.setChargeSource("Сетевой адаптер");
            }

            if (isCharged) {
                activity.setChargeStatus("Полностью заряжен");
            }

            if (isCharging) {
                activity.setChargeStatus("Идет зарядка");
            }

            if (isDisCharging) {
                activity.setChargeStatus("Батарея разряжается");
            }
        }
    }
}

