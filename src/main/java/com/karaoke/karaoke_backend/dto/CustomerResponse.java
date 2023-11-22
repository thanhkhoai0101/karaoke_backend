package com.karaoke.karaoke_backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Builder
public class CustomerResponse {
    private Long id;
    private String name;
    private String phoneNumber;
    private String avatar;
    private Instant createdAt;
}
