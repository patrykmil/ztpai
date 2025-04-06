package iu.iu.spring_app.components.model;

import iu.iu.spring_app.users.model.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Timestamp;
import java.util.HashSet;

@Data
@Entity
@Table(name = "component")
public class Component {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "component_id")
    private Integer id;

    @Column(name = "component_name", nullable = false, length = 30)
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User author;

    @ManyToOne
    @JoinColumn(name = "color_id", referencedColumnName = "color_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Color color;

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "type_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Type type;

    @ManyToOne
    @JoinColumn(name = "set_id", referencedColumnName = "set_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Set set;

    @Column(columnDefinition = "TEXT")
    private String html;

    @Column(columnDefinition = "TEXT")
    private String css;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    private Timestamp createdAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "component_tag",
            joinColumns = @JoinColumn(name = "component_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private java.util.Set<Tag> tags = new HashSet<>();

}
