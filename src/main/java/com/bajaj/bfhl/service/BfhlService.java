package com.bajaj.bfhl.service;

import com.bajaj.bfhl.dto.BfhlRequestDto;
import com.bajaj.bfhl.dto.BfhlResponseDto;

public interface BfhlService {
    BfhlResponseDto processRequest(BfhlRequestDto requestDto);
}
