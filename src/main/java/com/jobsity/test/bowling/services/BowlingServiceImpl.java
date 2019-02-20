package com.jobsity.test.bowling.services;

import com.jobsity.test.bowling.exceptions.InconsistentDataException;
import com.jobsity.test.bowling.model.Frame;
import com.jobsity.test.bowling.model.InputRoll;
import com.jobsity.test.bowling.util.BowlingUtils;
import com.jobsity.test.bowling.util.Constants;
import com.jobsity.test.bowling.util.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service("BowlingService")
public class BowlingServiceImpl implements BowlingService {

    @Override
    public Map<String, List<InputRoll>> readInputs() throws IOException {
        return FileUtil.readFile(Constants.FILE_INPUT_PAHT)
                .stream()
                .map(s -> {
                    String[] parts = s.split(Constants.TAB);
                    InputRoll inputRoll = new InputRoll(parts[0], parts[1]);
                    return inputRoll;
                })
                .collect(Collectors.groupingBy(InputRoll::getPlayer));
    }

    @Override
    public Map<String, List<Frame>> process(Map<String, List<InputRoll>> mapRolls) throws InconsistentDataException {
        try {


            Map<String, List<Frame>> mapFrames = new HashMap<>();

            mapRolls.forEach((player, rolls) -> {

                List<Frame> frames = new ArrayList<>();
                Integer frameIndex = 1;
                Integer currentRoll = 0;

                while (frameIndex < 11) {

                    Frame frame = new Frame();

                    frame.setIndex(frameIndex);
                    frame.setPlayer(player);

                    String currentScore = rolls.get(currentRoll).getScore();

                    if (frameIndex == 10 && validateTenPoints(currentScore)) {

                        frame.setRollOne(Constants.STRIKE);
                        String nextScore = rolls.get(++currentRoll).getScore();
                        String lastScore = rolls.get(++currentRoll).getScore();

                        if (validateTenPoints(nextScore)) {
                            frame.setRollTwo(Constants.STRIKE);

                            if (validateTenPoints(lastScore)) {
                                frame.setRollThree(Constants.STRIKE);
                            } else {
                                frame.setRollThree(lastScore);
                            }
                        } else {

                            frame.setRollTwo(nextScore);

                            if (validateTenPoints(nextScore, lastScore)) {
                                frame.setRollThree(Constants.SPARE);
                            } else {
                                frame.setRollThree(lastScore);
                            }

                        }

                    } else if (frameIndex == 10) {

                        frame.setRollOne(currentScore);
                        String nextScore = rolls.get(++currentRoll).getScore();

                        if (validateTenPoints(nextScore, currentScore)) {
                            frame.setRollTwo(Constants.SPARE);
                        } else {
                            frame.setRollTwo(nextScore);
                        }

                    } else {

                        if (currentScore.equals(Constants.FOUL)) {
                            frame.setRollOne(Constants.FOUL);

                            String nextScore = rolls.get(++currentRoll).getScore();

                            if (validateTenPoints(nextScore)) {
                                frame.setRollTwo(Constants.SPARE);
                            } else {
                                frame.setRollTwo(nextScore);
                            }
                        } else if (validateTenPoints(currentScore)) {
                            frame.setRollTwo(Constants.STRIKE);
                        } else {

                            String nextScore = rolls.get(++currentRoll).getScore();
                            frame.setRollOne(currentScore);

                            if (validateTenPoints(currentScore, nextScore)) {
                                frame.setRollTwo(Constants.SPARE);
                            } else {
                                frame.setRollTwo(nextScore);
                            }

                        }
                    }

                    frames.add(frame);
                    currentRoll++;
                    frameIndex++;

                }

                mapFrames.put(player, frames);
            });
            return mapFrames;
        } catch (IndexOutOfBoundsException ex) {
            throw new InconsistentDataException(ex);
        }

    }

    @Override
    public void processResults(Map<String, List<Frame>> mapFrames) throws InconsistentDataException {
        try {
            mapFrames.forEach((player, frames) -> {

                Integer currentScore = 0;

                for (int i = 0; i < frames.size(); i++) {
                    Integer scoreFrame = 0;
                    if (i < 9) {
                        if (frames.get(i).getRollTwo().equals(Constants.STRIKE)) {

                            scoreFrame += BowlingUtils.getNumericValue(frames.get(i).getRollTwo());

                            if (frames.get(i + 1).getRollTwo().equals(Constants.STRIKE)) {

                                scoreFrame += BowlingUtils.getNumericValue(frames.get(i + 1).getRollTwo());
                                if (frames.get(i + 2).getRollTwo().equals(Constants.STRIKE)) {

                                    scoreFrame += BowlingUtils.getNumericValue(frames.get(i + 2).getRollTwo());
                                } else {
                                    scoreFrame += BowlingUtils.getNumericValue(frames.get(i + 2).getRollOne());
                                }
                            } else if (frames.get(i + 1).getRollTwo().equals(Constants.SPARE)) {

                                scoreFrame += BowlingUtils.getNumericValue(frames.get(i + 1).getRollTwo());
                            } else {
                                scoreFrame += BowlingUtils.getNumericValue(frames.get(i + 1).getRollOne()) + BowlingUtils.getNumericValue(frames.get(i + 1).getRollTwo());
                            }
                        } else if (frames.get(i).getRollTwo().equals(Constants.SPARE)) {

                            scoreFrame += BowlingUtils.getNumericValue(frames.get(i).getRollTwo());
                            if (frames.get(i + 1).getRollTwo().equals(Constants.STRIKE)) {

                                scoreFrame += BowlingUtils.getNumericValue(frames.get(i + 1).getRollTwo());
                            } else {
                                scoreFrame += BowlingUtils.getNumericValue(frames.get(i + 1).getRollOne());
                            }

                        } else {
                            scoreFrame += BowlingUtils.getNumericValue(frames.get(i).getRollOne()) + BowlingUtils.getNumericValue(frames.get(i).getRollTwo());
                        }
                    } else {

                        if (frames.get(i).getRollOne().equals(Constants.STRIKE)) {
                            scoreFrame += BowlingUtils.getNumericValue(frames.get(i).getRollOne());

                            if (frames.get(i).getRollThree().equals(Constants.STRIKE)) {
                                scoreFrame += 20;
                            } else if (frames.get(i).getRollThree().equals(Constants.SPARE)) {
                                scoreFrame += BowlingUtils.getNumericValue(frames.get(i).getRollThree());
                            } else {
                                scoreFrame += BowlingUtils.getNumericValue(frames.get(i).getRollTwo()) + BowlingUtils.getNumericValue(frames.get(i).getRollThree());
                            }

                        } else if (frames.get(i).getRollTwo().equals(Constants.SPARE)) {
                            scoreFrame += 10;
                        } else {
                            scoreFrame += BowlingUtils.getNumericValue(frames.get(i).getRollOne()) + BowlingUtils.getNumericValue(frames.get(i).getRollTwo());
                        }

                    }

                    currentScore += scoreFrame;
                    frames.get(i).setScore(currentScore);

                }

            });
        } catch (IndexOutOfBoundsException ex) {
            throw new InconsistentDataException(ex);
        }
    }


    @Override
    public void showResults(Map<String, List<Frame>> mapFrames) throws InconsistentDataException {

        try {
            Integer lengthPlayerCell = 12;
            Integer lengthFrameheaderCell = 10;
            Integer lengthPinfallCell = 5;

            for (int i = 0; i <= 10; i++) {

                String cell;

                if (i == 0) {
                    cell = StringUtils.rightPad("Frame", lengthPlayerCell, StringUtils.SPACE);
                } else {
                    cell = StringUtils.rightPad(String.valueOf(i), lengthFrameheaderCell, StringUtils.SPACE);
                }

                System.out.print(cell);
            }

            System.out.println();

            //print players information
            mapFrames.forEach((player, frameList) -> {

                System.out.println(StringUtils.rightPad(player, lengthPlayerCell, StringUtils.SPACE));

                //print pinfalls
                for (int i = 0; i <= 10; i++) {

                    String cell;
                    if (i == 0) {
                        cell = StringUtils.rightPad("Pinfalls", lengthPlayerCell, StringUtils.SPACE);
                    } else {
                        cell = StringUtils.rightPad(Objects.toString(frameList.get(i - 1).getRollOne(), StringUtils.EMPTY), lengthPinfallCell, StringUtils.SPACE);
                        cell = cell + StringUtils.rightPad(Objects.toString(frameList.get(i - 1).getRollTwo(), StringUtils.EMPTY), lengthPinfallCell, StringUtils.SPACE);

                        if (i == 10) {
                            cell = cell + StringUtils.rightPad(Objects.toString(frameList.get(i - 1).getRollThree(), StringUtils.EMPTY), lengthPinfallCell, StringUtils.SPACE);
                        }
                    }

                    System.out.print(cell);
                }

                System.out.println();

                //print scores
                for (int i = 0; i <= 10; i++) {

                    String cell;
                    if (i == 0) {
                        cell = StringUtils.rightPad("Score", lengthPlayerCell, StringUtils.SPACE);
                    } else {
                        cell = StringUtils.rightPad(Objects.toString(frameList.get(i - 1).getScore(), StringUtils.EMPTY), lengthFrameheaderCell, StringUtils.SPACE);
                    }

                    System.out.print(cell);
                }

                System.out.println();

            });

        } catch (IndexOutOfBoundsException ex) {
            throw new InconsistentDataException(ex);
        }
    }

    private Boolean validateTenPoints(String... rolls) {

        Integer total = Arrays.asList(rolls).stream()
                .map(Integer::valueOf)
                .mapToInt(i -> i.intValue())
                .sum();

        return total.equals(10);
    }

}
