package fragment;

import com.blunderer.materialdesignlibrary.handlers.ViewPagerHandler;

import custom.DWCViewPagerFragment;
import fragment.companyDocumentsScreen.CertificatesAndAgreementsFragment;
import fragment.companyDocumentsScreen.CustomerDocumentsFragment;

/**
 * Created by Abanoub Wagdy on 9/8/2015.
 */
public class CompanyDocumentsFragment extends DWCViewPagerFragment {

    @Override
    public boolean showViewPagerIndicator() {
        return true;
    }

    @Override
    public ViewPagerHandler getViewPagerHandler() {
        return new ViewPagerHandler(getActivity())
                .addPage("True Copies", CertificatesAndAgreementsFragment.newInstance("CertificatesAndAgreementsFragment"))
                .addPage("Customer Documents", CustomerDocumentsFragment.newInstance("CustomerDocumentsFragment"));
    }

    @Override
    public int defaultViewPagerPageSelectedPosition() {
        return 0;
    }


}

