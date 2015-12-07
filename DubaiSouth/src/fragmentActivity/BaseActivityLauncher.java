package fragmentActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.salesforce.androidsdk.rest.RestClient;

import cloudconcept.dwc.BaseActivity;
import cloudconcept.dwc.R;
import utilities.ExceptionHandler;
import fragment.CompanyInfoFragment;
import fragment.DashboardFragment;
import fragment.MyRequestsFragment;
import fragment.NeedHelpFragment;
import fragment.NotificationFragment;
import fragment.QuickAccessFragment;
import fragment.ViewStatementFragment;
import fragment.VisasAndCardsFragment;
import utilities.AdapterConfiguration;

/**
 * Created by Abanoub Wagdy on 10/25/2015.
 */
public class BaseActivityLauncher extends BaseActivity {

    protected RestClient client;
    String ScreenTag;
    Fragment fragment = null;
    static String title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ScreenTag = getIntent().getExtras().getString("ScreenTag");
                if (fragment == null) {
                    if (ScreenTag.equals(AdapterConfiguration.VISAS_AND_CARDS_SCREEN)) {
                        fragment = new VisasAndCardsFragment();
                        title = getString(R.string.visas_card);
                    } else if (ScreenTag.equals(AdapterConfiguration.VIEW_STATEMENT_SCREEN)) {
                        fragment = new ViewStatementFragment();
                        title = getString(R.string.title_view_statement);
                    } else if (ScreenTag.equals(AdapterConfiguration.QUICK_ACCESS_SCREEN)) {
                        fragment = QuickAccessFragment.newInstance();
                        title = getString(R.string.title_quick_access);
                    } else if (ScreenTag.equals(AdapterConfiguration.NEED_HELP_SCREEN)) {
                        fragment = NeedHelpFragment.newInstance("Need Help");
                        title = getString(R.string.title_need_help);
                    } else if (ScreenTag.equals(AdapterConfiguration.NOTIFICATIONS_SCREEN)) {
                        fragment = new NotificationFragment();
                        title = getString(R.string.title_notifications);
                    } else if (ScreenTag.equals(AdapterConfiguration.MYREQUESTS_SCREEN)) {
                        fragment = MyRequestsFragment.newInstance("My Requests");
                        title = getString(R.string.title_my_requests);
                    } else if (ScreenTag.equals(AdapterConfiguration.COMPANY_INFO_SCREEN)) {
                        fragment = new CompanyInfoFragment();
                        title = getString(R.string.title_Company_Info);
                    } else if (ScreenTag.equals(AdapterConfiguration.DASHBOARD_SCREEN)) {
                        fragment = DashboardFragment.newInstance("Dashboard");
                        title = getString(R.string.title_dashboard);
                    }
                }
            }
        }).start();
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
    }

    @Override
    public void onResume(RestClient client) {
        this.client = client;
    }

    @Override
    public int getNotificationVisibillity() {
        return View.VISIBLE;
    }

    @Override
    public int getMenuVisibillity() {
        return View.VISIBLE;
    }

    @Override
    public int getBackVisibillity() {
        return View.GONE;
    }

    @Override
    public String getHeaderTitle() {
        return title;
    }

    @Override
    public Fragment GetFragment() {
        return fragment;
    }

    @Override
    public void onDestroy() {
        fragment = null;
        super.onDestroy();
    }
}
