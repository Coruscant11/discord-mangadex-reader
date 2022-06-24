package fr.carlens.murasaki.logic.api.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChapterAggregate {

    private String chapter;
    private String id;
    private List<String> others;
    private int count;

    public ChapterAggregate() {}

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getOthers() {
        return others;
    }

    public void setOthers(List<String> others) {
        this.others = others;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static List<ChapterAggregate> sortChaptersList(Map<String, ChapterAggregate> chapters) {
        var list = new ArrayList<>(chapters.values().stream().toList());
        list.sort((v1, v2) -> {
            int v1d, v2d;
            try {
                v1d = v1.getChapter().equalsIgnoreCase("none") ? Integer.MAX_VALUE : Integer.parseInt(v1.getChapter());
            } catch (NumberFormatException e) {
                v1d = Integer.MAX_VALUE;
            }
            try {
                v2d = v2.getChapter().equalsIgnoreCase("none") ? Integer.MAX_VALUE : Integer.parseInt(v2.getChapter());
            } catch (NumberFormatException e) {
                v2d = Integer.MAX_VALUE;
            }

            return v1d - v2d;
        });

        return list;
    }
}
