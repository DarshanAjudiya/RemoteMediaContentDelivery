package com.example.projectapp.systemsdata;

import android.app.AlarmManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.Calendar;

public class adBotService  extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public BroadcastReceiver batteryStatusReceiver= new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            BatteryDetails.alertMessage="";
            if (intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT,false))
            {
                BatteryDetails.is_present=true;
                int level=intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
                int scale=intent.getIntExtra(BatteryManager.EXTRA_SCALE,-1);
                BatteryDetails.previousLevel= BatteryDetails.CurrentLevel;
                BatteryDetails.CurrentLevel=(level/(double)scale)*100d;

                int health=intent.getIntExtra(BatteryManager.EXTRA_HEALTH,0);
                switch (health)
                {
                    case BatteryManager.BATTERY_HEALTH_COLD:
                        BatteryDetails.health="Cold";
                        break;
                    case BatteryManager.BATTERY_HEALTH_DEAD:
                        BatteryDetails.health="Dead";
                        break;
                    case BatteryManager.BATTERY_HEALTH_GOOD:
                        BatteryDetails.health="Good";
                        break;
                    case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                        BatteryDetails.health="Over Voltage";
                        break;
                    case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                        BatteryDetails.health="Over Hitting";
                        break;
                    default:    BatteryDetails.health="Unknown";
                }
                int status=intent.getIntExtra(BatteryManager.EXTRA_STATUS,1);
                BatteryDetails.is_charging=false;
                switch (status)
                {

                    case BatteryManager.BATTERY_STATUS_CHARGING:
                        BatteryDetails.is_charging=true;
                        break;
                    case BatteryManager.BATTERY_STATUS_DISCHARGING:
                        BatteryDetails.last_charged= Calendar.getInstance().getTime();
                        break;
                }
                if (intent.getBooleanExtra(BatteryManager.EXTRA_BATTERY_LOW,false))
                    BatteryDetails.alertMessage+="Battery is running low";


            }
           else
            {
                BatteryDetails.is_present=false;
            }

        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter=new IntentFilter();
      //  filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        registerReceiver(batteryStatusReceiver,filter);
       //AlarmManager alarmManager= (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(batteryStatusReceiver);
    }
}
