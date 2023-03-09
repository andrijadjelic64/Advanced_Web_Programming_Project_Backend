package com.example.nwp_projekat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "permissions")
@Getter
@Setter
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id")
    private Integer permissionId;

    @Column(name = "permission")
    private String permission;

    @ManyToMany
    @JoinTable(
            name = "permissions_users",
            joinColumns = @JoinColumn(name = "pru_per_id", referencedColumnName = "permission_id"),
            inverseJoinColumns = @JoinColumn(name = "pru_usr_id", referencedColumnName = "user_id")
    )
    @JsonIgnore
    private List<User> users = new ArrayList<>();



    public Permission() {

    }

    public Permission(Integer permissionId, String permission) {
        this.permissionId = permissionId;
        this.permission = permission;
    }

    //    @ManyToMany(mappedBy = "groups", targetEntity = Student.class)
//    private List<Student> students;
}
