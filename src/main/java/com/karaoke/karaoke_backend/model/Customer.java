package com.karaoke.karaoke_backend.model;

import com.karaoke.karaoke_backend.dto.CustomerResponse;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 255)
    @Column(name = "name")
    private String name;

    @Size(max = 15)
    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Size(max = 255)
    @Column(name = "avatar")
    private String avatar;

    public CustomerResponse toCustomerResponse(){
        return CustomerResponse.builder()
                .avatar(getAvatar())
                .createdAt(getCreatedAt())
                .phoneNumber(getPhoneNumber())
                .id(getId())
                .name(getName())
                .build();
    }
}
