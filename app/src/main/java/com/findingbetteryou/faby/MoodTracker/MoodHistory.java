package com.findingbetteryou.faby.MoodTracker;

public class MoodHistory {
    String timeStamp;
    String energyLevel;
    String mood;

    public MoodHistory(String timeStamp, String energyLevel, String mood) {
        this.timeStamp = timeStamp;
        this.energyLevel = energyLevel;
        this.mood = mood;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getEnergyLevel() {
        return energyLevel;
    }

    public String getMood() {
        return mood;
    }
}
