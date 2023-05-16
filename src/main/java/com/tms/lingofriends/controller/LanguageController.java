package com.tms.lingofriends.controller;

import com.tms.lingofriends.model.Language;
import com.tms.lingofriends.service.LanguageService;
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
@RequestMapping("/language")
public class LanguageController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final LanguageService languageService;

    @Autowired
    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping
    public ResponseEntity<ArrayList<Language>> getAllLanguage() {
        ArrayList<Language> list = languageService.getAllLanguage();
        return new ResponseEntity<>(list, (!list.isEmpty()) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Language> getLanguageById(@PathVariable int id) {
        Language language = languageService.getLanguageById(id);
        if (language == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(language, HttpStatus.OK);
    }

   /* @GetMapping("/ln/{ln}")
    public ResponseEntity<Language> findLanguageByLastName(@PathVariable String ln) {
        Optional<User> user = userService.findUserByLastName(ln);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(),HttpStatus.OK);
        } return new ResponseEntity<>(HttpStatus.CONFLICT);
    }*/

    @PostMapping
    public ResponseEntity<HttpStatus> createLanguage(@RequestBody Language language, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {

                log.warn("We have bindingResult error : " + o);
            }
        }
        languageService.createLanguage(language);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public void updateLanguage(@RequestBody Language language) {
        languageService.updateLanguage(language);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteLanguage(@PathVariable int id) {
        languageService.deleteLanguage(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}