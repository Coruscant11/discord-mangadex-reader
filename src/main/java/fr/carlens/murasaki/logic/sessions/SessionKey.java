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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj instanceof SessionKey otherKey) {
            return this.channelId.equals(otherKey.channelId) && this.userId.equals(otherKey.userId);
        }

        return super.equals(obj);
    }
}
