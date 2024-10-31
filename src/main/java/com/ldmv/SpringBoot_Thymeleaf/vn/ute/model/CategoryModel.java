package com.ldmv.SpringBoot_Thymeleaf.vn.ute.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryModel implements Serializable {
    @Id
    private Long id;
    private String name;
    private String images;
    private int status;
    private Boolean isEdit = false;
}