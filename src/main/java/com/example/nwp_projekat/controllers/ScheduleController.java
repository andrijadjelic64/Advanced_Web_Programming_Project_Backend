package com.example.nwp_projekat.controllers;

import com.example.nwp_projekat.services.MachineService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    private final MachineService machineService;

    public ScheduleController(MachineService machineService) {
        this.machineService = machineService;
    }


    @GetMapping(value = "/start/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> startMachine(@PathVariable Integer id, @RequestParam("date") @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm") Date date){
        return this.machineService.scheduleStartMachine(id, date);
    }
    @GetMapping(value = "/stop/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> stopMachine(@PathVariable Integer id, @RequestParam("date") @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm") Date date){
        return this.machineService.scheduleStopMachine(id, date);
    }
    @GetMapping(value = "/restart/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> restartMachine(@PathVariable Integer id, @RequestParam("date") @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm") Date date){
        return this.machineService.scheduleRestartMachine(id, date);
    }
}
