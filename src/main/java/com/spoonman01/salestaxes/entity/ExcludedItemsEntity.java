package com.spoonman01.salestaxes.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="excluded_taxes")
public class ExcludedItemsEntity {

    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "description")
    private String description;

    @ManyToOne
    //@JoinColumn(name="id", nullable=false)
    private ExcludedCategoryEntity category;
}
