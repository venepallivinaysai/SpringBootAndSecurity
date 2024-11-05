package com.springBlog.SpringBlog.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Column(nullable = false, unique = true)
    private String username;
    private String password;
    @Column(nullable = false, unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles"
            ,joinColumns = @JoinColumn (columnDefinition = "user_id", referencedColumnName ="id")
            ,inverseJoinColumns = @JoinColumn( columnDefinition = "roles_id", referencedColumnName = "id")
    )
    private Set<Role> roles;

}
