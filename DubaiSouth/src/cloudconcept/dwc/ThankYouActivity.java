package cloudconcept.dwc;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import cloudconcept.dwc.R;
import fragment.GenericThankYouFragment;

/**
 * Created by Abanoub Wagdy on 10/25/2015.
 */
public class ThankYouActivity extends FragmentActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_info_inner_fragment);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, GenericThankYouFragment.newInstance(getIntent().getExtras().getString("caseNumber"), null, null), "Thank You")
                .commitAllowingStateLoss();
    }
}
