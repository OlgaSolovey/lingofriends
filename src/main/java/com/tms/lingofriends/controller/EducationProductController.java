package com.tms.lingofriends.controller;

import com.tms.lingofriends.model.EducationProduct;
import com.tms.lingofriends.service.EducationProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

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

    @GetMapping("/ln/{languageId}")
    public ResponseEntity<ArrayList<EducationProduct>> findEducationProductByLanguageId(@PathVariable String languageId) {
        Optional<ArrayList<EducationProduct>> educationProducts = educationProductService.findEducationProductByLanguageId(languageId);
        if (educationProducts.isPresent()) {
            return new ResponseEntity<>(educationProducts.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
    @GetMapping("/us/{userId}")
    public ResponseEntity<ArrayList<EducationProduct>> findEducationProductByUserId(@PathVariable String userId) {
        Optional<ArrayList<EducationProduct>> educationProducts = educationProductService.findEducationProductByUserId(userId);
        if (educationProducts.isPresent()) {
            return new ResponseEntity<>(educationProducts.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createEducationProduct(@RequestParam("file1") MultipartFile file1,
                                                             @RequestParam("file2") MultipartFile file2,
                                                             @RequestParam("file3") MultipartFile file3,
                                                             @RequestParam("file4") MultipartFile file4,
                                                             @RequestParam("file5") MultipartFile file5,
                                                             @RequestBody EducationProduct educationProduct, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {

                log.warn("We have bindingResult error : " + o);
            }
        }
        educationProductService.createEducationProduct(educationProduct, file1, file2, file3, file4, file5);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public void updateEducationProduct(@RequestBody EducationProduct educationProduct) {
        educationProductService.updateEducationProduct(educationProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteEducationProduct(@PathVariable int id) {
        educationProductService.deleteEducationProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}