package skripsi.code.ektp.helper;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;


/**
 * Created by jahid on 12/10/15.
 */
public class DatePickerFragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    EditText editText;
    private int day;
    private int month;
    private int birthYear;
    private Context context;

    final Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

    public DatePickerFragment(Context context, int editTextViewID){
        Activity act = (Activity)context;
        this.editText = (EditText)act.findViewById(editTextViewID);
        this.editText.setOnClickListener(this);
        this.context = context;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        birthYear = year;
        month = monthOfYear;
        day = dayOfMonth;
        updateDisplay();
    }
    @Override
    public void onClick(View v) {

        DatePickerDialog dialog = new DatePickerDialog(context, this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();

    }

    // updates the date in the birth date EditText
    private void updateDisplay() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editText.setText(sdf.format(calendar.getTime()));

    }
}