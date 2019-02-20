package com.jobsity.test.bowling.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Frame {

    private String player;
    private String rollOne;
    private String rollTwo;
    private String rollThree;
    private Integer score;
    private Integer index;

}
