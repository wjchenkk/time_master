package com.example.team.util;

/**
 * 读取Excel时，封装读取的每一行的数据
 */
public class DataVo {
    /**
     * 用户名
     */
    private String userName;

    /**
     * 待办名
     */
    private String name;

    /**
     * 待办集名
     */
    private String sName;

    /**
     * 待办情况记录
     */
    private String record;

    /**
     * 待办情况记录
     */
    private String setRecord;

    /**
     * 待办完成度
     */
    private String completion;

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSName() {
        return sName;
    }

    public void setSName(String sName) {
        this.sName = sName;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getSetRecord() {
        return setRecord;
    }

    public void setSetRecord(String setRecord) {
        this.setRecord = setRecord;
    }

    public String getCompletion(){
        return completion;
    }

    public void setCompletion(String completion){
        this.completion = completion;
    }

}
