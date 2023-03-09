package com.example.nwp_projekat.schedulers;

import com.example.nwp_projekat.model.Machine;
import com.example.nwp_projekat.utils.AsyncUtil;

public class SchedulerRestart implements Runnable{

    private final Machine machine;
    private final AsyncUtil asyncUtil;

    public SchedulerRestart(Machine machine, AsyncUtil asyncUtil) {
        this.machine = machine;
        this.asyncUtil = asyncUtil;
    }

    @Override
    public void run() {
        try {
            asyncUtil.asyncRestartMachine(machine);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}