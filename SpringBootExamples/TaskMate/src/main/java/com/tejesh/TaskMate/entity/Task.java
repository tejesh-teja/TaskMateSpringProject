package com.tejesh.TaskMate.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="task")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name ="taskname", nullable = false)
    private String taskName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Users users;
}
