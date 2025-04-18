package iu.iu.spring_app.api.components.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import iu.iu.spring_app.api.users.model.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
@Table(name = "set")
public class Set {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "set_id")
    private Integer id;

    @Column(name = "set_name", nullable = false, length = 30)
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JsonIgnore
    private User user;

}
