package fr.carlens.murasaki.logic.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = false)
public class MangaAttributes {

    private Map<String, String> title;
    private List<Map<String, String>> altTitles;
    private Map<String, String> description;
    private boolean isLocked;
    private Map<String, String> links;
    private String originalLanguage;
    private String lastVolume;
    private String lastChapter;
    private String publicationDemographic;
    private String status;
    private String state;
    private String createdAt;
    private String updatedAt;
    private int year;
    private int version;
    private String contentRating;
    private boolean chapterNumbersResetOnNewVolume;
    private List<String> availableTranslatedLanguages;
    private List<Tag> tags;



    public MangaAttributes() {}

    public Map<String, String> getTitle() {
        return title;
    }

    public String getTitle(String language) {
        if (title.containsKey(language)) {
            return title.get(language);
        } else {
            if (title.containsKey("en")) {
                return title.get("en");
            } else {
                return title.values().iterator().next();
            }
        }
    }

    public void setTitle(Map<String, String> title) {
        this.title = title;
    }

    public List<Map<String, String>> getAltTitles() {
        return altTitles;
    }

    public void setAltTitles(List<Map<String, String>> altTitles) {
        this.altTitles = altTitles;
    }

    public Map<String, String> getDescription() {
        return description;
    }

    public void setDescription(Map<String, String> description) {
        this.description = description;
    }

    public boolean isLocked() {
        return isLocked;
    }

    @JsonProperty("isLocked")
    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public Map<String, String> getLinks() {
        return links;
    }

    public void setLinks(Map<String, String> links) {
        this.links = links;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getLastVolume() {
        return lastVolume;
    }

    public void setLastVolume(String lastVolume) {
        this.lastVolume = lastVolume;
    }

    public String getLastChapter() {
        return lastChapter;
    }

    public void setLastChapter(String lastChapter) {
        this.lastChapter = lastChapter;
    }

    public String getPublicationDemographic() {
        return publicationDemographic;
    }

    public void setPublicationDemographic(String publicationDemographic) {
        this.publicationDemographic = publicationDemographic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getContentRating() {
        return contentRating;
    }

    public void setContentRating(String contentRating) {
        this.contentRating = contentRating;
    }

    public boolean isChapterNumbersResetOnNewVolume() {
        return chapterNumbersResetOnNewVolume;
    }

    public void setChapterNumbersResetOnNewVolume(boolean chapterNumbersResetOnNewVolume) {
        this.chapterNumbersResetOnNewVolume = chapterNumbersResetOnNewVolume;
    }

    public List<String> getAvailableTranslatedLanguages() {
        return availableTranslatedLanguages;
    }

    public void setAvailableTranslatedLanguages(List<String> availableTranslatedLanguages) {
        this.availableTranslatedLanguages = availableTranslatedLanguages;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
