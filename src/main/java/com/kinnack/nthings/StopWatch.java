package com.kinnack.nthings;

/*
    Copyright (c) 2005, Corey Goldberg

    StopWatch.java is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.
*/

public class StopWatch {
    
    private long startTime = 0;
    private long stopTime = 0;
    private boolean running = false;
    
    public void start() {
        startTime = System.currentTimeMillis();
        running = true;
    }
    
    public void stop() {
        stopTime = System.currentTimeMillis();
        running = false;
    }
    
    public long getElapsedTime() {
        long start = running ? System.currentTimeMillis() : startTime;
        return stopTime - start;
    }
    
    public long getElapsedTimeSecs() {
        return getElapsedTime() / 1000L;
    }
    
    /** Sample usage. */
    public static void main(String[] args) {
        StopWatch s = new StopWatch();
        s.start();
        // code you want to time goes here
        s.stop();
        System.out.println("elapsed time in milliseconds: " + s.getElapsedTime());
    }

    //~ Accessors (mostly for testing)
    
    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getStopTime() {
        return stopTime;
    }

    public void setStopTime(long stopTime) {
        this.stopTime = stopTime;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

}
