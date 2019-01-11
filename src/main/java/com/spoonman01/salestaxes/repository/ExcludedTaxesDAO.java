package com.spoonman01.salestaxes.repository;

import com.spoonman01.salestaxes.entity.ExcludedItemsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExcludedTaxesDAO extends CrudRepository<ExcludedItemsEntity, Integer> {

    //Returns true if a given substring is present in the ExcludedItems table (books, food, and medical products)
    boolean existsByDescriptionContaining(String searchString);
}
