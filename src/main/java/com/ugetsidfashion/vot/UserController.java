package com.ugetsidfashion.vot;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserProfileRepository userProfileRepository;
    private final GarmetExplorer garmetExplorer;

    public UserController(UserProfileRepository userProfileRepository, GarmetExplorer garmetExplorer) {
        this.userProfileRepository = userProfileRepository;
        this.garmetExplorer = garmetExplorer;
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserProfile userProfile) {
        // Check if the user already exists
        if (userProfileRepository.findByEmail(userProfile.getEmail()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "User with this email already exists"));
        }

        userProfile = userProfileRepository.save(userProfile);

        return ResponseEntity.ok(userProfile);
    }

    @PostMapping(value = "/tryOn", consumes = "multipart/form-data")
    public ResponseEntity<?> tryOn(
            @RequestParam("email") String email,
            @RequestParam("image") MultipartFile image) throws IOException {
        // Validate image
        String filename = image.getOriginalFilename();
        if (filename == null || !filename.matches("(?i).+\\.(jpg|jpeg|png|gif|bmp)$")) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Invalid image format. Supported formats are: jpg, jpeg, png, gif, bmp"));
        }

        if (image.getSize() > 5 * 1024 * 1024) { // 5 MB limit
            ResponseEntity.badRequest()
                    .body(Map.of("error", "Image size exceeds the limit of 5 MB"));
        }

        // Get user profile
        Optional<UserProfile> userProfile = userProfileRepository.findByEmail(email);
        if (userProfile.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        byte[] generatedImage = garmetExplorer.tryOn(image.getBytes(), image.getContentType(), userProfile.get());

        return ResponseEntity.ok()
                .header("Content-Type", "image/png")
                .body(generatedImage);
    }
}
