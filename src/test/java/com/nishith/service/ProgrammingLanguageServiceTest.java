package com.nishith.service;

import java.util.Optional;
import javax.persistence.EntityNotFoundException;

import com.nishith.entity.ProgrammingLanguageEntity;
import com.nishith.mapper.ProgrammingLanguageMapper;
import com.nishith.model.ProgrammingLanguage;
import com.nishith.repository.ProgrammingLanguageRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProgrammingLanguageServiceTest {

    @InjectMocks
    private ProgrammingLanguageService subject;

    @Mock
    private ProgrammingLanguageRepository programmingLanguageRepository;

    @Mock
    private ProgrammingLanguageMapper programmingLanguageMapper;

    @Test
    void getProgrammingLanguageByName_ValidExistingName_ProgrammingLanguageObject() {
        long id = 1L;
        String languageName = "Java";
        String languageKey = "java";
        ProgrammingLanguageEntity programmingLanguageEntity = new ProgrammingLanguageEntity(id, languageName, languageKey);
        ProgrammingLanguage expectation = new ProgrammingLanguage(id, languageName, languageKey);
        Optional<ProgrammingLanguageEntity> programmingLanguageEntityOptional = Optional.of(programmingLanguageEntity);

        when(programmingLanguageRepository.findByLanguageName(languageName))
                .thenReturn(programmingLanguageEntityOptional);
        when(programmingLanguageMapper.map(programmingLanguageEntity)).thenReturn(expectation);

        ProgrammingLanguage actual = subject.getProgrammingLanguageByName(languageName);

        Assertions.assertThat(actual).usingRecursiveComparison().isEqualTo(expectation);
    }

    @Test
    void getProgrammingLanguageByName_LanguageNameNotExists_EntityNotFoundException() {
        String languageName = "Java";
        Optional<ProgrammingLanguageEntity> programmingLanguageEntityOptional = Optional.empty();

        when(programmingLanguageRepository.findByLanguageName(languageName))
                .thenReturn(programmingLanguageEntityOptional);

        Assertions.assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> subject.getProgrammingLanguageByName(languageName));
    }

    @Test
    void saveProgrammingLanguage_ValidProgrammingLanguageObject_ProgrammingLanguageSavedSuccessfully() {
        long id = 1L;
        String languageName = "Java";
        String languageKey = "java";
        ProgrammingLanguage programmingLanguage = new ProgrammingLanguage(null, languageName, languageKey);
        ProgrammingLanguageEntity programmingLanguageEntity = new ProgrammingLanguageEntity(null, languageName, languageKey);
        ProgrammingLanguageEntity savedProgrammingLanguageEntity = new ProgrammingLanguageEntity(id, languageName, languageKey);

        when(programmingLanguageMapper.map(programmingLanguage)).thenReturn(programmingLanguageEntity);

        when(programmingLanguageRepository.save(programmingLanguageEntity)).thenReturn(savedProgrammingLanguageEntity);

        Assertions.assertThatNoException().isThrownBy(() -> subject.saveProgrammingLanguage(programmingLanguage));

    }
}