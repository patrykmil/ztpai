package iu.iu.spring_app.api.administration.model;

import lombok.Data;

@Data
public class DeleteComponentModel {
    Integer componentId;
    String reason;
}
