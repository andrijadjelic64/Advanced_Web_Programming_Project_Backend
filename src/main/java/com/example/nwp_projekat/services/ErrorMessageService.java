package com.example.nwp_projekat.services;

import com.example.nwp_projekat.model.ErrorMessage;
import com.example.nwp_projekat.model.Machine;
import com.example.nwp_projekat.model.User;
import com.example.nwp_projekat.repositories.ErrorMessageRepository;
import com.example.nwp_projekat.repositories.MachineRepository;
import com.example.nwp_projekat.repositories.UserRepository;
import com.example.nwp_projekat.requests.error_message.CreateErrorMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ErrorMessageService implements IService<ErrorMessage,Integer>{

    private final ErrorMessageRepository errorMessageRepository;
    private final MachineRepository machineRepository;
    private final UserRepository userRepository;

    public ErrorMessageService(ErrorMessageRepository errorMessageRepository, MachineRepository machineRepository, UserRepository userRepository) {
        this.errorMessageRepository = errorMessageRepository;
        this.machineRepository = machineRepository;
        this.userRepository = userRepository;
    }


//@Transactional
//    public ResponseEntity<?> createError(CreateErrorMessage createErrorMessage) {
//        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
//        Optional<Machine> machine = machineRepository.findById(createErrorMessage.getMachineId());
//        Machine machine1=null;
//        if(machine.isPresent()){
//            machine1 = machine.get();
//        }
//        ErrorMessage errorMessage = new ErrorMessage(null, machine1, user,
//                createErrorMessage.getOperation(),createErrorMessage.getMessage(), new Date());
//
//        errorMessageRepository.save(errorMessage);
//        return ResponseEntity.ok().build();
//    }
@Transactional
    public void newError(Machine machine, String operation, String message) {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        ErrorMessage errorMessage = new ErrorMessage(null, machine, user,
                operation,message, new Date());
        errorMessageRepository.save(errorMessage);
    }

    public List<ErrorMessage> getAllByUser() {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        return errorMessageRepository.findAllByUser(user);
    }
    @Override
    public ErrorMessage save(ErrorMessage errorMessage) {
        return errorMessageRepository.save(errorMessage);
    }

    @Override
    public Optional<ErrorMessage> findById(Integer var1) {
        return Optional.empty();
    }

    @Override
    public List<ErrorMessage> findAll() {
        return null;
    }

    @Override
    public void deleteById(Integer var1) {

    }
}
