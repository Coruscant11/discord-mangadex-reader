package fr.carlens.murasaki.back.api.wrapper.models;

import java.util.Map;

public class VolumeAggregate {
    private String volume;
    private int count;
    private Map<String, ChapterAggregate> chapters;

    public VolumeAggregate() {}

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Map<String, ChapterAggregate> getChapters() {
        return chapters;
    }

    public void setChapters(Map<String, ChapterAggregate> chapters) {
        this.chapters = chapters;
    }
}
