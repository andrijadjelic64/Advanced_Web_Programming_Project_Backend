package com.example.nwp_projekat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email",unique = true)
    private String email;

    @Column(name = "password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "permissions_users",
            joinColumns = @JoinColumn(name = "pru_usr_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "pru_per_id", referencedColumnName = "permission_id")
    )
    private List<Permission> permissions = new ArrayList<>();

    @OneToMany(mappedBy = "createdBy")
    @JsonIgnore
    private List<Machine> machines = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<ErrorMessage> errorMessages = new ArrayList<>();



    public User(Integer userId, String firstName, String lastName, String email, String password, List<Permission> permissions) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.permissions.addAll(permissions);
    }

    public User() {
    }

    public void addPermission(Permission permission) {
        permissions.add(permission);
        permission.getUsers().add(this);
    }

    public void removePermission(Permission permission) {
        permissions.remove(permission);
        permission.getUsers().remove(this);
    }
}
