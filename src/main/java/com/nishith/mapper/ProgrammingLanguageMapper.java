package com.nishith.mapper;

import com.nishith.entity.ProgrammingLanguageEntity;
import com.nishith.model.ProgrammingLanguage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProgrammingLanguageMapper {

    ProgrammingLanguageMapper INSTANCE = Mappers.getMapper(ProgrammingLanguageMapper.class);

    @Mappings({
            @Mapping(source = "languageName", target = "programmingLanguageName"),
            @Mapping(source = "languageKey", target = "key")
    })
    ProgrammingLanguage map(ProgrammingLanguageEntity programmingLanguageEntity);

    @Mappings({
            @Mapping(source = "programmingLanguageName", target = "languageName"),
            @Mapping(source = "key", target = "languageKey")
    })
    ProgrammingLanguageEntity map(ProgrammingLanguage programmingLanguage);
}
