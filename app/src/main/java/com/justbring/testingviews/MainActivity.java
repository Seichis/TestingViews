package com.justbring.testingviews;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.andreabaccega.formedittextvalidator.CreditCardValidator;
import com.andreabaccega.formedittextvalidator.EmailValidator;
import com.andreabaccega.formedittextvalidator.OrValidator;
import com.andreabaccega.widget.FormEditText;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.iangclifton.android.floatlabel.FloatLabel;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    public static final String DATEPICKER_TAG = "datepicker";
    public static final String TIMEPICKER_TAG = "timepicker";
    Button foldableActivityButton,unfoldableActivityButton,validateEditTextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final Calendar calendar = Calendar.getInstance();

        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), true);
        final TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false, false);

        findViewById(R.id.dateButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                datePickerDialog.setVibrate(true);
                datePickerDialog.setYearRange(1985, 2028);
                datePickerDialog.setCloseOnSingleTapDay(false);
                datePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);
            }
        });

        findViewById(R.id.timeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.setVibrate(true);
                timePickerDialog.setCloseOnSingleTapMinute(false);
                timePickerDialog.show(getSupportFragmentManager(), TIMEPICKER_TAG);
            }
        });

        if (savedInstanceState != null) {
            DatePickerDialog dpd = (DatePickerDialog) getSupportFragmentManager().findFragmentByTag(DATEPICKER_TAG);
            if (dpd != null) {
                dpd.setOnDateSetListener(this);
            }

            TimePickerDialog tpd = (TimePickerDialog) getSupportFragmentManager().findFragmentByTag(TIMEPICKER_TAG);
            if (tpd != null) {
                tpd.setOnTimeSetListener(this);
            }
        }


        foldableActivityButton=(Button)findViewById(R.id.foldable_button);
        unfoldableActivityButton=(Button)findViewById(R.id.unfoldable_button);

        foldableActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FoldableListActivity.class));
            }
        });
        unfoldableActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UnfoldableDetailsActivity.class));
            }
        });

        validateEditTextButton=(Button)findViewById(R.id.validate_button);
        
        validateEditTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickValidate(v);
            }
        });

        // This is how you add a custom animator
        final FloatLabel floatLabel = (FloatLabel) findViewById(R.id.float_label_custom_layout_1);
//        floatLabel.setLabelAnimator(new CustomLabelAnimator());

        FormEditText fdt = (FormEditText) findViewById(R.id.edit_text);
        fdt.setError("Whatever does not work");
        fdt.addValidator(
                new OrValidator(
                        "This is neither a creditcard or an email",
                        new CreditCardValidator(null), // we specify null as the message string cause the Or validator will use his own message
                        new EmailValidator(null) // same here for null
                )
        );

    }

    public void onClickValidate(View v) {
        FormEditText fdt = (FormEditText) findViewById(R.id.edit_text);
        if (fdt.testValidity()) {
            Toast.makeText(this, ":)", Toast.LENGTH_LONG).show();
        }
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

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        Log.i("new date:", " " + year + "-" + month + "-" + day);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        Log.i("new time:", " " + hourOfDay + "-" + minute);
    }


    /**
     * LabelAnimator that uses a custom X shift and fade.
     *
     * @author Ian G. Clifton
     */
    private static class CustomLabelAnimator implements FloatLabel.LabelAnimator {
        /*package*/ static final float SCALE_X_SHOWN = 1f;
        /*package*/ static final float SCALE_X_HIDDEN = 2f;
        /*package*/ static final float SCALE_Y_SHOWN = 1f;
        /*package*/ static final float SCALE_Y_HIDDEN = 0f;

        @Override
        public void onDisplayLabel(View label) {
            final float shift = label.getWidth() / 2;
            label.setScaleX(SCALE_X_HIDDEN);
            label.setScaleY(SCALE_Y_HIDDEN);
            label.setX(shift);
            label.animate().alpha(1).scaleX(SCALE_X_SHOWN).scaleY(SCALE_Y_SHOWN).x(0f);
        }

        @Override
        public void onHideLabel(View label) {
            final float shift = label.getWidth() / 2;
            label.setScaleX(SCALE_X_SHOWN);
            label.setScaleY(SCALE_Y_SHOWN);
            label.setX(0f);
            label.animate().alpha(0).scaleX(SCALE_X_HIDDEN).scaleY(SCALE_Y_HIDDEN).x(shift);
        }
    }
}
