package com.github.userservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @Column(name = "UserId")
    private String userId;
    @Column(name = "Name")
    private String name;
    @Column(name = "Email", unique = true)
    private String email;
    @Column(name = "About")
    private String about;

    @Transient
    private List<Rating> ratings = new ArrayList<>();

}
