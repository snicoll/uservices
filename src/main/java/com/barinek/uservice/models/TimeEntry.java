package com.barinek.uservice.models;

import java.util.Date;

public class TimeEntry {
    private int id;
    private int projectId;
    private int userId;
    private Date date;
    private int hours;

    public TimeEntry() {
    }

    public TimeEntry(int projectId, int userId, Date date, int hours) {
        this.projectId = projectId;
        this.userId = userId;
        this.date = date;
        this.hours = hours;
    }

    public TimeEntry(int id, int projectId, int userId, Date date, int hours) {
        this.id = id;
        this.projectId = projectId;
        this.userId = userId;
        this.date = date;
        this.hours = hours;
    }

    public int getId() {
        return id;
    }

    public int getProjectId() {
        return projectId;
    }

    public int getUserId() {
        return userId;
    }

    public Date getDate() {
        return date;
    }

    public int getHours() {
        return hours;
    }
}
