package com.getindata.tutorial.base.model;

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.Instant;

@JsonSerialize
public class SongEvent {

    public static SongEventBuilder builder() {
        return new SongEventBuilder();
    }

    private long songId;
    private long timestamp;
    private SongEventType type;
    private int userId;

    public SongEvent() {
    }

    public SongEvent(long songId, long timestamp, SongEventType type, int userId) {
        this.songId = songId;
        this.timestamp = timestamp;
        this.type = type;
        this.userId = userId;
    }

    public long getSongId() {
        return songId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public SongEventType getType() {
        return type;
    }

    public int getUserId() {
        return userId;
    }

    public void setSongId(long songId) {
        this.songId = songId;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setType(SongEventType type) {
        this.type = type;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "SongEvent{" +
                "songId=" + songId +
                ", timestamp=" + Instant.ofEpochMilli(timestamp) +
                ", type=" + type +
                ", userId=" + userId +
                '}';
    }

}
