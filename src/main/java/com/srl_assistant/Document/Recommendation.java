package com.srl_assistant.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Recommendation {
    private int lineNo;
    private String description;
}
