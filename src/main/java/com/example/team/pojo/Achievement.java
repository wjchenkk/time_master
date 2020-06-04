package com.example.team.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "achievement")
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="achievement_id")
    @JsonIgnore
    private int aId;
    @Column(name = "a_status")
    private int status;
    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonIgnore
    private User user;
    @ManyToOne
    @JoinColumn(name="type")
    private AchievementType achievementType;


    public int getType() {
        return type;
    }

    public void setaId(int aId) {
        this.aId = aId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AchievementType getAchievementType() {
        return achievementType;
    }


    public void setAchievementType(AchievementType achievementType) {
        this.achievementType = achievementType;
    }
}
