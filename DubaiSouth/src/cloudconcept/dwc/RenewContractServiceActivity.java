package cloudconcept.dwc;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.salesforce.androidsdk.app.SalesforceSDKManager;
import com.salesforce.androidsdk.rest.ClientManager;
import com.salesforce.androidsdk.rest.RestClient;
import com.salesforce.androidsdk.rest.RestRequest;
import com.salesforce.androidsdk.rest.RestResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import RestAPI.JSONConstants;
import RestAPI.RestMessages;
import RestAPI.SFResponseManager;
import RestAPI.config.DWCConfiguration;
import custom.customdialog.NiftyDialogBuilder;
import model.Case;
import model.Contract_DWC__c;
import model.Contract_Line_Item__c;
import model.User;
import utilities.ActivitiesLauncher;
import utilities.StoreData;
import utilities.Utilities;

/**
 * Created by Abanoub Wagdy on 12/13/2015.
 */
public class RenewContractServiceActivity extends Activity implements View.OnClickListener {

    TextView tvRenewContractTitle, tvAmount, tvKnowledgeFees;
    Button btnPayAndSubmit, btnCancel;
    Gson gson;
    private Contract_DWC__c contract_dwc__c;
    private String actionType;
    private NiftyDialogBuilder builder;
    private String selectedService;
    String soql = "select id , Amount__c , Display_Name__c , Require_Knowledge_Fee__c , Knowledge_Fee__c , Knowledge_Fee__r.Amount__c from Receipt_Template__c where Service_Identifier__c = '%s'";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.renew_contract_service);
        InitializeViews();
        gson = new Gson();
        contract_dwc__c = gson.fromJson(getIntent().getExtras().getString("object"), Contract_DWC__c.class);

        Contract_Line_Item__c contract_line_item__c = contract_dwc__c.getContract_line_item__cs().get(0);

        if (contract_line_item__c.getInventory_unit__r().getProduct_Type__r().getName().equals("Smart Desk")) {
            selectedService = "BC-SMART DESK";
        } else if (contract_line_item__c.getInventory_unit__r().getProduct_Type__r().getName().equals("Smart Office")) {
            selectedService = "BC-SMART OFFICE";
        } else if (contract_line_item__c.getInventory_unit__r().getProduct_Type__r().getName().equals("Permanent Office")) {
            selectedService = "BC-PERMANENT SO";
        } else if (contract_line_item__c.getInventory_unit__r().getProduct_Type__r().getName().equals("Permanent Desk")) {
            selectedService = "BC-PERMANENT SD";
        } else if (contract_line_item__c.getInventory_unit__r().getProduct_Type__r().getName().equals(" Permanent Exclusive Office")) {
            selectedService = "BC-PERMANENT EO";
        } else {
            selectedService = "BC-PERMANENT EO";
        }

        soql = String.format(soql, selectedService);
        Utilities.showloadingDialog(this);
        new ClientManager(RenewContractServiceActivity.this, SalesforceSDKManager.getInstance().getAccountType(), SalesforceSDKManager.getInstance().getLoginOptions(), SalesforceSDKManager.getInstance().shouldLogoutWhenTokenRevoked()).getRestClient(RenewContractServiceActivity.this, new ClientManager.RestClientCallback() {
            @Override
            public void authenticatedRestClient(final RestClient client) {
                if (client == null) {
                    SalesforceSDKManager.getInstance().logout(RenewContractServiceActivity.this);
                    return;
                } else {
                    try {
                        RestRequest restRequest = RestRequest.getRequestForQuery(getString(R.string.api_version), soql);
                        client.sendAsync(restRequest, new RestClient.AsyncRequestCallback() {
                            @Override
                            public void onSuccess(RestRequest request, RestResponse response) {
                                Utilities.dismissLoadingDialog();
                                try {
                                    JSONObject jsonFullObject = new JSONObject(response.toString());
                                    tvRenewContractTitle.setText(jsonFullObject.getJSONArray(JSONConstants.RECORDS).getJSONObject(0).getString("Display_Name__c"));
                                    tvAmount.setText(Utilities.processAmount(jsonFullObject.getJSONArray(JSONConstants.RECORDS).getJSONObject(0).getString("Amount__c")) + " AED.");
                                    tvKnowledgeFees.setText(Utilities.processAmount(jsonFullObject.getJSONArray(JSONConstants.RECORDS).getJSONObject(0).getJSONObject("Knowledge_Fee__r").getString("Amount__c")) + " AED.");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Exception exception) {
                                Utilities.dismissLoadingDialog();
                            }
                        });
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void InitializeViews() {

        tvRenewContractTitle = (TextView) findViewById(R.id.tvRenewContractTitle);
        tvAmount = (TextView) findViewById(R.id.tvAmount);
        tvKnowledgeFees = (TextView) findViewById(R.id.tvKnowledgeFees);
        btnPayAndSubmit = (Button) findViewById(R.id.btnPayandSubmit);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnPayAndSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnPayAndSubmit) {
            builder = Utilities.showCustomNiftyDialog("Pay Process", this, listenerOkPay, "Are you sure want to Pay for the service ?");
        } else if (v == btnCancel) {
            builder = Utilities.showCustomNiftyDialog("Cancel Process", this, listenerOk1, "Are you sure want to cancel this process ?");
        }
    }

    private View.OnClickListener listenerOk1 = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            builder.dismiss();
            finish();
        }
    };
    private View.OnClickListener listenerOkPay = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            builder.dismiss();
            if (contract_dwc__c.IS_BC_Contract__c()) {
                actionType = "CreateBCContractRenewalRequest";
            } else {
                actionType = "CreateNonBCContractRenewalRequest";
            }
            new ClientManager(RenewContractServiceActivity.this, SalesforceSDKManager.getInstance().getAccountType(), SalesforceSDKManager.getInstance().getLoginOptions(), SalesforceSDKManager.getInstance().shouldLogoutWhenTokenRevoked()).getRestClient(RenewContractServiceActivity.this, new ClientManager.RestClientCallback() {
                @Override
                public void authenticatedRestClient(final RestClient client) {
                    if (client == null) {
                        SalesforceSDKManager.getInstance().logout(RenewContractServiceActivity.this);
                        return;
                    } else {
                        new DoRenewRequest(client, actionType, contract_dwc__c).execute();
                    }
                }
            });

        }
    };

    public class DoRenewRequest extends AsyncTask<Void, Void, Void> {

        private String actionType;
        private RestClient client;
        User _user;
        Gson gson;
        Contract_DWC__c contract_dwc__c;
        private String result;

        public DoRenewRequest(RestClient client, String actionType, Contract_DWC__c contract_dwc__c) {
            this.client = client;
            this.actionType = actionType;
            gson = new Gson();
            _user = gson.fromJson(new StoreData(getApplicationContext()).getUserDataAsString(), User.class);
            this.contract_dwc__c = contract_dwc__c;
        }

        @Override
        protected void onPreExecute() {
            Utilities.showloadingDialog(RenewContractServiceActivity.this, "Renewing Contract....");
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String attUrl = client.getClientInfo().resolveUrl(DWCConfiguration.MOBILE_SERVICE_UTILITY_URL).toString();
            HttpClient tempClient = new DefaultHttpClient();
            URI theUrl = null;
            try {
                JSONObject parent = new JSONObject();
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject();
                    jsonObject.put("AccountId", _user.get_contact().get_account().getID());
                    jsonObject.put("contractId", contract_dwc__c.getID());
                    if (contract_dwc__c.IS_BC_Contract__c()) {
                        jsonObject.put("QuoteId", contract_dwc__c.getQuote().getId());
                    }
                    jsonObject.put("actionType", actionType);
                    parent.put("wrapper", jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                theUrl = new URI(attUrl);
                HttpPost getRequest = new HttpPost();

                getRequest.setURI(theUrl);
                getRequest.setHeader("Authorization", "Bearer " + client.getAuthToken());
                HttpResponse httpResponse = null;
                StringEntity se = null;
                try {
                    se = new StringEntity(parent.toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                getRequest.setEntity(se);
                try {
                    httpResponse = tempClient.execute(getRequest);
                    StatusLine statusLine = httpResponse.getStatusLine();
                    if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                        HttpEntity httpEntity = httpResponse.getEntity();
                        Log.d("response", httpEntity.toString());
                        if (httpEntity != null) {
                            result = EntityUtils.toString(httpEntity);
                        }
                    } else {
                        httpResponse.getEntity().getContent().close();
                        throw new IOException(statusLine.getReasonPhrase());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (result.contains("Success")) {
                new ClientManager(RenewContractServiceActivity.this, SalesforceSDKManager.getInstance().getAccountType(), SalesforceSDKManager.getInstance().getLoginOptions(), SalesforceSDKManager.getInstance().shouldLogoutWhenTokenRevoked()).getRestClient(RenewContractServiceActivity.this, new ClientManager.RestClientCallback() {
                    @Override
                    public void authenticatedRestClient(final RestClient client) {
                        if (client == null) {
                            SalesforceSDKManager.getInstance().logout(RenewContractServiceActivity.this);
                            return;
                        } else {
                            getCaseInfo(result.substring(8, result.length() - 1), client);
                        }
                    }
                });

            } else {
                Utilities.dismissLoadingDialog();
                Utilities.showToast(RenewContractServiceActivity.this, RestMessages.getInstance().getErrorMessage());
            }
        }
    }

    private void getCaseInfo(String caseId, final RestClient client) {
        String soql = "select id , CaseNumber , Service_Requested__c , Registration_Amendment__r.Service_Identifier__c , Registration_Amendment__c , Registration_Amendment__r.Require_Fees__c , Invoice__c , Invoice__r.Amount__c  from Case where Id=" + "\'" + caseId + "\'";
        try {
            RestRequest restRequest = RestRequest.getRequestForQuery(this.getString(R.string.api_version), soql);
            client.sendAsync(restRequest, new RestClient.AsyncRequestCallback() {
                @Override
                public void onSuccess(RestRequest request, RestResponse response) {
                    Utilities.dismissLoadingDialog();
                    Case _case = SFResponseManager.parseCaseObject(response.toString());
                    String message = String.format(getString(R.string.ServiceThankYouReservation), _case.getCaseNumber());
                    ActivitiesLauncher.openThankYouActivity(RenewContractServiceActivity.this, _case.getCaseNumber());
                }

                @Override
                public void onError(Exception exception) {
                    Utilities.dismissLoadingDialog();
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
