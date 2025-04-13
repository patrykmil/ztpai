package iu.iu.spring_app.api.components.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Integer id;

    @Column(name = "tag_name", nullable = false, unique = true, length = 30)
    private String name;

    @ManyToOne
    @JoinColumn(name = "color_id", referencedColumnName = "color_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Color color;

    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Component> components = new HashSet<>();

}
