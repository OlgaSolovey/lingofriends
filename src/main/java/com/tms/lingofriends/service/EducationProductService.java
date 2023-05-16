package com.tms.lingofriends.service;

import com.tms.lingofriends.model.EducationProduct;
import com.tms.lingofriends.model.Image;
import com.tms.lingofriends.repository.EducationProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

@Service
@Slf4j
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

    public EducationProduct createEducationProduct(EducationProduct educationProduct, MultipartFile file1,
                                                   MultipartFile file2, MultipartFile file3, MultipartFile file4,
                                                   MultipartFile file5) throws IOException {
        Image image1;
        Image image2;
        Image image3;
        Image image4;
        Image image5;

        if (file1.getSize() != 0) {
            image1 = toImageEntity(file1);
            image1.setPreviewImage(true);
            educationProduct.addImageToProduct(image1);
        }
        if (file2.getSize() != 0) {
            image2 = toImageEntity(file2);
            educationProduct.addImageToProduct(image2);
        }
        if (file3.getSize() != 0) {
            image3 = toImageEntity(file3);
            educationProduct.addImageToProduct(image3);
        }
        if (file4.getSize() != 0) {
            image4 = toImageEntity(file4);
            educationProduct.addImageToProduct(image4);
        }
        if (file5.getSize() != 0) {
            image5 = toImageEntity(file5);
            educationProduct.addImageToProduct(image5);
        }


        log.info("Saving new Education Product. Education name:{}", educationProduct.getEdProductName());
        EducationProduct educationProductFromDb = educationProductRepository.save(educationProduct);
        educationProductFromDb.setPreviewImageId(educationProductFromDb.getImages().get(0).getId());
        return educationProductRepository.save(educationProduct);
    }

    private Image toImageEntity(MultipartFile file1) throws IOException {
        Image image = new Image();
        image.setName(file1.getName());
        image.setOriginalFileName(file1.getOriginalFilename());
        image.setContentType(file1.getContentType());
        image.setSize(file1.getSize());
        image.setBytes(file1.getBytes());
        return image;
    }

    public EducationProduct updateEducationProduct(EducationProduct educationProduct) {
        return educationProductRepository.saveAndFlush(educationProduct);
    }

    @Transactional
    public void deleteEducationProduct(int id) {
        educationProductRepository.deleteEducationProduct(id);
    }
}