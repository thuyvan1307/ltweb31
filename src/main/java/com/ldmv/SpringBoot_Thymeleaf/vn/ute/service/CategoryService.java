package com.ldmv.SpringBoot_Thymeleaf.vn.ute.service;

import com.ldmv.SpringBoot_Thymeleaf.vn.ute.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> findAll();

    List<Category> findAll(Sort sort);

    Page<Category> findAll(Pageable pageable);

    Optional<Category> findById(Long aLong);

    Optional<Category> findByNameContaining(String keyword);

    Page<Category> findByNameContaining(String keyword, Pageable pageable);

    <S extends Category> S save(S entity);

    long count();

    void deleteById(Long aLong);

    Optional<Category> findByName(String name);
}
