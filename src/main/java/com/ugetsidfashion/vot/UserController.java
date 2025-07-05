package com.ugetsidfashion.vot;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserProfileRepository userProfileRepository;

    public UserController(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserProfile userProfile) {
        // Check if the user already exists
        if (userProfileRepository.findByEmail(userProfile.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }

        userProfile = userProfileRepository.save(userProfile);

        return ResponseEntity.ok(userProfile);
    }

    @PostMapping(value = "/tryOn", consumes = "multipart/form-data")
    public ResponseEntity<?> tryOn(
            @RequestParam("email") String email,
            @RequestParam("image") MultipartFile image) {
        String filename = image.getOriginalFilename();
        if (filename == null || !filename.matches("(?i).+\\.(jpg|jpeg|png|gif|bmp)$")) {
            throw new InvalidImageFileTypeException("Only image files are supported (jpg, jpeg, png, gif, bmp)");
        }

        if (image.getSize() > 5 * 1024 * 1024) { // 5 MB limit
            throw new ImageSizeExceededException("Image size exceeds the limit of 5 MB");
        }

        // Implement logic to handle the image upload for the user with the given email
        // Example: find user by email, save image, etc.
        return ResponseEntity.ok("Image uploaded successfully");
    }
}
