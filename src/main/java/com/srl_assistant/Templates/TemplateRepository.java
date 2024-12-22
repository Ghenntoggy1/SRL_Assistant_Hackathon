package com.srl_assistant.Templates;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Integer> {
    @Query("SELECT t FROM Template t WHERE t.tag.type = :tagType")
    Template findByTag(@Param("tagType") String tagType);

    Template findByName(String name);
}

