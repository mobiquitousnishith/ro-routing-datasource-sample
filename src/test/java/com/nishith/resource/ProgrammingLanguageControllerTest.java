package com.nishith.resource;

import java.nio.charset.StandardCharsets;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nishith.model.ProgrammingLanguage;
import com.nishith.service.ProgrammingLanguageService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@Slf4j
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {ProgrammingLanguageController.class})
class ProgrammingLanguageControllerTest {

    private static final String URI_GET_PROGRAMMING_LANGUAGE_BY_NAME = "/programming/language/name/{name}";
    private static final String URI_PROGRAMMING_LANGUAGE = "/programming/language";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProgrammingLanguageService programmingLanguageService;

    @Captor
    private ArgumentCaptor<ProgrammingLanguage> programmingLanguageArgumentCaptor;

    @BeforeEach
    void setupController() {
        this.mockMvc = standaloneSetup(new ProgrammingLanguageController(programmingLanguageService)).build();
    }

    @AfterEach
    void clearMocks() {
        Mockito.reset(programmingLanguageService);
    }

    @Test
    void getProgrammingLanguageByName_ValidLanguageName_ProgrammingLanguageObject() throws Exception {
        long id = 1L;
        String languageName = "Java";
        String languageKey = "java";
        ProgrammingLanguage expectation = new ProgrammingLanguage(id, languageName, languageKey);

        when(programmingLanguageService.getProgrammingLanguageByName(languageName))
                .thenReturn(expectation);

        String content = mockMvc.perform(MockMvcRequestBuilders.get(URI_GET_PROGRAMMING_LANGUAGE_BY_NAME, languageName))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        log.debug("Content: {}", content);

        ProgrammingLanguage actual = MAPPER.readValue(content, ProgrammingLanguage.class);

        Assertions.assertThat(actual).usingRecursiveComparison().isEqualTo(expectation);
    }

    @Test
    void getProgrammingLanguageByName_LanguageNameNotExists_StatusNotFound() throws Exception {
        String languageName = "Ruby";

        when(programmingLanguageService.getProgrammingLanguageByName(languageName))
                .thenThrow(new EntityNotFoundException("Unable to find any Programming language by name [%s]".formatted(languageName)));

        String content = mockMvc.perform(MockMvcRequestBuilders.get(URI_GET_PROGRAMMING_LANGUAGE_BY_NAME, languageName))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        log.debug("Content: {}", content);
    }

    @Test
    void saveProgrammingLanguage_ValidPayload_StatusCreated() throws Exception {
        long id = 1L;
        String languageName = "Java";
        String languageKey = "java";
        ProgrammingLanguage payload = new ProgrammingLanguage(id, languageName, languageKey);

        doNothing().when(programmingLanguageService).saveProgrammingLanguage(payload);

        mockMvc.perform(MockMvcRequestBuilders.post(URI_PROGRAMMING_LANGUAGE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(payload))
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void saveProgrammingLanguage_InvalidPayload_StatusCreated() throws Exception {
        long id = 1L;
        String languageName = "Java";
        String languageKey = "java";
        ProgrammingLanguage payload = new ProgrammingLanguage(id, languageName, languageKey);

        doThrow(new RuntimeException("Something went wrong."))
                .when(programmingLanguageService)
                .saveProgrammingLanguage(programmingLanguageArgumentCaptor.capture());

        String content = mockMvc.perform(MockMvcRequestBuilders.post(URI_PROGRAMMING_LANGUAGE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(payload))
                ).andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        log.debug("Content: {}", content);
    }
}