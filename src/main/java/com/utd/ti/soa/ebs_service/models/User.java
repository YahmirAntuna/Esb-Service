package com.utd.ti.soa.ebs_service.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String username;
    private String password;
    private String phone;
}