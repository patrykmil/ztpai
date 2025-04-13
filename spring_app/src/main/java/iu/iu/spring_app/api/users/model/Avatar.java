package iu.iu.spring_app.api.users.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "avatar")
public class Avatar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "avatar_id")
    private Integer avatarID;

    @Column(nullable = false)
    private String avatarPath;
}