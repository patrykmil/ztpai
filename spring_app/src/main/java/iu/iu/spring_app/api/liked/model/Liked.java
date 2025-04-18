package iu.iu.spring_app.api.liked.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "liked")
@Data
@IdClass(LikedId.class)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Liked {
    @Id
    @Column(name = "user_id")
    private Integer userID;

    @Id
    @Column(name = "component_id")
    private Integer componentID;

    @Column(name = "liked_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    @Builder.Default
    private Timestamp likedAt = new Timestamp(System.currentTimeMillis());
}
