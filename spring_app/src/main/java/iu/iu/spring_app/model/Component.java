package iu.iu.spring_app.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "component_details_view")
public class Component {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "component_id")
    private Integer id;

    @Column(name = "component_name")
    private String name;

    @Column(name = "user_id")
    private Integer userId;

    private String username;

    private String hex;

    @Column(name = "type_name")
    private String typeName;

    @Column(name = "set_name")
    private String setName;

    @Column(name = "created_at")
    private String createdAt;

    private Integer likes;

    private String tags;

    private String html;

    private String css;
}
