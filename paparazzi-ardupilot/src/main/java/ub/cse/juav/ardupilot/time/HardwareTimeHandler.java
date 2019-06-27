package ub.cse.juav.ardupilot.time;

import juav.simulator.time.TimeHandler;

public class HardwareTimeHandler extends TimeHandler{
    private double time = 0;
    @Override
    public double getTime() {
        return time+=1/1000;
    }
}
