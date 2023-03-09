package com.example.nwp_projekat.requests.machine;

import com.example.nwp_projekat.model.User;

import lombok.Data;


@Data
public class MachineUpdateRequest {
    private Integer machineId;

    private String name;

}
