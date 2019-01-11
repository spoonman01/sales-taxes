package com.spoonman01.salestaxes.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="excluded_category")
public class ExcludedCategoryEntity {

    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy="category")
    private Set<ExcludedItemsEntity> items;
}
