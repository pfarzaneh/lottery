package com.bynder.lottery.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterParticipantResponseDto {

    private Long id;

    public static RegisterParticipantResponseDto build(Long id) {
        return new RegisterParticipantResponseDto(id);
    }
}
