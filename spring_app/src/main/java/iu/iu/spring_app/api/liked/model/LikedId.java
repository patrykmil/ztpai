package iu.iu.spring_app.api.liked.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class LikedId implements Serializable {
    private Integer userID;
    private Integer componentID;
}
