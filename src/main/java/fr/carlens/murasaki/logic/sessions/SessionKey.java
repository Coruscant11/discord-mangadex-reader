package fr.carlens.murasaki.logic.sessions;

public class SessionKey {
    public final Long channelId;
    public final Long userId;

    public SessionKey(long channelId, long userId) {
        this.channelId = channelId;
        this.userId = userId;
    }

    public Long getChannelId() {
        return channelId;
    }

    public Long getUserId() {
        return userId;
    }
}
