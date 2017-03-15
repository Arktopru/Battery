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

            int chargePlug = battery.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);

            switch (chargePlug) {
                case BatteryManager.BATTERY_PLUGGED_USB:
                    activity.setChargeSource("USB");
                    break;
                case BatteryManager.BATTERY_PLUGGED_AC:
                    activity.setChargeSource("Сетевой адаптер");
                    break;
                case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                    activity.setChargeSource("Беспровдн. зарядка");
                    break;
                default:
                    activity.setChargeSource("Отключено");
            }

            int status = battery.getIntExtra(BatteryManager.EXTRA_HEALTH, -1);

            switch (status) {
                case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                    activity.setBatteryWarnings("Сотояние бат. не распознано!");
                    break;

                case BatteryManager.BATTERY_HEALTH_GOOD:
                    activity.setBatteryWarnings("Батарея в порядке!");
                    break;

                case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                    activity.setBatteryWarnings("Батарея пергрета!");
                    break;

                case BatteryManager.BATTERY_HEALTH_DEAD:
                    activity.setBatteryWarnings("Батарею нужно заменить");
                    break;

                case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                    activity.setBatteryWarnings("Батарея перезаряжена!");
                    break;

                case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                    activity.setBatteryWarnings("Неопределенная поломка батареи");
                    break;

                case BatteryManager.BATTERY_HEALTH_COLD:
                    activity.setBatteryWarnings("Батарея замерзла!");
                    break;
            }
            status = battery.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

            switch (status) {
                case BatteryManager.BATTERY_STATUS_FULL:
                    activity.setChargeStatus("Полностью заряжена");
                    break;

                case BatteryManager.BATTERY_STATUS_CHARGING:
                    activity.setChargeStatus("Идет зарядка");
                    break;

                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    activity.setChargeStatus("Батарея разряжается");
                    break;
            }

            BatteryManager mBatteryManager = (BatteryManager) activity.getSystemService(Context.BATTERY_SERVICE);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                activity.setcurrentNow("" + mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_AVERAGE));
                activity.setcurrentAverage("" + mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW));
                activity.setChargeCount("" + mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER));
                activity.setEnergyCount("" + mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_ENERGY_COUNTER));
                activity.setCapacity("" + mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY));
            } else {
                activity.setcurrentNow("Не поддерживатеся");
                activity.setcurrentAverage("Не поддерживается");
                activity.setChargeCount("Не поддерживатеся");
                activity.setEnergyCount("Не поддерживатеся");
                activity.setCapacity("Не поддерживатеся");
            }


        }
    }
}

