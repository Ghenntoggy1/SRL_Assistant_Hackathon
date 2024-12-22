package com.srl_assistant.Document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {

    List<Document> findByUserId(Integer userId);
    Document findByLinkMinio(String linkMinio);
}