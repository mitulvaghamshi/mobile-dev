package me.mitul.aij.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateParser {
    public String a(String paramString) {
        try {
            Date localDate = new SimpleDateFormat("MM-yyyy", Locale.US).parse(paramString);
            return new SimpleDateFormat("MMM,yyyy", Locale.US).format(localDate);
        } catch (ParseException localParseException) {
            localParseException.printStackTrace();
        }
        return "";
    }

    public String b(String paramString) {
        try {
            Date localDate = new SimpleDateFormat("dd-MM-yyyy", Locale.US).parse(paramString);
            return new SimpleDateFormat("dd-MMM-yyyy,EEE", Locale.US).format(localDate);
        } catch (ParseException localParseException) {
            localParseException.printStackTrace();
        }
        return "";
    }

    public String c(String paramString) {
        try {
            Date localDate = new SimpleDateFormat("dd-MM-yyyy", Locale.US).parse(paramString);
            return new SimpleDateFormat("dd-MMM,EEE", Locale.US).format(localDate);
        } catch (ParseException localParseException) {
            localParseException.printStackTrace();
        }
        return "";
    }

    public String d(String paramString) {
        try {
            Date localDate = new SimpleDateFormat("dd-MM-yyyy", Locale.US).parse(paramString);
            return new SimpleDateFormat("dd,EEE", Locale.US).format(localDate);
        } catch (ParseException localParseException) {
            localParseException.printStackTrace();
        }
        return "";
    }

    public String e(String paramString) {
        try {
            Date localDate = new SimpleDateFormat("dd-MM-yyyy", Locale.US).parse(paramString);
            return new SimpleDateFormat("dd-MM-yyyy,EEE", Locale.US).format(localDate);
        } catch (ParseException localParseException) {
            localParseException.printStackTrace();
        }
        return "";
    }

/*    private void setDateTimeField() {
        Calendar localCalendar = Calendar.getInstance();
        this.birthDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker paramAnonymousDatePicker, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {
                Calendar localCalendar = Calendar.getInstance();
                localCalendar=paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3);
                RegistrationActivity.this.edDOB.setText(RegistrationActivity.this.dateFormatter.format(localCalendar.getTime()));
            }
        }, localCalendar.get(1), localCalendar.get(2), localCalendar.get(5));
    }*/
}
