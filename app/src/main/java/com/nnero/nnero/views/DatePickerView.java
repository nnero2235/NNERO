package com.nnero.nnero.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by NNERO on 16/1/28.
 */
public class DatePickerView extends LinearLayout{
    public DatePickerView(Context context) {
        super(context);
    }

    public DatePickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DatePickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


//    @InjectView(R.id.year_view)  WheelView yearView;
//    @InjectView(R.id.month_view) WheelView monthView;
//    @InjectView(R.id.day_view)   WheelView dayView;
//
//    private Calendar calendar;
//    private View datePickView; //布局填充形式 植入选择器
//
//    private int defCurTvSize   = 47; //默认中心位置字体
//    private int defCurItemSize = 42;//默认外围位置字体
//
//    private int minYear = 2009; //年最低值
//
//    private NumericWheelAdapter yearAdapter;
//    private NumericWheelAdapter monthAdapter;
//    private NumericWheelAdapter dayAdapter;
//    private int                 currentYear;
//    private int                 currentMonth;
//    private int currentDay;
//    private boolean isMonthChanged; //避免频繁改变月份adapter
//
//    private String yearText;
//    private String monthText;
//    private String dayText;
//
//    public DatePickerView(Context context) {
//        super(context);
//        init();
//    }
//
//    public DatePickerView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init();
//    }
//
//    public DatePickerView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init();
//    }
//
//    private void init() {
//        setPadding(0, ScreenSizeCaculation.dip2px(getContext(), 25), 0, ScreenSizeCaculation.dip2px(getContext(), 25));
//        calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
//
//        yearText = getResources().getString(R.string.edu_baby_year);
//        monthText = getResources().getString(R.string.edu_baby_month);
//        dayText = getResources().getString(R.string.edu_baby_day);
//
//        initViews();
//        initListeners();
//        initCurrentDay();
//    }
//
//    //设置当前时间是 今天
//    private void initCurrentDay() {
//        currentYear = calendar.get(Calendar.YEAR);
//        currentMonth = calendar.get(Calendar.MONTH);
//        currentDay = calendar.get(Calendar.DAY_OF_MONTH);
//
//        changeMonthAndDayByCurrentYear(currentYear);
//        changeDayByCurrentYearAndMonth(currentYear, currentMonth);
//
//        yearView.setCurrentItem(currentYear - minYear);
//        monthView.setCurrentItem(currentMonth);
//        dayView.setCurrentItem(currentDay - 1);
//
//    }
//
//    private void initViews(){
//        datePickView = View.inflate(getContext(), R.layout.date_picker_view, null);
//        ButterKnife.inject(this, datePickView);
//
//        LinearLayout centerArea = new LinearLayout(getContext());
//        centerArea.setOrientation(LinearLayout.VERTICAL);
//        RelativeLayout.LayoutParams centerParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        centerParams.addRule(RelativeLayout.CENTER_IN_PARENT);
//        centerArea.setLayoutParams(centerParams);
//
//        View lineViewTop = new View(getContext());
//        View lineViewDown = new View(getContext());
//        lineViewTop.setBackgroundColor(getResources().getColor(R.color.edu_date_picker_line_bg));
//        lineViewDown.setBackgroundColor(getResources().getColor(R.color.edu_date_picker_line_bg));
//        LinearLayout.LayoutParams lineTopParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ScreenSizeCaculation.dip2px(getContext(),1));
//        LinearLayout.LayoutParams lineDownParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ScreenSizeCaculation.dip2px(getContext(),1));
//        lineDownParams.topMargin =  ScreenSizeCaculation.dip2px(getContext(),32);
//        centerArea.addView(lineViewTop,lineTopParams);
//        centerArea.addView(lineViewDown,lineDownParams);
//
//        yearView.setVisibleItems(5);
//        monthView.setVisibleItems(5);
//        dayView.setVisibleItems(5);
//
//        int curTextSize = Util.getFrontSize(getContext(), defCurTvSize);
//        int itemSize = Util.getFrontSize(getContext(),defCurItemSize);
//        yearView.setTextSize(curTextSize, itemSize);
//        monthView.setTextSize(curTextSize, itemSize);
//        dayView.setTextSize(curTextSize, itemSize);
//
//        yearView.setCyclic(true);
//        monthView.setCyclic(true);
//        dayView.setCyclic(true);
//
//        addView(datePickView);
//        addView(centerArea);
//    }
//
//    private void initListeners(){
//        yearView.addScrollingListener(new OnWheelScrollListener() {
//            @Override
//            public void onScrollingStarted(WheelView wheel) {
//            }
//
//            @Override
//            public void onScrollingFinished(WheelView wheel) {
//                changeMonthAndDayByCurrentYear(Integer.parseInt(yearAdapter.getItem(yearView.getCurrentItem()).replace(yearText,"")));
//            }
//        });
//        monthView.addScrollingListener(new OnWheelScrollListener() {
//            @Override
//            public void onScrollingStarted(WheelView wheel) {
//            }
//
//            @Override
//            public void onScrollingFinished(WheelView wheel) {
//                changeDayByCurrentYearAndMonth(
//                        Integer.parseInt(yearAdapter.getItem(yearView.getCurrentItem()).replace(yearText, "")),
//                        Integer.parseInt(monthAdapter.getItem(monthView.getCurrentItem()).replace(monthText,"")));
//            }
//        });
//    }
//
//    private void changeDayByMonth(int month){
//        dayAdapter = new NumericWheelAdapter(1,calculateDay(month),"%1s"+dayText);
//        dayView.setAdapter(dayAdapter);
//    }
//
//    //计算 day的 数值  分为 大月 小月 2月 包括闰年判断
//    private int calculateDay(int month){
//        int day = 0;
//        switch (month){
//        case 1:
//        case 3:
//        case 5:
//        case 7:
//        case 8:
//        case 10:
//        case 12:
//            day = 31;
//            break;
//        case 4:
//        case 6:
//        case 9:
//        case 11:
//            day = 30;
//            break;
//        case 2:
//            String year = yearAdapter.getItem(yearView.getCurrentItem());
//            year = year.replace(getResources().getString(R.string.edu_baby_month),"");
//            try { //防止意外
//                if(TimeHelper.isLeapYear(Integer.parseInt(year))){
//                    day = 29;
//                } else {
//                    day = 28;
//                }
//            } catch (NumberFormatException e) {
//                day = 28;
//            }
//            break;
//        }
//        return day;
//    }
//
//    //滚动年份计算 当年份是当前年的 时候 会重新计算 月份 和日 的adapter  因为选择生日 不能超过今天
//    private void changeMonthAndDayByCurrentYear(int year){
//        if(year == currentYear){
//            monthAdapter = new NumericWheelAdapter(1,currentMonth+1,"%1s"+monthText);
//            monthView.setAdapter(monthAdapter);
//            isMonthChanged = true;
//        } else if(year ==  minYear){
//            monthAdapter = new NumericWheelAdapter(currentMonth+1,12,"%1s"+monthText);
//            monthView.setAdapter(monthAdapter);
//            isMonthChanged = true;
//        } else {
//            if(isMonthChanged) {
//                monthAdapter = new NumericWheelAdapter(1, 12, "%1s" + monthText);
//                monthView.setAdapter(monthAdapter);
//                isMonthChanged = false;
//            }
//            changeDayByMonth(monthView.getCurrentItem()+1);
//        }
//    }
//    //滚动月份计算
//    private void changeDayByCurrentYearAndMonth(int year,int month){
//        if(year == currentYear && month == currentMonth+1){
//            dayAdapter = new NumericWheelAdapter(1,currentDay,"%1s"+dayText);
//            dayView.setAdapter(dayAdapter);
//        } else if(year == minYear && month == currentMonth+1){
//            dayAdapter = new NumericWheelAdapter(currentDay,calculateDay(month),"%1s"+dayText);
//            dayView.setAdapter(dayAdapter);
//        } else {
//            changeDayByMonth(monthView.getCurrentItem() + 1);
//        }
//    }
//
//    /**
//     * 获取选中的日期 yyyy-MM-dd
//     * @return
//     */
//    public String getDate(){
//        String year = yearAdapter.getItem(yearView.getCurrentItem());
//        String month = monthAdapter.getItem(monthView.getCurrentItem());
//        String day = dayAdapter.getItem(dayView.getCurrentItem());
//        if(year == null){
//            Toast.makeText(getContext(), R.string.edu_date_request_year, Toast.LENGTH_SHORT).show();
//            return null;
//        } else if(month == null){
//            Toast.makeText(getContext(),R.string.edu_date_request_month,Toast.LENGTH_SHORT).show();
//            return null;
//        } else if(day == null){
//            Toast.makeText(getContext(),R.string.edu_date_request_day,Toast.LENGTH_SHORT).show();
//            return null;
//        }
//        return year.replace(yearText, "")+"-"+formatNumber(month.replace(monthText, "")) +"-"+formatNumber(day.replace(dayText,""));
//    }
//
//    private String formatNumber(String number){
//        return number.length() == 1 ? "0"+number : number;
//    }
//
//    /**
//     * 支持 yyyy-MM-dd 的日期
//     */
//    public void setDate(String date){
//        if(StringUtils.isBlank(date)){
//            return;
//        }
//        String[] elements = date.split("-");
//        try {
//            int year = Integer.parseInt(elements[0]);
//            int month = Integer.parseInt(elements[1]);
//            int day = Integer.parseInt(elements[2]);
//            changeMonthAndDayByCurrentYear(year);
//            changeDayByCurrentYearAndMonth(year, month);
//            yearView.setCurrentItem(year-minYear);
//            monthView.setCurrentItem(month-1);
//            dayView.setCurrentItem(day-1);
//        } catch (NumberFormatException e) {
//        }
//    }
//
//    /**
//     * 设置最新年值 根据最大年龄计算
//     * @param age
//     */
//    public void setMinYear(int age){
//        minYear = currentYear - age;
//        yearAdapter = new NumericWheelAdapter(minYear, calendar.get(Calendar.YEAR), "%1s" + yearText);
//        yearView.setAdapter(yearAdapter);
//        yearView.setCurrentItem(currentYear - minYear);
//    }
}
