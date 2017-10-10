package com.chen.database.jedis.service;

/**
 */
public class GroupMessageData {
    private String groupId;
    private String msgId;
    private long score;

    public GroupMessageData(String groupId, String msgId, long score) {
        this.groupId = groupId;
        this.msgId = msgId;
        this.score = score;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }
}
