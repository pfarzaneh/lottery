package com.bynder.lottery.domain.entity;

import com.bynder.lottery.domain.BallotUnit;
import com.bynder.lottery.domain.LotteryState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Lottery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String name;
    @NotNull
    private Timestamp createDate;
    @NotEmpty
    private String award;
    @NotNull
    @PositiveOrZero
    private Long ballotPrice;
    @NotNull
    @Enumerated(EnumType.STRING)
    private BallotUnit ballotUnit;
    @NotNull
    @Enumerated(EnumType.STRING)
    private LotteryState state;

    private Lottery(Long id) {
        this.id = id;
    }

    public static Lottery build(Long id) {
        return new Lottery(id);
    }

}
