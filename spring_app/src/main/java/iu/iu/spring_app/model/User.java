package iu.iu.spring_app.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "iuser", schema = "public")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    private String email;

    private String username;

    @Column(name = "password_hash")
    private String password;

    private Boolean admin = false;
}