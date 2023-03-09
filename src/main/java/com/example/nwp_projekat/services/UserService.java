package com.example.nwp_projekat.services;

import com.example.nwp_projekat.model.Permission;
import com.example.nwp_projekat.model.User;
import com.example.nwp_projekat.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



@Service
public class UserService implements UserDetailsService{

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User myUser = this.userRepository.findByEmail(email);
        if(myUser == null) {
            throw new UsernameNotFoundException("Email "+email+" not found");
        }
//        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        myUser.getPermissions().forEach(permission -> {
            authorities.add(new SimpleGrantedAuthority(permission.getPermission()));
//            System.out.println("authorities " + authorities);
        });
        return new org.springframework.security.core.userdetails.User(myUser.getEmail(), myUser.getPassword(), authorities);
    }
    public List<String> getPermissionsFromEmail(String email){
        User myUser = this.userRepository.findByEmail(email);
        List<String> permissions = new ArrayList<>();
        for (Permission per:myUser.getPermissions()) {
            permissions.add(per.getPermission());
        }

        return permissions;
    }
}
