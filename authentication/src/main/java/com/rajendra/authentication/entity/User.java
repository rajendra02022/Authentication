package com.rajendra.authentication.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = "username", name = "UK_USER_USERNAME"),
    @UniqueConstraint(columnNames = "email", name = "UK_USER_EMAIL") // Ensure email is unique
})
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @NaturalId
    @NotBlank(message = "Username is mandatory")
    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @NotBlank(message = "Email is mandatory")
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
}

