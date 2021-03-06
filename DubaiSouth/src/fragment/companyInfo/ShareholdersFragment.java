package fragment.companyInfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.salesforce.androidsdk.app.SalesforceSDKManager;
import com.salesforce.androidsdk.rest.ClientManager;
import com.salesforce.androidsdk.rest.RestClient;
import com.salesforce.androidsdk.rest.RestRequest;
import com.salesforce.androidsdk.rest.RestResponse;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import RestAPI.SFResponseManager;
import RestAPI.SoqlStatements;
import adapter.GenericAdapter;
import cloudconcept.dwc.R;
import custom.ExpandableLayoutListView;
import utilities.StoreData;
import model.ShareOwnership;
import model.User;
import utilities.AdapterConfiguration;
import utilities.CallType;

/**
 * Created by Abanoub on 7/2/2015.
 */
public class ShareholdersFragment extends Fragment {

    private static final String ARG_TEXT = "ShareholdersFragment";
    int offset = 0;
    int limit = 10;
    private SwipyRefreshLayout swipyRefreshLayout;
    private ExpandableLayoutListView lvShareholders;
    private String soqlQuery;
    private RestRequest restRequest;
    private ArrayList<ShareOwnership> _shareOwnerships;
    private int top;
    private int index;

    public static ShareholdersFragment newInstance(String text) {
        ShareholdersFragment fragment = new ShareholdersFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TEXT, text);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shareholders, container, false);
        InitializeViews(view);
        CallShareholdersService(CallType.FIRSTTIME, offset, limit);
        return view;
    }

    private void InitializeViews(View view) {
        swipyRefreshLayout = (SwipyRefreshLayout) view.findViewById(R.id.activity_main_swipe_refresh_layout);
        lvShareholders = (ExpandableLayoutListView) view.findViewById(R.id.expandableLayoutListView);
        swipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh(SwipyRefreshLayoutDirection swipyRefreshLayoutDirection) {
                if (swipyRefreshLayoutDirection == SwipyRefreshLayoutDirection.TOP) {
                    offset = 0;
                    index=0;
                    top=0;
                    getListPosition();
                    CallShareholdersService(CallType.REFRESH, offset, limit);
                } else {
                    offset += limit;
                    getListPosition();
                    CallShareholdersService(CallType.LOADMORE, offset, limit);
                }
            }
        });
    }

    private void CallShareholdersService(final CallType serviceCall, int offset, int limit) {
        if (serviceCall == CallType.FIRSTTIME && !new StoreData(getActivity().getApplicationContext()).getShareholdersResponse().equals("")) {
            try {
                _shareOwnerships = SFResponseManager.parseShareOwnerShipObject(new StoreData(getActivity().getApplicationContext()).getShareholdersResponse());
                lvShareholders.setAdapter(new GenericAdapter(getActivity(),_shareOwnerships, AdapterConfiguration.SHAREHOLDERS_Employee_TAG));
                restoreListPosition();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            Gson gson = new Gson();
            User _user = gson.fromJson(new StoreData(getActivity().getApplicationContext()).getUserDataAsString(), User.class);
            soqlQuery = SoqlStatements.getInstance().constructShareHoldersQuery(_user.get_contact().get_account().getID(), limit, offset);
            try {
                restRequest = RestRequest.getRequestForQuery(
                        getActivity().getString(R.string.api_version), soqlQuery);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            new ClientManager(getActivity(), SalesforceSDKManager.getInstance().getAccountType(), SalesforceSDKManager.getInstance().getLoginOptions(), SalesforceSDKManager.getInstance().shouldLogoutWhenTokenRevoked()).getRestClient(getActivity(), new ClientManager.RestClientCallback() {

                @Override
                public void authenticatedRestClient(RestClient client) {
                    if (client == null) {
                        SalesforceSDKManager.getInstance().logout(getActivity());
                        return;
                    } else {
                        client.sendAsync(restRequest, new RestClient.AsyncRequestCallback() {

                            @Override
                            public void onSuccess(RestRequest request, RestResponse response) {
                                if (serviceCall == CallType.LOADMORE || serviceCall == CallType.REFRESH)
                                    swipyRefreshLayout.setRefreshing(false);
                                try {
                                    if (serviceCall == CallType.LOADMORE) {
                                        ArrayList<ShareOwnership> shareOwnerships = SFResponseManager.parseShareOwnerShipObject(response.toString());
                                        if (shareOwnerships.size() > 0) {
                                            new StoreData(getActivity().getApplicationContext()).setShareholdersResponse(response.toString());
                                            for (int i = 0; i < shareOwnerships.size(); i++) {
                                                boolean found = false;
                                                for (int j = 0; j < _shareOwnerships.size(); j++) {
                                                    if (shareOwnerships.get(i).getID().equals(_shareOwnerships.get(j).getID())) {
                                                        found = true;
                                                        break;
                                                    }
                                                }
                                                if (!found) {
                                                    _shareOwnerships.add(shareOwnerships.get(i));
                                                }
                                            }
                                        }
                                    } else {
                                        _shareOwnerships = new ArrayList<ShareOwnership>();
                                        _shareOwnerships = SFResponseManager.parseShareOwnerShipObject(response.toString());
                                        if (_shareOwnerships.size() > 0) {
                                            new StoreData(getActivity().getApplicationContext()).setShareholdersResponse(response.toString());
                                        }
                                    }
                                    lvShareholders.setAdapter(new GenericAdapter(getActivity(),_shareOwnerships, AdapterConfiguration.SHAREHOLDERS_Employee_TAG));
                                    restoreListPosition();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Exception exception) {

                            }
                        });
                    }
                }
            });
        }
    }


    public void getListPosition() {
        index = lvShareholders.getFirstVisiblePosition();
        View v = lvShareholders.getChildAt(0);
        top = (v == null) ? 0 : (v.getTop() - lvShareholders.getPaddingTop());
    }

    public void restoreListPosition() {

        lvShareholders.setSelectionFromTop(index, top);
    }
}