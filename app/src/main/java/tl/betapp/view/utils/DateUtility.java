package tl.betapp.view.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import tl.betapp.R;
import tl.betapp.view.service.ServiceResponse;


/**
 * Created  on 12/23/2016.
 *
 * @author Jitendra Sharma
 */

public class DateUtility {

    private static LogPrint logConfig = LogPrint.getInstance();

    /**
     * Function to compare two dates.
     * Input Date format must be in yyyy-MM-dd
     *
     * @param curDate
     * @param oldDate
     * @return True if date2 is greater then date1
     */
    public static boolean isGreaterDate(String curDate, String oldDate, String dateFormat) {
        boolean isGreaterDate = false;
        try {
            logConfig.printP("isGreaterDate", " curDate: " + curDate + " oldDate: " + oldDate);
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            Date date1 = formatter.parse(curDate);
            Date date2 = formatter.parse(oldDate);

            logConfig.printP("isGreaterDate", " compareTo: " + date1.compareTo(date2));
            if (date1.compareTo(date2) >= 0) {
                isGreaterDate = true;
            }
        } catch (Exception e1) {
            logConfig.printP("", " Exception datecompare: " + e1.getMessage());
            e1.printStackTrace();
        }
        return isGreaterDate;
    }

    /**
     * Function to convert Date format date to string.
     *
     * @param date1
     * @param date2
     * @return stringDate .
     */
    public static boolean compareTwoEqualDates(String date1, String date2) {

        boolean isDateEquals = false;
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            isDateEquals = fmt.format(fmt.parse(date1)).equals(fmt.format(fmt.parse(date2)));
        } catch (Exception e) {
            isDateEquals = false;
            e.printStackTrace();
        }
        logConfig.printP("compareTwoEqualDates", " isDateEquals " + isDateEquals);

        return isDateEquals;
    }//compareTwoEqualDates


    public static boolean compareDate(String str1, String str2, String format) {

        logConfig.printP("CompareDate", " str1= " + str1 + " str2 " + str2);
        try {

            SimpleDateFormat formatter = new SimpleDateFormat(format);
            Date date1 = formatter.parse(str1);
            Date date2 = formatter.parse(str2);

            if (date1.compareTo(date2) > 0) {
                logConfig.printP("", "date2 is greater than my date1");
                return false;
            } else if (date1.compareTo(date2) == 0) {
                return true;
            } else {
                return true;
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return false;
    }


    /**
     * Function to convert Date format date to string.
     *
     * @param date
     * @return stringDate .
     */
    public static String convertDateToString(Date date) {
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)

        DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        // Using DateFormat format method we can create a string
        // representation of a date with the defined format.
        String stringDate = df.format(date);

        logConfig.printP("convertDateToString", " stringDate " + stringDate);
        return stringDate;
    }//convertDateToString


    public static String convertStringToDate(String date, String dateFormat) {
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)

        DateFormat df = new SimpleDateFormat(dateFormat);
        SimpleDateFormat fromUser = new SimpleDateFormat(dateFormat);
        // Using DateFormat format method we can create a string
        // representation of a date with the defined format.
        //String reformattedStr = myFormat.format(fromUser.parse(inputString));
        String stringDate = null;
        try {
            stringDate = df.format(fromUser.parse(date));
        } catch (Exception e) {
            logConfig.printP("convertDateToString", " error  " + e.getMessage());
        }

        logConfig.printP("convertDateToString", " stringDate " + stringDate);
        return stringDate;
    }//convertStringToDate

    public static String parseDateToddMMyyyy(String time) {

        String inputPattern = "EEE MMM dd HH:mm:ss Z yyyy";    //9/2/2017
        String outputPattern = "yyyy-MM-dd HH:mm:ss";   //2017-02-08
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String parseDateToTime(String time) {

        String inputPattern = "dd-MM-yyyy HH:mm";    //22-02-2018 01:05
        String outputPattern = "HH:mm aa";   //2017-02-08
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String convertDateFormat(String dateStr, String formatIn, String formatOut) {

        logConfig.printP("convertDateFormat ", "dateStr:" + dateStr);
        String convertDate = dateStr;
        if (dateStr != null && dateStr.length() > 0) {
            Date date = null;
            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat(formatIn);
                SimpleDateFormat outputFormat = new SimpleDateFormat(formatOut);
                inputFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
                outputFormat.setTimeZone(TimeZone.getDefault());
                date = inputFormat.parse(dateStr);
                convertDate = outputFormat.format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
            logConfig.printP("convertDateFormat ", "convertDate: " + convertDate);
        }
        return convertDate;
    } //convertDateFormat

    /**
     * Function to get current date.
     *
     * @param dateFormat . Formate of date in which you want.
     * @return stringDate .
     */
    public static String getCurrentDate(String dateFormat) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        String strDate = simpleDateFormat.format(calendar.getTime());

        return strDate;
    }//getCurrentDate

    /**
     * Function to get current date.
     *
     * @param dateFormat . Formate of date in which you want.
     * @return stringDate .
     */
    public static long getDifferenceTwoDate(String startDate, String endDate, String dateFormat) {

        long strDate = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);

        try {
            Date date1 = simpleDateFormat.parse(startDate);
            Date date2 = simpleDateFormat.parse(endDate);
            strDate = printDifference(date1, date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return strDate;
    }//getCurrentDate


    //1 minute = 60 seconds
    //1 hour = 60 x 60 = 3600
    //1 day = 3600 x 24 = 86400
    public static long printDifference(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : " + endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        System.out.printf("%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);

        return elapsedHours;
    }

    /**
     * Function to get current time.
     *
     * @param timeFormat . Format of time in which you want.
     * @return string time.
     */
    public static String getCurrentTime(String timeFormat) {
        return getCurrentDate(timeFormat);
    }//getCurrentTime

    /**
     * Function to get current time.
     *
     * @return string time.
     */
    public String getCurrentTime() {
        String timeFormat = "HH:mm:ss";
        return getCurrentDate(timeFormat);
    }//getCurrentTime

   /* public static void callDatePickerDialog(Activity mActivity, ServiceResponse serviceResponse) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Get Current Date
        DatePickerDialog datePickerDialog = new DatePickerDialog(mActivity,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        serviceResponse.requestResponse(date);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }*/

    public static void callDatePickerDialogNew(Activity mActivity, final TextView txtDate) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Get Current Date
        DatePickerDialog datePickerDialog = new DatePickerDialog(mActivity,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        txtDate.setText(DateUtility.convertDateFormat(date,
                                "dd-MM-yyyy", "dd-MMM-yyyy"));
                    }
                }, year, month, day);
        datePickerDialog.show();
    }
    @SuppressLint("ResourceType")
    public static void callAgeDatePicker(Activity mActivity, final TextView txtDate) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Get Current Date
        DatePickerDialog datePickerDialog = new DatePickerDialog(mActivity,R.style.my_dialog_theme,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        txtDate.setText(DateUtility.convertDateFormat(date,
                                "dd-MM-yyyy", "dd-MMM-yyyy"));
                    }
                }, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 568025136000L);

        datePickerDialog.show();
    }

   /* public static void callTimePickerDialog(Activity mActivity, ServiceResponse serviceResponse) {

        // Get Current Time
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(mActivity,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String time = "" + hourOfDay + ":" + minute;
                        serviceResponse.requestResponse(time);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }*/


}//class