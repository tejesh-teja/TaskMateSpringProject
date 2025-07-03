package com.tejesh.TaskMate.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email"}),
        @UniqueConstraint(columnNames = {"name"})
})

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name ="name", nullable = false)
    private String name;

    @Column(name ="email", nullable = false)
    private String email;

    @Column(name ="password", nullable = false)
    private String password;

}
