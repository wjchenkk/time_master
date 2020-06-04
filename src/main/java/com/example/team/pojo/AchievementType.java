package com.example.team.pojo;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "achievementtype")
public class AchievementType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="achievementtype_id")
    @JsonIgnore
    private int aId;
    @Column(name="achievement_name")
    private String aname;
    @Column(name = "a_introduction")
    private String aintro;
    @Column(name = "acctime")
    @JsonIgnore
    private int acctime;

    @ManyToMany(mappedBy = "achievements", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    public int getAId() {
        return aId;
    }

    public void setaId(int aId) {
        this.aId = aId;
    }

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname;
    }

    public String getAintro() {
        return aintro;
    }

    public void setAintro(String aintro) {
        this.aintro = aintro;
    }

    public int getAcctime() {
        return acctime;
    }

    public void setAcctime(int acctime) {
        this.acctime = acctime;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
