package com.example.test.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.test.models.PropertyModel;
import com.example.test.repositories.PropertyRepository;

@Service
public class PropertyService {

    @Autowired
    PropertyRepository propertyRepository;

    public Optional<PropertyModel> getPropertyById(Long id) {
        return propertyRepository.findById(id);
    }

}
