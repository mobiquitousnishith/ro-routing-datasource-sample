package com.nishith.model;

import java.io.Serial;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgrammingLanguage implements Serializable {

    @Serial
    private static final long serialVersionUID = -6497254081257440703L;

    private Long id;

    private String programmingLanguageName;

    private String key;
}
