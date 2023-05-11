package com.tms.lingofriends.service;

import com.tms.lingofriends.model.EducationProduct;
import com.tms.lingofriends.repository.EducationProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class EducationProductService {
    EducationProductRepository educationProductRepository;

    @Autowired
    public EducationProductService(EducationProductRepository educationProductRepository) {
        this.educationProductRepository = educationProductRepository;
    }

    public ArrayList<EducationProduct> getAllEducationProduct() {
        return (ArrayList<EducationProduct>) educationProductRepository.findAll();
    }

    public EducationProduct getEducationProductById(int id) {
        return educationProductRepository.findById(id).orElse(new EducationProduct());
    }

    public EducationProduct createEducationProduct(EducationProduct educationProduct) {
        return educationProductRepository.save(educationProductRepository);
    }

    public EducationProduct updateEducationProduct(EducationProduct educationProduct) {
        return educationProductRepository.saveAndFlush(educationProductRepository);
    }
   /* public boolean deleteEducationProduct(int id) {
        return educationProductRepository.deleteById(id);
    }*/
}

