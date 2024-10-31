package com.ldmv.SpringBoot_Thymeleaf.vn.ute.service;

import com.ldmv.SpringBoot_Thymeleaf.vn.ute.entity.Category;
import com.ldmv.SpringBoot_Thymeleaf.vn.ute.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CatergoryServiceImpl implements CategoryService{
    @Autowired
    CategoryRepository CategoryRepository;

    @Override
    public List<Category> findAll() {
        return CategoryRepository.findAll();
    }

    @Override
    public List<Category> findAll(Sort sort) {
        return CategoryRepository.findAll(sort);
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return CategoryRepository.findAll(pageable);
    }

    @Override
    public Optional<Category> findById(Long aLong) {
        return CategoryRepository.findById(aLong);
    }

    @Override
    public Optional<Category> findByNameContaining(String keyword) {
        return CategoryRepository.findByNameContaining(keyword);
    }

    @Override
    public Page<Category> findByNameContaining(String keyword, Pageable pageable) {
        return CategoryRepository.findByNameContaining(keyword, pageable);
    }

    @Override
    public <S extends Category> S save(S entity) {
        return CategoryRepository.save(entity);
    }

    @Override
    public long count() {
        return CategoryRepository.count();
    }

    @Override
    public void deleteById(Long aLong) {
        CategoryRepository.deleteById(aLong);
    }

    @Override
    public Optional<Category> findByName(String name) {
        return Optional.ofNullable(CategoryRepository.findByName(name)
                .orElse(null));
    }
}
