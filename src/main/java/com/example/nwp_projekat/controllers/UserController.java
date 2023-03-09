package com.example.nwp_projekat.controllers;


import com.example.nwp_projekat.model.User;
import com.example.nwp_projekat.requests.user.UserCreateRequest;
import com.example.nwp_projekat.requests.user.UserUpdateRequest;
import com.example.nwp_projekat.services.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserServiceImpl userServiceImpl;


    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping(value = "/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers(){
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        return userServiceImpl.findAll();
    }

    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchUser(@PathVariable("id") Integer id){
        Optional<User> user = userServiceImpl.findById(id);
        if(user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody UserCreateRequest userCreateRequest){

        Optional<User> existingUser = userServiceImpl.findByEmail(userCreateRequest.getEmail());

        if(existingUser.isPresent()) {
            return new ResponseEntity<>("Account mail exist", HttpStatus.BAD_REQUEST);
        }

        User user = userServiceImpl.makeUserBeforeDatabase(userCreateRequest);

        if(user==null){
            return ResponseEntity.badRequest().build();
        }else{
            return ResponseEntity.ok(userServiceImpl.save(user));
        }
    }

    @PutMapping(value = "/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateRequest userUpdateRequest){
        Optional<User> user = userServiceImpl.findById(userUpdateRequest.getUserId());
        if(user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return userServiceImpl.updateUser(user.get(), userUpdateRequest);
//         userServiceImpl.save(userRequest);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Integer id){
        userServiceImpl.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
