package com.chen.database.jedis.service;

import java.util.ArrayList;

public class RegionBean extends ArrayList<Number> {
    public RegionBean() {
        super(2);
    }

    public Number getStart() {
        return get(0);
    }

    public long getStartAsLong() {
        Number start = getStart();
        if (start == null) return -1L;
        else return start.longValue();
    }

    public void setStart(Number start) {
        int size = size();
        if (size == 0) add(start);
        else set(0, start);
    }

    public Number getEnd() {
        int size = size();
        if (size < 2) return null;
        return get(1);
    }

    public long getEndAsLong() {
        Number end = getEnd();
        if (end == null) return -1L;
        else return end.longValue();
    }

    public void setEnd(Number end) {
        int size = size();
        if (size == 0) {
            add(0L);
            add(end);
        } else if (size == 1) {
            add(end);
        } else {
            set(1, end);
        }
    }

    public boolean contains(Number score) {
        if (score == null) {
            return getEnd() == null;
        }
        if (getStart() == null)
            return false;
        long startLong = getStart().longValue();
        long scoreLong = score.longValue();
        return scoreLong >= startLong && (getEnd() == null || getEnd().longValue() >= scoreLong);
    }
}
