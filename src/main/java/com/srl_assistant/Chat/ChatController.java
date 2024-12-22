package com.srl_assistant.Chat;

import com.srl_assistant.Utils.ApiResponse;
import com.srl_assistant.Utils.ResponseHelper;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin
public class ChatController {

    @Autowired
    private OpenAiService openAiService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> askChatGpt(@RequestBody ChatRequest chatRequest) {
        try {
            if (chatRequest == null || chatRequest.getQuestion() == null || chatRequest.getQuestion().isEmpty()) {
                return ResponseEntity.badRequest().body(ResponseHelper.error("Question cannot be empty", 400));
            }

            String response = getChatGptResponse(chatRequest.getQuestion());

            return ResponseEntity.ok(ResponseHelper.success(response, "ChatGPT response retrieved successfully."));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(ResponseHelper.error("Error while retrieving response: " + e.getMessage(), 500));
        }
    }

    private String getChatGptResponse(String question) throws Exception {
        String systemPrompt = "You are an expert in Moldovan law. Answer questions based only on Moldovan legislation and legal principles.";

        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage("system", systemPrompt));
        messages.add(new ChatMessage("user", question));

        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model("gpt-4")
                .messages(messages)
                .temperature(0.7)
                .maxTokens(1000)
                .build();

        return openAiService.createChatCompletion(request)
                .getChoices()
                .get(0)
                .getMessage()
                .getContent();
    }
}
