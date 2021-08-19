package com.nishith.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nishith.entity.ProgrammingLanguageEntity;

@Repository
public interface ProgrammingLanguageRepository extends JpaRepository<ProgrammingLanguageEntity, Long> {

    Optional<ProgrammingLanguageEntity> findByLanguageName(String languageName);
}
