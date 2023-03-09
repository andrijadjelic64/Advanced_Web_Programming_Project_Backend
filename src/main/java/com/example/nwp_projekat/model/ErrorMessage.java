package com.example.nwp_projekat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "error_message")
@Getter
@Setter
public class ErrorMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "error_id")
    private Integer errorId;

    @ManyToOne
    @JoinColumn(name = "machine_id", referencedColumnName = "machine_id")
    private Machine machine;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Column
    private String operation;

    @Column
    private String message;

    @Column
    private Date date;

    public ErrorMessage(Integer errorId, Machine machine, User user, String operation, String message, Date date) {
        this.errorId = errorId;
        this.machine = machine;
        this.user = user;
        this.operation = operation;
        this.message = message;
        this.date = date;
    }

    public ErrorMessage() {
    }
}

