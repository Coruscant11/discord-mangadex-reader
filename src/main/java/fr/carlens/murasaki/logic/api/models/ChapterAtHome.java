package fr.carlens.murasaki.logic.api.models;

import java.util.List;

public class ChapterAtHome {

    public enum PageQuality {
        DATA("data"),
        DATA_SAVER("data_saver");

        private String value;
        PageQuality(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    };

    private String hash;
    private List<String> data;
    private List<String> dataSaver;

    public ChapterAtHome() {}

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public List<String> getDataSaver() {
        return dataSaver;
    }

    public void setDataSaver(List<String> dataSaver) {
        this.dataSaver = dataSaver;
    }

    public static String buildPageUrl(PageQuality quality, String baseUrl, String chapterHash, String qualityUrl) {
        StringBuilder sb = new StringBuilder();
        sb.append(baseUrl);
        sb.append("/");
        sb.append(quality);
        sb.append("/");
        sb.append(chapterHash);
        sb.append("/");
        sb.append(qualityUrl);
        return sb.toString();
    }
}
