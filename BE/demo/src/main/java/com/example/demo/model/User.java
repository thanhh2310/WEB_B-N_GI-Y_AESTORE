package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.Set;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "User")
@EqualsAndHashCode(exclude = {"cart", "roles"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private Integer userId;

    @Column(name = "Username", nullable = false, unique = true)
    private String username;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "Email", nullable = false, unique = true)
    private String email;

    @Column(name = "Phone")
    private String phone;

    @Column(name = "Status")
    private Boolean status = true;

    @Column(name = "DoB")
    private Date dob;

    @Column(name = "Gender")
    private Character gender;

    @Column(name = "FullName")
    private String fullName;

    @ManyToMany
    @JoinTable(
            name = "UserRole",
            joinColumns = @JoinColumn(name = "UserID"),
            inverseJoinColumns = @JoinColumn(name = "RoleID")
    )
    private Set<Role> roles;

    @OneToMany(mappedBy = "user")
    private Set<Address> addresses;

     @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
   @JsonBackReference
    private Cart cart;

    @OneToMany(mappedBy = "user")
    private Set<Order> orders;

    @OneToMany(mappedBy = "user")
    private Set<Review> reviews;
    private boolean active = true;
}
