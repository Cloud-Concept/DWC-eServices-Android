package fragmentActivity;

import android.support.v4.app.Fragment;
import android.view.View;

import cloudconcept.dwc.BaseActivity;
import fragment.PreviewFragment;

/**
 * Created by Abanoub Wagdy on 9/9/2015.
 */
public class PreviewActivity extends BaseActivity {

    @Override
    public int getNotificationVisibillity() {
        return View.GONE;
    }

    @Override
    public int getMenuVisibillity() {
        return View.GONE;
    }

    @Override
    public int getBackVisibillity() {
        return View.VISIBLE;
    }

    @Override
    public String getHeaderTitle() {
        return "Preview";
    }

    @Override
    public Fragment GetFragment() {
        Fragment fragment = PreviewFragment.newInstance(getApplicationContext(), getIntent().getExtras().getString("object"), getIntent().getExtras().getString("type"));
        return fragment;
    }
}
