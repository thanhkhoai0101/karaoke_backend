package com.karaoke.karaoke_backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Getter
@Setter
public class EmployeeResponse {
    private String name;
    private String avatar;
    private String phoneNumber;
}
