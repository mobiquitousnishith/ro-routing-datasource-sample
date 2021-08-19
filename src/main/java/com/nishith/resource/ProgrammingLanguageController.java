package com.nishith.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nishith.model.ProgrammingLanguage;
import com.nishith.service.ProgrammingLanguageService;
import lombok.RequiredArgsConstructor;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("programming/language")
public class ProgrammingLanguageController {

    private final ProgrammingLanguageService programmingLanguageService;

    @GetMapping(path = "name/{name}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ProgrammingLanguage> getProgrammingLanguageByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(programmingLanguageService.getProgrammingLanguageByName(name));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveProgrammingLanguage(@RequestBody ProgrammingLanguage programmingLanguage) {
        programmingLanguageService.saveProgrammingLanguage(programmingLanguage);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
