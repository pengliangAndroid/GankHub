package com.wstrong.mygank;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@RunWith(JUnit4.class)
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date d1 = new Date();
            Date d2 = sdf.parse("2016-09-13 08:55");
            long diff = d1.getTime() - d2.getTime();
            double hour = diff * 1.0 / (1000 * 60 * 60);
            System.out.println("hour"+hour);
            //String result = String.format("%.2f",hour);
            int hourInt = (int) hour;
            if(hour - hourInt > 0.0){
                hourInt = hourInt + 1;
            }

            System.out.println(hourInt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testComparable(){
        List<String> dataList = new ArrayList<>();
        dataList.add("2015-07-05");
        dataList.add("2015-07-06");
        dataList.add("2015-07-07");

        Collections.sort(dataList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        });

        for (int i = 0; i < dataList.size(); i++) {
            System.out.println(dataList.get(i));
        }
    }
}