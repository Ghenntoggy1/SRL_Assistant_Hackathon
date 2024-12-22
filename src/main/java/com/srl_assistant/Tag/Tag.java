package com.srl_assistant.Tag;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.srl_assistant.Document.Document;

import com.srl_assistant.Templates.Template;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Integer id;

    @Column(name = "type", unique = true, nullable = false)
    private String type;


    @OneToOne(mappedBy = "tag")
    private Document document;

    @OneToOne(mappedBy = "tag")
    private Template template;
}
