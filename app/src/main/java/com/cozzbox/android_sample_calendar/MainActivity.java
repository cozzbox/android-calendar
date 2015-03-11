package com.cozzbox.android_sample_calendar;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.cozzbox.util.DateUtil;
import com.cozzbox.util.DisplayUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends ActionBarActivity {

    private static final float CELL_FONT_SIZE = 18f;

    private Calendar calendar;
    private int calWidth, cellWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendar = Calendar.getInstance();

        TextView nextMonth = (TextView) findViewById(R.id.txt_next);
        nextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, +1);
                createCalendar();
            }
        });

        TextView prevMonth = (TextView) findViewById(R.id.txt_prev);
        prevMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, -1);
                createCalendar();
            }
        });

        cellWidth = getCalendarCellWidth();
    }

    private int getCalendarCellWidth() {
        WindowManager wm = getWindowManager();

        Point point = new Point();
        Display display = wm.getDefaultDisplay();
        display.getSize(point);

        calWidth = point.x - (int) DisplayUtil.getDPtoPX(this, 22f);
        return calWidth / 7;
    }

    private void createCalendar() {
        String year = calendar.get(Calendar.YEAR) + "";
        String month= (calendar.get(Calendar.MONTH) +1) + "";

        TextView tvYM = (TextView) findViewById(R.id.txt_ym);
        tvYM.setText(year + "/" + month);
        tvYM.setWidth(calWidth);

        TableLayout tblCalendar = (TableLayout) findViewById(R.id.calendar_tbl_root);
        tblCalendar.removeAllViews();

        String[] strWeeks = getResources().getStringArray(R.array.calendar_weeks);
        TableRow rowWeeks = new TableRow(this);
        for (int i=0; i<strWeeks.length; i++) {
            TextView tv = new TextView(this);
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(getResources().getColor(R.color.text_dark));
            tv.setText(strWeeks[i]);
            tv.setWidth(cellWidth);

            rowWeeks.addView(tv);
        }
        tblCalendar.addView(rowWeeks);

        Calendar calClone = (Calendar) calendar.clone();
        calClone.set(Calendar.DAY_OF_MONTH, 1);

        int startWeek = calClone.get(Calendar.DAY_OF_WEEK);
        int endDay = calClone.getActualMaximum(GregorianCalendar.DATE);

        ArrayList<String> calDayList = new ArrayList<String>(40);

        for (int i=1; i<startWeek; i++) {
            calDayList.add("");
        }

        for (int i=1; i<=endDay; i++) {
            calDayList.add(i+"");
        }

        int endWeek = calDayList.size() % 7;
        if (endWeek != 0) {
            for (int i=endWeek; i<=7; i++) {
                calDayList.add("");
            }
        }

        TableRow row = null;
        int pos = 0;

        for (String tmpDay: calDayList) {

            if (pos % 7 == 0) {
                row = new TableRow(this);
                tblCalendar.addView(row);
            }

            TextView dayText = new TextView(this);
            dayText.setWidth(cellWidth);
            dayText.setHeight(cellWidth /2);
            dayText.setGravity(Gravity.CENTER_HORIZONTAL);
            dayText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, CELL_FONT_SIZE);
            dayText.setTextColor(getResources().getColor(R.color.text_dark));
            dayText.setText(tmpDay);

            RelativeLayout dayLayout = new RelativeLayout(this);
            dayLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            dayLayout.setTag(tmpDay);
            dayLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String year_ = calendar.get(Calendar.YEAR) + "";
                    String month_= (calendar.get(Calendar.MONTH) +1) + "";
                    Toast.makeText(getApplicationContext(), year_+"/"+month_+"/"+v.getTag(), Toast.LENGTH_LONG).show();
                }
            });

            TableRow.LayoutParams params = new TableRow.LayoutParams(cellWidth, cellWidth);
            params.setMargins(2,2,2,2);
            dayLayout.setLayoutParams(params);

            if (!tmpDay.equals("")) {
                Calendar cal = Calendar.getInstance();
                cal.set(Integer.parseInt(year), Integer.parseInt(month)-1, Integer.parseInt(tmpDay));
                if (DateUtil.formatDateYYYYMMDD(cal).equals(DateUtil.formatDateYYYYMMDD(Calendar.getInstance()))) {
                    dayLayout.setBackgroundResource(R.drawable.cal_back_today);
                } else {
                    dayLayout.setBackgroundResource(R.drawable.cal_back);
                }
                dayText.setTextColor(getResources().getColor(R.color.text_white));
            }

            LinearLayout dayItemLayout = new LinearLayout(this);
            dayItemLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            dayItemLayout.setOrientation(LinearLayout.VERTICAL);
            dayItemLayout.addView(dayText);

            dayLayout.addView(dayItemLayout);
            if (row != null) row.addView(dayLayout);
            pos++;
        }

        LinearLayout linerLayoutCal = (LinearLayout) findViewById(R.id.calendar_week_layout);
        linerLayoutCal.setVisibility(View.VISIBLE);
    }

    @Override
    public  void onStart() {
        createCalendar();

        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
