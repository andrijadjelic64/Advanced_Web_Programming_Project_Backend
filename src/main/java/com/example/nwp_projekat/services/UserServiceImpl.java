package com.example.nwp_projekat.services;


import com.example.nwp_projekat.model.Permission;
import com.example.nwp_projekat.model.User;
import com.example.nwp_projekat.repositories.PermissionRepository;
import com.example.nwp_projekat.repositories.UserRepository;
import com.example.nwp_projekat.requests.user.UserCreateRequest;
import com.example.nwp_projekat.requests.user.UserUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements IService<User,Integer>{

    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PermissionRepository permissionRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    @PreAuthorize("hasAnyAuthority('can_update_users')")
    public ResponseEntity<User> updateUser(User originalUser, UserUpdateRequest updatedUser){
        originalUser.setFirstName(updatedUser.getFirstName());
        originalUser.setLastName(updatedUser.getLastName());
        originalUser.setEmail(updatedUser.getEmail());

        List<Permission> permissionsList=  verifyPermissions(updatedUser.getPermissions());
        if(permissionsList==null){
            return ResponseEntity.badRequest().build();
        }
        originalUser.setPermissions(permissionsList);
        return ResponseEntity.ok(userRepository.save(originalUser));
    }

    @PreAuthorize("hasAnyAuthority('can_create_users')")
    public User makeUserBeforeDatabase(UserCreateRequest userCreateRequest){
        List<String> permissions = userCreateRequest.getPermissions();
        System.out.println("Hello from makeUserBeforeDatabase");
        List<Permission> permissionsList=  verifyPermissions(permissions);
        if(permissionsList==null){
            return null;
        }

        User user = new User();
        user.setFirstName(userCreateRequest.getFirstName());
        user.setLastName(userCreateRequest.getLastName());
        user.setEmail(userCreateRequest.getEmail());
        user.setPassword(userCreateRequest.getPassword());
        user.setPermissions(permissionsList);
        return user;
    }
    @Transactional
    List<Permission> verifyPermissions(List<String> permissionStrings){
        List<Permission> permissions = new ArrayList<>();
        for (String p:permissionStrings) {
            Permission p1 = permissionRepository.findByPermission(p);
            if(p1==null){
                return null;
            }
            permissions.add(p1);
        }
        return permissions;
    }
    @Transactional
    @Override
    @PreAuthorize("hasAnyAuthority('can_create_users','can_update_users')")
    public User save(User user) {
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        return userRepository.save(user);
    }
    @Transactional
    @Override
    @PreAuthorize("hasAnyAuthority('can_read_users')")
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }
    @Transactional
    @Override
    @PreAuthorize("hasAnyAuthority('can_read_users')")
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }
    @Transactional
    @Override
    @PreAuthorize("hasAnyAuthority('can_delete_users')")
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    @Transactional
    @PreAuthorize("hasAnyAuthority('can_read_users')")
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }
}
