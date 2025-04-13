package iu.iu.spring_app.api.components.model;

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

    @Column(nullable = false, length = 6)
    private String hex;
}
