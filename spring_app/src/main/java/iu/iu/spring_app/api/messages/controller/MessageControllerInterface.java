package iu.iu.spring_app.api.messages.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import iu.iu.spring_app.api.errors.ExceptionResponse;
import iu.iu.spring_app.api.messages.model.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/messages")
public interface MessageControllerInterface {
    @Operation(summary = "Get all messages for user", description = "Retrieves all user messages")
    @ApiResponse(responseCode = "200", description = "Message found", content = @Content(schema = @Schema(implementation = Message.class)))
    @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @GetMapping
    ResponseEntity<List<Message>> getUserMessages(Authentication authentication);

    @Operation(summary = "Add new message for user", description = "Admin adds new message for user")
    @ApiResponse(responseCode = "200", description = "Message added",
            content = @Content(schema = @Schema(implementation = Message.class)))
    @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Message> addMessage(@RequestBody Message payload);
}
