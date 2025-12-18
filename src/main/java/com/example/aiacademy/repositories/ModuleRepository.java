package com.example.aiacademy.repositories;

import com.example.aiacademy.models.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface ModuleRepository extends JpaRepository<Module,Long> {

    List<Module> findByCourseIdOrderById(Long courseId);
}
