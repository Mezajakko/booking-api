package com.example.test.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.test.models.PropertyModel;

@Repository
public interface PropertyRepository extends CrudRepository<PropertyModel, Long> {

}
