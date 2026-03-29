package com.neurotrack.backend.controller;

public class InputData {

    private double screen_time;
    private int unlocks;
    private int night_usage;

    public double getScreen_time() {
        return screen_time;
    }

    public void setScreen_time(double screen_time) {
        this.screen_time = screen_time;
    }

    public int getUnlocks() {
        return unlocks;
    }

    public void setUnlocks(int unlocks) {
        this.unlocks = unlocks;
    }

    public int getNight_usage() {
        return night_usage;
    }

    public void setNight_usage(int night_usage) {
        this.night_usage = night_usage;
    }
}