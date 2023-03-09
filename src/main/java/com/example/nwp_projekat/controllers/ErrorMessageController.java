package com.example.nwp_projekat.controllers;

import com.example.nwp_projekat.model.ErrorMessage;
import com.example.nwp_projekat.model.Machine;
import com.example.nwp_projekat.requests.error_message.CreateErrorMessage;
import com.example.nwp_projekat.requests.machine.MachineCreateRequest;
import com.example.nwp_projekat.services.ErrorMessageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/errors")
public class ErrorMessageController {

    private final ErrorMessageService errorMessageService;

    public ErrorMessageController(ErrorMessageService errorMessageService) {
        this.errorMessageService = errorMessageService;
    }


    @GetMapping(value = "/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ErrorMessage> getAllErrors(){

        return errorMessageService.getAllByUser();
    }

//    @PostMapping(value = "/create",
//            consumes = MediaType.APPLICATION_JSON_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> createError(@RequestBody CreateErrorMessage createErrorMessage){
//        return ResponseEntity.ok(errorMessageService.createError(createErrorMessage));
//    }
}
