package com.bynder.lottery.service;

import com.bynder.lottery.domain.model.WinnerInfo;

public interface WinnerService {

    WinnerInfo readWinnerByDate(String date);
}
