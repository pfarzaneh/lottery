package com.bynder.lottery.controller;

import com.bynder.lottery.controller.dto.WinnerInfoResponseDto;
import com.bynder.lottery.domain.model.WinnerInfo;
import com.bynder.lottery.mapper.WinnerMapper;
import com.bynder.lottery.service.WinnerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/winner")
public class WinnerController {

    private final WinnerService winnerService;

    public WinnerController(WinnerService winnerService) {
        this.winnerService = winnerService;
    }

    @GetMapping("/of/{date}")
    public WinnerInfoResponseDto readWinnerByDate(@PathVariable String date) {
        WinnerInfo winnerInfo = winnerService.readWinnerByDate(date);
        return WinnerMapper.map(winnerInfo);
    }

}
