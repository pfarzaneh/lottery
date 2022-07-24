package com.bynder.lottery.controller;

import com.bynder.lottery.controller.dto.CreateLotteryRequestDto;
import com.bynder.lottery.controller.dto.CreateLotteryResponseDto;
import com.bynder.lottery.controller.dto.LotteryResponseDto;
import com.bynder.lottery.domain.entity.Lottery;
import com.bynder.lottery.mapper.LotteryMapper;
import com.bynder.lottery.service.LotteryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/lottery")
public class LotteryController {

    private final LotteryService lotteryService;

    public LotteryController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateLotteryResponseDto create(@Valid @RequestBody CreateLotteryRequestDto requestBody) {
        Long lotteryId = lotteryService.create(LotteryMapper.mapToActiveLottery(requestBody));
        return CreateLotteryResponseDto.build(lotteryId);
    }

    @GetMapping
    public List<LotteryResponseDto> readAll(@RequestParam(required = false, defaultValue = "0") @Min(0) Integer page,
                                            @RequestParam(required = false, defaultValue = "10") @Max(200) @Min(1) Integer size) {

        List<Lottery> lotteries = lotteryService.readAll(page, size);
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
                        lottery.getState()))
                .collect(Collectors.toList());
    }

    @PatchMapping("/{id}/finish")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void finish(@PathVariable Long id) {
        lotteryService.finish(id);
    }

}
