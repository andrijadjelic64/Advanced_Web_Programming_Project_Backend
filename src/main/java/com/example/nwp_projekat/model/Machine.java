package com.example.nwp_projekat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "machines")
@Getter
@Setter
public class Machine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "machine_id")
    private Integer machineId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "user_id")
    @JsonIgnore
    private User createdBy;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @OneToMany(mappedBy = "machine")
    @JsonIgnore
    private List<ErrorMessage> errorMessages = new ArrayList<>();

    public Machine(Integer machineId, String name, String status, User createdBy, Boolean active) {
        this.machineId = machineId;
        this.name = name;
        this.status = status;
        this.createdBy = createdBy;
        this.active = active;
    }

    public Machine() {

    }
}
