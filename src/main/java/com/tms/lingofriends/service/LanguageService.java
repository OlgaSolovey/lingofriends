package com.tms.lingofriends.service;

import com.tms.lingofriends.model.Language;
import com.tms.lingofriends.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class LanguageService {
    LanguageRepository languageRepository;

    @Autowired
    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }


    public ArrayList<Language> getAllLanguage() {
        return (ArrayList<Language>) languageRepository.findAll();
    }

    public Language getLanguageById(int id) {
        return languageRepository.findById(id).orElse(new Language());
    }

  //  public Optional<Language> findUserByLastName(String ln) {
   //     return userRepository.findUserByLastName(ln);
  //  }

    public Language createLanguage(Language language ) {
        return languageRepository.save(language);
    }

    public Language updateLanguage(Language language) {

        return languageRepository.saveAndFlush(language);
    }

   @Transactional
    public void deleteLanguage(int id) {
        languageRepository.deleteLanguage(id);
    }
}
