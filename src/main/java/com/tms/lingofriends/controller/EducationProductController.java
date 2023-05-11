package com.tms.lingofriends.controller;

import com.tms.lingofriends.model.EducationProduct;
import com.tms.lingofriends.model.User;
import com.tms.lingofriends.service.EducationProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/education")
public class EducationProductController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final EducationProductService educationProductService;

    @Autowired
    public EducationProductController(EducationProductService educationProductService) {
        this.educationProductService = educationProductService;
    }
    @GetMapping
    public ResponseEntity<ArrayList<EducationProduct>> getAllEducationProduct() {
        ArrayList<EducationProduct> list = educationProductService.getAllEducationProduct();
        return new ResponseEntity<>(list, (!list.isEmpty()) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
    @GetMapping("/{id}")
    public ResponseEntity<EducationProduct> getEducationProductById(@PathVariable int id) {
        EducationProduct educationProduct = educationProductService.getEducationProductById(id);
        if (educationProduct == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(educationProduct, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createEducationProduct(@RequestBody EducationProduct educationProduct, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {

                log.warn("We have bindingResult error : " + o);
            }
        }
        educationProductService.createEducationProduct(educationProduct);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public void updateEducationProduct(@RequestBody EducationProduct educationProduct) {
        educationProductService.updateEducationProduct(educationProduct);
    }

  /*  @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteEducationProduct(@PathVariable int id) {
        educationProductService.deleteEducationProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }*/
}
