package com.karaoke.karaoke_backend.model;

import com.karaoke.karaoke_backend.dto.EmployeeResponse;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 255)
    @Column(name = "full_name")
    private String fullName;

    @Size(max = 255)
    @Column(name = "avatar")
    private String avatar;

    @Size(max = 255)
    @Column(name = "username")
    private String username;

    @Size(max = 255)
    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    public EmployeeResponse toEmployeeResponse(){
        return EmployeeResponse.builder()
                .phoneNumber(getPhoneNumber())
                .avatar(getAvatar())
                .name(getFullName())
                .build();
    }
}
