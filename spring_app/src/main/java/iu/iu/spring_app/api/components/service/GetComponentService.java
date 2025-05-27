package iu.iu.spring_app.api.components.service;

import iu.iu.spring_app.api.components.model.Component;
import iu.iu.spring_app.api.components.model.ComponentFilter;
import iu.iu.spring_app.api.components.repository.ComponentRepository;
import iu.iu.spring_app.api.errors.ResourceNotFoundException;
import iu.iu.spring_app.api.users.service.GetUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetComponentService {
    private final ComponentRepository componentRepository;
    private final ValidationService validationService;
    private final SearchService searchService;
    private final GetUserService getUserService;

    public GetComponentService(ComponentRepository componentRepository,
                               ValidationService validationService,
                               SearchService searchService, GetUserService getUserService) {
        this.componentRepository = componentRepository;
        this.validationService = validationService;
        this.searchService = searchService;
        this.getUserService = getUserService;
    }

    public Page<Component> getAllComponents(int page, int size, String sort) {
        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        Sort.Direction direction = sortParams.length > 1 && sortParams[1].equalsIgnoreCase("desc") ?
                Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
        return componentRepository.findAll(pageable)
                .map(component -> {
                    validationService.unescapeComponent(component);
                    return component;
                });
    }

    public Component getComponentById(Integer id) {
        Component component = componentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Component not found"));

        validationService.unescapeComponent(component);

        return component;
    }

    public List<Component> getFilteredComponents(ComponentFilter filter) {
        return componentRepository.findAll().stream()
                .filter(component -> searchService.filterByType(component, filter.getTypes()))
                .filter(component -> searchService.matchesSearchQuery(component, filter.getQuery()))
                .peek(validationService::unescapeComponent)
                .sorted((c1, c2) -> searchService.sortComponents(c1, c2, filter.getSorting()))
                .collect(Collectors.toList());
    }

    public List<Component> getLikedByUserComponents(String username) {
        Integer userId = getUserService.getUserByUsername(username).getId();
        return componentRepository.findAllLikedByUserId(userId)
                .stream()
                .peek(validationService::unescapeComponent)
                .collect(Collectors.toList());
    }

    public List<Component> getSetComponents(Integer setId) {
            return componentRepository.findAllBySetId(setId)
                    .stream()
                    .peek(validationService::unescapeComponent)
                    .collect(Collectors.toList());
    }
}