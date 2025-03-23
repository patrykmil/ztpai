package iu.iu.spring_app.components.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "color")
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "color_id")
    private Integer id;

    private String hex;
}
