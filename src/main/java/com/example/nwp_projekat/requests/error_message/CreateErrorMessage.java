package com.example.nwp_projekat.requests.error_message;

import com.example.nwp_projekat.model.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
@Data
public class CreateErrorMessage {

    private Integer machineId;

    private String operation;

    private String message;

}
