package com.karaoke.karaoke_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeRequest {
    private String fullName;
    private String avatar;
    private String username;
    private String password;
    private String phoneNumber;
}
