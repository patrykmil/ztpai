package iu.iu.spring_app.components.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Integer id;
    @Column(name = "tag_name")
    private String name;
    @Column(name = "color_id")
    private Integer color_id;
}
