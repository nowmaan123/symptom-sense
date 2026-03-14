package com.symptomchecker.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class SymptomRequest {

    @NotEmpty(message = "At least one symptom must be provided")
    private List<String> symptoms;

    private Integer age;
    private String gender;

    // ─── Constructors ─────────────────────────────────────────
    public SymptomRequest() {}

    public SymptomRequest(List<String> symptoms, Integer age, String gender) {
        this.symptoms = symptoms;
        this.age = age;
        this.gender = gender;
    }

    // ─── Getters & Setters ────────────────────────────────────
    public List<String> getSymptoms() { return symptoms; }
    public void setSymptoms(List<String> symptoms) { this.symptoms = symptoms; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    @Override
    public String toString() {
        return "SymptomRequest{symptoms=" + symptoms + ", age=" + age + ", gender='" + gender + "'}";
    }
}
