package com.bynder.lottery.service.impl;

import com.bynder.lottery.domain.LotteryState;
import com.bynder.lottery.domain.entity.Ballot;
import com.bynder.lottery.domain.entity.Lottery;
import com.bynder.lottery.domain.entity.Participant;
import com.bynder.lottery.domain.entity.Submission;
import com.bynder.lottery.exception.FinishedLotteryException;
import com.bynder.lottery.exception.NotFoundException;
import com.bynder.lottery.repository.BallotRepository;
import com.bynder.lottery.repository.ParticipantRepository;
import com.bynder.lottery.repository.SubmissionRepository;
import com.bynder.lottery.service.LotteryService;
import com.bynder.lottery.service.ParticipantService;
import com.bynder.lottery.util.BallotGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Transactional
public class ParticipantServiceImpl implements ParticipantService {

    private final ParticipantRepository participantRepository;
    private final SubmissionRepository submissionRepository;
    private final BallotRepository ballotRepository;
    private final LotteryService lotteryService;

    public ParticipantServiceImpl(ParticipantRepository participantRepository,
                                  SubmissionRepository submissionRepository,
                                  BallotRepository ballotRepository,
                                  LotteryService lotteryService) {
        this.participantRepository = participantRepository;
        this.submissionRepository = submissionRepository;
        this.ballotRepository = ballotRepository;
        this.lotteryService = lotteryService;
    }

    @Override
    public Long register(Participant participant) {
        if (!lotteryService.isActive(participant.getLottery().getId()))
            throw new FinishedLotteryException("Lottery is not active anymore !!!");

        Optional<Participant> optionalParticipant = participantRepository.findBySsnAndLottery(participant.getSsn(), participant.getLottery());
        Participant p = optionalParticipant.orElseGet(() -> participantRepository.save(participant));
        return p.getId();
    }

    @Override
    public List<String> submit(Submission submission) {
        Optional<Participant> optionalParticipant =
            participantRepository.findBySsnAndLottery(
                submission.getParticipant().getSsn(),
                submission.getParticipant().getLottery());

        if (optionalParticipant.isEmpty()) throw new NotFoundException("Participant is not registered for lottery !!!");
        Participant participant = optionalParticipant.get();
        if(LotteryState.FINISHED.equals(participant.getLottery().getState())) throw new FinishedLotteryException("Lottery is not active anymore !!!");
        submission.setParticipant(participant);
        Submission insertedSubmission = submissionRepository.save(submission);
        List<String> codes = new ArrayList<>();
        IntStream.rangeClosed(1, insertedSubmission.getNumberOfBallots()).forEach(prefix -> {
            Ballot b = new Ballot();
            b.setCode(BallotGenerator.generate(prefix, insertedSubmission.getParticipant().getId().intValue()));
            b.setSubmission(insertedSubmission);
            ballotRepository.save(b);
            codes.add(b.getCode());
        });
        return codes;
    }

    @Override
    public List<String> readAllBallotsOfLottery(String ssn, Long lotteryId) {
        List<Ballot> ballots = ballotRepository.findAllBySubmission_Participant_SsnAndSubmission_Participant_Lottery_Id(ssn, lotteryId);
        return ballots
            .stream()
            .map(Ballot::getCode)
            .collect(Collectors.toList());

    }

    @Override
    public List<Lottery> readActiveRegisteredLotteries(String ssn) {
        List<Participant> participants = participantRepository.findAllBySsnAndLottery_State(ssn, LotteryState.ACTIVE);
        return
            participants
                .stream()
                .map(Participant::getLottery)
                .collect(Collectors.toList());
    }

}
