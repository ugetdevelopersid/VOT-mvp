package com.ugetsidfashion.vot;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class UserProfile {
    @Id
    @JsonIgnore
    private long id;

    @Column(nullable = false)
    @NaturalId
    @NotNull
    @Size(max = 255)
    private String email;

    @Column(nullable = false)
    @NotNull
    @Size(max = 20)
    private String gender;

    @Column(nullable = false)
    @NotNull
    @Min(1)
    private int age;

    @Column(nullable = false)
    @NotNull
    @Min(1)
    private int height;

    @Column(nullable = false)
    @NotNull
    @Min(1)
    private int chestSize;

    @Column(nullable = false)
    @NotNull
    @Min(1)
    private int waistSize;

    @Column(nullable = false)
    @NotNull
    @Min(1)
    private int hipSize;

    @Column(nullable = false)
    @NotNull
    @Size(max = 50)
    private String skinTone;

    @Column(nullable = false)
    @NotNull
    @Size(max = 20)
    private String bodySize;

    @Size(max = 20)
    private String faceShape;

    @Column(nullable = false)
    @NotNull
    @Size(max = 20)
    private String hairLength;

    @Column(nullable = false)
    @NotNull
    @Size(max = 20)
    private String hairTexture;

    @Column(nullable = false)
    @NotNull
    @Size(max = 20)
    private String hairColour;

    public UserProfile() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getChestSize() {
        return chestSize;
    }

    public void setChestSize(int chestSize) {
        this.chestSize = chestSize;
    }

    public int getWaistSize() {
        return waistSize;
    }

    public void setWaistSize(int waistSize) {
        this.waistSize = waistSize;
    }

    public int getHipSize() {
        return hipSize;
    }

    public void setHipSize(int hipSize) {
        this.hipSize = hipSize;
    }

    public String getSkinTone() {
        return skinTone;
    }

    public void setSkinTone(String skinTone) {
        this.skinTone = skinTone;
    }

    public String getBodySize() {
        return bodySize;
    }

    public void setBodySize(String bodySize) {
        this.bodySize = bodySize;
    }

    public String getFaceShape() {
        return faceShape;
    }

    public void setFaceShape(String faceShape) {
        this.faceShape = faceShape;
    }

    public String getHairLength() {
        return hairLength;
    }

    public void setHairLength(String hairLength) {
        this.hairLength = hairLength;
    }

    public String getHairTexture() {
        return hairTexture;
    }

    public void setHairTexture(String hairTexture) {
        this.hairTexture = hairTexture;
    }

    public String getHairColour() {
        return hairColour;
    }

    public void setHairColour(String hairColour) {
        this.hairColour = hairColour;
    }
}
