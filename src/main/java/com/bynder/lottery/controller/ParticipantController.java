package com.bynder.lottery.controller;

import com.bynder.lottery.controller.dto.*;
import com.bynder.lottery.domain.entity.Lottery;
import com.bynder.lottery.mapper.ParticipantMapper;
import com.bynder.lottery.mapper.SubmissionMapper;
import com.bynder.lottery.service.ParticipantService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/participant")
public class ParticipantController {

    private final ParticipantService participantService;

    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public RegisterParticipantResponseDto register(@Valid @RequestBody RegisterParticipantRequestDto requestBody) {
        Long participantId = participantService.register(ParticipantMapper.map(requestBody));
        return RegisterParticipantResponseDto.build(participantId);
    }

    @GetMapping("/{ssn}/lotteries")
    public List<LotteryResponseDto> readActiveRegisteredLotteries(@PathVariable String ssn) {
        List<Lottery> lotteries = participantService.readActiveRegisteredLotteries(ssn);
        return
            lotteries
                .stream()
                .map(lottery ->
                    LotteryResponseDto.build(
                        lottery.getId(),
                        lottery.getName(),
                        lottery.getCreateDate(),
                        lottery.getAward(),
                        lottery.getBallotPrice(),
                        lottery.getBallotUnit(),
                        lottery.getState()
                    ))
                .collect(Collectors.toList());
    }

    @PostMapping("/{ssn}/submit")
    public ParticipantBallotsResponseDto submit(@Valid @RequestBody SubmissionRequestDto requestDto, @PathVariable String ssn) {
        List<String> ballots = participantService.submit(SubmissionMapper.map(requestDto, ssn));
        return ParticipantBallotsResponseDto.build(ballots);
    }

    @GetMapping("/{ssn}/lottery/{lotteryId}/ballots")
    public ParticipantBallotsResponseDto readAllBallotsOfLottery(@PathVariable String ssn, @PathVariable Long lotteryId) {
        List<String> ballots = participantService.readAllBallotsOfLottery(ssn, lotteryId);
        return ParticipantBallotsResponseDto.build(ballots);
    }

}
