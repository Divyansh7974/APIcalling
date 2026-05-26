package com.bajaj.bfhl.dto;

import java.util.List;
import jakarta.validation.constraints.NotNull;

public class BfhlRequestDto {
    
    @NotNull(message = "Input data array 'data' cannot be null")
    private List<String> data;

    // Constructors
    public BfhlRequestDto() {
    }

    public BfhlRequestDto(List<String> data) {
        this.data = data;
    }

    // Getters and Setters
    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
