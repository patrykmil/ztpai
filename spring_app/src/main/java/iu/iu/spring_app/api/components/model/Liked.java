package iu.iu.spring_app.api.components.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "liked")
@Data
@IdClass(LikedId.class)
public class Liked {
    @Id
    @Column(name = "user_id")
    private Integer userID;

    @Id
    @Column(name = "component_id")
    private Integer componentID;

    @Column(name = "liked_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    private Timestamp likedAt;

}
