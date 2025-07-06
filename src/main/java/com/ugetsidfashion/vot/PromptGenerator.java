package com.ugetsidfashion.vot;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PromptGenerator {
    public static final String PROMPT_FORMAT = """
            Generate a photorealistic image of a full-body fashion Mannequin. The model is in a minimal studio with a
            soft, neutral grey background. The character model's form must be rendered with strict adherence to the following physical parameters:
                [BODY_PROFILE]
            The mannequin has [HAIR_LENGTH], [HAIR_TYPE], [HAIR_COLOUR] hair. The hairstyle is elegant and styled to complement the garment.
            The mannequin has [SKIN_COLOUR] skin colour.
            
            The primary task is to realistically drape the garment from the [Input Garment Image] onto the defined character model.
            To achieve a realistic fit, you must follow this chain of reasoning: First, analyze the [Input Garment Image].
            Identify its key characteristics: its garment category (e.g., dress, top, pants), its silhouette (e.g., A-line, slim-fit, oversized), its material
            properties (e.g., structured like denim, fluid like silk, stiff like leather), and its key details (e.g., sleeveless, V-neck, cropped length).
            
            Next, consider the physical parameters given above. You have been given a detailed description of the model's unique build, shape, and proportions.
            Then, determine the realistic fit. Based on your analysis of the garment and the body, simulate how the garment would physically interact with the form. Your simulation must
            respect the principles of gravity, tension, and fabric properties. For example, a fitted garment over a fuller curve
            must show appropriate, subtle tension, while a fluid fabric should conform to and drape over the body's contours.
            The final drape must look natural and physically plausible.
            
            Finally, render the complete image according to this determined fit.
            [GENDER_POSSESSIVE_ADJECTIVE_PRONOUN] posture should convey confidence through
            [GENDER_POSSESSIVE_ADJECTIVE_PRONOUN] body language. Full body shot, eye-level perspective.
            
            The final image aspect ratio must be --ar 9:16 for a vertical, mobile-friendly format. The subject is to be perfectly centered in the frame.
            Aesthetic: Professional e-commerce photography, hyper-realistic, clean, and cinematic.
            Lighting: Soft, multi-source studio lighting that mimics diffuse natural light, creating gentle shadows that reveal fabric texture and body contour.
            Detail: 8K UHD, tack-sharp focus, hyper-detailed fabric weave, realistic material simulation, photorealistic skin texture.
            CRITICAL DIRECTIVE: Maintain anatomically correct human proportions as defined. The focus is on the accuracy of the body shape and the realism of the garment's fit.
            AVOID: showing the front of the face, distorted or mutated anatomy, unrealistic body proportions, text, watermarks, signatures, logos, blur, cartoonish features.
            """;

    public static final Map<String, String> BUILD_DESCRIPTIONS = Map.of(
            "Very Slender", "[GENDER_SUBJECT_PRONOUN] has a very slender build, with a particularly lean and narrow frame.",
            "Slender", "[GENDER_SUBJECT_PRONOUN] has a slender build, with a lean and streamlined frame.",
            "Average", "[GENDER_SUBJECT_PRONOUN] has a balanced, average build, with moderate proportions throughout her frame.",
            "Slightly Full", "[GENDER_SUBJECT_PRONOUN] has a slightly full build, characterized by a softer frame with gentle curves.",
            "Full-Figured", "[GENDER_SUBJECT_PRONOUN] has a full-figured build, characterized by a soft, substantial frame with prominent and rounded curves."
    );

    public static final Map<String, String> SHAPE_DESCRIPTIONS = Map.ofEntries(
            Map.entry("Hourglass", "[GENDER_POSSESSIVE_ADJECTIVE_PRONOUN] geometric shape is a classic 'Hourglass', defined by a narrow waist with bust and hip measurements that are nearly identical, creating a balanced and symmetrical curved silhouette."),
            Map.entry("Top Hourglass", "[GENDER_POSSESSIVE_ADJECTIVE_PRONOUN] geometric shape is a 'Top Hourglass', with a full bust and a defined, narrow waist. [GENDER_POSSESSIVE_ADJECTIVE_PRONOUN] hips are slightly narrower than her bust."),
            Map.entry("Bottom Hourglass", "[GENDER_POSSESSIVE_ADJECTIVE_PRONOUN] geometric shape is a 'Bottom Hourglass', with full hips and a defined, narrow waist. [GENDER_POSSESSIVE_ADJECTIVE_PRONOUN] bust is slightly narrower than her hips."),
            Map.entry("Pear (Triangle)", "[GENDER_POSSESSIVE_ADJECTIVE_PRONOUN] geometric shape is a 'Pear' (or Triangle), defined by hips that are structurally wider than her bust and shoulders. She has a defined waist that sits above her fuller hips."),
            Map.entry("Apple (Inverted Triangle)", "[GENDER_POSSESSIVE_ADJECTIVE_PRONOUN] geometric shape is an 'Apple' (or Inverted Triangle), defined by a bust and torso that are broader than her hips, with less waist definition. [GENDER_POSSESSIVE_ADJECTIVE_PRONOUN] legs are often a primary feature."),
            Map.entry("Rectangle", "[GENDER_POSSESSIVE_ADJECTIVE_PRONOUN] geometric shape is a 'Rectangle', characterized by bust, waist, and hip measurements that are relatively similar, creating a straight, athletic silhouette with minimal waist definition."),
            Map.entry("Diamond", "[GENDER_POSSESSIVE_ADJECTIVE_PRONOUN] geometric shape is a 'Diamond', characterized by hips that are broader than the shoulders and a waist that is the fullest point of the torso."),
            Map.entry("Spoon", "[GENDER_POSSESSIVE_ADJECTIVE_PRONOUN] geometric shape is a 'Spoon', similar to a Pear but characterized by a distinct 'shelf' where the hips extend from a defined waist."),
            Map.entry("Column", "[GENDER_POSSESSIVE_ADJECTIVE_PRONOUN] geometric shape is a silhouette build which is a chic and modelesque Column. Bust, waist, and hips are beautifully aligned with minimal differentiation, creating a strong, linear frame. This balanced, athletic line is a versatile canvas, perfect for architectural shapes, dramatic drapes, and embracing bold, oversized styles"),
            Map.entry("Cornet Silhouette", "[GENDER_POSSESSIVE_ADJECTIVE_PRONOUN] geometric shape is a Cornet Silhouette build, which is an especially pronounced hourglass figure. While bust and hips are in beautiful balance, waist is exceptionally defined. This creates a striking, vintage-inspired curve that is powerful and incredibly rare."),
            Map.entry("Teardrop", "[GENDER_POSSESSIVE_ADJECTIVE_PRONOUN] geometric shape is a beautiful Teardrop. Proportions feature elegantly defined shoulders and a slender upper body that gracefully widens to fullest point at the hips. This creates a strong, grounded, and feminine silhouette with a naturally low center of gravity."),
            Map.entry("Athletic Frame", "[GENDER_POSSESSIVE_ADJECTIVE_PRONOUN] geometric shape is a powerful Athletic Frame. This silhouette is defined by strong, broad shoulders and a fuller bust that tapers to a leaner lower body. Dynamic 'V' shape is celebrated for its commanding presence and is a testament to strength and vitality."),
            Map.entry("Lollipop Silhouette", "[GENDER_POSSESSIVE_ADJECTIVE_PRONOUN] geometric shape is a proportions that align with the Lollipop Silhouette, a distinctive shape celebrated in high-fashion. It's characterized by a slender frame with lean hips and long legs, juxtaposed with a full bust. This creates a striking and memorable proportional contrast that is both bold and playful.")
    );

    public static final Map<String, String> MODIFIER_DESCRIPTIONS = Map.of(
            "Petite", "[GENDER_SUBJECT_PRONOUN] overall vertical line is petite.",
            "Moderate", "[GENDER_SUBJECT_PRONOUN] overall vertical line is moderate.",
            "Tall", "[GENDER_SUBJECT_PRONOUN] overall vertical line is tall and statuesque.",
            "Plus-Size", "[GENDER_SUBJECT_PRONOUN] overall frame can be described as plus-size, with generous and soft proportions."
    );

    public static final Map<String, String> GENDER_SUBJECT_PRONOUNS = Map.of(
            "Male", "He",
            "Female", "She"
    );

    public static final Map<String, String> GENDER_POSSESSIVE_ADJECTIVE_PRONOUNS = Map.of(
            "Male", "His",
            "Female", "Her"
    );

    private String getHeightDescription(final int height) {
        if (height < 162) {
            return "Petite";
        } else if (height < 176) {
            return "Moderate";
        }

        // >= 176
        return "Tall";
    }

    private String getBuild(final int height, final int waist) {
        double waistToHeightRatio = (double) waist / height;
        if (waistToHeightRatio < 0.41) {
            return "Very Slender";
        } else if (waistToHeightRatio < 0.46) {
            return "Slender";
        } else if (waistToHeightRatio < 0.52) {
            return "Average";
        } else if (waistToHeightRatio < 0.57) {
            return "Slightly Full";
        }

        // >= 0.57
        return "Full-Figured";
    }

    private String getShape(final int bust, final int waist, final int hip) {
        if (waist > 90 && hip > 115) {
            return "Plus-Size";
        }

        double bustToHipRatio = (double) bust / hip;
        double waistToBustRatio = (double) waist / bust;
        double waistToHipRatio = (double) waist / hip;
        double hipToBustRatio = (double) hip / bust;

        if (bustToHipRatio >= 0.95 && bustToHipRatio <= 1.05 && waistToHipRatio >= 0.90) {
            return "Column";
        }

        if (bustToHipRatio >= 0.95 && bustToHipRatio <= 1.05 && waistToHipRatio < 0.65) {
            return "Cornet Silhouette";
        }

        if (hipToBustRatio >= 1.25) {
            return "Teardrop";
        }

        if (bustToHipRatio >= 1.20 && waistToBustRatio >= 0.75) {
            return "Athletic Frame";
        }

        if (bustToHipRatio >= 1.20 && waist <= 75 && hip <= 95) {
            return "Lollipop Silhouette";
        }

        if (bustToHipRatio >= 0.95 && bustToHipRatio <= 1.05 && waistToHipRatio < 0.75) {
            return "Hourglass";
        }

        if (bustToHipRatio > 1.05 && waistToBustRatio < 0.75) {
            return "Top Hourglass";
        }

        if (hipToBustRatio > 1.05 && waistToHipRatio < 0.75) {
            return "Bottom Hourglass";
        }

        if (hipToBustRatio > 1.05 && waistToHipRatio >= 0.75) {
            return "Pear (Triangle)";
        }

        if (bustToHipRatio > 1.05 && waistToBustRatio >= 0.75) {
            return "Apple (Inverted Triangle)";
        }

        if (bustToHipRatio >= 0.95 && bustToHipRatio <= 1.05 && waistToHipRatio >= 0.75) {
            return "Rectangle";
        }

        if (waistToBustRatio > 1 && waistToHipRatio > 1) {
            return "Diamond";
        }

        // hipToBustRatio > 1.05) && (waistToHipRatio < 0.75) && high_hip_shelf
        return "Spoon";
    }

    public String generatePrompt(final UserProfile userProfile) {
        String buildDescription = BUILD_DESCRIPTIONS.get(getBuild(userProfile.getHeight(), userProfile.getWaistSize()))
                .replace("[GENDER_SUBJECT_PRONOUN]", GENDER_SUBJECT_PRONOUNS.get(userProfile.getGender()));

        String shapeDescription = SHAPE_DESCRIPTIONS.get(getShape(userProfile.getChestSize(),
                        userProfile.getWaistSize(), userProfile.getHipSize()))
                .replace("[GENDER_POSSESSIVE_ADJECTIVE_PRONOUN]",
                        GENDER_POSSESSIVE_ADJECTIVE_PRONOUNS.get(userProfile.getGender()));

        String modifierDescription = MODIFIER_DESCRIPTIONS.get(getHeightDescription(userProfile.getHeight()))
                .replace("[GENDER_SUBJECT_PRONOUN]", GENDER_SUBJECT_PRONOUNS.get(userProfile.getGender()));

        String bodyProfile = String.format("%s\n%s\n%s\n", buildDescription, shapeDescription, modifierDescription);

        return PROMPT_FORMAT
                .replace("[GENDER_POSSESSIVE_ADJECTIVE_PRONOUN]", GENDER_SUBJECT_PRONOUNS.get(userProfile.getGender()))
                .replace("[BODY_PROFILE]", bodyProfile)
                .replace("[HAIR_LENGTH]", userProfile.getHairLength())
                .replace("[HAIR_TYPE]", userProfile.getHairTexture())
                .replace("[HAIR_COLOUR]", userProfile.getHairColour())
                .replace("[SKIN_COLOUR]", userProfile.getSkinTone());
    }
}
