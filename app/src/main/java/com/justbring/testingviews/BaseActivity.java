package com.justbring.testingviews;

import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by User1 on 4/12/2015.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    @NonNull
//    @Override
//    public ActionBar getSupportActionBar() {
//        // Making getSupportActionBar() method to be @NonNull
//        ActionBar actionBar = super.getSupportActionBar();
//        if (actionBar == null) throw new NullPointerException("Action bar was not initialized");
//        return actionBar;
//    }

}
