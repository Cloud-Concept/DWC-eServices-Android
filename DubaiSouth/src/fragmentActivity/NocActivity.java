package fragmentActivity;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;

import com.google.gson.Gson;

import cloudconcept.dwc.R;
import utilities.StoreData;
import utilities.ExceptionHandler;
import fragment.NOC.NocMainFragment;
import model.User;
import model.Visa;

public class NocActivity extends BaseFragmentActivity {

    private Gson gson;
    private String objectType;
    public static Visa _visa;
    private FragmentManager fragmentManager;
    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.noc);
        gson = new Gson();
        objectType = getIntent().getExtras().getString("ObjectType");
        if (objectType.equals("Visa")) {
            _visa = gson.fromJson(getIntent().getExtras().getString("objectAsString"), Visa.class);
        } else {
            user = gson.fromJson(new StoreData(getApplicationContext()).getUserDataAsString(), User.class);
        }
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, NocMainFragment.newInstance("BaseEmployee", this))
                .commit();

    }

    public static Visa get_visa() {
        return _visa;
    }
}
