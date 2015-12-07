package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;
import com.salesforce.androidsdk.app.SalesforceSDKManager;
import com.salesforce.androidsdk.rest.ClientManager;
import com.salesforce.androidsdk.rest.RestClient;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import cloudconcept.dwc.R;
import utilities.StoreData;
import model.User;
import utilities.Utilities;

/**
 * Created by Bibo on 7/28/2015.
 */
public class DashboardFragment extends Fragment {

    private URI theUrl;

    public static Fragment newInstance(String dashboard) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle bundle = new Bundle();
        bundle.putString("DashboardFragment", dashboard);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard, container, false);
        final WebView webView = (WebView) view.findViewById(R.id.sf__oauth_webview);
        Utilities.showloadingDialog(getActivity());
        new ClientManager(getActivity(), SalesforceSDKManager.getInstance().getAccountType(), SalesforceSDKManager.getInstance().getLoginOptions(), SalesforceSDKManager.getInstance().shouldLogoutWhenTokenRevoked()).getRestClient(getActivity(), new ClientManager.RestClientCallback() {

            @Override
            public void authenticatedRestClient(RestClient client) {
                if (client == null) {
                    SalesforceSDKManager.getInstance().logout(getActivity());
                    return;
                } else {
                    final Gson gson = new Gson();
                    User user = gson.fromJson(new StoreData(getActivity().getApplicationContext()).getUserDataAsString(), User.class);
                    String access_token = SalesforceSDKManager.getInstance().getKey("access_token");
                    Log.d("access", access_token);
                    String attUrl = client.getClientInfo().resolveUrl("/apex/DWCPortal_DashboardMobile").toString();
                    String url = attUrl + "?sid=" + client.getRefreshToken() + "&accountId=" + user.get_contact().get_account().getID() + "&authToken=" + client.getAuthToken() + "&client_id=" + "3MVG9sLbBxQYwWqsa8dlM02Kaxs1PH5oIbF_o2Y4FfeOoF.1__CEjo_6_fIBta82uOIvzvuJ9tVtR1iU9PbtA" + "&client_secret=3MVG9sLbBxQYwWqsa8dlM02Kaxs1PH5oIbF_o2Y4FfeOoF.1__CEjo_6_fIBta82uOIvzvuJ9tVtR1iU9PbtA";
                    HttpClient tempClient = new DefaultHttpClient();
                    try {
                        theUrl = new URI(url);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }

                    final WebSettings webSettings = webView.getSettings();
                    webSettings.setJavaScriptEnabled(true);
                    webSettings.setAllowFileAccessFromFileURLs(true);
                    webSettings.setDatabaseEnabled(true);
                    webSettings.setDomStorageEnabled(true);

                    webView.setWebViewClient(new WebViewClient() {
                        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                            getActivity().finish();
                        }
                    });
                    try {
                        webView.loadUrl(theUrl.toURL().toString());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    if (Utilities.getIsProgressLoading()) {
                        Utilities.dismissLoadingDialog();
                    }
                }
            }
        });
        return view;
    }
}