package com.srl_assistant.RequiredFields;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RequiredFieldRepository extends JpaRepository<RequiredField, Integer> {

    List<RequiredField> findByTemplateId(Integer templateId);
}
