package com.rewago.groq_ai_integration.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ChatController {

    private final OpenAiChatModel chatModel;

    @Autowired
    public ChatController(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/ai/generate")
    public Map<String, Object> generate(@RequestParam String message) throws JsonProcessingException, InterruptedException {
        String engineeredMessage = "Get a fresh perspective! Simply provide a message, and rephrase it into one big sentence for my ecommerce application.Only give me the result. I don't need extra details. Here's the input: " + message;
        Prompt prompt = new Prompt(new UserMessage(engineeredMessage));
        ChatResponse response = this.chatModel.call(prompt);
        return Map.of("result", response.getResult().getOutput().getContent());
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/ai/generate/translation")
    public Map<String, Object> generateTranslation(@RequestParam String message) throws JsonProcessingException, InterruptedException {
        String engineeredMessage = "You are a translator. A message can be given in any language. Translate the text and provide the result. Only give me the result without extra details." + message;
        Prompt prompt = new Prompt(new UserMessage(engineeredMessage));
        ChatResponse response = this.chatModel.call(prompt);
        return Map.of("result", response.getResult().getOutput().getContent());
    }
}
