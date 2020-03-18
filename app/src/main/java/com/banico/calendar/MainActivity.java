package com.banico.calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {

    AppCompatTextView date;
    GridView gridView;
    Button next, previous;

    DateConvertor dateConvertor;
    int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        date = findViewById(R.id.date);
        gridView = findViewById(R.id.grid);
        next = findViewById(R.id.next);
        previous = findViewById(R.id.previous);

        InitCalendar();
        setButtons();
    }

    private void InitCalendar() {
        dateConvertor = new DateConvertor();
        date.setText(dateConvertor.getIranianYear() + "/" + dateConvertor.getIranianMonth());
        year = dateConvertor.getIranianYear();
        month = dateConvertor.getIranianMonth();
        day = dateConvertor.getIranianDay();
        JalaliCalendarRVAdapter jalaliCalendarRVAdapter = new JalaliCalendarRVAdapter(this, year, month, day);
        gridView.setAdapter(jalaliCalendarRVAdapter);
    }

    private void setButtons() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (month > 11) {
                    year++;
                    month = 1;
                }else {
                    month++;
                }

                date.setText(year + "/" + month);
                day = (dateConvertor.getIranianYear() == year && dateConvertor.getIranianMonth() == month) ? dateConvertor.getIranianDay() : 1;
                gridView.setAdapter(new JalaliCalendarRVAdapter(MainActivity.this, year, month, day));
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (month <= 1) {
                    year--;
                    month = 12;
                }else {
                    month--;
                }

                date.setText(year + "/" + month);
                day = (dateConvertor.getIranianYear() == year && dateConvertor.getIranianMonth() == month) ? dateConvertor.getIranianDay() : 1;
                gridView.setAdapter(new JalaliCalendarRVAdapter(MainActivity.this, year, month, day));
            }
        });
    }
}
