package com.srl_assistant.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ValidationResult {
    private int score;
    private List<Check> checks;
}
