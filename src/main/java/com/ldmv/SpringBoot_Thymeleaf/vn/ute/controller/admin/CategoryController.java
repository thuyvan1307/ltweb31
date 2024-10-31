package com.ldmv.SpringBoot_Thymeleaf.vn.ute.controller.admin;

import com.ldmv.SpringBoot_Thymeleaf.vn.ute.entity.Category;
import com.ldmv.SpringBoot_Thymeleaf.vn.ute.model.CategoryModel;
import com.ldmv.SpringBoot_Thymeleaf.vn.ute.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("")
    public String findAllCate(Model model) {
        Page<Category> categoryPage = new PageImpl<>(categoryService.findAll());
        model.addAttribute("categoryPage", categoryPage);
        return "admin/categorylistpage";
    }

    @GetMapping("/add")
    public String addCategory(Model model){
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setIsEdit(false);
        model.addAttribute("category", categoryModel);
        return "admin/categoryadd";
    }

    @PostMapping("/save")
    public ModelAndView saveOrUpdate(ModelMap model,
                                     @ModelAttribute("category") CategoryModel cateModel
                                     ) {

        Category entity = new Category();
        BeanUtils.copyProperties(cateModel, entity);

        String message = "";
        if(categoryService.findByName(entity.getName()).isPresent()){

            message = "Categoryname already exists!";
        }
        else {
            categoryService.save(entity);
            if (cateModel.getIsEdit()) {
                message = "Category is Edited!";
            } else {
                message = "Category is Saved!";
            }
        }

        model.addAttribute("message", message);
        return new ModelAndView("forward:/admin/categories", model);
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(ModelMap model, @PathVariable("id") Long categoryId) {
        Optional<Category> optCategory = categoryService.findById(categoryId);
        CategoryModel cateModel = new CategoryModel();

        if (optCategory.isPresent()) {
            Category entity = optCategory.get();
            BeanUtils.copyProperties(entity, cateModel);
            cateModel.setIsEdit(true);
            model.addAttribute("category", cateModel);
            return new ModelAndView("admin/categoryadd", model);
        }
        model.addAttribute("message", "Category does not exist!");
        return new ModelAndView("forward:/admin/categories", model);
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(ModelMap model, @PathVariable("id") Long categoryId) {
        categoryService.deleteById(categoryId);
        model.addAttribute("message", "Category is deleted!");
        return new ModelAndView("forward:/admin/categories", model);
    }

    @RequestMapping("/searchpaginated")
    public String search(ModelMap model,
                         @RequestParam(name = "name", required = false) String name,
                         @RequestParam("page") Optional<Integer> page,
                         @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(3);
        currentPage = Math.max(currentPage, 1);

        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("name"));
        Page<Category> resultPage = StringUtils.hasText(name) ?
                categoryService.findByNameContaining(name, pageable) :
                categoryService.findAll(pageable);

        model.addAttribute("name", name);
        model.addAttribute("categoryPage", resultPage != null ? resultPage : new PageImpl<>(new ArrayList<>()));

        int totalPages = resultPage.getTotalPages();
        if (totalPages > 0) {
            int start = Math.max(1, currentPage - 2);
            int end = Math.min(currentPage + 2, totalPages);

            List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "admin/categorylistpage";
    }
}
