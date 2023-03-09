package com.example.nwp_projekat.requests.machine;

import com.example.nwp_projekat.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class MachineCreateRequest {

    private String name;

    private Boolean active;
}
