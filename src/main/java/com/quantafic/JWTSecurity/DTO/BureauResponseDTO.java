package com.quantafic.JWTSecurity.DTO;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BureauResponseDTO {
    private ControlData controlData;
    private List<ConsumerCreditData> consumerCreditData;
    public ControlData getControlData() {
        return controlData;
    }

    public void setControlData(ControlData controlData) {
        this.controlData = controlData;
    }



    public List<ConsumerCreditData> getConsumerCreditData() {
        return consumerCreditData;
    }

    public void setConsumerCreditData(List<ConsumerCreditData> consumerCreditData) {
        this.consumerCreditData = consumerCreditData;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ControlData {
        private boolean success;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ConsumerCreditData {
        private List<Score> scores;

        public List<Score> getScores() {
            return scores;
        }

        public void setScores(List<Score> scores) {
            this.scores = scores;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Score {
        private String scoreName;
        private String score;

        public String getScoreName() {
            return scoreName;
        }

        public void setScoreName(String scoreName) {
            this.scoreName = scoreName;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        @Override
        public String toString() {
            return "Score{" +
                    "scoreName='" + scoreName + '\'' +
                    ", score='" + score + '\'' +
                    '}';
        }
    }
}
