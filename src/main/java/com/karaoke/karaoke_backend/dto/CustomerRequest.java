package com.karaoke.karaoke_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequest {
    private String id;
    private String name;
    private String avatar;
    private String phoneNumber;
}
