package iu.iu.spring_app.components.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "type")
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    private Integer id;
    @Column(name = "type_name")
    private String name;
}
