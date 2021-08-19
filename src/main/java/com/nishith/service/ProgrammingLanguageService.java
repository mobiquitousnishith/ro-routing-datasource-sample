package com.nishith.service;

import java.util.Optional;
import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nishith.entity.ProgrammingLanguageEntity;
import com.nishith.mapper.ProgrammingLanguageMapper;
import com.nishith.model.ProgrammingLanguage;
import com.nishith.repository.ProgrammingLanguageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProgrammingLanguageService {

    private final ProgrammingLanguageRepository programmingLanguageRepository;

    private final ProgrammingLanguageMapper programmingLanguageMapper;

    @Transactional(readOnly = true, propagation = NOT_SUPPORTED)
    public ProgrammingLanguage getProgrammingLanguageByName(String name) {
        Optional<ProgrammingLanguageEntity> programmingLanguageEntityOptional = programmingLanguageRepository.findByLanguageName(name);
        ProgrammingLanguage programmingLanguage = programmingLanguageMapper.map(programmingLanguageEntityOptional
                .orElseThrow(EntityNotFoundException::new));
        log.debug("Programming language [{}] has been retrieved successfully.", programmingLanguage.getId());
        return programmingLanguage;
    }

    @Transactional(propagation = REQUIRED)
    public void saveProgrammingLanguage(ProgrammingLanguage programmingLanguage) {
        ProgrammingLanguageEntity programmingLanguageEntity = programmingLanguageMapper.map(programmingLanguage);
        programmingLanguageEntity = programmingLanguageRepository.save(programmingLanguageEntity);
        log.debug("Programming language with id [{}] has been created successfully.", programmingLanguageEntity.getId());
    }
}
