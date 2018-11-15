package com.example.akhilbatchu.iot;

public class Userdetail {
private int temperature,humidity,DigitalSmoke,AnalogSmoke;
    public Userdetail()
    {

    }

    public Userdetail(int temperature, int humidity, int DigitalSmoke, int AnalogSmoke) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.DigitalSmoke = DigitalSmoke;
        this.AnalogSmoke = AnalogSmoke;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getDigitalSmoke() {
        return DigitalSmoke;
    }

    public void setDigitalSmoke(int digitalSmoke) {
        DigitalSmoke = digitalSmoke;
    }

    public int getAnalogSmoke() {
        return AnalogSmoke;
    }

    public void setAnalogSmoke(int analogSmoke) {
        AnalogSmoke = analogSmoke;
    }
}
