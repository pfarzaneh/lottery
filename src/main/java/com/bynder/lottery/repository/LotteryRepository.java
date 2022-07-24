package com.bynder.lottery.repository;

import com.bynder.lottery.domain.entity.Lottery;
import com.bynder.lottery.domain.LotteryState;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LotteryRepository extends JpaRepository<Lottery, Long> {

    List<Lottery> findAllByOrderByCreateDateDesc(Pageable pageable);

    @Modifying
    @Query("update Lottery l set l.state = :state where l.id = :id")
    void finish(@Param("id") Long id, @Param("state") LotteryState state);
}
