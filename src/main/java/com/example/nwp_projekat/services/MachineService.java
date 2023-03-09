package com.example.nwp_projekat.services;

import com.example.nwp_projekat.model.Machine;
import com.example.nwp_projekat.model.Status;
import com.example.nwp_projekat.model.User;
import com.example.nwp_projekat.repositories.MachineRepository;
import com.example.nwp_projekat.repositories.UserRepository;
import com.example.nwp_projekat.requests.machine.MachineCreateRequest;
import com.example.nwp_projekat.schedulers.SchedulerRestart;
import com.example.nwp_projekat.schedulers.SchedulerStart;
import com.example.nwp_projekat.schedulers.SchedulerStop;
import com.example.nwp_projekat.utils.AsyncUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MachineService implements IService<Machine,Integer>{

    private final MachineRepository machineRepository;
    private final UserRepository userRepository;

    private final ErrorMessageService errorMessageService;

    private final TaskScheduler taskScheduler;



    private final AsyncUtil asyncUtil;


    public MachineService(MachineRepository machineRepository, UserRepository userRepository, ErrorMessageService errorMessageService, TaskScheduler taskScheduler, AsyncUtil asyncUtil) {
        this.machineRepository = machineRepository;
        this.userRepository = userRepository;
        this.errorMessageService = errorMessageService;
        this.taskScheduler = taskScheduler;
        this.asyncUtil = asyncUtil;
    }
    @Transactional
    @Override
    @PreAuthorize("hasAnyAuthority('can_create_users')")
    public Machine save(Machine machine) {
        return machineRepository.save(machine);
    }

    @Override
    public Optional<Machine> findById(Integer id) {
        return machineRepository.findById(id);
    }

    @Override
    public List<Machine> findAll() {
        return (List<Machine>) machineRepository.findAll();
    }


    @Transactional
    @PreAuthorize("hasAnyAuthority('can_start_machines')")
    public ResponseEntity<?> startMachine(Integer id){
        Optional<Machine> optMachine = this.machineRepository.findById(id);
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());


        if(optMachine.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Machine machine = optMachine.get();
        if(!(user.equals(machine.getCreatedBy()))){
            return ResponseEntity.badRequest().body("This machine is not yours");
        }
        if(machine.getStatus().equals(Status.RUNNING.toString())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Machine is already RUNNING.");
        }
        try {
            asyncUtil.asyncStartMachine(machine);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ObjectOptimisticLockingFailureException exception) {
            this.startMachine(id);
        }

        return ResponseEntity.ok().build();
    }


    @Transactional
    @PreAuthorize("hasAnyAuthority('can_stop_machines')")
    public ResponseEntity<?> stopMachine( Integer id){
        Optional<Machine> optMachine = this.machineRepository.findById(id);
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());


        if(optMachine.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Machine machine = optMachine.get();
        if(!(user.equals(machine.getCreatedBy()))){
            return ResponseEntity.badRequest().body("This machine is not yours");
        }
        if(machine.getStatus().equals(Status.STOPPED.toString())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Machine is already STOPPED");
        }
        try {
            asyncUtil.asyncStopMachine(machine);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ObjectOptimisticLockingFailureException exception) {
            this.stopMachine(id);
        }

        return ResponseEntity.ok().build();
    }

    @Transactional
    @PreAuthorize("hasAnyAuthority('can_restart_machines')")
    public ResponseEntity<?> restartMachine(Integer id){
        Optional<Machine> optMachine = this.machineRepository.findById(id);
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());


        if(optMachine.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Machine machine = optMachine.get();
        if(!(user.equals(machine.getCreatedBy()))){
            return ResponseEntity.badRequest().body("This machine is not yours");
        }
        if(machine.getStatus().equals(Status.STOPPED.toString())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Machine is not RUNNING");
        }
        try {
            asyncUtil.asyncRestartMachine(machine);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ObjectOptimisticLockingFailureException exception) {
            this.restartMachine(id);
        }

        return ResponseEntity.ok().build();
    }
    @Transactional
    @PreAuthorize("hasAnyAuthority('can_destroy_machines')")
    public ResponseEntity<?> destroyMachine(Integer id ){
        Optional<Machine> optMachine = this.machineRepository.findById(id);
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());


        if(optMachine.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Machine machine = optMachine.get();
        if(!(user.equals(machine.getCreatedBy()))){
            return ResponseEntity.badRequest().body("This machine is not yours");
        }
        if(machine.getStatus().equals(Status.RUNNING.toString())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Machine is RUNNING.Stop the machine before destroying it");
        }
        try {
            machine.setActive(false);
            machineRepository.save(machine);

        } catch (ObjectOptimisticLockingFailureException exception) {
            this.destroyMachine(id);
        }

        return ResponseEntity.ok().build();
    }
    @Transactional
    @PreAuthorize("hasAnyAuthority('can_create_machines')")
    public Machine createMachine(MachineCreateRequest machineCreateRequest){
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        return machineRepository.save(new Machine(null,machineCreateRequest.getName(), Status.STOPPED.toString(), user, machineCreateRequest.getActive()));
    }
    @PreAuthorize("hasAnyAuthority('can_search_machines')")
    public List<Machine> findAllByCreator(String name, String status) {

//        System.out.println("TOKEEEEEN " + SecurityContextHolder.getContext().getAuthentication().getName());
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
//        System.out.println("userid is: " + user.getUserId());


        return  machineRepository.findAllBySearch(user.getUserId(), name, status);
    }
    @Override
    public void deleteById(Integer id) {
        machineRepository.deleteById(id);
    }

    @PreAuthorize("hasAnyAuthority('can_start_machines')")
    public ResponseEntity<?> scheduleStartMachine(Integer id, Date date){
        Optional<Machine> optMachine = this.machineRepository.findById(id);
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());


        if(optMachine.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Machine machine = optMachine.get();
        if(!(user.equals(machine.getCreatedBy()))){
            errorMessageService.newError(machine,"START","Unauthorized");
            return ResponseEntity.badRequest().body("This machine is not yours");
        }
        if(machine.getStatus().equals(Status.RUNNING.toString())){
            System.out.println("Hello from MachineService/scheduleStartMachine");
            errorMessageService.newError(machine,"START","Machine already RUNNING");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Machine is already RUNNING");
        }


        this.taskScheduler.schedule(new SchedulerStart(machine,asyncUtil),date);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('can_stop_machines')")
    public ResponseEntity<?> scheduleStopMachine(Integer id, Date date){
        Optional<Machine> optMachine = this.machineRepository.findById(id);
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());


        if(optMachine.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Machine machine = optMachine.get();
        if(!(user.equals(machine.getCreatedBy()))){
            errorMessageService.newError(machine,"STOP","Unauthorized");
            return ResponseEntity.badRequest().body("This machine is not yours");
        }
        if(machine.getStatus().equals(Status.STOPPED.toString())){
            errorMessageService.newError(machine,"STOP","Machine already STOPPED");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Machine is already STOPPED");
        }


        this.taskScheduler.schedule(new SchedulerStop(machine,asyncUtil),date);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('can_restart_machines')")
    public ResponseEntity<?> scheduleRestartMachine(Integer id, Date date){
        Optional<Machine> optMachine = this.machineRepository.findById(id);
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());


        if(optMachine.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Machine machine = optMachine.get();
        if(!(user.equals(machine.getCreatedBy()))){
            errorMessageService.newError(machine,"RESTART","Unauthorized");
            return ResponseEntity.badRequest().body("This machine is not yours");
        }
        if(machine.getStatus().equals(Status.STOPPED.toString())){
            errorMessageService.newError(machine,"RESTART","Machine not RUNNING");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Machine is not RUNNING");
        }


        this.taskScheduler.schedule(new SchedulerRestart(machine,asyncUtil),date);
        return ResponseEntity.ok().build();
    }
}
