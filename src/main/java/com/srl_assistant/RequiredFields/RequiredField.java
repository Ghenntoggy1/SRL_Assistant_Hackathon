package com.srl_assistant.RequiredFields;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.srl_assistant.Templates.Template;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "required_fields")
@Getter
@Setter
public class RequiredField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "template_id")
    private Template template;

    private String fieldName;
    private String format;

}