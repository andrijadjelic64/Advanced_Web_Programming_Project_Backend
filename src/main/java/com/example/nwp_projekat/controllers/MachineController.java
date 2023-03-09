package com.example.nwp_projekat.controllers;


import com.example.nwp_projekat.model.Machine;
import com.example.nwp_projekat.model.User;
import com.example.nwp_projekat.requests.machine.MachineCreateRequest;
import com.example.nwp_projekat.requests.machine.MachineUpdateRequest;
import com.example.nwp_projekat.requests.user.UserCreateRequest;
import com.example.nwp_projekat.services.MachineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/machines")
public class MachineController {

    private final MachineService machineService;

    public MachineController(MachineService machineService) {
        this.machineService = machineService;
    }

    @GetMapping(value = "/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Machine> getAllMachines(@RequestParam Optional<String> name,
                                        @RequestParam Optional<String> status){
//        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());

        String name1 = name.orElse("");
        String status1 = status.orElse("");

//        System.out.println("NAME:" + name1);
//        System.out.println("STATUS:" +status1);
//        String name1;
//        if(name==null){
//            name1="";
//        }else{
//            name1 = name;
//        }
//        String status1="";
//        if(status==null){
//            status1="";
//        }else{
//            status1 = status;
//        }
//        if(name.isPresent()){
//            System.out.println(name.get());        }
//        if(status.isPresent()){
//            System.out.println(status.get());
//        }

        return machineService.findAllByCreator( name1, status1);
    }

    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createMachine(@RequestBody MachineCreateRequest machineCreateRequest){
        return ResponseEntity.ok(machineService.createMachine(machineCreateRequest));
    }

    @GetMapping(value = "/start/{id}")
    public ResponseEntity<?> startMachine(@PathVariable("id") Integer id){
        return machineService.startMachine(id);
    }

    @GetMapping(value = "/stop/{id}")
    public ResponseEntity<?> stopMachine(@PathVariable("id") Integer id){
        return machineService.stopMachine(id);
    }

    @GetMapping(value = "/restart/{id}")
    public ResponseEntity<?> restartMachine(@PathVariable("id") Integer id){
        return machineService.restartMachine(id);
    }

    @GetMapping(value = "/destroy/{id}")
    public ResponseEntity<?> destroyMachine(@PathVariable("id") Integer id){
        return machineService.destroyMachine(id);
    }

}
