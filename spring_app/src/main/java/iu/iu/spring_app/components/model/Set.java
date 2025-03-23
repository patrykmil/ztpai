package iu.iu.spring_app.components.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "set")
public class Set {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "set_id")
    private Integer id;

    @Column(name = "set_name")
    private String name;

    private Integer user_id;
}
