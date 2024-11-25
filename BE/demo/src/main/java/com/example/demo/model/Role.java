package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "Role")
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RoleID")
    private Integer roleId;
    
    @Column(name = "Name", nullable = false, unique = true)
    private String name;

  
}
