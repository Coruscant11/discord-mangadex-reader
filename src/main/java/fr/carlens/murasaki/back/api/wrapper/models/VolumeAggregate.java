package fr.carlens.murasaki.back.api.wrapper.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
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

    public static List<VolumeAggregate> sortVolumesMap(Map<String, VolumeAggregate> volumes) {
        var list = new ArrayList<>(volumes.values().stream().toList());
        list.sort((v1, v2) -> {
            int v1d, v2d;
            try {
                v1d = v1.getVolume().equalsIgnoreCase("none") ? Integer.MAX_VALUE : Integer.parseInt(v1.getVolume());
            } catch (NumberFormatException e) {
                v1d = Integer.MAX_VALUE;
            }
            try {
                v2d = v2.getVolume().equalsIgnoreCase("none") ? Integer.MAX_VALUE : Integer.parseInt(v2.getVolume());
            } catch (NumberFormatException e) {
                v2d = Integer.MAX_VALUE;
            }

            return v1d - v2d;
        });

        return list;
    }
}
