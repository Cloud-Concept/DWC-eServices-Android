package fragmentActivity;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cloudconcept.dwc.R;
import utilities.StoreData;
import utilities.ExceptionHandler;
import fragment.License.Change.MainChangeOrRenewLicense;
import fragment.License.Renew.MainRenew;
import model.Country__c;
import model.User;
import model.WebForm;

/**
 * Created by M_Ghareeb on 9/14/2015.
 */
public class LicenseActivity extends BaseFragmentActivity {
    public String getService_Requested__c() {
        return service_Requested__c;
    }

    public void setService_Requested__c(String service_Requested__c) {
        this.service_Requested__c = service_Requested__c;
    }


    private String service_Requested__c = null;


    public ArrayList<model.OriginalBusinessActivity> get_licenses() {
        return _licenses;
    }

    public void set_licenses(ArrayList<model.OriginalBusinessActivity> _licenses) {
        this._licenses = _licenses;
    }


    public Set<model.LicenseActivity> getRemoved() {
        return removed;
    }

    public void setRemoved(Set<model.LicenseActivity> removed) {
        this.removed = removed;
    }

    Set<model.LicenseActivity> removed;

    ArrayList<model.OriginalBusinessActivity> _licenses;


    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }


    public String getCaseRecordTypeId() {
        return caseRecordTypeId;
    }

    public void setCaseRecordTypeId(String caseRecordTypeId) {
        this.caseRecordTypeId = caseRecordTypeId;
    }

    public String getInsertedServiceId() {
        return insertedServiceId;
    }

    public void setInsertedServiceId(String insertedServiceId) {
        this.insertedServiceId = insertedServiceId;
    }


    private String caseRecordTypeId = null;
    private String insertedServiceId = null;

    public ArrayList<Country__c> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<Country__c> countries) {
        this.countries = countries;
    }

    ArrayList<Country__c> countries;


    private String caseNumber = null;

    private Map<String, Object> serviceFields = new HashMap<String, Object>();
    private Map<String, String> parameters = new HashMap<String, String>();

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }


    public WebForm get_webForm() {
        return _webForm;
    }

    public void set_webForm(WebForm _webForm) {
        this._webForm = _webForm;
    }

    public Map<String, Object> getServiceFields() {
        return serviceFields;
    }

    public void setServiceFields(Map<String, Object> serviceFields) {
        this.serviceFields = serviceFields;
    }

    private WebForm _webForm;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.new_card);
        gson = new Gson();
        user = gson.fromJson(new StoreData(getApplicationContext()).getUserDataAsString(), User.class);
        setInsertedServiceId(user.get_contact().get_account().get_currentLicenseNumber().getId());
        setType(getIntent().getExtras().getString("type"));

        if (type.equals("Change License Activity") || type.equals("License Renewal")) {
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, MainChangeOrRenewLicense.newInstance(type))
                    .commit();
        } else if (type.equals("Renewal License")) {
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, MainRenew.newInstance())
                    .commit();
        }
    }
}


