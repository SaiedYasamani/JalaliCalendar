package com.banico.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class JalaliCalendarRVAdapter extends BaseAdapter {

    private DateConvertor dateConvertor;
    private Context _context;
    private List<String> list;
    private static final int DAY_OFFSET = 0;

    private int daysInMonth;
    private int currentDayOfMonth;
    private AppCompatTextView txt_day;


    public JalaliCalendarRVAdapter(Context context, int year, int month, int day) {
        super();
        this._context = context;
        this.list = new ArrayList<>();
        this.dateConvertor = new DateConvertor();
        this.dateConvertor.setIranianDate(year, month, day);
        this.currentDayOfMonth = this.dateConvertor.getIranianDay();
        this.printMonth(month, year);

    }

    private void printMonth(int mm, int yy) {
        //Log.e(tag, "==> printMonth: mm: " + mm + " " + "yy: " + yy);
        int trailingSpaces = 0;
        int daysInPrevMonth = 0;
        int prevMonth = 0;
        int prevYear = 0;
        int nextMonth = 0;
        int nextYear = 0;

        int currentMonth = mm - 1;
        String currentMonthName = this.dateConvertor.getMonthName(currentMonth);//getMonthAsString(currentMonth);
        daysInMonth = this.dateConvertor.getDaysInMonth(currentMonth);//getNumberOfDaysOfMonth(currentMonth);

        //Log.e(tag, "Current Month: " + " " + currentMonthName + " having " + daysInMonth + " days.");

        //Log.e(tag, "Jalali Calendar:= " + this.dateConvertor.getIranianMonth() + " " + this.dateConvertor.getIranianDay());

        if (currentMonth == 11) {
            prevMonth = currentMonth - 1;
            daysInPrevMonth = this.dateConvertor.getDaysInMonth(prevMonth) + 1;//getNumberOfDaysOfMonth(prevMonth);
            nextMonth = 0;
            prevYear = yy;
            nextYear = yy + 1;
            /*Log.d(tag, "*->PrevYear: " + prevYear + " PrevMonth:"
                    + prevMonth + " NextMonth: " + nextMonth
                    + " NextYear: " + nextYear);*/
        } else if (currentMonth == 0) {
            prevMonth = 11;
            prevYear = yy - 1;
            nextYear = yy;
            daysInPrevMonth = this.dateConvertor.getDaysInMonth(prevMonth) + 1;//getNumberOfDaysOfMonth(prevMonth);
            nextMonth = 1;
            /*Log.d(tag, "**--> PrevYear: " + prevYear + " PrevMonth:"
                    + prevMonth + " NextMonth: " + nextMonth
                    + " NextYear: " + nextYear);*/
        } else {
            prevMonth = currentMonth - 1;
            nextMonth = currentMonth + 1;
            nextYear = yy;
            prevYear = yy;
            daysInPrevMonth = this.dateConvertor.getDaysInMonth(prevMonth) + 1;//getNumberOfDaysOfMonth(prevMonth);
            /*Log.d(tag, "***---> PrevYear: " + prevYear + " PrevMonth:"
                    + prevMonth + " NextMonth: " + nextMonth
                    + " NextYear: " + nextYear);*/
        }

        DateConvertor tmpDateConvertor = new DateConvertor();
        tmpDateConvertor.setIranianDate(yy, mm, 1);
        //this.dateConvertor.setIranianDate(yy, mm, 1);
        GregorianCalendar cal = new GregorianCalendar(tmpDateConvertor.getGregorianYear(), tmpDateConvertor.getGregorianMonth() - 1, tmpDateConvertor.getGregorianDay());
        ////
        int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;

        trailingSpaces = (currentWeekDay + 1) % 7;

        /*Log.d(tag, "Week Day:" + currentWeekDay + " is " + this.dateConvertor.getIranianDay());//getWeekDayAsString(currentWeekDay));
        Log.d(tag, "No. Trailing space to Add: " + trailingSpaces);
        Log.d(tag, "No. of Days in Previous Month: " + daysInPrevMonth);*/

        /*Calculate Leap Years*/
        if (tmpDateConvertor.IsLeap(yy))
            if (mm == 12)
                ++daysInMonth;
            else if (mm == 1)
                ++daysInPrevMonth;

        // Trailing Month days
        for (int i = 0; i < trailingSpaces; i++) {
            /*Log.d(tag,
                    "PREV MONTH:= "
                            + prevMonth
                            + " => "
                            + this.dateConvertor.getMonthName(prevMonth)
                            + " "
                            + String.valueOf((daysInPrevMonth
                            - trailingSpaces + DAY_OFFSET)
                            + i));*/
            list.add(String
                    .valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET) + i)
                    + "-GREY"
                    + "-"
                    + this.dateConvertor.getMonthName(prevMonth)//getMonthAsString(prevMonth)
                    + "-"
                    + prevYear);
        }

        // Current Month Days
        for (int i = 1; i <= daysInMonth; i++) {

            if (i == this.currentDayOfMonth && this.currentDayOfMonth == new DateConvertor().getIranianDay()) {
                list.add(String.valueOf(i) + "-RED" + "-" + this.dateConvertor.getMonthName(currentMonth) + "-" + yy);
            } else {
                list.add(String.valueOf(i) + "-GREEN" + "-" + this.dateConvertor.getMonthName(currentMonth) + "-" + yy);
            }

        }

        // Leading Month days
        // Man
        for (int i = 0; i < list.size() % 7 + 7; i++) {
            //Log.d(tag, "NEXT MONTH:= " + this.dateConvertor.getMonthName(nextMonth));
            list.add(String.valueOf(i + 1) + "-GREY" + "-" + this.dateConvertor.getMonthName(nextMonth) + "-" + nextYear);
        }

        //this.change2RTL();
    }

    /**
     * Changes the order of the List items to suite the RTL formatting.
     */
    private void change2RTL() {
        List<String> tmList1 = new ArrayList<String>();
        List<String> lstRTL = new ArrayList<String>();

        for (int i = 0; i < this.list.size(); i += 7) {
            tmList1.clear();
            for (int c = i; c < i + 7; c++) {
                tmList1.add(this.list.get(c));
            }

            Collections.reverse(tmList1);
            lstRTL.addAll(tmList1);
        }

        this.list.clear();
        this.list.addAll(lstRTL);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.row_calendar, parent, false);
        }

        txt_day = row.findViewById(R.id.txt_day);
        String[] dayElements = list.get(position).split("-");
        String theday = dayElements[0];
        String themonth = dayElements[2];
        String theyear = dayElements[3];

        txt_day.setText(theday);
        txt_day.setTag(theday + "-" + themonth + "-" + theyear);

        if (dayElements[1].equals("GREY")) {
            txt_day.setTextColor(this._context.getResources().getColor(R.color.colorGray));
        }
        if (dayElements[1].equals("GREEN")) {
            txt_day.setTextColor(this._context.getResources().getColor(R.color.colorGreen));
        }
        if (dayElements[1].equals("RED")) {
            txt_day.setTextColor(this._context.getResources().getColor(R.color.colorRed));
        }

        return row;
    }

    public long getDatesDifference(String match_date) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date today = Calendar.getInstance().getTime();
        String currentDate = formatter.format(today);

        Date date1 = null;
        Date date2 = null;
        try {
            date1 = formatter.parse(currentDate);
            date2 = formatter.parse(match_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long difference = date2.getTime() - date1.getTime();

        return difference;
    }

}
