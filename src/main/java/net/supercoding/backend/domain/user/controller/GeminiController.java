package net.supercoding.backend.domain.user.controller;

import lombok.RequiredArgsConstructor;
import net.supercoding.backend.domain.user.service.GeminiProService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gemini")
@RequiredArgsConstructor
public class GeminiController {

    private final GeminiProService geminiService;

    @GetMapping("/ask")
    public String askGemini(@RequestParam String prompt) {
        return geminiService.askGemini(prompt);
    }
}

