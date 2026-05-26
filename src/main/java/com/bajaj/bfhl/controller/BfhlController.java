package com.bajaj.bfhl.controller;

import com.bajaj.bfhl.dto.BfhlRequestDto;
import com.bajaj.bfhl.dto.BfhlResponseDto;
import com.bajaj.bfhl.service.BfhlService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/bfhl")
@CrossOrigin(origins = "*") // Allow requests from any origin (e.g., frontend frameworks)
public class BfhlController {

    private final BfhlService bfhlService;

    @Autowired
    public BfhlController(BfhlService bfhlService) {
        this.bfhlService = bfhlService;
    }

    @PostMapping
    public ResponseEntity<BfhlResponseDto> handlePost(@Valid @RequestBody BfhlRequestDto requestDto) {
        BfhlResponseDto response = bfhlService.processRequest(requestDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> handleGet() {
        Map<String, Object> response = new HashMap<>();
        response.put("operation_code", 1); // standard response for GET method
        return ResponseEntity.ok(response);
    }
}
