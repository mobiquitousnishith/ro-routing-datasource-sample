package com.nishith.resource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nishith.model.ErrorResponse;
import com.nishith.model.ProgrammingLanguage;
import com.nishith.service.ProgrammingLanguageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
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

    @ExceptionHandler(EntityNotFoundException.class)
    ResponseEntity<ErrorResponse> handleNotFound(Exception exception, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        logError(exception, requestURI);
        ErrorResponse response = ErrorResponse.builder()
                .errors(Stream.of(exception.getMessage()).toList())
                .status(NOT_FOUND.value())
                .time(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .uri(requestURI)
                .statusMessage(NOT_FOUND.name())
                .build();
        return ResponseEntity.status(NOT_FOUND).body(response);
    }

    @ExceptionHandler({Exception.class})
    ResponseEntity<ErrorResponse> handleException(Exception exception, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        logError(exception, requestURI);
        ErrorResponse response = ErrorResponse.builder()
                .errors(Stream.of(exception.getMessage()).toList())
                .status(INTERNAL_SERVER_ERROR.value())
                .time(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .uri(requestURI)
                .statusMessage(INTERNAL_SERVER_ERROR.name())
                .build();
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(response);
    }

    private void logError(Exception exception, String requestUri) {
        log.error("Error while processing request [{}]", requestUri);
        log.error(exception.getMessage(), exception);
    }
}
