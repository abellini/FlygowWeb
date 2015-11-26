package br.com.flygonow.model;

/**
 * Created by tiago on 18/11/15.
 */
public class AttendantChartLastAlertsModel {

    private String alertType;
    private int value;
    private String color;

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
