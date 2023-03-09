package com.example.nwp_projekat.schedulers;

import com.example.nwp_projekat.controllers.MachineController;
import com.example.nwp_projekat.model.Machine;
import com.example.nwp_projekat.repositories.MachineRepository;
import com.example.nwp_projekat.utils.AsyncUtil;

public class SchedulerStart implements Runnable{

    private final Machine machine;
    private final AsyncUtil asyncUtil;

    public SchedulerStart(Machine machine, AsyncUtil asyncUtil) {
        this.machine = machine;
        this.asyncUtil = asyncUtil;
    }

    @Override
    public void run() {
        try {
            asyncUtil.asyncStartMachine(machine);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
