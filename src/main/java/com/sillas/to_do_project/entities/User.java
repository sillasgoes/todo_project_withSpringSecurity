package com.sillas.to_do_project.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "tb_user")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private long user_id;


    private String username;

    private String password;

    private Set<Role> role;
}
