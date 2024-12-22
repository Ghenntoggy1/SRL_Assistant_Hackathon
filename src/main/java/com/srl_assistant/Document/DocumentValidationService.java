package com.srl_assistant.Document;

import com.srl_assistant.RequiredFields.RequiredField;
import com.srl_assistant.RequiredFields.RequiredFieldRepository;
import com.srl_assistant.Templates.Template;
import com.srl_assistant.Templates.TemplateRepository;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentValidationService {

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private RequiredFieldRepository requiredFieldRepository;

    @Autowired
    private OpenAiService openAiService;

    @Autowired
    private PDFTextExtractor pdfTextExtractor;
    @Autowired
    private DocumentRepository documentRepository;

    public ValidationResult validateDocument(String fileUrl, String tag, int documentId) throws Exception {
        String documentText = pdfTextExtractor.extractTextFromUrl(fileUrl);

        Template template = templateRepository.findByTag(tag);
        if (template == null) {
            throw new IllegalArgumentException("Template not found for tag: " + tag);
        }

        List<RequiredField> requiredFields = requiredFieldRepository.findByTemplateId(template.getId());

        List<ChatMessage> chatMessages = generateChatMessages(documentText, requiredFields);

        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model("gpt-4")
                .messages(chatMessages)
                .temperature(0.7)
                .maxTokens(1000)
                .build();

        String chatGptResponse = openAiService.createChatCompletion(request)
                .getChoices()
                .get(0)
                .getMessage()
                .getContent();

        return parseChatGptResponse(chatGptResponse, documentId);
    }

    private List<ChatMessage> generateChatMessages(String documentText, List<RequiredField> requiredFields) {
        List<ChatMessage> messages = new ArrayList<>();

        messages.add(new ChatMessage("system", "You are an expert in document validation. Your task is to validate a document based on the provided content and criteria. Perform as many checks as possible on your own and categorize them under relevant categories like 'Input Validation', 'Format Validation', 'Logical Validation', and 'Other Checks'."));

        StringBuilder userPrompt = new StringBuilder();
        userPrompt.append("Document content:\n\n").append(documentText).append("\n\n");
        userPrompt.append("Validation criteria:\n");
        for (RequiredField field : requiredFields) {
            userPrompt.append("- Field: ").append(field.getFieldName());
            userPrompt.append(" [Mandatory]");
            if (field.getFormat() != null) {
                userPrompt.append(" [Format: ").append(field.getFormat()).append("]");
            }
            userPrompt.append("\n");
        }
        userPrompt.append("\nInstructions for validation:\n");
        userPrompt.append("1. Identify if all mandatory fields are present and contain valid data.\n");
        userPrompt.append("2. Verify if the fields match the required formats.\n");
        userPrompt.append("3. Look for logical inconsistencies (e.g., invalid dates, mismatched IDs).\n");
        userPrompt.append("4. Extract additional issues or observations related to the document.\n");
        userPrompt.append("\nCategorize the checks into:\n");
        userPrompt.append("- Input Validation: Ensures mandatory fields are present.\n");
        userPrompt.append("- Format Validation: Ensures fields match required formats.\n");
        userPrompt.append("- Logical Validation: Ensures logical consistency (e.g., dates, IDs).\n");
        userPrompt.append("- Other Checks: Additional observations or errors.\n");
        userPrompt.append("\nFor each category, list checks in the format:\n");
        userPrompt.append("Check Name: Description of the check performed.\n");
        userPrompt.append("Result: Passed/Failed.\n");

        messages.add(new ChatMessage("user", userPrompt.toString()));
        return messages;
    }



    private ValidationResult parseChatGptResponse(String response, int documentId) {
        List<Check> checks = new ArrayList<>();

        List<String> inputValidationChecks = extractCategoryChecks(response, "Input Validation");
        checks.addAll(inputValidationChecks.stream()
                .map(check -> new Check("Input Validation", check.endsWith("Passed")))
                .collect(Collectors.toList()));

        List<String> formatValidationChecks = extractCategoryChecks(response, "Format Validation");
        checks.addAll(formatValidationChecks.stream()
                .map(check -> new Check("Format Validation", check.endsWith("Passed")))
                .collect(Collectors.toList()));

        List<String> logicalValidationChecks = extractCategoryChecks(response, "Logical Validation");
        checks.addAll(logicalValidationChecks.stream()
                .map(check -> new Check("Logical Validation", check.endsWith("Passed")))
                .collect(Collectors.toList()));

        List<String> otherChecks = extractCategoryChecks(response, "Other Checks");
        checks.addAll(otherChecks.stream()
                .map(check -> new Check("Other Checks", check.endsWith("Passed")))
                .collect(Collectors.toList()));

        int totalChecks = checks.size();
        long passedChecks = checks.stream().filter(Check::isPassed).count();
        int overallScore = totalChecks > 0 ? (int) (((double) passedChecks / totalChecks) * 100) : 100;


        ValidationResult validationResult = new ValidationResult(overallScore, checks);

        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new IllegalArgumentException("Document not found with ID: " + documentId));

        document.setScore(overallScore);

        documentRepository.save(document);

        return validationResult;
    }

    private List<String> extractCategoryChecks(String response, String category) {
        int startIndex = response.indexOf(category + ":");
        if (startIndex == -1) {
            return List.of();
        }
        int endIndex = response.indexOf("\n\n", startIndex);
        String sectionText = response.substring(startIndex, endIndex == -1 ? response.length() : endIndex);
        return List.of(sectionText.split("\n")).stream()
                .skip(1)
                .map(String::trim)
                .filter(line -> !line.isEmpty() && line.contains("Result:"))
                .collect(Collectors.toList());
    }


}
