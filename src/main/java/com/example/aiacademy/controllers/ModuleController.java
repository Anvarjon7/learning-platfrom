package com.example.aiacademy.controllers;

import com.example.aiacademy.dto.ModuleRequest;
import com.example.aiacademy.services.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/courses/{courseId}/modules")
@RequiredArgsConstructor
public class ModuleController {

    private final ModuleService moduleService;

    @PostMapping
    public ResponseEntity<?> create(@PathVariable Long courseId,
                                    @RequestBody ModuleRequest request,
                                    Principal principal) {

        return ResponseEntity.ok(
                moduleService.createModule(courseId, principal.getName(), request)
        );
    }

    @GetMapping
    public ResponseEntity<?> getByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(
                moduleService.getModulesByCourse(courseId)
        );
    }

    @PutMapping("/{moduleId}")
    public ResponseEntity<?> update(@PathVariable Long moduleId,
                                    Principal principal,
                                    @RequestBody ModuleRequest request) {
        return ResponseEntity.ok(
                moduleService.update(moduleId, principal.getName(), request)
        );
    }

    @DeleteMapping("/{moduleId}")
    public ResponseEntity<?> delete(@PathVariable Long moduleId,
                                    Principal principal) {
        moduleService.delete(moduleId, principal.getName());

        return ResponseEntity.noContent().build();
    }
}
