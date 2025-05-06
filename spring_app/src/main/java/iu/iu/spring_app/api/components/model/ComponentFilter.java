package iu.iu.spring_app.api.components.model;


import lombok.Data;

import java.util.Map;

@Data
public class ComponentFilter {
    String query;
    Map<String, Boolean> types;
    String sorting;
}
