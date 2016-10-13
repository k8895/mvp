package test.wjk.com.mylooklook.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import test.wjk.com.mylooklook.R;

public class AboutActivity extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

    }
}
