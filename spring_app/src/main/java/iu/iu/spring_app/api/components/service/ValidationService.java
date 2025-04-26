package iu.iu.spring_app.api.components.service;

import iu.iu.spring_app.api.components.model.Component;
import iu.iu.spring_app.api.components.model.Tag;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ValidationService {
    private static final int MAX_NAME_LENGTH = 30;
    private static final int MAX_HTML_LENGTH = 5000;
    private static final int MAX_CSS_LENGTH = 5000;
    private static final int MAX_TAGS = 10;

    public void validateComponent(Component payload) {
        if (payload == null || StringUtils.isBlank(payload.getName())) {
            throw new IllegalArgumentException("Invalid payload");
        }
        if (payload.getName().length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("Name too long");
        }
        if (payload.getHtml().length() > MAX_HTML_LENGTH) {
            throw new IllegalArgumentException("HTML too long");
        }
        if (payload.getCss().length() > MAX_CSS_LENGTH) {
            throw new IllegalArgumentException("CSS too long");
        }
    }

    public String sanitizeInput(String input) {
        if (input == null) return null;
        return StringUtils.trim(StringEscapeUtils.escapeHtml4(input));
    }

    public String sanitizeHtml(String html) {
        if (html == null) return null;
        return StringEscapeUtils.escapeHtml4(html);
    }

    public String sanitizeCss(String css) {
        if (css == null) return null;
        return StringEscapeUtils.escapeHtml4(css)
                .replaceAll("javascript:", "")
                .replaceAll("expression\\s*\\(", "")
                .replaceAll("@import", "");
    }

    public boolean isValidHexColor(String hex) {
        return hex != null && hex.matches("^#?[0-9A-F]{6}$");
    }

    public void validateTags(Set<Tag> tags) {
        if (tags.size() > MAX_TAGS) {
            throw new IllegalArgumentException("Too many tags");
        }
        for (Tag tag : tags) {
            if (tag == null || tag.getName() == null) {
                throw new IllegalArgumentException("Invalid tag");
            }
        }
    }

    public void unescapeComponent(Component component) {
        component.setName(StringEscapeUtils.unescapeHtml4(component.getName()));
        component.setHtml(StringEscapeUtils.unescapeHtml4(component.getHtml()));
        component.setCss(StringEscapeUtils.unescapeHtml4(component.getCss()));
        if (component.getTags() != null) {
            component.getTags().forEach(tag ->
                    tag.setName(StringEscapeUtils.unescapeHtml4(tag.getName()))
            );
        }
    }
}