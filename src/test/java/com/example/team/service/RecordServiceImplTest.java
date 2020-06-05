package com.example.team.service;

import com.example.team.dao.DailyRecordDAO;
import com.example.team.pojo.AccRecord;
import com.example.team.pojo.DailyRecord;
import com.example.team.pojo.MonthRecord;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class RecordServiceImplTest {
    @Autowired
    private RecordService recordService;
    @Autowired
    private DailyRecordDAO dailyRecordDAO;

    /*@Test
    void getAccRecord() {

        //AccRecord accRecord = recordService.getAccRecord(2);
        //recordService.addAccRecord(5,5,2);

        recordService.updateAccRecord(5,10,2);
        AccRecord accRecord = recordService.getAccRecord(5);
        Assert.assertEquals(1, accRecord.getAccRecordId());
        Assert.assertEquals(2, accRecord.getSuccessCount());
        Assert.assertEquals(0, accRecord.getFailCount());
    }/*

    @Test
    void getDailyRecord() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = simpleDateFormat.parse("2020-04-10 08:00:00");
        DailyRecord dailyRecord = recordService.getDailyRecord(2, new Date(date.getTime()));
        Assert.assertEquals(1, dailyRecord.getDailyRecordId());
        Assert.assertEquals(0, dailyRecord.getSuccessCount());
        Assert.assertEquals(0, dailyRecord.getFailCount());
    }

    @Test
    void getMonthRecord() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = simpleDateFormat.parse("2020-04-01 08:00:00");
        MonthRecord monthRecord = recordService.getMonthRecord(2, new Date(date.getTime()));
        Assert.assertEquals(1, monthRecord.getMonthRecordId());
        Assert.assertEquals(0, monthRecord.getSuccessCount());
        Assert.assertEquals(0, monthRecord.getFailCount());
    }

    /*@Test
    void getTypeRecord() {
    }*/

    /*@Test
    void listDailyRecordByMonth() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date1 = simpleDateFormat.parse("2020-04-01");
        java.util.Date date2 = simpleDateFormat.parse("2020-05-01");
        List<DailyRecord> list = recordService.listDailyRecordByMonth(2, new Date(date1.getTime()),
                new Date(date2.getTime()));
        Assert.assertEquals(1, list.get(0).getDailyRecordId());
        Assert.assertEquals(0, list.get(0).getSuccessCount());
        Assert.assertEquals(0, list.get(0).getFailCount());
    }*/
}