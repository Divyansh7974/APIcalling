package com.bajaj.bfhl.service;

import com.bajaj.bfhl.dto.BfhlRequestDto;
import com.bajaj.bfhl.dto.BfhlResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BfhlServiceImpl implements BfhlService {

    @Value("${app.user-id:divyansh_porwal_23092005}")
    private String userId;

    @Value("${app.email:divyanshporwal230951@acropolis.in}")
    private String email;

    @Value("${app.roll-number:0827CS231085}")
    private String rollNumber;

    @Override
    public BfhlResponseDto processRequest(BfhlRequestDto requestDto) {
        List<String> inputData = requestDto.getData();
        
        List<String> oddNumbers = new ArrayList<>();
        List<String> evenNumbers = new ArrayList<>();
        List<String> alphabets = new ArrayList<>();
        List<String> specialCharacters = new ArrayList<>();
        
        long sum = 0;
        List<Character> allAlphabetChars = new ArrayList<>();

        if (inputData != null) {
            for (String str : inputData) {
                if (str == null) continue;

                // Check if string represents an integer
                if (str.matches("-?\\d+")) {
                    try {
                        long num = Long.parseLong(str);
                        sum += num;
                        if (num % 2 == 0) {
                            evenNumbers.add(str);
                        } else {
                            oddNumbers.add(str);
                        }
                    } catch (NumberFormatException e) {
                        // Fallback for extremely large numbers that exceed Long limits
                        // We still consider them numbers. Let's look at the last digit to check parity.
                        char lastChar = str.charAt(str.length() - 1);
                        int lastDigit = Character.getNumericValue(lastChar);
                        if (lastDigit % 2 == 0) {
                            evenNumbers.add(str);
                        } else {
                            oddNumbers.add(str);
                        }
                    }
                } 
                // Check if string consists purely of alphabetic characters
                else if (str.matches("[a-zA-Z]+")) {
                    alphabets.add(str.toUpperCase());
                } 
                // Otherwise, treat as special characters
                else {
                    specialCharacters.add(str);
                }

                // Extract all alphabetical characters for the reverse-alternating-caps concatenation
                for (int i = 0; i < str.length(); i++) {
                    char ch = str.charAt(i);
                    if (Character.isLetter(ch)) {
                        allAlphabetChars.add(ch);
                    }
                }
            }
        }

        // Generate the concatenation string
        // 1. Reverse the list of all alphabetical characters
        Collections.reverse(allAlphabetChars);
        
        // 2. Alternating caps (index 0: Upper, index 1: Lower, ...)
        StringBuilder concatBuilder = new StringBuilder();
        for (int i = 0; i < allAlphabetChars.size(); i++) {
            char ch = allAlphabetChars.get(i);
            if (i % 2 == 0) {
                concatBuilder.append(Character.toUpperCase(ch));
            } else {
                concatBuilder.append(Character.toLowerCase(ch));
            }
        }
        String concatString = concatBuilder.toString();

        return new BfhlResponseDto(
                true,
                userId,
                email,
                rollNumber,
                oddNumbers,
                evenNumbers,
                alphabets,
                specialCharacters,
                String.valueOf(sum),
                concatString
        );
    }
}
