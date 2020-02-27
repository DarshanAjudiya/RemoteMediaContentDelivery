package com.example.projectapp.systemsdata;

import java.util.Date;

public class BatteryDetails {
    public static Double previousLevel = 0d;
    public static Double CurrentLevel = 0d;
    public static Boolean is_charging;
    public static Date last_charged;
    public static String health;
    public static String alertMessage;
    public static Boolean is_present;

    public static String getBatteryInfo() {
        if (!is_present) {
            return "Battery not present!!!";
        }
        String info = "";
        if (is_charging)
            info += "on charge ";
        else {
            info += "on Battery. \n" + "last charged: " + last_charged.toString();
        }
        if (!alertMessage.equals(""))
            info += "\n" + alertMessage;

        info += "\nCharge remaining: " + CurrentLevel + "%";
        if ((previousLevel - CurrentLevel) > 2)
            info += "\nBattery usage is high";
        info += "\nHealth: " + health;
        return info;
    }
}

