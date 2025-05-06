package iu.iu.spring_app.api.components.service;

import iu.iu.spring_app.api.components.model.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SearchService {
    public boolean filterByType(Component component, Map<String, Boolean> types) {
        if (types == null || types.isEmpty()) return true;
        return types.getOrDefault(component.getType().getName().toLowerCase(), false);
    }

    public boolean matchesSearchQuery(Component component, String query) {
        if (query == null || query.trim().isEmpty()) return true;

        String[] searchTerms = query.toLowerCase().split("\\s+");
        String searchText = String.join(" ",
                component.getName().toLowerCase(),
                component.getAuthor().getName().toLowerCase(),
                component.getSet() != null ? component.getSet().getName().toLowerCase() : "",
                component.getTags().stream()
                        .map(tag -> tag.getName().toLowerCase())
                        .collect(Collectors.joining(" "))
        );

        return Arrays.stream(searchTerms)
                .allMatch(searchText::contains);
    }

    public int sortComponents(Component c1, Component c2, String sorting) {
        return switch (sorting.toLowerCase()) {
            case "most likes" -> c2.getLikesCount().compareTo(c1.getLikesCount());
            case "newest" -> c2.getCreatedAt().compareTo(c1.getCreatedAt());
            case "oldest" -> c1.getCreatedAt().compareTo(c2.getCreatedAt());
            default -> 1;
        };
    }
}
