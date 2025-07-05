package com.ugetsidfashion.vot;

import org.springframework.stereotype.Service;

@Service
public class VotService {
    private String getHeightDescription(final int height) {
        if (height < 160) {
            return "Petite, Short";
        } else if (height <= 168) {
            return "Average height, Medium height";
        } else if (height <= 175) {
            return "Above average height, Tall";
        } else {
            return "Very tall, Statuesque";
        }
    }

    private String getBodyTypeDescription(final String bodyType) {
        return switch (bodyType.toLowerCase()) {
            case "slim" -> "Slim build, slender figure, lean, thin frame";
            case "medium" -> "Medium build, average frame, balanced weight";
            case "athletic" -> "Athletic build, toned, defined muscles, fit physique";
            case "plus-size" -> "Plus-size, full-figured, curvy, voluptuous";
            case "obese" -> "Obese, heavy build, large frame, soft rounded body";
            default -> "Very obese, significantly heavy build, very large frame";
        };
    }

    private String getChestToWaistRatioDescription(final int chest, final int waist) {
        double chestToWaistRatio = (double) chest / waist;
        if (chestToWaistRatio >= 0.95 && chestToWaistRatio <= 1.05) {
            return "Rectangular body shape, straight silhouette, athletic build, less defined waist";
        } else if (chestToWaistRatio > 1.05 && chestToWaistRatio <= 1.15) {
            return "Pear shape (Triangle), wider hips and thighs relative to the bust and shoulders, defined waist";
        } else if (chestToWaistRatio > 1.15 && chestToWaistRatio <= 1.25) {
            return "Classic Hourglass shape, well-defined waist, bust and hips are prominent and balanced";
        } else if (chestToWaistRatio > 1.25 && chestToWaistRatio <= 1.55) {
            return "Inverted Triangle shape, broad shoulders and bust, narrower hips, athletic upper body";
        } else {
            return "Apple shape (Round), rounder midsection, fuller waist, slimmer legs and arms";
        }
    }
}
