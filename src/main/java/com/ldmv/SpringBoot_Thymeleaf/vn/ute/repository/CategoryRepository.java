package com.ldmv.SpringBoot_Thymeleaf.vn.ute.repository;

import com.ldmv.SpringBoot_Thymeleaf.vn.ute.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByNameContaining(String keyword);
    Page<Category> findByNameContaining(String keyword, Pageable pageable);
    Optional<Category> findByName(String name);
}