package com.ugetsidfashion.vot;

import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GarmetExplorer {
    private final PromptGenerator promptGenerator;

    private final Client geminiClient;

    public GarmetExplorer(PromptGenerator promptGenerator, Client geminiClient) {
        this.promptGenerator = promptGenerator;
        this.geminiClient = geminiClient;
    }

    public byte[] tryOn(final byte[] inputGarmentImage, final String mimeType, final UserProfile userProfile) {
        // Generate the prompt based on the user profile
        String prompt = promptGenerator.generatePrompt(userProfile);

        // Call gemini API to process the image with the prompt
        Content requestContent = Content.fromParts(
                Part.fromText(prompt),
                Part.fromBytes(inputGarmentImage, mimeType));

        // Configure gemini to generate image content
        GenerateContentConfig config = GenerateContentConfig.builder()
                .responseModalities("TEXT", "IMAGE")
                .build();

        // Generate the content using the gemini client
        GenerateContentResponse response = geminiClient.models.generateContent(
                "gemini-2.0-flash-preview-image-generation",
                requestContent,
                config
        );

        return getImageBytes(response);
    }

    private byte[] getImageBytes(GenerateContentResponse response) {
        var parts = getParts(response);

        if (parts.isEmpty()) {
            throw new IllegalStateException("No bytes in the first part of the first candidate returned from Gemini API");
        }

        var inlineData = parts.getFirst().inlineData().orElseThrow(
                () -> new IllegalStateException("No inline data in the part returned from Gemini API"));
        return inlineData.data().orElseThrow(
                () -> new IllegalStateException("No bytes in the inline data of the part returned from Gemini API"));
    }

    private List<Part> getParts(GenerateContentResponse response) {
        var content = getContent(response);

        var partsOpt = content.parts();
        var parts = partsOpt.orElseThrow(
                () -> new IllegalStateException("No parts in the first candidate returned from Gemini API"));
        if (parts.isEmpty()) {
            throw new IllegalStateException("No bytes in the first part of the first candidate returned from Gemini API");
        }
        return parts;
    }

    private Content getContent(GenerateContentResponse response) {
        var candidates = response.candidates().orElseThrow(
                () -> new IllegalStateException("No candidates returned from Gemini API"));
        if (candidates.isEmpty()) {
            throw new IllegalStateException("No image content returned from Gemini API");
        }

        var firstCandidate = candidates.getFirst();
        var contentOpt = firstCandidate.content();
        return contentOpt.orElseThrow(
                () -> new IllegalStateException("No content in the first candidate returned from Gemini API"));
    }
}
