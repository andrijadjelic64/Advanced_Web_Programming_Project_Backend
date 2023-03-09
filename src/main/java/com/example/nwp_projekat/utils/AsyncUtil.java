package com.example.nwp_projekat.utils;

import com.example.nwp_projekat.model.Machine;
import com.example.nwp_projekat.model.Status;
import com.example.nwp_projekat.repositories.MachineRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class AsyncUtil {

    private final MachineRepository machineRepository;

    public AsyncUtil(MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }


    @Async
    public void asyncStartMachine(Machine machine) throws InterruptedException {
        Thread.sleep(10000 + new Random().nextInt(5000));

        machine.setStatus(Status.RUNNING.toString());
        machineRepository.save(machine);
    }

    @Async
    public void asyncStopMachine(Machine machine) throws InterruptedException {
        Thread.sleep(10000 + new Random().nextInt(5000));

        machine.setStatus(Status.STOPPED.toString());
        machineRepository.save(machine);
    }

    @Async
    public void asyncRestartMachine(Machine machine) throws InterruptedException {
        Thread.sleep(5000 + new Random().nextInt(5000));

        machine.setStatus(Status.STOPPED.toString());
        machineRepository.save(machine);

        Thread.sleep(5000 + new Random().nextInt(5000));

        machine.setStatus(Status.RUNNING.toString());
        machineRepository.save(machine);
    }
}
