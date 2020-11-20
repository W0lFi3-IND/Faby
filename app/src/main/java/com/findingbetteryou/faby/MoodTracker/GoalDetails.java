package com.findingbetteryou.faby.MoodTracker;

public class GoalDetails {
    private String goal;
    private String checkpointReached;

    public GoalDetails(String goal, String checkpointReached) {
        this.goal = goal;
        this.checkpointReached = checkpointReached;
    }

    public String getGoal() {
        return goal;
    }

    public String getCheckpointReached() {
        return checkpointReached;
    }
}
