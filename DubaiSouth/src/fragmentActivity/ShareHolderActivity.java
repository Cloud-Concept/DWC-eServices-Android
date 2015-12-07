package fragmentActivity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cloudconcept.dwc.R;
import utilities.StoreData;
import utilities.ExceptionHandler;
import fragment.ShareHolder.ShareAmount.MainShareHolderFragment;
import model.Country__c;
import model.ShareOwnership;
import model.User;
import model.WebForm;

/**
 * Created by M_Ghareeb on 9/8/2015.
 */
public class ShareHolderActivity extends BaseFragmentActivity {

    public ArrayList<Object> getShareHolders() {
        return shareHolders;
    }

    public void setShareHolders(ArrayList<Object> shareHolders) {
        this.shareHolders = shareHolders;
    }

    public int getShareno() {
        return shareno;
    }

    public void setShareno(int shareno) {
        this.shareno = shareno;
    }

    int shareno;
    private ArrayList<Object> shareHolders;

    public ShareOwnership getShareHolder() {
        return ShareHolder;
    }

    public void setShareHolder(ShareOwnership ShareHolder) {
        this.ShareHolder = ShareHolder;
    }

    private ShareOwnership ShareHolder;

    public ShareOwnership getSelectedShareHolder() {
        return selectedShareHolder;
    }

    public void setSelectedShareHolder(ShareOwnership selectedShareHolder) {
        this.selectedShareHolder = selectedShareHolder;
    }

    private ShareOwnership selectedShareHolder;

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    private String Duration;


    public String getService_Requested__c() {
        return service_Requested__c;
    }

    public void setService_Requested__c(String service_Requested__c) {
        this.service_Requested__c = service_Requested__c;
    }

    private String service_Requested__c = null;

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

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

        type = getIntent().getExtras().getString("type");
        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().containsKey("share")) {
                setShareHolder((ShareOwnership) getIntent().getExtras().get("share"));
                setInsertedServiceId(getShareHolder().getID());
            } else
                ShareHolder = new ShareOwnership();
            if (getIntent().getExtras().containsKey("shareHolders")) {
                setShareHolders((ArrayList<Object>) getIntent().getExtras().get("shareHolders"));
            }
        } else {
            ShareHolder = new ShareOwnership();
        }

        if (type.equals("1")) {
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, MainShareHolderFragment.newInstance("NewShareHolder"))
                    .commit();
        }
    }


}
