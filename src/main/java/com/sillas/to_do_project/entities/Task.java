package com.sillas.to_do_project.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "tb_task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String task_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String content;

    @CreationTimestamp
    private Instant creationTimestamp;

    @Getter
    public enum Status{

        OPEN(1L),
        CLOSE(2L),
        FROZEN(3L);

        final long status_id;

        Status(long status_idId) {
            status_id = status_idId;
        }

    }
}
