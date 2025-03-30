package com.utd.ti.soa.ebs_service.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cliente {
    private String name;
    private String last_name;
    private String email;
    private String phone;
    private String birth_date;
    private String address;
}