package com.barinek.uservice.models;

import java.util.Date;

public class Allocation {
    private int id;
    private int userId;
    private int projectId;
    private Date firstDay;
    private Date lastDay;

    public Allocation() { // for jackson
    }

    public Allocation(int projectId, int userId, Date firstDay, Date lastDay) { // for rest
        this.projectId = projectId;
        this.userId = userId;
        this.firstDay = firstDay;
        this.lastDay = lastDay;
    }

    public Allocation(int id, int projectId, int userId, Date firstDay, Date lastDay) { // for jdbc
        this.id = id;
        this.projectId = projectId;
        this.userId = userId;
        this.firstDay = firstDay;
        this.lastDay = lastDay;
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

    public Date getFirstDay() {
        return firstDay;
    }

    public Date getLastDay() {
        return lastDay;
    }
}
