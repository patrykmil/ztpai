package iu.iu.spring_app.api.administration.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import iu.iu.spring_app.api.administration.model.DeleteComponentModel;
import iu.iu.spring_app.api.components.model.Component;
import iu.iu.spring_app.api.errors.ExceptionResponse;
import iu.iu.spring_app.api.users.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/admin")
public interface AdminControllerInterface {
    @Operation(summary = "Delete component by admin", description = "Deletes a specific component with admin privileges")
    @ApiResponse(responseCode = "200", description = "Component deleted successfully", content = @Content(schema = @Schema(implementation = Component.class)))
    @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "Component not found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Delete component model", required = true, content = @Content(schema = @Schema(implementation = DeleteComponentModel.class)))
    @DeleteMapping("/components")
    ResponseEntity<Component> deleteComponentAdmin(@RequestBody DeleteComponentModel deleteComponentModel);

    @Operation(summary = "Ban user by admin", description = "Bans a specific user with admin privileges")
    @ApiResponse(responseCode = "200", description = "User banned successfully", content = @Content(schema = @Schema(implementation = User.class)))
    @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @DeleteMapping("/users/{userId}")
    ResponseEntity<User> banUserAdmin(@PathVariable Integer userId);
}
