package com.nishith.service;

import com.nishith.repository.ProgrammingLanguageRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProgrammingLanguageServiceTest {

    @InjectMocks
    private ProgrammingLanguageService subject;

    @Mock
    private ProgrammingLanguageRepository programmingLanguageRepository;
}