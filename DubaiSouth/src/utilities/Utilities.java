package utilities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.Switch;
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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import RestAPI.JSONConstants;
import RestAPI.RelatedServiceType;
import adapter.FormFieldAdapter;
import adapter.HorizontalListViewAdapter;
import adapter.NationalityAdapter;
import cloudconcept.dwc.R;
import custom.CircularProgressBarDrawable;
import custom.DWCRoundedImageView;
import custom.HorizontalListView;
import custom.customdialog.Effectstype;
import custom.customdialog.NiftyDialogBuilder;
import fragmentActivity.CardActivity;
import fragmentActivity.ChangeAndRemovalActivity;
import fragmentActivity.NameReservationActivity;
import fragmentActivity.RequestTrueCopyActivity;
import fragmentActivity.ShareHolderActivity;
import fragmentActivity.VisaActivity;
import model.Attachment;
import model.Card_Management__c;
import model.Contract_DWC__c;
import model.DWCView;
import model.EServices_Document_Checklist__c;
import model.FormField;
import model.ItemType;
import model.NOC__c;
import model.Nationality;
import model.Receipt_Template__c;
import model.ServiceItem;
import model.User;
import model.Visa;
import model.WebForm;

/**
 * Utilities is factory class that defines a set of methods that perform common, often re-used functions. define most of these common methods under static scope.
 */
public class Utilities {

    public static String contactEmail = "";
    static ProgressBar mProgressBar;
    public static ProgressDialog _progress;
    private static Receipt_Template__c eServiceAdministration;
    private static String Amount;
    private static String TotalAmount;
    private static Pattern pattern;
    private static Matcher matcher;
    private static View lineView;
    private static RestRequest restRequest;
    private static JSONObject jsonObject1;
    private static String _nameReservationAmount;

//    /**
//     * Email pattern
//     */
//
//    private static final String EMAIL_PATTERN =
//            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
//                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
//
//    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
//            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    /**
     * @param title:title               of dialog
     * @param act:container             activity that want this diaog to be shown
     * @param listenerPositive:listener for positive button ( listener for Ok)
     *                                  Show Custom dialog with two buttons  (Ok ,cancel) and handle ok by listenerPositive attribute
     */
    @SuppressWarnings("deprecation")
    public static NiftyDialogBuilder showNiftyDialog(String title, Activity act,
                                                     OnClickListener listenerPositive) {

        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder
                .getInstance(act);

        dialogBuilder
                .withTitle(title)
                .withTitleColor("#FFFFFF")
                .withDividerColor("#22b2bd")
                .withMessage(act.getResources().getString(R.string.sf__passcode_logout_confirmation))
                .withMessageColor("#FFFFFFFF")
                .withDialogColor(act.getResources().getColor(R.color.dwc_blue_color))
                .withIcon(
                        act.getResources().getDrawable(R.mipmap.dwc_launcher))
                .withDuration(300).withEffect(Effectstype.Newspager)
                .withButton1Text("OK").withButton2Text("Cancel")
                .isCancelableOnTouchOutside(true)
                .setButton1Click(listenerPositive)
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                    }
                }).show();
        return dialogBuilder;
    }

    /**
     * @param title:title               of dialog
     * @param act:container             activity that want this diaog to be shown
     * @param Text:Message              displayed inside dialog
     * @param listenerPositive:listener for positive button ( listener for Ok)
     *                                  Show Custom dialog with two buttons with custom text (Ok ,cancel) and handle ok by listenerPositive attribute
     */
    public static NiftyDialogBuilder showCustomNiftyDialog(String title, Activity act, OnClickListener listenerPositive, String Text) {
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder
                .getInstance(act);
        dialogBuilder
                .withTitle(title)
                .withTitleColor("#FFFFFF")
                .withDividerColor(act.getResources().getColor(R.color.dwc_blue_color))
                .withMessage(Text)
                .withMessageColor("#FFFFFFFF")
                .withDialogColor(act.getResources().getColor(R.color.dwc_blue_color))
                .withIcon(
                        act.getResources().getDrawable(R.mipmap.dwc_launcher))
                .withDuration(300).withEffect(Effectstype.Slidetop)
                .withButton1Text("OK").withButton2Text("Cancel")
                .isCancelableOnTouchOutside(true)
                .setButton1Click(listenerPositive)
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                    }
                }).show();
        return dialogBuilder;
    }

    /**
     * @param activity:container activity that want this dialog to be shown
     *                           Show Loading dialog which is called all over the application
     */
    public static void showloadingDialog(Activity activity) {
        CircularProgressBarDrawable mipmap = new CircularProgressBarDrawable();
        mipmap.setColors(new int[]{0xffff0000, 0xffff00a8, 0xffb400ff, 0xff2400ff, 0xff008aff,
                0xff00ffe4, 0xff00ff60, 0xff0cff00, 0xffa8ff00, 0xffffc600, 0xffff3600, 0xffff0000});
        _progress = new ProgressDialog(activity);
        _progress.setMessage("Loading ...");
        _progress.setCancelable(false);
        _progress.setProgressDrawable(mipmap);
//        _progress.setMax(100);
//        _progress.setProgress(100);
//        _progress = ProgressDialog.show(activity.getApplicationContext(), "Fetching Data", "Loading", true, false);
        _progress.show();
    }

    /**
     * check whether progress is showing to user or not
     */
    public static boolean getIsProgressLoading() {
        if (_progress != null) {
            return _progress.isShowing();
        } else {
            return false;
        }
    }

    /**
     * @param activity:container activity that want this diaog to be shown
     * @param text:text          displayed within loading dialog
     *                           Show Loading dialog which is called all over the application.
     */
    public static void showloadingDialog(Activity activity, String text) {
        _progress = new ProgressDialog(activity);
        _progress.setMessage(text);
        _progress.setCancelable(false);
        _progress.show();
    }

    /**
     * dismiss current shown loading dialog
     */
    public static void dismissLoadingDialog() {
        _progress.dismiss();
    }

    /**
     * @param act:container activity that want this message to be shown
     * @param message:the   message to be shown within toast
     *                      Show short message indicating what message want to be displayed to user
     */
    public static void showToast(Activity act, String message) {
        Toast.makeText(act, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param act:container activity that want this message to be shown
     * @param message:the   message to be shown within toast
     *                      Show long message indicating what message want to be displayed to user
     */
    public static void showLongToast(Activity act, String message) {
        Toast.makeText(act, message, Toast.LENGTH_LONG).show();
    }

    /**
     * @param act:container           activity that want this message to be shown
     * @param attachmentId:salesforce user attachment id
     * @param smartImageView:custom   rounded image to set the photo after downloading from salesforce
     *                                Check if file is already downloaded ,get from external storage and set to bitmap .If not exists and attachment id not null ,download attachment using  DownloadAttachment method.
     */
    public static synchronized void setUserPhoto(Activity act, final String attachmentId, final DWCRoundedImageView smartImageView) {
        boolean isFound = false;
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File folder = new File(extStorageDirectory, "attachment-export");
        if (folder.exists()) {
            ArrayList<String> files = getListOfAttachments();
            for (int i = 0; i < files.size(); i++) {
                if (files.get(i).equals(attachmentId)) {
                    isFound = true;
                    break;
                }
            }
            if (isFound) {
                String path = Environment.getExternalStorageDirectory() + "/attachment-export/" + attachmentId;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap bitmap = BitmapFactory.decodeFile(path, options);
                smartImageView.setImageBitmap(bitmap);
            } else {
                DownloadAttachment(act, attachmentId, smartImageView);
            }
        } else {
            DownloadAttachment(act, attachmentId, smartImageView);
        }
    }

    /**
     * @param act:container           activity that want this message to be shown
     * @param attachmentId:salesforce user attachment id
     * @param smartImageView:custom   rounded image to set the photo after downloading from salesforce
     *                                Download user photo using attachment id as bitmap and set to rounded image view
     */
    public static void DownloadAttachment(final Activity act, final String attachmentId, final DWCRoundedImageView smartImageView) {
        if (!attachmentId.equals("") && attachmentId != null) {
            List<String> fieldList = new ArrayList<String>();
            fieldList.add("Id");
            fieldList.add("Body");

            try {
                final RestRequest restRequest = RestRequest.getRequestForRetrieve(act.getApplicationContext().getResources().getString(R.string.api_version), "Attachment", attachmentId, fieldList);
                new ClientManager(act, SalesforceSDKManager.getInstance().getAccountType(), SalesforceSDKManager.getInstance().getLoginOptions(), SalesforceSDKManager.getInstance().shouldLogoutWhenTokenRevoked()).getRestClient(act, new ClientManager.RestClientCallback() {
                    @Override
                    public void authenticatedRestClient(final RestClient client) {
                        if (client == null) {
                            act.finish();
                        } else {
                            client.sendAsync(restRequest, new RestClient.AsyncRequestCallback() {
                                @Override
                                public void onSuccess(RestRequest request, RestResponse response) {
                                    try {
                                        final JSONObject json = new JSONObject(response.toString());
                                        final Attachment attachment = new Attachment();
                                        attachment.setID(json.getString("Id"));
                                        attachment.setBody(json.getString("Body"));
                                        try {
                                            new DownloadAttachmentBodyForRoundedImage(client, attachment, smartImageView).execute().get();
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        } catch (ExecutionException e) {
                                            e.printStackTrace();
                                        }

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
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            smartImageView.setImageDrawable(act.getResources().getDrawable(R.mipmap.avatar));
        }
    }

    /**
     * @param act:container           activity that want this message to be shown
     * @param attachmentId:salesforce user attachment id
     * @param smartImageView:custom   rounded image to set the photo after downloading from salesforce
     *                                Download user photo using attachment id as bitmap and set to normal image view
     */
    public static synchronized void setUserPhoto(final Activity act, final String attachmentId, final ImageView smartImageView) {

        if (!attachmentId.equals("") && attachmentId != null) {
            List<String> fieldList = new ArrayList<String>();
            fieldList.add("Id");
            fieldList.add("Body");
            try {
                final RestRequest restRequest = RestRequest.getRequestForRetrieve(act.getApplicationContext().getResources().getString(R.string.api_version), "Attachment", attachmentId, fieldList);
                new ClientManager(act, SalesforceSDKManager.getInstance().getAccountType(), SalesforceSDKManager.getInstance().getLoginOptions(), SalesforceSDKManager.getInstance().shouldLogoutWhenTokenRevoked()).getRestClient(act, new ClientManager.RestClientCallback() {
                    @Override
                    public void authenticatedRestClient(final RestClient client) {
                        if (client == null) {
                            act.finish();
                        } else {
                            client.sendAsync(restRequest, new RestClient.AsyncRequestCallback() {
                                @Override
                                public void onSuccess(RestRequest request, RestResponse response) {
                                    try {
                                        final JSONObject json = new JSONObject(response.toString());
                                        final Attachment attachment = new Attachment();
                                        attachment.setID(json.getString("Id"));
                                        attachment.setBody(json.getString("Body"));
                                        try {
                                            new DownloadAttachmentBody(client, attachment, smartImageView).execute().get();
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        } catch (ExecutionException e) {
                                            e.printStackTrace();
                                        }

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
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            smartImageView.setImageDrawable(act.getResources().getDrawable(R.mipmap.avatar));
        }
    }

    public static void drawstaticFormFields(final Activity act, final Context applicationContext, LinearLayout linearLayout, final ArrayList<FormField> formFields, Map<String, String> parameters, final Card_Management__c _noc) {

        LayoutInflater inflater = (LayoutInflater)
                applicationContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        for (FormField field : formFields) {

            if (field.getType().equals("CUSTOMTEXT")) {

                View view = inflater.inflate(R.layout.wizard_form_field_header, null, false);
                TextView tvHeader = (TextView) view.findViewById(R.id.formFieldheader);
                tvHeader.setText(field.getMobileLabel());
                linearLayout.addView(view);

            } else if (field.isParameter()) {
                {


                    View view = null;
                    TextView tvLabel;
                    EditText tvValue;


                    view = inflater.inflate(R.layout.wizard_form_field_label_disabled, null, false);


                    tvLabel = (TextView) view.findViewById(R.id.formFieldLabel);
                    tvValue = (EditText) view.findViewById(R.id.formFieldvalue);
                    tvLabel.setText(field.getMobileLabel());
                    tvValue.setText(parameters.get(field.getTextValue()));
                    String name = field.getName();
                    Field[] fields = Card_Management__c.class.getFields();
                    for (int j = 0; j < fields.length; j++)
                        if (name.toLowerCase().equals(fields[j].getName().toLowerCase()))
                            try {
                                fields[j].set(_noc, parameters.get(field.getTextValue()));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                    if (!field.isHidden())
                        linearLayout.addView(view);


                }

            } else {
                View view = null;
                TextView tvLabel;
                EditText tvValue;


                view = inflater.inflate(R.layout.wizard_form_field_label_disabled, null, false);

                tvLabel = (TextView) view.findViewById(R.id.formFieldLabel);
                tvValue = (EditText) view.findViewById(R.id.formFieldvalue);
                tvLabel.setText(field.getMobileLabel());

                String stringValue = "";
                String name = field.getName();
                Field[] fields = Card_Management__c.class.getFields();
                for (int j = 0; j < fields.length; j++)
                    if (name.toLowerCase().equals(fields[j].getName().toLowerCase()))
                        try {
                            stringValue = (String) fields[j].get(_noc);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                tvValue.setText(stringValue);
                if (!field.isHidden())
                    linearLayout.addView(view);


            }
        }
        setupUI(linearLayout, act);

    }

    public static void setupUI(View view, final Activity act) {

        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    Utilities.hideSoftKeyboard(act);
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView, act);
            }
        }
    }

    public static void DrawFormFieldsOnLayout(final Activity act, final Context applicationContext, LinearLayout linearLayout, final ArrayList<FormField> formFields, Visa _visa, JSONObject visaJson, Map<String, String> parameters, final Card_Management__c _noc) {

        LayoutInflater inflater = (LayoutInflater)
                applicationContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);


        for (FormField field : formFields) {

            if (field.isQuery() == false && field.isParameter() == false) {

                if (field.getType().equals("CUSTOMTEXT")) {

                    View view = inflater.inflate(R.layout.wizard_form_field_header, null, false);
                    TextView tvHeader = (TextView) view.findViewById(R.id.formFieldheader);
                    tvHeader.setText(field.getMobileLabel());
                    if (!field.isHidden())
                        linearLayout.addView(view);

                } else if (field.getType().equals("PICKLIST")) {

                    View view = inflater.inflate(R.layout.wizard_form_field_picklist, null, false);
                    Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
                    spinner.setTag(field);
                    final TextView tvLabel = (TextView) view.findViewById(R.id.tvLabel);
                    tvLabel.setText(field.getMobileLabel());
//                    spinner.setHint(field.getMobileLabel());
                    final String[] entries = field.getPicklistEntries().split(",");
//                    FormFieldAdapter adapter = new FormFieldAdapter(act, android.R.layout.simple_dropdown_item_1line, 0, entries);
                    FormFieldAdapter adapter = new FormFieldAdapter(act, R.layout.spinner_item_wizard, 0, entries);

                    spinner.setAdapter(adapter);

//
//                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(applicationContext, R.layout.spinner_item, entries) {
//
//                        public View getView(int position, View convertView, ViewGroup parent) {
//
//                            View v = super.getView(position, convertView, parent);
//
//                            ((TextView) v).setGravity(Gravity.CENTER);
//
//                            ((TextView) v).setTextSize(12);
//
//                            return v;
//
//                        }
//
//                        public View getDropDownView(int position, View convertView, ViewGroup parent) {
//
//                            View v = super.getDropDownView(position, convertView, parent);
//
//                            ((TextView) v).setGravity(Gravity.CENTER);
//                            ((TextView) v).setTextSize(12);
//                            return v;
//
//                        }
//
//                    };
//
//                    adapter.setDropDownViewResource(R.layout.spinner_item_center);
//                    spinner.setAdapter(adapter);


                    spinner.setSelection(0);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            new StoreData(applicationContext).setPickListSelected(tvLabel.getText().toString(), entries[position]);
                            FormField f = (FormField) parent.getTag();
                            String name = f.getName();
                            Field[] fields = Card_Management__c.class.getFields();
                            for (int j = 0; j < fields.length; j++)
                                if (name.toLowerCase().equals(fields[j].getName().toLowerCase()))
                                    try {
                                        fields[j].set(_noc, entries[position]);
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    if (!field.isHidden())
                        linearLayout.addView(view);

                } else if (field.getType().equals("REFERENCE")) {

                    View view = inflater.inflate(R.layout.wizard_form_field_picklist, null, false);
                    Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
                    spinner.setTag(field);
                    final TextView tvLabel = (TextView) view.findViewById(R.id.tvLabel);
                    tvLabel.setText(field.getMobileLabel());
//                    spinner.setHint(field.getMobileLabel());
                    NationalityAdapter adapter = new NationalityAdapter(act, R.layout.spinner_item_wizard, 0, ((CardActivity) act).getCountries());
                    adapter.setDropDownViewResource(R.layout.customtext);
//                    adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    spinner.setAdapter(adapter);
                    spinner.setSelection(0);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            FormField f = (FormField) parent.getTag();
                            String name = f.getName();
                            Field[] fields = Card_Management__c.class.getFields();
                            for (int j = 0; j < fields.length; j++)
                                if (name.toLowerCase().equals(fields[j].getName().toLowerCase()))
                                    try {
                                        fields[j].set(_noc, ((CardActivity) act).getCountries().get(position).getId());
                                        Nationality nationality = new Nationality();
                                        nationality.setId(((CardActivity) act).getCountries().get(position).getId());
                                        nationality.setName(((CardActivity) act).getCountries().get(position).getNationality_Name__c());
                                        _noc.setNationality__r(nationality);

                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    }


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    if (!field.isHidden())
                        linearLayout.addView(view);


                } else if (field.getType().equals("BOOLEAN")) {
                    View view = inflater.inflate(R.layout.wizard_form_field_checkbox, null, false);
                    final TextView tvLabel;
                    Switch switchView;
                    tvLabel = (TextView) view.findViewById(R.id.tvLabel);
                    switchView = (Switch) view.findViewById(R.id.switchView);
                    switchView.setTag(field);
                    tvLabel.setText(field.getMobileLabel());

                    switchView.setOncheckListener(new Switch.OnCheckListener() {

                        @Override
                        public void onCheck(Switch aSwitch, boolean b) {

                            FormField f = (FormField) aSwitch.getTag();
                            String name = f.getName();
                            Field[] fields = Card_Management__c.class.getFields();
                            for (int j = 0; j < fields.length; j++)
                                if (name.toLowerCase().equals(fields[j].getName().toLowerCase()))
                                    try {
                                        fields[j].set(_noc, b);
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    }

                        }
                    });
                    if (!field.isHidden())
                        linearLayout.addView(view);

                } else if (field.getType().equals("EMAIL")) {

                    View view = inflater.inflate(R.layout.wizard_form_field_edit_text_email, null, false);
                    EditText etEmail = (EditText) view.findViewById(R.id.etEmail);
                    TextView tvLabel = (TextView) view.findViewById(R.id.tvLabel);
                    String stringValue = "";
                    String name = field.getName();
                    if (name.equals("NOC_Receiver_Email__c")) {
                        String user = new StoreData(applicationContext).getUserDataAsString();
                        Gson g = new Gson();
                        User u = g.fromJson(user, User.class);
                        stringValue = contactEmail;
                        Field[] fields = Card_Management__c.class.getFields();
                        for (int j = 0; j < fields.length; j++)
                            if (name.toLowerCase().equals(fields[j].getName().toLowerCase()))
                                try {
                                    fields[j].set(_noc, stringValue);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                    } else {
                        Field[] fields = Card_Management__c.class.getFields();
                        for (int j = 0; j < fields.length; j++)
                            if (name.toLowerCase().equals(fields[j].getName().toLowerCase()))
                                try {
                                    stringValue = (String) fields[j].get(_noc);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                    }

                    etEmail.setText(stringValue);
                    etEmail.setTag(field);
                    etEmail.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);

                    etEmail.addTextChangedListener(new GenericTextWatcherCard(etEmail, _noc));
                    tvLabel.setText(field.getMobileLabel());
                    if (!field.isHidden())
                        linearLayout.addView(view);
                } else if (field.getType().equals("DOUBLE")) {
                    View view = inflater.inflate(R.layout.wizard_form_field_label_enabled, null, false);
                    EditText etEmail = (EditText) view.findViewById(R.id.formFieldvalue);
                    TextView tvLabel = (TextView) view.findViewById(R.id.formFieldLabel);

                    TextView formFieldLabelrequired = (TextView) view.findViewById(R.id.formFieldLabelrequired);
                    if (field.isRequired())
                        formFieldLabelrequired.setVisibility(View.VISIBLE);
                    String stringValue = "";
                    String name = field.getName();
                    etEmail.setText(stringValue);
                    etEmail.setHint(field.getMobileLabel());
                    etEmail.setTag(field);
                    etEmail.setInputType(InputType.TYPE_CLASS_NUMBER);
                    etEmail.addTextChangedListener(new GenericTextWatcherCard(etEmail, _noc));
                    tvLabel.setText(field.getMobileLabel());
                    if (!field.isHidden())
                        linearLayout.addView(view);
                } else if (field.getType().equals("STRING")) {
                    View view = inflater.inflate(R.layout.wizard_form_field_label_enabled, null, false);
                    EditText etEmail = (EditText) view.findViewById(R.id.formFieldvalue);
                    TextView tvLabel = (TextView) view.findViewById(R.id.formFieldLabel);

                    TextView formFieldLabelrequired = (TextView) view.findViewById(R.id.formFieldLabelrequired);
                    if (field.isRequired())
                        formFieldLabelrequired.setVisibility(View.VISIBLE);
                    String stringValue = "";
                    String name = field.getName();
                    etEmail.setText(stringValue);
                    etEmail.setTag(field);
                    etEmail.setInputType(InputType.TYPE_CLASS_TEXT);
                    etEmail.addTextChangedListener(new GenericTextWatcherCard(etEmail, _noc));
                    tvLabel.setText(field.getMobileLabel());
                    etEmail.setHint(field.getMobileLabel());
                    if (!field.isHidden())
                        linearLayout.addView(view);
                } else if (field.getType().equals("DATE")) {
                    String name = field.getName();
                    Field[] fields = Card_Management__c.class.getFields();
                    for (int j = 0; j < fields.length; j++)
                        if (name.toLowerCase().equals(fields[j].getName().toLowerCase()))
                            try {
                                fields[j].set(_noc, "2017-02-22");
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                }

            } else if (field.isParameter() == false && field.isQuery() == true) {
                if (field.getType().equals("STRING")) {

                    View view = null;
                    TextView tvLabel;
                    EditText tvValue;

                    if (field.isCalculated()) {
                        view = inflater.inflate(R.layout.wizard_form_field_label_disabled, null, false);
                    } else {
                        view = inflater.inflate(R.layout.wizard_form_field_label_enabled, null, false);
                    }

                    tvLabel = (TextView) view.findViewById(R.id.formFieldLabel);
                    tvValue = (EditText) view.findViewById(R.id.formFieldvalue);
                    tvLabel.setText(field.getMobileLabel());

                    String stringValue = "";
                    String name = field.getName();
                    Field[] fields = Card_Management__c.class.getFields();
                    for (int j = 0; j < fields.length; j++)
                        if (name.toLowerCase().equals(fields[j].getName().toLowerCase()))
                            try {
                                stringValue = (String) fields[j].get(_noc);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                    tvValue.setText(stringValue);
                    if (!field.isHidden())
                        linearLayout.addView(view);

                }
            } else if (field.isParameter() == true && field.isQuery() == false) {


                View view = null;
                TextView tvLabel;
                EditText tvValue;


                view = inflater.inflate(R.layout.wizard_form_field_label_disabled, null, false);


                tvLabel = (TextView) view.findViewById(R.id.formFieldLabel);
                tvValue = (EditText) view.findViewById(R.id.formFieldvalue);
                tvLabel.setText(field.getMobileLabel());
                tvValue.setText(parameters.get(field.getTextValue()));
                String name = field.getName();
                Field[] fields = Card_Management__c.class.getFields();
                for (int j = 0; j < fields.length; j++)
                    if (name.toLowerCase().equals(fields[j].getName().toLowerCase()))
                        try {
                            fields[j].set(_noc, parameters.get(field.getTextValue()));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                if (!field.isHidden())
                    linearLayout.addView(view);
            }
        }
        setupUI(linearLayout, act);
    }

    public static void DrawFormFieldsOnLayout(final Activity act, final Context applicationContext, final LinearLayout linearLayout, final ArrayList<FormField> formFields, final Map<String, String> parameters, final EServices_Document_Checklist__c eServices_document_checklist__c, final Receipt_Template__c eService_administration__r) {

        String soql = "SELECT ID, Amount__c, Total_Amount__c, Knowledge_Fee__c FROM Receipt_Template__c WHERE ID=" + "\'" + eService_administration__r.getID() + "\'";
        Utilities.showloadingDialog(act, "Displaying Fields");

        try {
            final RestRequest restRequest = RestRequest.getRequestForQuery(act.getString(R.string.api_version), soql);
            new ClientManager(act, SalesforceSDKManager.getInstance().getAccountType(), SalesforceSDKManager.getInstance().getLoginOptions(), SalesforceSDKManager.getInstance().shouldLogoutWhenTokenRevoked()).getRestClient(act, new ClientManager.RestClientCallback() {
                @Override
                public void authenticatedRestClient(final RestClient client) {
                    if (client == null) {
                        SalesforceSDKManager.getInstance().logout(act);
                        return;
                    } else {
                        client.sendAsync(restRequest, new RestClient.AsyncRequestCallback() {

                            @Override
                            public void onSuccess(RestRequest request, RestResponse result) {
                                Utilities.dismissLoadingDialog();
                                try {
                                    JSONObject jsonObject = new JSONObject(result.toString());
                                    JSONArray jArrayRecords = jsonObject.getJSONArray(JSONConstants.RECORDS);
                                    JSONObject json = jArrayRecords.getJSONObject(0);
                                    Amount = json.getString("Amount__c");
                                    TotalAmount = json.getString("Total_Amount__c");
                                    eService_administration__r.setAmount__c(Double.valueOf(Amount));
                                    eService_administration__r.setTotal_Amount__c(Double.valueOf(TotalAmount));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                LayoutInflater inflater = (LayoutInflater)
                                        applicationContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

                                for (FormField field : formFields) {
                                    if (!field.isMobileAvailable() || field.isHidden()) {
                                        continue;
                                    }
                                    if (field.getMobileLabel().equals("Account ID")) {
                                        continue;
                                    }
                                    if (field.isQuery() == false && field.isParameter() == false) {

                                        if (field.getType().equals("CUSTOMTEXT")) {

                                            View view = inflater.inflate(R.layout.wizard_form_field_header, null, false);
                                            TextView tvHeader = (TextView) view.findViewById(R.id.formFieldheader);
                                            tvHeader.setText(field.getMobileLabel());
                                            linearLayout.addView(view);

                                        } else if (field.getType().equals("PICKLIST")) {

                                            View view = inflater.inflate(R.layout.wizard_form_field_picklist, null, false);
                                            Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
                                            spinner.setTag(field);
                                            final TextView tvLabel = (TextView) view.findViewById(R.id.tvLabel);
                                            tvLabel.setText(field.getMobileLabel());
//                    spinner.setHint(field.getMobileLabel());
                                            final String[] entries = field.getPicklistEntries().split(",");
                                            FormFieldAdapter adapter = new FormFieldAdapter(act, android.R.layout.simple_list_item_1, 0, entries);
                                            adapter.setDropDownViewResource(R.layout.customtext);
                                            spinner.setAdapter(adapter);
                                            spinner.setSelection(0);
                                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                                @Override
                                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                    new StoreData(applicationContext).setPickListSelected(tvLabel.getText().toString(), entries[position]);
                                                    FormField f = (FormField) parent.getTag();
                                                    String name = f.getName();
                                                    Field[] fields = EServices_Document_Checklist__c.class.getFields();
                                                    for (int j = 0; j < fields.length; j++)
                                                        if (name.toLowerCase().equals(fields[j].getName().toLowerCase()))
                                                            try {
                                                                fields[j].set(eServices_document_checklist__c, entries[position]);
                                                            } catch (IllegalAccessException e) {
                                                                e.printStackTrace();
                                                            }

                                                }

                                                @Override
                                                public void onNothingSelected(AdapterView<?> parent) {

                                                }
                                            });

                                            linearLayout.addView(view);

                                        } else if (field.getType().equals("REFERENCE")) {

//                    View view = inflater.inflate(R.layout.wizard_form_field_picklist, null, false);
//                    Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
//                    spinner.setTag(field);
//                    final TextView tvLabel = (TextView) view.findViewById(R.id.tvLabel);
//                    tvLabel.setText(field.getMobileLabel());
////                    spinner.setHint(field.getMobileLabel());
//                    NationalityAdapter adapter = new NationalityAdapter(act, android.R.layout.simple_list_item_1, 0, field.getPicklistEntries());
//                    adapter.setDropDownViewResource(R.layout.customtext);
//                    spinner.setAdapter(adapter);
//                    spinner.setSelection(0);
//                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//                        @Override
//                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                            FormField f = (FormField) parent.getTag();
//                            String name = f.getName();
//                            Field[] fields = EServices_Document_Checklist__c.class.getFields();
//                            for (int j = 0; j < fields.length; j++)
//                                if (name.toLowerCase().equals(fields[j].getName().toLowerCase()))
//                                    try {
//                                        fields[j].set(_noc, ((CardActivity) act).getCountries().get(position).getId());
//                                    } catch (IllegalAccessException e) {
//                                        e.printStackTrace();
//                                    }
//
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> parent) {
//
//                        }
//                    });
//
//                    linearLayout.addView(view);


                                        } else if (field.getType().equals("BOOLEAN")) {
                                            View view = inflater.inflate(R.layout.wizard_form_field_checkbox, null, false);
                                            final TextView tvLabel;
                                            Switch switchView;
                                            tvLabel = (TextView) view.findViewById(R.id.tvLabel);
                                            switchView = (Switch) view.findViewById(R.id.switchView);
                                            switchView.setTag(field);
                                            tvLabel.setText(field.getMobileLabel());

                                            switchView.setOncheckListener(new Switch.OnCheckListener() {

                                                @Override
                                                public void onCheck(Switch aSwitch, boolean b) {

                                                    FormField f = (FormField) aSwitch.getTag();
                                                    String name = f.getName();
                                                    Field[] fields = EServices_Document_Checklist__c.class.getFields();
                                                    for (int j = 0; j < fields.length; j++)
                                                        if (name.toLowerCase().equals(fields[j].getName().toLowerCase()))
                                                            try {
                                                                fields[j].set(eServices_document_checklist__c, b);
                                                            } catch (IllegalAccessException e) {
                                                                e.printStackTrace();
                                                            }

                                                }
                                            });

                                            linearLayout.addView(view);

                                        } else if (field.getType().equals("EMAIL")) {

                                            View view = inflater.inflate(R.layout.wizard_form_field_label_request_true_copy_enabled, null, false);
                                            EditText etEmail = (EditText) view.findViewById(R.id.formFieldvalue);
                                            TextView tvLabel = (TextView) view.findViewById(R.id.formFieldLabel);
                                            etEmail.setImeOptions(EditorInfo.IME_ACTION_DONE);
                                            TextView formFieldLabelrequired = (TextView) view.findViewById(R.id.formFieldLabelrequired);
                                            if (field.isRequired())
                                                formFieldLabelrequired.setVisibility(View.VISIBLE);
                                            String stringValue = "";
                                            String name = field.getName();
//                                            Gson gson = new Gson();
//                                            User user = gson.fromJson(new StoreData(view.getContext()).getUserDataAsString(), User.class);
//                                            Field[] fields = EServices_Document_Checklist__c.class.getFields();
//                                            for (int j = 0; j < fields.length; j++)
//                                                if (name.toLowerCase().equals(fields[j].getName().toLowerCase()))
//                                                    try {
//                                                        stringValue = (String) fields[j].get(eServices_document_checklist__c);
//                                                        if (stringValue == null || stringValue.equals("")) {
//                                                            stringValue = user.get_contact().get_account().getEmail();
//                                                        }
//                                                        fields[j].set(eServices_document_checklist__c, stringValue);
//                                                    } catch (IllegalAccessException e) {
//                                                        e.printStackTrace();
//                                                    }

                                            String user = new StoreData(applicationContext).getUserDataAsString();
                                            Gson g = new Gson();
                                            User u = g.fromJson(user, User.class);
                                            stringValue = contactEmail;
                                            Field[] fields = EServices_Document_Checklist__c.class.getFields();
                                            for (int j = 0; j < fields.length; j++)
                                                if (name.toLowerCase().equals(fields[j].getName().toLowerCase()))
                                                    try {
                                                        fields[j].set(eServices_document_checklist__c, stringValue);
                                                    } catch (IllegalAccessException e) {
                                                        e.printStackTrace();
                                                    }


                                            etEmail.setText(stringValue);
                                            etEmail.setTag(field);
                                            etEmail.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);

                                            etEmail.addTextChangedListener(new GenericTextWatcherEServiceDocument(etEmail, eServices_document_checklist__c));
                                            tvLabel.setText(field.getMobileLabel());
                                            linearLayout.addView(view);
                                        } else if (field.getType().equals("DOUBLE")) {
                                            View view = inflater.inflate(R.layout.wizard_form_field_label_enabled, null, false);
                                            EditText etEmail = (EditText) view.findViewById(R.id.formFieldvalue);
                                            TextView tvLabel = (TextView) view.findViewById(R.id.formFieldLabel);

                                            TextView formFieldLabelrequired = (TextView) view.findViewById(R.id.formFieldLabelrequired);
                                            if (field.isRequired())
                                                formFieldLabelrequired.setVisibility(View.VISIBLE);
                                            String stringValue = "";
                                            String name = field.getName();
                                            etEmail.setText(stringValue);
                                            etEmail.setHint(field.getMobileLabel());
                                            etEmail.setTag(field);
                                            etEmail.setInputType(InputType.TYPE_CLASS_NUMBER);
                                            etEmail.addTextChangedListener(new GenericTextWatcherEServiceDocument(etEmail, eServices_document_checklist__c));
                                            tvLabel.setText(field.getMobileLabel());

                                            linearLayout.addView(view);
                                        } else if (field.getType().equals("STRING")) {
                                            View view = inflater.inflate(R.layout.wizard_form_field_label_enabled, null, false);
                                            EditText etEmail = (EditText) view.findViewById(R.id.formFieldvalue);
                                            TextView tvLabel = (TextView) view.findViewById(R.id.formFieldLabel);

                                            TextView formFieldLabelrequired = (TextView) view.findViewById(R.id.formFieldLabelrequired);
                                            if (field.isRequired())
                                                formFieldLabelrequired.setVisibility(View.VISIBLE);
                                            String stringValue = "";
                                            String name = field.getName();
                                            etEmail.setText(stringValue);
                                            etEmail.setTag(field);
                                            etEmail.setInputType(InputType.TYPE_CLASS_TEXT);
                                            etEmail.addTextChangedListener(new GenericTextWatcherEServiceDocument(etEmail, eServices_document_checklist__c));
                                            tvLabel.setText(field.getMobileLabel());
                                            etEmail.setHint(field.getMobileLabel());

                                            linearLayout.addView(view);
                                        } else if (field.getType().equals("DATE")) {
                                            String name = field.getName();
                                            Field[] fields = EServices_Document_Checklist__c.class.getFields();
                                            for (int j = 0; j < fields.length; j++)
                                                if (name.toLowerCase().equals(fields[j].getName().toLowerCase()))
                                                    try {
                                                        fields[j].set(eServices_document_checklist__c, "2017-02-22");
                                                    } catch (IllegalAccessException e) {
                                                        e.printStackTrace();
                                                    }
                                        }

                                    } else if (field.isParameter() == false && field.isQuery() == true) {
                                        if (field.getType().equals("STRING")) {
                                            View view = null;
                                            TextView tvLabel;
                                            EditText tvValue;

                                            if (field.isCalculated()) {
                                                view = inflater.inflate(R.layout.wizard_form_field_label_disabled, null, false);
                                            } else {
                                                view = inflater.inflate(R.layout.wizard_form_field_label_enabled, null, false);
                                            }

                                            tvLabel = (TextView) view.findViewById(R.id.formFieldLabel);
                                            tvValue = (EditText) view.findViewById(R.id.formFieldvalue);
                                            tvLabel.setText(field.getMobileLabel());

                                            String stringValue = "";
                                            String name = field.getName();
                                            Field[] fields = EServices_Document_Checklist__c.class.getFields();
                                            for (int j = 0; j < fields.length; j++)
                                                if (name.toLowerCase().equals(fields[j].getName().toLowerCase()))
                                                    try {
                                                        stringValue = (String) fields[j].get(eServices_document_checklist__c);
                                                    } catch (IllegalAccessException e) {
                                                        e.printStackTrace();
                                                    }
                                            tvValue.setText(stringValue);
                                            linearLayout.addView(view);

                                        }
                                    } else if (field.isParameter() == true && field.isQuery() == false) {
                                        View view = null;
                                        TextView tvLabel;
                                        EditText tvValue;
                                        view = inflater.inflate(R.layout.wizard_form_field_label_disabled, null, false);
                                        tvLabel = (TextView) view.findViewById(R.id.formFieldLabel);
                                        tvValue = (EditText) view.findViewById(R.id.formFieldvalue);
                                        tvLabel.setText(field.getMobileLabel());
                                        tvValue.setText(parameters.get(field.getTextValue()));
                                        String name = field.getName();
                                        Field[] fields = EServices_Document_Checklist__c.class.getFields();
                                        for (int j = 0; j < fields.length; j++)
                                            if (name.toLowerCase().equals(fields[j].getName().toLowerCase()))
                                                try {
                                                    fields[j].set(eServices_document_checklist__c, parameters.get(field.getTextValue()));
                                                } catch (IllegalAccessException e) {
                                                    e.printStackTrace();
                                                }
                                        linearLayout.addView(view);
                                    }
                                }


                                View view = inflater.inflate(R.layout.wizard_form_field_header, null, false);
                                TextView tvHeader = (TextView) view.findViewById(R.id.formFieldheader);
                                tvHeader.setText("REQUEST INFORMATION");
                                linearLayout.addView(view);

//                                View view2 = inflater.inflate(R.layout.wizard_form_field_label_enabled, null, false);
//
//                                EditText etEmail = (EditText) view2.findViewById(R.id.formFieldvalue);
//                                TextView tvLabel = (TextView) view2.findViewById(R.id.formFieldLabel);
//
//                                tvLabel.setText("E-Service Price");
//                                etEmail.setText(Amount + " AED.");
//                                etEmail.setKeyListener(null);
//
//                                linearLayout.addView(view2);


                                View view3 = inflater.inflate(R.layout.wizard_form_field_currency_label_enabled, null, false);

                                EditText etEmail3 = (EditText) view3.findViewById(R.id.formFieldvalue);
                                TextView tvLabel3 = (TextView) view3.findViewById(R.id.formFieldLabel);

                                tvLabel3.setText("Total Amount To Pay");
                                etEmail3.setText(Utilities.processAmount(TotalAmount) + " AED.");

                                etEmail3.setKeyListener(null);

                                linearLayout.addView(view3);
//                                RequestTrueCopyActivity act2 = (RequestTrueCopyActivity) act;
//                                act2.getWebForm().set_formFields(formFields);

                            }

                            @Override
                            public void onError(Exception exception) {
                                Utilities.dismissLoadingDialog();
                            }
                        });
                    }
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        setupUI(linearLayout, act);
    }


    public static void DrawFormFieldsOnLayout(Activity act, final Context applicationContext, LinearLayout linearLayout, final ArrayList<FormField> formFields, Visa _visa, JSONObject visaJson, Map<String, String> parameters, final NOC__c _noc) {

        LayoutInflater inflater = (LayoutInflater)
                applicationContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);


        for (FormField field : formFields) {
            if (field.isQuery() == false && field.isParameter() == false) {

                if (field.getType().equals("CUSTOMTEXT")) {

                    View view = inflater.inflate(R.layout.wizard_form_field_header, null, false);
                    TextView tvHeader = (TextView) view.findViewById(R.id.formFieldheader);
                    tvHeader.setText(field.getMobileLabel());
                    linearLayout.addView(view);

                } else if (field.getType().equals("PICKLIST")) {

                    View view = inflater.inflate(R.layout.wizard_form_field_picklist, null, false);
                    Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
                    spinner.setTag(field);
                    final TextView tvLabel = (TextView) view.findViewById(R.id.tvLabel);
                    tvLabel.setText(field.getMobileLabel());
//                    spinner.setHint(field.getMobileLabel());
                    final String[] entries = field.getPicklistEntries().split(",");
                    FormFieldAdapter adapter = new FormFieldAdapter(act, android.R.layout.simple_list_item_1, 0, entries);
                    adapter.setDropDownViewResource(R.layout.customtext);
                    spinner.setAdapter(adapter);
                    spinner.setSelection(0);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            new StoreData(applicationContext).setPickListSelected(tvLabel.getText().toString(), entries[position]);
                            FormField f = (FormField) parent.getTag();
                            String name = f.getName();
                            Field[] fields = NOC__c.class.getFields();
                            for (int j = 0; j < fields.length; j++)
                                if (name.toLowerCase().equals(fields[j].getName().toLowerCase()))
                                    try {
                                        fields[j].set(_noc, entries[position]);
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    linearLayout.addView(view);

                } else if (field.getType().equals("BOOLEAN")) {
                    getNocAndFields(visaJson, field, _noc);
                    View view = inflater.inflate(R.layout.wizard_form_field_checkbox, null, false);
                    final TextView tvLabel;
                    Switch switchView;
                    tvLabel = (TextView) view.findViewById(R.id.tvLabel);
                    switchView = (Switch) view.findViewById(R.id.switchView);
                    switchView.setTag(field);
                    tvLabel.setText(field.getMobileLabel());

                    switchView.setOncheckListener(new Switch.OnCheckListener() {

                        @Override
                        public void onCheck(Switch aSwitch, boolean b) {

                            FormField f = (FormField) aSwitch.getTag();
                            String name = f.getName();
                            Field[] fields = NOC__c.class.getFields();
                            for (int j = 0; j < fields.length; j++)
                                if (name.toLowerCase().equals(fields[j].getName().toLowerCase()))
                                    try {
                                        fields[j].set(_noc, b);
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    }

                        }
                    });

                    linearLayout.addView(view);

                } else if (field.getType().equals("EMAIL")) {
//                    getNocAndFields(visaJson, field, _noc);
                    View view = inflater.inflate(R.layout.wizard_form_field_edit_text_email, null, false);
                    EditText etEmail = (EditText) view.findViewById(R.id.etEmail);
                    TextView tvLabel = (TextView) view.findViewById(R.id.tvLabel);
                    String stringValue = "";
                    String name = field.getName();
                    if (name.equals("NOC_Receiver_Email__c")) {
                        String user = new StoreData(applicationContext).getUserDataAsString();
                        Gson g = new Gson();
                        User u = g.fromJson(user, User.class);
                        stringValue = contactEmail;
                        Field[] fields = NOC__c.class.getFields();
                        for (int j = 0; j < fields.length; j++)
                            if (name.toLowerCase().equals(fields[j].getName().toLowerCase()))
                                try {
                                    fields[j].set(_noc, stringValue);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                    } else {
                        getNocAndFields(visaJson, field, _noc);
                        Field[] fields = NOC__c.class.getFields();
                        for (int j = 0; j < fields.length; j++)
                            if (name.toLowerCase().equals(fields[j].getName().toLowerCase()))
                                try {
                                    stringValue = (String) fields[j].get(_noc);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                    }

                    etEmail.setText(stringValue);
                    etEmail.setTag(field);
                    etEmail.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);

                    etEmail.addTextChangedListener(new GenericTextWatcher(etEmail, _noc));
                    tvLabel.setText(field.getMobileLabel());
                    linearLayout.addView(view);
                } else if (field.getType().equals("DOUBLE")) {
                    View view = inflater.inflate(R.layout.wizard_form_field_label_enabled, null, false);
                    EditText etEmail = (EditText) view.findViewById(R.id.formFieldvalue);
                    TextView tvLabel = (TextView) view.findViewById(R.id.formFieldLabel);

                    TextView formFieldLabelrequired = (TextView) view.findViewById(R.id.formFieldLabelrequired);
                    if (field.isRequired())
                        formFieldLabelrequired.setVisibility(View.VISIBLE);
                    String stringValue = "";
                    String name = field.getName();
                    etEmail.setText(stringValue);
                    etEmail.setHint(field.getMobileLabel());
                    etEmail.setTag(field);
                    etEmail.setInputType(InputType.TYPE_CLASS_NUMBER);
                    etEmail.addTextChangedListener(new GenericTextWatcher(etEmail, _noc));
                    tvLabel.setText(field.getMobileLabel());

                    linearLayout.addView(view);
                } else if (field.getType().equals("STRING")) {
                    View view = inflater.inflate(R.layout.wizard_form_field_label_enabled, null, false);
                    EditText etEmail = (EditText) view.findViewById(R.id.formFieldvalue);
                    TextView tvLabel = (TextView) view.findViewById(R.id.formFieldLabel);

                    TextView formFieldLabelrequired = (TextView) view.findViewById(R.id.formFieldLabelrequired);
                    if (field.isRequired())
                        formFieldLabelrequired.setVisibility(View.VISIBLE);
                    String stringValue = "";
                    String name = field.getName();
                    etEmail.setText(stringValue);
                    etEmail.setTag(field);
                    etEmail.setInputType(InputType.TYPE_CLASS_TEXT);
                    etEmail.addTextChangedListener(new GenericTextWatcher(etEmail, _noc));
                    tvLabel.setText(field.getMobileLabel());
                    etEmail.setHint(field.getMobileLabel());

                    linearLayout.addView(view);
                }

            } else if (field.isParameter() == false && field.isQuery() == true) {
                if (field.getType().equals("STRING")) {
                    getNocAndFields(visaJson, field, _noc);

                    View view = null;
                    TextView tvLabel;
                    EditText tvValue;

                    if (field.isCalculated()) {
                        view = inflater.inflate(R.layout.wizard_form_field_label_disabled, null, false);
                    } else {
                        view = inflater.inflate(R.layout.wizard_form_field_label_enabled, null, false);
                    }

                    tvLabel = (TextView) view.findViewById(R.id.formFieldLabel);
                    tvValue = (EditText) view.findViewById(R.id.formFieldvalue);
                    tvLabel.setText(field.getMobileLabel());

                    String stringValue = "";
                    String name = field.getName();
                    Field[] fields = NOC__c.class.getFields();
                    for (int j = 0; j < fields.length; j++)
                        if (name.toLowerCase().equals(fields[j].getName().toLowerCase()))
                            try {
                                stringValue = (String) fields[j].get(_noc);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                    tvValue.setText(stringValue);
                    linearLayout.addView(view);

                }
            } else if (field.isParameter() == true && field.isQuery() == false) {


                View view = null;
                TextView tvLabel;
                EditText tvValue;


                view = inflater.inflate(R.layout.wizard_form_field_label_disabled, null, false);


                tvLabel = (TextView) view.findViewById(R.id.formFieldLabel);
                tvValue = (EditText) view.findViewById(R.id.formFieldvalue);
                tvLabel.setText(field.getMobileLabel());
                tvValue.setText(parameters.get(field.getTextValue()));
                String name = field.getName();
                Field[] fields = NOC__c.class.getFields();
                for (int j = 0; j < fields.length; j++)
                    if (name.toLowerCase().equals(fields[j].getName().toLowerCase()))
                        try {
                            fields[j].set(_noc, parameters.get(field.getTextValue()));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                linearLayout.addView(view);


            }

        }

        setupUI(linearLayout, act);
    }

    /**
     * @param filterItem:View statement filter item
     * @return array of start date and end date according to view statement filter item
     * filter start date and end date of according to chosen view statement filter item and define which quarter that user is working on
     */
    public static Date[] formatStartAndEndDate(String filterItem) {

        Date startDate = null, endDate = null;
        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentYear = calendar.get(Calendar.YEAR);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        Calendar calendar1 = null;
        try {
            calendar1 = getDateCalendar(currentYear, currentMonth, currentDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (currentMonth >= 1 && currentMonth <= 3) {
            calendar1.set(currentYear, 1, 1);
        } else if (currentMonth >= 4 && currentMonth <= 6) {
            calendar1.set(currentYear, 3, 1);
        } else if (currentMonth >= 7 && currentMonth <= 9) {
            calendar1.set(currentYear, 6, 1);
        } else if (currentMonth >= 10 && currentMonth <= 12) {
            calendar1.set(currentYear, 9, 1);
        }

        if (filterItem.equals("Current Quarter")) {
            startDate = calendar1.getTime();
            calendar1.add(Calendar.MONTH, 3);
            endDate = calendar1.getTime();
        } else if (filterItem.equals("Last Quarter")) {
            endDate = calendar1.getTime();
            calendar1.add(Calendar.MONTH, -3);
            startDate = calendar1.getTime();
        } else if (filterItem.equals("Current Year")) {
            startDate = calendar1.getTime();
            calendar1.add(Calendar.YEAR, 1);
            endDate = calendar1.getTime();

        } else if (filterItem.equals("Last Year")) {
            endDate = calendar1.getTime();
            calendar1.add(Calendar.YEAR, -1);
            startDate = calendar1.getTime();
        }
        return new Date[]{startDate, endDate};
    }

    public static Calendar getDateCalendar(int year, int month, int day) throws ParseException {
        Calendar calendar = Calendar.getInstance();
//        cal.set(Calendar.YEAR, year);
//        cal.set(Calendar.MONTH, month);
//        cal.set(Calendar.DAY_OF_MONTH, day);
//        cal.set(Calendar.HOUR_OF_DAY, 0);
//        cal.set(Calendar.MINUTE, 0);
//        cal.set(Calendar.SECOND, 0);
//        cal.set(Calendar.MILLISECOND, 0);
        calendar.clear();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar;
    }

    private static Date getDate(int currentYear, int month, int day) {
        Date date = new Date();
        date.setYear(currentYear);
        date.setMonth(month);
        date.setDate(day);
        return date;
    }

    /**
     * @param s:date as string
     * @return string indicating the date after formatting
     * Get date and format it like this (01-oct-2015)
     */
    public static String formatVisitVisaDate(String s) {
        if (s == null || s.equals("")) {
            return "";
        } else {
            String[] date = s.split("-");
            if (date[1].equals("01")) {
                return date[2] + "-Jan-" + date[0];
            } else if (date[1].equals("02")) {
                return date[2] + "-Feb-" + date[0];
            } else if (date[1].equals("03")) {
                return date[2] + "-Mar-" + date[0];
            } else if (date[1].equals("04")) {
                return date[2] + "-Apr-" + date[0];
            } else if (date[1].equals("05")) {
                return date[2] + "-May-" + date[0];
            } else if (date[1].equals("06")) {
                return date[2] + "-Jun-" + date[0];
            } else if (date[1].equals("07")) {
                return date[2] + "-Jul-" + date[0];
            } else if (date[1].equals("08")) {
                return date[2] + "-Aug-" + date[0];
            } else if (date[1].equals("09")) {
                return date[2] + "-Sep-" + date[0];
            } else if (date[1].equals("10")) {
                return date[2] + "-Oct-" + date[0];
            } else if (date[1].equals("11")) {
                return date[2] + "-Nov-" + date[0];
            } else if (date[1].equals("12")) {
                return date[2] + "-Dec-" + date[0];
            } else {
                return "";
            }
        }
    }
//
//    public static Date getEndDate(Date date) {
//
//    }

    /**
     * background task to download image and set to indicated rounded image
     */
    private static class DownloadAttachmentBodyForRoundedImage extends AsyncTask<Void, Void, Void> {


        private final RestClient client;
        private final Attachment attachment;
        String path;
        DWCRoundedImageView smartImageView;
        boolean isFound = false;

        public DownloadAttachmentBodyForRoundedImage(RestClient client, Attachment attachment, DWCRoundedImageView smartImageView) {
            this.client = client;
            this.attachment = attachment;
            this.smartImageView = smartImageView;
        }

        @Override
        protected Void doInBackground(Void... params) {
            String attUrl = client.getClientInfo().resolveUrl(attachment.getBody()).toString();
            HttpClient tempClient = new DefaultHttpClient();
            URI theUrl = null;
            try {
                String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                File folder = new File(extStorageDirectory, "attachment-export");
                if (!folder.exists()) {
                    folder.mkdir();
                }

                ArrayList<String> filesname = getListOfAttachments();
                for (int i = 0; i < filesname.size(); i++) {
                    if (filesname.get(i).equals(attachment.getID())) {
                        isFound = true;
                        break;
                    }
                }

                if (!isFound) {
                    theUrl = new URI(attUrl);
                    HttpGet getRequest = new HttpGet();
                    getRequest.setURI(theUrl);
                    getRequest.setHeader("Authorization", "Bearer " + client.getAuthToken());
                    HttpResponse httpResponse = null;
                    try {
                        httpResponse = tempClient.execute(getRequest);
                        StatusLine statusLine = httpResponse.getStatusLine();
                        if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                            FileOutputStream fos = null;
                            fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory(), "/attachment-export/" + attachment.getID()));
                            path = Environment.getExternalStorageDirectory() + "/attachment-export/" + attachment.getID();
                            HttpEntity entity = httpResponse.getEntity();
                            entity.writeTo(fos);
                            entity.consumeContent();
                            fos.flush();
                            fos.close();
                        } else {
                            httpResponse.getEntity().getContent().close();
                            throw new IOException(statusLine.getReasonPhrase());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    path = Environment.getExternalStorageDirectory() + "/attachment-export/" + attachment.getID();
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(path, options);
            smartImageView.setImageBitmap(bitmap);
        }
    }

    /**
     * background task to download image and set to indicated image
     */
    private static class DownloadAttachmentBody extends AsyncTask<Void, Void, Void> {


        private final RestClient client;
        private final Attachment attachment;
        String path;
        ImageView smartImageView;
        boolean isFound = false;

        public DownloadAttachmentBody(RestClient client, Attachment attachment, ImageView smartImageView) {
            this.client = client;
            this.attachment = attachment;
            this.smartImageView = smartImageView;
        }

        @Override
        protected Void doInBackground(Void... params) {
            String attUrl = client.getClientInfo().resolveUrl(attachment.getBody()).toString();
            HttpClient tempClient = new DefaultHttpClient();
            URI theUrl = null;
            try {
                String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                File folder = new File(extStorageDirectory, "attachment-export");
                if (!folder.exists()) {
                    folder.mkdir();
                }

                ArrayList<String> filesname = getListOfAttachments();
                for (int i = 0; i < filesname.size(); i++) {
                    if (filesname.get(i).equals(attachment.getID())) {
                        isFound = true;
                        break;
                    }
                }

                if (!isFound) {
                    theUrl = new URI(attUrl);
                    HttpGet getRequest = new HttpGet();
                    getRequest.setURI(theUrl);
                    getRequest.setHeader("Authorization", "Bearer " + client.getAuthToken());
                    HttpResponse httpResponse = null;
                    try {
                        httpResponse = tempClient.execute(getRequest);
                        StatusLine statusLine = httpResponse.getStatusLine();
                        if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                            FileOutputStream fos = null;
                            fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory(), "/attachment-export/" + attachment.getID()));
                            path = Environment.getExternalStorageDirectory() + "/attachment-export/" + attachment.getID();
                            HttpEntity entity = httpResponse.getEntity();
                            entity.writeTo(fos);
                            entity.consumeContent();
                            fos.flush();
                            fos.close();
                        } else {
                            httpResponse.getEntity().getContent().close();
                            throw new IOException(statusLine.getReasonPhrase());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    path = Environment.getExternalStorageDirectory() + "/attachment-export/" + attachment.getID();
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(path, options);
            smartImageView.setImageBitmap(bitmap);
        }
    }

    /**
     * @return list of string represents downloaded attachments
     * connect to storage ,get list of files and return them as list of strings
     */
    public static ArrayList<String> getListOfAttachments() {
        File sdCardRoot = Environment.getExternalStorageDirectory();

        File yourDir = new File(sdCardRoot, "attachment-export");
        ArrayList<String> filesname = new ArrayList<String>();
        for (File f : yourDir.listFiles()) {
            if (f.isFile()) {
                filesname.add(f.getName());
            }
        }

        return filesname;
    }

    /**
     * @param expiryDate:expiry date of visa ,passport ...etc or any date.
     * @return number of days between current date and parameter one
     * Get Current date and get the actual difference between today and expiryDate paramter
     */
    public static long daysDifference(String expiryDate) {

        String ActualExpiryDate = "";
        long diffSeconds;
        long diffMinutes;
        long diffHours;
        long diffDays = 0;
        String[] expiry_date_array = {"2015", "07", "20"};
        try {
            expiry_date_array = expiryDate.split("-");
        } catch (NullPointerException e) {

        }
        long diff = 0;

        ActualExpiryDate = expiry_date_array[1] + "/" + expiry_date_array[2] + "/" + expiry_date_array[0] + " 09:29:58";

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        Calendar cal = Calendar.getInstance();
        String today = format.format(cal.getTime());
        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(ActualExpiryDate);
            d2 = format.parse(today);
            diff = d1.getTime() - d2.getTime();
//            diffSeconds = diff / 1000 % 60;
//            diffMinutes = diff / (60 * 1000) % 60;
//            diffHours = diff / (60 * 60 * 1000) % 24;
            diffDays = diff / (24 * 60 * 60 * 1000);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return diffDays;
    }

    /**
     * @param activity:container         activity that want this message to be shown
     * @param object:reference           object to draw fields
     * @param applicationContext:Context of whole application
     * @param _views:array               list of custom views
     * @return view contains all the views described in _views parameter
     * It draws custom views according to its type (HEADER,LABEL,VALUE,HORIZONTALLISTVIEW,LINE) into linear layout
     */
    public static View drawViewsOnLayout(Activity activity, Object object, Context applicationContext, ArrayList<DWCView> _views) {

        LayoutInflater inflater = (LayoutInflater)
                applicationContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        LinearLayout linear;

        View view = inflater.inflate(R.layout.fragment_generic, null, false);
        linear = (LinearLayout) view.findViewById(R.id.relativeViews);
        for (int i = 0; i < _views.size(); i++) {
            if (_views.get(i).getType() == ItemType.HEADER) {
                View viewHeader = LayoutInflater.from(applicationContext).inflate(R.layout.generic_view_header_item, null);
                TextView tvHeader = (TextView) viewHeader.findViewById(R.id.tvHeader);
                tvHeader.setText(_views.get(i).getValue());
                if (i == 0) {
                    linear.addView(viewHeader);
                } else {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.topMargin = 10;
                    params.leftMargin = 5;
                    params.bottomMargin = 10;
                    viewHeader.setLayoutParams(params);
                    linear.addView(viewHeader);
                }
            } else if (_views.get(i).getType() == ItemType.LABEL) {

                View viewHeader = LayoutInflater.from(applicationContext).inflate(R.layout.generic_view_label_item, null);
                TextView tvHeader = (TextView) viewHeader.findViewById(R.id.tvLabelItem);
                tvHeader.setText(_views.get(i).getValue());
                if (i == 0) {
                    linear.addView(viewHeader);
                } else {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.topMargin = 10;
                    params.leftMargin = 5;
                    params.bottomMargin = 10;
                    viewHeader.setLayoutParams(params);
                    linear.addView(viewHeader);
                }

            } else if (_views.get(i).getType() == ItemType.VALUE) {

                View viewHeader = LayoutInflater.from(applicationContext).inflate(R.layout.generic_view_value_item, null);
                TextView tvHeader = (TextView) viewHeader.findViewById(R.id.tvValueItem);
                tvHeader.setText(_views.get(i).getValue());
                if (i == 0) {
                    linear.addView(viewHeader);
                } else {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.topMargin = 10;
                    params.leftMargin = 5;
                    params.bottomMargin = 10;
                    viewHeader.setLayoutParams(params);
                    linear.addView(viewHeader);
                }

            } else if (_views.get(i).getType() == ItemType.HORIZONTAL_LIST_VIEW) {

                View viewHeader = LayoutInflater.from(applicationContext).inflate(R.layout.generic_view_horizontal_list_view_item, null);
                final HorizontalListView hlvServices = (HorizontalListView) viewHeader.findViewById(R.id.hlvServices);
                String[] services;
                if (_views.get(i).getValue().contains(",")) {
                    services = _views.get(i).getValue().split(",");
                } else {
                    services = new String[1];
                    services[0] = _views.get(i).getValue();
                }

                ArrayList<ServiceItem> _items = new ArrayList<ServiceItem>();
                for (int j = 0; j < services.length; j++) {
                    if (services[j].toLowerCase().trim().replace(" ", "").equals("NewNOC".toLowerCase())) {
                        _items.add(new ServiceItem("New NOC", R.mipmap.noc_service_image));
                    } else if (services[j].toLowerCase().trim().replace(" ", "").equals("AddressChange".toLowerCase())) {
                        _items.add(new ServiceItem("Address Change", R.mipmap.address_change_service));
                    } else if (services[j].toLowerCase().trim().replace(" ", "").equals("RenewLicense".toLowerCase())) {
                        _items.add(new ServiceItem("Renew License", R.mipmap.renew_license));
                    } else if (services[j].toLowerCase().trim().replace(" ", "").equals("RenewLicenseActivity".toLowerCase())) {
                        _items.add(new ServiceItem("Renew License" + System.getProperty("line.separator") + "Activity", R.mipmap.renew_card));
                    } else if (services[j].toLowerCase().trim().replace(" ", "").equals("CancelVisa".toLowerCase())) {
                        _items.add(new ServiceItem("Cancel Visa", R.mipmap.cancel_visa));
                    } else if (services[j].toLowerCase().trim().replace(" ", "").equals("CancelLicense".toLowerCase())) {
                        _items.add(new ServiceItem("Cancel License", R.mipmap.cancel_card));
                    } else if (services[j].toLowerCase().trim().replace(" ", "").equals("NewNOCCompany".toLowerCase())) {
                        _items.add(new ServiceItem("New NOC", R.mipmap.company_noc));
                    } else if (services[j].toLowerCase().trim().replace(" ", "").equals("ReserveName".toLowerCase())) {
                        _items.add(new ServiceItem("Reserve Name", R.mipmap.name_reservation));
                    } else if (services[j].toLowerCase().trim().replace(" ", "").equals("NameChange".toLowerCase())) {
                        _items.add(new ServiceItem("Name Change", R.mipmap.name_change));
                    } else if (services[j].toLowerCase().trim().replace(" ", "").equals("ChangeCapital".toLowerCase())) {
                        _items.add(new ServiceItem("Change Capital", R.mipmap.capital_change));
                    } else if (services[j].toLowerCase().trim().replace(" ", "").equals("AddressChange".toLowerCase())) {
                        _items.add(new ServiceItem("Address Change", R.mipmap.address_change_service));
                    } else if (services[j].toLowerCase().trim().replace(" ", "").equals("ChangeLicenseActivity".toLowerCase())) {
                        _items.add(new ServiceItem("Change License" + System.getProperty("line.separator") + "Activity", R.mipmap.change_license_activity));
                    } else if (services[j].toLowerCase().trim().replace(" ", "").equals("CancelCard".toLowerCase())) {
                        _items.add(new ServiceItem("Cancel Card", R.mipmap.cancel_card));
                    } else if (services[j].toLowerCase().trim().replace(" ", "").equals("ReplaceCard".toLowerCase())) {
                        _items.add(new ServiceItem("Replace Card", R.mipmap.replace_card));
                    } else if (services[j].toLowerCase().trim().replace(" ", "").equals("RenewCard".toLowerCase())) {
                        _items.add(new ServiceItem("Renew Card", R.mipmap.renew_card));
                    } else if (services[j].toLowerCase().trim().replace(" ", "").equals("LostCard".toLowerCase())) {
                        _items.add(new ServiceItem("Lost Card", R.mipmap.replace_card));
                    } else if (services[j].toLowerCase().trim().replace(" ", "").equals("RenewContract".toLowerCase())) {
                        Contract_DWC__c contract_dwc__c = (Contract_DWC__c) object;
//                        if (contract_dwc__c.IS_BC_Contract__c()) {
//                            _items.add(new ServiceItem("Renew Contract", R.mipmap.lease_bc_contract));
//                        } else {
//                            _items.add(new ServiceItem("Renew Contract", R.mipmap.lease_ac_contract));
//                        }
                        _items.add(new ServiceItem("Renew Contract", R.mipmap.renew_contract));

                    } else if (services[j].toLowerCase().trim().replace(" ", "").equals("CancelContract".toLowerCase())) {
                        _items.add(new ServiceItem("Cancel Contract", R.mipmap.cancel_contract));
                    } else if (services[j].toLowerCase().trim().replace(" ", "").equals("RenewPassport".toLowerCase())) {
                        _items.add(new ServiceItem("Renew Passport", R.mipmap.noc_service_image));
                    }


                }

                if (_items.size() > 0) {

                    if (i == 0) {
                        linear.addView(viewHeader);
                    } else {
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.topMargin = 0;
                        params.leftMargin = 5;
                        params.bottomMargin = 0;
                        viewHeader.setLayoutParams(params);
                        hlvServices.setAdapter(new HorizontalListViewAdapter(object, activity, applicationContext, _items));
//                        hlvServices.requestFocus();
//                        hlvServices.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                            @Override
//                            public void onFocusChange(View v, boolean hasFocus) {
//                                if (hasFocus) {
//                                    hlvServices.requestFocus();
//                                    hlvServices.setFocusable(true);
//                                } else {
//                                    hlvServices.setFocusable(false);
//                                }
//                            }
//                        });
                        linear.addView(viewHeader);
                    }
                }

            } else if (_views.get(i).getType() == ItemType.LINE) {
                View viewHeader = LayoutInflater.from(applicationContext).inflate(R.layout.generic_view_line_item, null);
                if (i == 0) {
                    linear.addView(viewHeader);
                } else {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.topMargin = 7;
                    params.leftMargin = 4;
                    params.bottomMargin = 7;
                    viewHeader.setLayoutParams(params);
                    linear.addView(viewHeader);
                }
            }
        }
        return view;
    }

    /**
     * @param s:string check if string is null ,it returns empty string.If not null ,it returns the same string
     */
    public static String stringNotNull(String s) {
        return s == null ? "" : s;
    }

    /**
     * @param context:Container context of whole applciation
     * @return the current logged in user object
     * Get user data from shared preference and convert it to user object
     */
    public static User getUserObject(Context context) {
        Gson gson = new Gson();
        User _user = gson.fromJson(new StoreData(context).getUserDataAsString(), User.class);
        return _user;
    }


    public static void getNocAndFields(JSONObject visa, FormField f, NOC__c _noc) {
        String key = f.getTextValue();
        if (key == null)
            return;
        String[] splits = key.split("\\.");
        List<String> keys = new ArrayList<String>();
        for (int i = 0; i < splits.length; i++)
            keys.add(splits[i]);
        String value = getValueFromJson(keys, visa);
        String name = f.getName();
        Field[] fields;
        fields = NOC__c.class.getFields();
        for (int i = 0; i < fields.length; i++) {
            if (name.toLowerCase().equals(fields[i].getName().toLowerCase())) {
                if (f.getType().equals("STRING") || f.getType().equals("EMAIL")) {
                    try {
                        fields[i].set(_noc, value == null ? "" : value);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } else if (f.getType().equals("BOOLEAN")) {
                    try {
                        fields[i].set(_noc, Boolean.valueOf(value));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


        return;
    }

    public static boolean flag = false;

    public static String getValueFromJson(List<String> key, JSONObject json) {
        String result = null;
        for (int i = 0; i < key.size(); i++) {
            if (i == (key.size() - 1)) {
                try {
                    result = json.getString(key.get(i));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                JSONObject jsonObject = json.optJSONObject(key.get(i));
                key.remove(i);
                result = getValueFromJson(key, jsonObject);
            }
        }
        return result;
    }

    /**
     * @param image:bitmap object
     * @return encoded string of bitmap image
     */
    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        Log.e("LOOK", imageEncoded);
        return imageEncoded;
    }

    /**
     * @param f:file       object needed to be decoded
     * @param WIDTH:width
     * @param HIGHT:height
     * @return bitmap of encoding file
     */
    public static Bitmap decodeFile(File f, int WIDTH, int HIGHT) {
        try {
            //Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            //The new size we want to scale to
            final int REQUIRED_WIDTH = WIDTH;
            final int REQUIRED_HIGHT = HIGHT;
            //Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_WIDTH && o.outHeight / scale / 2 >= REQUIRED_HIGHT)
                scale *= 2;

            //Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
        }
        return null;
    }

    /**
     * @param eServiceAdministration:Receipt template object
     *                                       set current eservice administration object to be used in all 5 steps of service
     */
    public static void setEServiceAdministration(Receipt_Template__c eServiceAdministration) {
        Utilities.eServiceAdministration = eServiceAdministration;
    }

    /**
     * @return current working eservice administration
     * you must call setEServiceAdministration before to call this method.If not it returns null
     */
    public static Receipt_Template__c geteServiceAdministration() {
        return eServiceAdministration;
    }

    //Declaration

    /**
     * class to implement changes in card management form field and track its changes whenever user changes field value
     */
    private static class GenericTextWatcherCard implements TextWatcher {

        private View view;
        Card_Management__c _noc;

        private GenericTextWatcherCard(View view, Card_Management__c _noc) {
            this.view = view;
            this._noc = _noc;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            FormField f = (FormField) view.getTag();
            String name = f.getName();
            Field[] fields = Card_Management__c.class.getFields();
            for (int j = 0; j < fields.length; j++)
                if (name.toLowerCase().equals(fields[j].getName().toLowerCase()))
                    try {
                        if (f.getType().equals("DOUBLE"))
                            fields[j].set(_noc, Double.parseDouble(text));
                        else
                            fields[j].set(_noc, text);

                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

        }
    }

    /**
     * class to implement changes in eservice document checklist form field and track its changes whenever user changes field value
     */
    private static class GenericTextWatcherEServiceDocument implements TextWatcher {

        private View view;
        EServices_Document_Checklist__c eServices_document_checklist__c;

        private GenericTextWatcherEServiceDocument(View view, EServices_Document_Checklist__c eServices_document_checklist__c) {
            this.view = view;
            this.eServices_document_checklist__c = eServices_document_checklist__c;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            FormField f = (FormField) view.getTag();
            String name = f.getName();
            Field[] fields = EServices_Document_Checklist__c.class.getFields();
//            for (int j = 0; j < fields.length; j++) {
//                if (name.toLowerCase().equals(fields[j].getName().toLowerCase()))
//                    try {
//                        if (f.getType().equals("DOUBLE"))
//                            fields[j].set(eServices_document_checklist__c, Double.parseDouble(text));
//                        else
//                            fields[j].set(eServices_document_checklist__c, text);
//
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    }
//            }

            for (int j = 0; j < fields.length; j++)
                if (name.toLowerCase().equals(fields[j].getName().toLowerCase()))
                    try {
                        if (f.getType().equals("DOUBLE")) {
                            fields[j].set(eServices_document_checklist__c, Double.parseDouble(text));
                        } else {
                            fields[j].set(eServices_document_checklist__c, text);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
        }
    }

    //Declaration

    /**
     * class to implement changes in noc form field and track its changes whenever user changes field value
     */
    private static class GenericTextWatcher implements TextWatcher {

        private View view;
        NOC__c _noc;

        private GenericTextWatcher(View view, NOC__c _noc) {
            this.view = view;
            this._noc = _noc;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            FormField f = (FormField) view.getTag();
            String name = f.getName();
            Field[] fields = NOC__c.class.getFields();
            for (int j = 0; j < fields.length; j++)
                if (name.toLowerCase().equals(fields[j].getName().toLowerCase()))
                    try {
                        if (f.getType().equals("DOUBLE"))
                            fields[j].set(_noc, Double.parseDouble(TextUtils.isEmpty(editable.toString()) ? "0" : editable.toString()));
                        else
                            fields[j].set(_noc, text);

                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

        }
    }


    public void ValidateAndDrawFormFields(Activity activity, RestClient client, ArrayList<FormField> formFields, WebForm webForm, Map<String, String> parameters, RelatedServiceType serviceType, Object object) {

        String selectVisaAccountQuery = "SELECT Id";
        String selectObjectQuery = "SELECT Id";
        boolean queryFields = false;
        Gson gson = new Gson();
        User user = gson.fromJson(new StoreData(activity.getApplicationContext()).getUserDataAsString(), User.class);

        Utilities.showloadingDialog(activity);
        for (int i = 0; i < formFields.size(); i++) {
            FormField formField = formFields.get(i);
            if (formField.getType().equals("PICKLIST")) {
                try {
                    new GetPicklistItems(client, formField, i, formFields).execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            } else if (formField.getType().equals("REFERENCE") && !formField.isParameter()) {
                try {
                    new GetReferenceItems(client, formField, i, formFields).execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }

        for (int i = 0; i < formFields.size(); i++) {
            FormField formField = formFields.get(i);
            if (formField.isParameter()) {
                formField.setFormFieldValue(parameters.get(formField.getTextValue()));
            }

            if (formField.getType().equals("CUSTOMTEXT")) {
                continue;
            }

            if (formField.getType().equals("REFERENCE")) {
                String id = formField.getName().replace("__c", "__r.Id");
                String name = formField.getName().replace("__c", "__r.Name");
                selectObjectQuery += ", " + id + ", " + name;
            } else {
                selectObjectQuery += ", " + formField.getName();
            }

            if (!formField.isQuery()) {
                continue;
            }

            queryFields = true;
            selectVisaAccountQuery += ", " + formField.getTextValue();
        }

        if (queryFields) {
            if (serviceType == RelatedServiceType.RelatedServiceTypeNewCompanyNOC) {
                selectVisaAccountQuery += " FROM Account WHERE ID = " + "\'" + "%s" + "\'" + " LIMIT 1";
                selectVisaAccountQuery = String.format(selectVisaAccountQuery, user.get_contact().get_account().getID());
            } else {
                selectVisaAccountQuery += " FROM Visa__c WHERE ID = " + "\'" + "%s" + "\'" + " LIMIT 1";
                Visa visa = (Visa) object;
                selectVisaAccountQuery = String.format(selectVisaAccountQuery, visa.getID());
            }
        } else {
            getObjectValue(webForm, selectObjectQuery, object);
        }
    }

    private void getObjectValue(WebForm webForm, String selectObjectQuery, Object object) {
        String soql = selectObjectQuery + " FROM %s WHERE ID = ";
        soql = String.format(soql, webForm.getObject_Name());
        if (webForm.getObject_Name().equals("Card_Management__c")) {
            Card_Management__c card_management__c = (Card_Management__c) object;
            soql += card_management__c.getId();
        } else {
            displayWebForm(webForm, selectObjectQuery, object);
        }
    }

    private void displayWebForm(WebForm webForm, String selectObjectQuery, Object object) {


    }

    private void CallReferenceWebService(RestClient client, final FormField formField, int i, ArrayList<FormField> formFields, Activity activity) {

        String soql = String.format("SELECT Id, Name FROM %s ORDER BY Name", formField.getTextValue());
        try {
            RestRequest restRequest = RestRequest.getRequestForQuery(activity.getString(R.string.api_version), soql);
            client.sendAsync(restRequest, new RestClient.AsyncRequestCallback() {
                @Override
                public void onSuccess(RestRequest request, RestResponse response) {


                }

                @Override
                public void onError(Exception exception) {

                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public class GetReferenceItems extends AsyncTask<Void, Void, Void> {


        public GetReferenceItems(RestClient client, FormField formField, int i, ArrayList<FormField> formFields) {

        }

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }
    }

    public class GetPicklistItems extends AsyncTask<Void, Void, Void> {

        private RestClient client;
        private FormField formfield;
        private int position;
        ArrayList<FormField> formFields;

        public GetPicklistItems(RestClient client, FormField formField, int position, ArrayList<FormField> formFields) {
            this.client = client;
            this.formfield = formField;
            this.position = position;
            this.formFields = formFields;
        }

        @Override
        protected Void doInBackground(Void... params) {
            CallPicklistWebService(client, formfield, position, formFields);
            return null;
        }
    }


    private static void CallPicklistWebService(RestClient client, FormField formField, int i, ArrayList<FormField> formFields) {

        String result = "";
        String attUrl = client.getClientInfo().resolveUrl("/services/apexrest/MobilePickListValuesWebService?fieldId=").toString();
        HttpClient tempClient = new DefaultHttpClient();

        URI theUrl = null;
        try {
            theUrl = new URI(attUrl + formField.getId());
            HttpGet getRequest = new HttpGet();
            getRequest.setURI(theUrl);
            getRequest.setHeader("Authorization", "Bearer " + client.getAuthToken());
            BasicHttpParams param = new BasicHttpParams();
            getRequest.setParams(param);
            HttpResponse httpResponse = null;
            try {
                httpResponse = tempClient.execute(getRequest);
                StatusLine statusLine = httpResponse.getStatusLine();
                if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                    HttpEntity httpEntity = httpResponse.getEntity();
                    if (httpEntity != null) {
                        result = EntityUtils.toString(httpEntity);
                        JSONObject jo = null;
                        try {
                            jo = new JSONObject(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        JSONArray ja = null;
                        ja = jo.getJSONArray("Requested_From__c");
                        formField.setPicklistEntries(convertJsonStringToString(ja));
                        formFields.set(i, formField);
                    }
                } else {
                    httpResponse.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static String convertJsonStringToString(JSONArray jsonArray) {
        String result = "";
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                result += jsonArray.getString(i);
                if (i != (jsonArray.length() - 1))
                    result += ",";
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * @param d1 first date
     * @param d2 second date
     * @return number of days between two dates
     */
    public static int daysBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    public static String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    /**
     * @param email email string
     * @return true if email valid ,false if email not valid
     */
    public static boolean isEmailValid(String email) {
//        boolean isValid = false;
//
//        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
//        CharSequence inputStr = email;
//
//        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
//        Matcher matcher = pattern.matcher(inputStr);
//        if (matcher.matches()) {
//            isValid = true;
//        }
//        return isValid;
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * @param Amount :amount
     * @return string indicating amount after formatting,Called for any processing for any amount value
     */
    public static String processAmount(String Amount) {
        if (!Amount.equals("")) {
            int length = Amount.length();
            if (length > 3) {
                NumberFormat format = NumberFormat.getCurrencyInstance();
                Amount = format.format(Double.valueOf(Amount));
                return Amount.substring(1, Amount.length() - 1);
            } else {
                return Amount;
            }
        } else {
            return "";
        }
    }

    //    public static String getFrontDoorUrl(RestClient client, String url, boolean isAbsUrl) {
//        String frontDoorUrl = client.getClientInfo().getInstanceUrlAsString() + "/secur/frontdoor.jsp?";
//        List<NameValuePair> params = new LinkedList<NameValuePair>();
//        params.add(new BasicNameValuePair("sid", client.getAuthToken()));
//
//		/*
//         * We need to use the absolute URL in some cases and relative URL in some
//		 * other cases, because of differences between instance URL and community
//		 * URL. Community URL can be custom and the logic of determining which
//		 * URL to use is in the 'resolveUrl' method in 'ClientInfo'.
//		 */
//        url = (isAbsUrl ? url : client.getClientInfo().resolveUrl(url).toString());
//        params.add(new BasicNameValuePair("retURL", url));
//        params.add(new BasicNameValuePair("display", "touch"));
//        frontDoorUrl += URLEncodedUtils.format(params, "UTF-8");
//        return frontDoorUrl;
//    }

    static TextView tvPersonName, tvDate, tvStatus, tvTotalAmount, tvRefNumber, lblPersonName, lblDate, lblStatus, lblTotalAmount, lblRefNumber, tvTitle;
    static ImageView drawableService;
    static LinearLayout linearLayout;

    public static View DrawGenericPayAndSubmitView(final Activity activity, RelatedServiceType relatedServiceType, final String personName, final String RefNumber, final String Date, final String Status, final String TotalAmount, ArrayList<FormField> formFields, Card_Management__c card_management__c, NOC__c noc__c) {

        LayoutInflater li = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = li.inflate(R.layout.fragment_noc_pay_and_submit, null, false);

        InitializePayAndSubmitView(convertView);

        if (relatedServiceType == RelatedServiceType.RelatedServiceTypeNameReservation) {
            drawableService.setImageResource(R.mipmap.name_reservation);
            Utilities.showloadingDialog(activity);
            new ClientManager(activity, SalesforceSDKManager.getInstance().getAccountType(), SalesforceSDKManager.getInstance().getLoginOptions(), SalesforceSDKManager.getInstance().shouldLogoutWhenTokenRevoked()).getRestClient(activity, new ClientManager.RestClientCallback() {
                @Override
                public void authenticatedRestClient(final RestClient client) {
                    if (client == null) {
                        activity.finish();
                        Utilities.dismissLoadingDialog();
                    } else {
                        CallNameReservationAmountService(client, activity, personName, RefNumber, Date, Status, TotalAmount);
                    }
                }
            });
        } else {
            if (relatedServiceType == RelatedServiceType.RelatedServiceTypeCancelCard || relatedServiceType == RelatedServiceType.RelatedServiceTypeNewCard || relatedServiceType == RelatedServiceType.RelatedServiceTypeReplaceCard) {
                DrawCardDetailsLayout(activity.getApplicationContext(), linearLayout, formFields, card_management__c);
            } else if (relatedServiceType == RelatedServiceType.RelatedServiceTypeNewCompanyNOC || relatedServiceType == RelatedServiceType.RelatedServiceTypeNewEmployeeNOC) {
                DrawNOCdetailsLayout(activity.getApplicationContext(), linearLayout, formFields, noc__c);
            } else if (relatedServiceType == RelatedServiceType.RelatedServiceTypeRenewVisa || relatedServiceType == RelatedServiceType.RelatedServiceTypeRenewPassport) {
                DrawRenewVisaDetailsLayout(activity, linearLayout);
            } else if (relatedServiceType == RelatedServiceType.RelatedServiceTypeRenewPassport) {
                DrawRenewPassportDetailsLayout(activity, linearLayout);
            } else if (relatedServiceType == RelatedServiceType.RelatedServiceTypeCancelVisa) {
                DrawCancelVisaDetailsLayout(activity, linearLayout);
            } else if (relatedServiceType == RelatedServiceType.RelatedServiceTypeCompanyAddressChange || relatedServiceType == RelatedServiceType.RelatedServiceTypeCompanyNameChange || relatedServiceType == RelatedServiceType.RelatedServiceTypeCapitalChange || relatedServiceType == RelatedServiceType.RelatedServiceTypeDirectorRemoval || relatedServiceType == RelatedServiceType.RelatedServiceTypeEstablishmentCard) {
                if (relatedServiceType == RelatedServiceType.RelatedServiceTypeCompanyAddressChange) {
                    DrawAddressChangeLayoutDetails(activity, linearLayout);
                } else if (relatedServiceType == RelatedServiceType.RelatedServiceTypeCompanyNameChange) {
                    DrawNameChangeLayoutDetails(activity, linearLayout);
                } else if (relatedServiceType == RelatedServiceType.RelatedServiceTypeCapitalChange) {
                    DrawCapitalChangeLayoutDetails(activity, linearLayout);
                } else if (relatedServiceType == RelatedServiceType.RelatedServiceTypeDirectorRemoval) {
                    DrawDirectorRemovalLayoutDetails(activity, linearLayout);
                } else if (relatedServiceType == RelatedServiceType.RelatedServiceTypeEstablishmentCard) {
                    if (((ChangeAndRemovalActivity) activity).getServiceIdentifier().equals("Establishment Card Renewal Fee")) {
                        tvTitle.setText("Renew Card Service");
                        drawableService.setImageResource(R.mipmap.renew_card);
                    } else if (((ChangeAndRemovalActivity) activity).getServiceIdentifier().equals("Establishment Card Lost Fee")) {
                        tvTitle.setText("Lost Card Service");
                        drawableService.setImageResource(R.mipmap.replace_card);
                    } else if (((ChangeAndRemovalActivity) activity).getServiceIdentifier().equals("Establishment Card Cancellation Fee")) {
                        tvTitle.setText("Cancel Card Service");
                        drawableService.setImageResource(R.mipmap.cancel_card);
                    }
                    lineView.setVisibility(View.GONE);
                }
            } else if (relatedServiceType == RelatedServiceType.RelatedServiceTypeDocumentTrueCopy) {
                DrawRequestTrueCopyDetailsLayout(activity, linearLayout, formFields);
            } else if (relatedServiceType == RelatedServiceType.RelatedServiceTypeShareTransfer) {
                DrawShareTransferDetailsLayout(activity, linearLayout);
                lineView.setVisibility(View.GONE);
            }
            ManageTopLayoutVisibillity(personName, RefNumber, Date, Status, TotalAmount);
            ManageTitleAndDrawableImage(relatedServiceType);
        }

        return convertView;
    }

    private static void DrawShareTransferDetailsLayout(Activity activity, LinearLayout linearLayout) {

        LayoutInflater inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.wizard_form_field_pay_header, null, false);
        TextView tvHeader = (TextView) view.findViewById(R.id.pay_header);
        tvHeader.setText("Share Transfer Details");
        linearLayout.addView(view);
        TextView tvLabel;
        TextView tvValue;
        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);

        tvLabel.setText("Transfer From" + "\t:");
        tvValue.setText(((ShareHolderActivity) activity).getShareHolder().get_shareholder().getName());
        linearLayout.addView(view);
        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("Transfer To" + "\t:");
        tvValue.setText(((ShareHolderActivity) activity).getSelectedShareHolder().get_shareholder().getName());
        linearLayout.addView(view);
        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("No of Transferred Shares" + "\t:");
        tvValue.setText(((ShareHolderActivity) activity).getShareno() + "");
        linearLayout.addView(view);

//        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
//        tvLabel = (TextView) view.findViewById(R.id.pay_title);
//        tvValue = (TextView) view.findViewById(R.id.pay_text);
//        tvLabel.setText("Total Amount" + "\t:");
//        tvValue.setText(((ShareHolderActivity) activity).geteServiceAdministration().getTotal_Amount__c() + "AED");
//        linearLayout.addView(view);
    }

    private static void ManageTitleAndDrawableImage(RelatedServiceType relatedServiceType) {
        if (relatedServiceType == RelatedServiceType.RelatedServiceTypeNewEmployeeNOC || relatedServiceType == RelatedServiceType.RelatedServiceTypeRenewPassport) {
            drawableService.setImageResource(R.mipmap.noc_service_image);
            tvTitle.setText("Noc Services");
        } else if (relatedServiceType == RelatedServiceType.RelatedServiceTypeNewCard) {
            drawableService.setImageResource(R.mipmap.new_card);
            tvTitle.setText("Card Services");
            tvTitle.setVisibility(View.VISIBLE);
        } else if (relatedServiceType == RelatedServiceType.RelatedServiceTypeRenewCard) {
            drawableService.setImageResource(R.mipmap.renew_card);
            tvTitle.setText("Card Services");
            tvTitle.setVisibility(View.VISIBLE);
        } else if (relatedServiceType == RelatedServiceType.RelatedServiceTypeCancelCard) {
            drawableService.setImageResource(R.mipmap.cancel_card);
            tvTitle.setText("Card Services");
            tvTitle.setVisibility(View.VISIBLE);
        } else if (relatedServiceType == RelatedServiceType.RelatedServiceTypeReplaceCard) {
            drawableService.setImageResource(R.mipmap.replace_card);
            tvTitle.setText("Card Services");
            tvTitle.setVisibility(View.VISIBLE);
        } else if (relatedServiceType == RelatedServiceType.RelatedServiceTypeNewVisa || relatedServiceType == RelatedServiceType.RelatedServiceTypeRenewVisa) {
            drawableService.setImageResource(R.mipmap.renew_visa);
            tvTitle.setText("Visa Services");
            tvTitle.setVisibility(View.VISIBLE);
        } else if (relatedServiceType == RelatedServiceType.RelatedServiceTypeCancelVisa) {
            drawableService.setImageResource(R.mipmap.cancel_visa);
            tvTitle.setText("Visa Services");
            tvTitle.setVisibility(View.VISIBLE);
        } else if (relatedServiceType == RelatedServiceType.RelatedServiceTypeContractRenewal) {
            drawableService.setImageResource(R.mipmap.notification_leasing);
            tvTitle.setText("Renew Contract");
        } else if (relatedServiceType == RelatedServiceType.RelatedServiceTypeLicenseRenewal) {
            drawableService.setImageResource(R.mipmap.renew_license);
            tvTitle.setText("Renew License Service");
        } else if (relatedServiceType == RelatedServiceType.RelatedServiceTypeNewCompanyNOC) {
            drawableService.setImageResource(R.mipmap.company_noc);
            tvTitle.setText("New Company NOC Service");
        } else if (relatedServiceType == RelatedServiceType.RelatedServiceTypeCompanyAddressChange) {
            drawableService.setImageResource(R.mipmap.address_change_service);
            tvTitle.setText("Address Change Service");
        } else if (relatedServiceType == RelatedServiceType.RelatedServiceTypeCompanyNameChange || relatedServiceType == RelatedServiceType.RelatedServiceTypeLicenseCancelation) {
            drawableService.setImageResource(R.mipmap.name_change_service);
            tvTitle.setText("Name Change Service");
        } else if (relatedServiceType == RelatedServiceType.RelatedServiceTypeContractCancelation) {
            drawableService.setImageResource(R.mipmap.cancel_contract);
            tvTitle.setText("Cancel Contract Service");
        } else if (relatedServiceType == RelatedServiceType.RelatedServiceTypeNameReservation) {
            drawableService.setImageResource(R.mipmap.name_reservation);
            tvTitle.setText("Reserve Name Service");
        } else if (relatedServiceType == RelatedServiceType.RelatedServiceTypeDirectorRemoval) {
            drawableService.setImageResource(R.mipmap.remove_directory);
            tvTitle.setText("Remove Director Service");
        } else if (relatedServiceType == RelatedServiceType.RelatedServiceTypeCapitalChange) {
            drawableService.setImageResource(R.mipmap.capital_change);
            tvTitle.setText("Capital Change Service");
        } else if (relatedServiceType == RelatedServiceType.RelatedServiceTypeLicenseRenewActivityChange) {
            drawableService.setImageResource(R.mipmap.renew_license_activity);
            tvTitle.setText("Renew License Activity Service");
        } else if (relatedServiceType == RelatedServiceType.RelatedServiceTypeLicenseChangeActivityChange) {
            drawableService.setImageResource(R.mipmap.change_license_activity);
            tvTitle.setText("Change License Activity Service");
        } else if (relatedServiceType == RelatedServiceType.RelatedServiceTypeShareTransfer) {
            drawableService.setImageResource(R.mipmap.share_transfer);
            tvTitle.setText("Share Transfer Service");
        } else if (relatedServiceType == RelatedServiceType.RelatedServiceTypeDocumentTrueCopy) {
            drawableService.setImageResource(R.mipmap.copy);
            tvTitle.setText("Request True Copy Service");
        }
    }

    private static void CallNameReservationAmountService(RestClient client, final Activity activity, final String personName, final String RefNumber, String Date, String Status, String TotalAmount) {

        String soql = "select id , Total_Amount__c from Receipt_Template__c where Service_Identifier__c = 'Reservation of Business Name'";
        try {
            restRequest = RestRequest.getRequestForQuery(activity.getString(R.string.api_version), soql);
            client.sendAsync(restRequest, new RestClient.AsyncRequestCallback() {
                @Override
                public void onSuccess(RestRequest request, RestResponse response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.toString());
                        JSONArray jsonArray = jsonObject.getJSONArray(JSONConstants.RECORDS);
                        jsonObject1 = jsonArray.getJSONObject(0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Utilities.dismissLoadingDialog();
                    try {
                        _nameReservationAmount = jsonObject1.getString("Total_Amount__c");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar calendar = Calendar.getInstance();
                    Date now = calendar.getTime();
                    String strDate = sdfDate.format(now);
                    ManageTopLayoutVisibillity(personName, RefNumber, strDate, "Draft", _nameReservationAmount);
                    DrawNameReservationDetailsLayout(activity, ((NameReservationActivity) activity).getChoice1Text(), ((NameReservationActivity) activity).getChoice2Text(), ((NameReservationActivity) activity).getChoice3Text());
                    ManageTitleAndDrawableImage(RelatedServiceType.RelatedServiceTypeNameReservation);
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

    private static void DrawCardDetailsLayout(Context context, LinearLayout linear, ArrayList<FormField> formFields, Card_Management__c card_management__c) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (FormField field : formFields) {
            if (!field.isHidden())
                if (field.getType().equals("CUSTOMTEXT")) {
                    View view = inflater.inflate(R.layout.wizard_form_field_pay_header, null, false);
                    TextView tvHeader = (TextView) view.findViewById(R.id.pay_header);
                    tvHeader.setText(field.getMobileLabel());
                    linear.addView(view);
                } else if (field.getType().equals("REFERENCE")) {
                    String stringValue = "";
                    Field[] fields = Card_Management__c.class.getFields();
                    String refName = field.getName().replace("__c", "");
                    try {
                        Class refclass = Class.forName("model." + refName);
                        Field reffield = null;
                        for (int l = 0; l < refclass.getFields().length; l++)
                            if (("name").equals(refclass.getFields()[l].getName().toLowerCase()))
                                reffield = refclass.getFields()[l];

                        for (int j = 0; j < fields.length; j++)
                            if ((refName + "__r").toLowerCase().equals(fields[j].getName().toLowerCase()))
                                try {
                                    stringValue = String.valueOf(reffield.get(fields[j].get(card_management__c)));
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }

                        TextView tvLabel;
                        TextView tvValue;
                        View view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
                        tvLabel = (TextView) view.findViewById(R.id.pay_title);
                        tvValue = (TextView) view.findViewById(R.id.pay_text);
                        tvLabel.setText(field.getMobileLabel() + "\t:");
                        tvValue.setText(stringValue);
                        linear.addView(view);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    TextView tvLabel;
                    TextView tvValue;
                    View view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
                    tvLabel = (TextView) view.findViewById(R.id.pay_title);
                    tvValue = (TextView) view.findViewById(R.id.pay_text);
                    tvLabel.setText(field.getMobileLabel() + "\t:");

                    String stringValue = "";
                    String name = field.getName();
                    Field[] fields = Card_Management__c.class.getFields();
                    for (int j = 0; j < fields.length; j++)
                        if (name.toLowerCase().equals(fields[j].getName().toLowerCase()))
                            try {
                                stringValue = String.valueOf(fields[j].get(card_management__c) == null ? "" : fields[j].get(card_management__c));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                    tvValue.setText(stringValue);
                    linear.addView(view);
                }
        }
    }

    private static void DrawNOCdetailsLayout(Context context, LinearLayout linear, ArrayList<FormField> formFields, NOC__c noc__c) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (FormField field : formFields) {
            if (field.getType().equals("CUSTOMTEXT")) {

                View view = inflater.inflate(R.layout.wizard_form_field_pay_header, null, false);
                TextView tvHeader = (TextView) view.findViewById(R.id.pay_header);
                tvHeader.setText(field.getMobileLabel());
                linear.addView(view);

            } else {


                TextView tvLabel;
                TextView tvValue;

                View view = inflater.inflate(R.layout.wizards_form_field_details, null, false);


                tvLabel = (TextView) view.findViewById(R.id.pay_title);
                tvValue = (TextView) view.findViewById(R.id.pay_text);
                tvLabel.setText(field.getMobileLabel() + "\t:");

                String stringValue = "";
                String name = field.getName();
                Field[] fields = NOC__c.class.getFields();
                for (int j = 0; j < fields.length; j++)
                    if (name.toLowerCase().equals(fields[j].getName().toLowerCase()))
                        try {
                            if (field.getType().equals("BOOLEAN"))
                                stringValue = Boolean.valueOf((Boolean) fields[j].get(noc__c)) ? "Yes" : "No";
                            else
                                stringValue = String.valueOf(fields[j].get(noc__c) == null ? "" : fields[j].get(noc__c));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                tvValue.setText(stringValue);
                linear.addView(view);
            }
        }
    }

    public static void DrawRenewVisaDetailsLayout(Activity activity, LinearLayout linear) {
        LayoutInflater inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.wizard_form_field_pay_header, null, false);
        TextView tvHeader = (TextView) view.findViewById(R.id.pay_header);
        tvHeader.setText("Visa Details");
        linear.addView(view);

        TextView tvLabel;
        TextView tvValue;
        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);

        tvLabel.setText("Name" + "\t:");
        tvValue.setText(((VisaActivity) activity).getVisa().getApplicant_Full_Name__c());
        linear.addView(view);
        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("Arabic Name" + "\t:");
        tvValue.setText(((VisaActivity) activity).getVisa().getApplicant_Full_Name_Arabic__c());
        linear.addView(view);
        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("Gender" + "\t:");
        tvValue.setText(((VisaActivity) activity).getVisa().getApplicant_Gender__c());
        linear.addView(view);
        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("Date of birth" + "\t:");
        tvValue.setText(((VisaActivity) activity).getVisa().getDate_of_Birth__c());
        linear.addView(view);
        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("Birth Country" + "\t:");
        tvValue.setText(((VisaActivity) activity).getVisa().getCountry_of_Birth__r() == null ? "" : ((VisaActivity) activity).getVisa().getCountry_of_Birth__r().getName());
        linear.addView(view);
        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("Place Country" + "\t:");
        tvValue.setText(((VisaActivity) activity).getVisa().getPlace_of_Birth__c());
        linear.addView(view);
        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("Email" + "\t:");
        tvValue.setText(((VisaActivity) activity).getVisa().getApplicant_Email__c());
        linear.addView(view);
        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("mobile" + "\t:");
        tvValue.setText(((VisaActivity) activity).getVisa().getApplicant_Mobile_Number__c());
        linear.addView(view);
        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("Marital status" + "\t:");
        tvValue.setText(((VisaActivity) activity).getVisa().getMarital_Status__c());
        linear.addView(view);
        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("mother's name" + "\t:");
        tvValue.setText(((VisaActivity) activity).getVisa().getMother_Name__c());
        linear.addView(view);
        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("current nationality" + "\t:");
        tvValue.setText(((VisaActivity) activity).getVisa().getCurrent_Nationality__r() == null ? "" : ((VisaActivity) activity).getVisa().getCurrent_Nationality__r().getName());
        linear.addView(view);
        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("Previous nationality" + "\t:");
        tvValue.setText(((VisaActivity) activity).getVisa().getPrevious_Nationality__r() == null ? "" : ((VisaActivity) activity).getVisa().getPrevious_Nationality__r().getName());
        linear.addView(view);
        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("religion" + "\t:");
        tvValue.setText(((VisaActivity) activity).getVisa().getReligion__c());
        linear.addView(view);
        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("languages spoken" + "\t:");
        tvValue.setText(((VisaActivity) activity).getVisa().getLanguages__c());
        linear.addView(view);
        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("passport no" + "\t:");
        tvValue.setText(((VisaActivity) activity).getVisa().getPassport_Number__c());
        linear.addView(view);
        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("date of expiry" + "\t:");
        tvValue.setText(((VisaActivity) activity).getVisa().getPassport_Expiry__c());
        linear.addView(view);
        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("country of issue" + "\t:");
        tvValue.setText(((VisaActivity) activity).getVisa().getPassport_Issue_Country__r() == null ? "" : ((VisaActivity) activity).getVisa().getPassport_Issue_Country__r().getName());
        linear.addView(view);
        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("place of issue" + "\t:");
        tvValue.setText(((VisaActivity) activity).getVisa().getPassport_Place_of_Issue__c());
        linear.addView(view);
        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("Monthly Salary" + "\t:");
        tvValue.setText(((VisaActivity) activity).getVisa().getMonthly_Basic_Salary_in_AED__c() + "AED");
        linear.addView(view);
        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("Monthly Allowance" + "\t:");
        tvValue.setText(((VisaActivity) activity).getVisa().getMonthly_Allowances_in_AED__c() + "AED");
        linear.addView(view);
        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("Occupation" + "\t:");
        tvValue.setText(((VisaActivity) activity).getVisa().getJob_Title_at_Immigration__r() == null ? "" : ((VisaActivity) activity).getVisa().getJob_Title_at_Immigration__r().getName());
        linear.addView(view);
        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("Qualification" + "\t:");
        tvValue.setText(((VisaActivity) activity).getVisa().getQualification__r() == null ? "" : ((VisaActivity) activity).getVisa().getQualification__r().getName());
        linear.addView(view);
        view = inflater.inflate(R.layout.wizard_form_field_pay_header, null, false);
        tvHeader = (TextView) view.findViewById(R.id.pay_header);
        tvHeader.setText("Additions");
        linear.addView(view);
        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("Urgent Stamping (AED 250)" + "\t:");
        tvValue.setText(((VisaActivity) activity).getVisa().Urgent_Stamping_Paid__c ? "Yes" : "No");
        linear.addView(view);
    }

    public static void DrawRenewPassportDetailsLayout(Activity activity, LinearLayout linear) {
        LayoutInflater inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.wizard_form_field_pay_header, null, false);
        TextView tvHeader = (TextView) view.findViewById(R.id.pay_header);
        tvHeader.setText("Visa Details");
        linear.addView(view);
        TextView tvLabel;
        TextView tvValue;
        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);

        tvLabel.setText("Passport holder" + "\t:");
        tvValue.setText(((VisaActivity) activity).getNewPassport().getPassport_Holder__r().getName());
        linear.addView(view);
        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("New passport no" + "\t:");
        tvValue.setText(((VisaActivity) activity).getNewPassport().getName());
        linear.addView(view);
        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("New Issue Date" + "\t:");
        tvValue.setText(((VisaActivity) activity).getNewPassport().getPassport_Issue_Date__c());
        linear.addView(view);
        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("New Expiry Date" + "\t:");
        tvValue.setText(((VisaActivity) activity).getNewPassport().getPassport_Expiry_Date__c());
        linear.addView(view);
        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("New Place of issue" + "\t:");
        tvValue.setText(((VisaActivity) activity).getNewPassport().getPassport_Place_of_Issue__c());
        linear.addView(view);
        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("Country of Issue" + "\t:");
        tvValue.setText(((VisaActivity) activity).getVisa().getPassport_Issue_Country__r().getName());
        linear.addView(view);
    }

    private static void DrawCancelVisaDetailsLayout(Activity activity, LinearLayout linearLayout) {
        LayoutInflater inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.wizard_form_field_pay_header, null, false);
        TextView tvHeader = (TextView) view.findViewById(R.id.pay_header);
        tvHeader.setText("Visa Details");
        linearLayout.addView(view);
        TextView tvLabel;
        TextView tvValue;
        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);

        tvLabel.setText("Name" + "\t:");
        tvValue.setText(((VisaActivity) activity).getVisa().getApplicant_Full_Name__c());
        linearLayout.addView(view);
        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("Passport Number" + "\t:");
        tvValue.setText(((VisaActivity) activity).getVisa().getPassport_Number__c());
        linearLayout.addView(view);
        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("Visa Expiry Date" + "\t:");
        tvValue.setText(((VisaActivity) activity).getVisa().getVisa_Expiry_Date__c());
        linearLayout.addView(view);
        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("Country of Issue" + "\t:");
        try {
            tvValue.setText(((VisaActivity) activity).getVisa().getPassport_Issue_Country__r().getName());
            linearLayout.addView(view);
        } catch (NullPointerException e) {
            linearLayout.addView(view);
        }

        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("Occupation" + "\t:");
        tvValue.setText(((VisaActivity) activity).getVisa().getJob_Title_at_Immigration__r().getName());
        linearLayout.addView(view);
        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("Qualification" + "\t:");
        tvValue.setText(((VisaActivity) activity).getVisa().getQualification__r().getName());
        linearLayout.addView(view);


//        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
//        tvLabel = (TextView) view.findViewById(R.id.pay_title);
//        tvValue = (TextView) view.findViewById(R.id.pay_text);
//        tvLabel.setText("Total Amount" + "\t:");
//        tvValue.setText(((VisaActivity) activity).getTotal() == null ? "0" : ((VisaActivity) activity).getTotal() + "AED");
//        linearLayout.addView(view);


        //Aditions
        view = inflater.inflate(R.layout.wizard_form_field_pay_header, null, false);
        tvHeader = (TextView) view.findViewById(R.id.pay_header);
        tvHeader.setText("Additions");
        linearLayout.addView(view);
        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("Urgent Cancellation" + "\t:");
        tvValue.setText(((VisaActivity) activity).getVisa().Urgent_Stamping_Paid__c ? "Yes" : "No");
        linearLayout.addView(view);
    }

    private static void DrawAddressChangeLayoutDetails(Activity activity, LinearLayout linearLayout) {

        TextView tvLabel;
        TextView tvValue;

        LayoutInflater inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.wizard_form_field_pay_header, null, false);
        TextView tvHeader = (TextView) view.findViewById(R.id.pay_header);
        tvHeader.setText("Current Address Information");
        linearLayout.addView(view);


        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("Current Mobile:" + "\t:");
        tvValue.setText(((ChangeAndRemovalActivity) activity).getCurrentMobile());
        linearLayout.addView(view);

        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("Current Fax:" + "\t:");
        tvValue.setText(((ChangeAndRemovalActivity) activity).getCurrentFax());
        linearLayout.addView(view);

        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("Current Email:" + "\t:");
        tvValue.setText(((ChangeAndRemovalActivity) activity).getCurrentEmail());
        linearLayout.addView(view);

        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("Current PO Box:" + "\t:");
        tvValue.setText(((ChangeAndRemovalActivity) activity).getCurrentPoBox());
        linearLayout.addView(view);

        view = inflater.inflate(R.layout.wizard_form_field_pay_header, null, false);
        tvHeader = (TextView) view.findViewById(R.id.pay_header);
        tvHeader.setText("New Address Information");
        linearLayout.addView(view);


        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("New Mobile:" + "\t:");
        tvValue.setText(((ChangeAndRemovalActivity) activity).getNewMobile());
        linearLayout.addView(view);

        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("New Fax:" + "\t:");
        tvValue.setText(((ChangeAndRemovalActivity) activity).getNewFax());
        linearLayout.addView(view);

        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("New Email:" + "\t:");
        tvValue.setText(((ChangeAndRemovalActivity) activity).getNewEmail());
        linearLayout.addView(view);

        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("New PO Box:" + "\t:");
        tvValue.setText(((ChangeAndRemovalActivity) activity).getNewPoBox());
        linearLayout.addView(view);
    }

    private static void DrawNameChangeLayoutDetails(Activity activity, LinearLayout linearLayout) {
        TextView tvLabel;
        TextView tvValue;

        LayoutInflater inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.wizard_form_field_pay_header, null, false);
        TextView tvHeader = (TextView) view.findViewById(R.id.pay_header);
        tvHeader.setText("Current Company Name");
        linearLayout.addView(view);


        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("Company Name:" + "\t:");
        tvValue.setText(((ChangeAndRemovalActivity) activity).getCompanyName());
        linearLayout.addView(view);

        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("Company Name(Arabic):" + "\t:");
        tvValue.setText(((ChangeAndRemovalActivity) activity).getCompanyNameArabic());
        linearLayout.addView(view);

        view = inflater.inflate(R.layout.wizard_form_field_pay_header, null, false);
        tvHeader = (TextView) view.findViewById(R.id.pay_header);
        tvHeader.setText("New Company Name");
        linearLayout.addView(view);


        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("New Company Name:" + "\t:");
        tvValue.setText(((ChangeAndRemovalActivity) activity).getNewCompanyName());
        linearLayout.addView(view);

        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("New Company Name(Arabic):" + "\t:");
        tvValue.setText(((ChangeAndRemovalActivity) activity).getNewCompanyNameArabic());
        linearLayout.addView(view);
    }

    private static void DrawCapitalChangeLayoutDetails(Activity activity, LinearLayout linearLayout) {
        TextView tvLabel;
        TextView tvValue;

        LayoutInflater inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.wizard_form_field_pay_header, null, false);
        TextView tvHeader = (TextView) view.findViewById(R.id.pay_header);
        tvHeader.setText("New Capital Information");
        linearLayout.addView(view);

        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("New Share Capital:" + "\t:");
        tvValue.setText(((ChangeAndRemovalActivity) activity).getNewShareCapital());
        linearLayout.addView(view);
    }

    private static void DrawDirectorRemovalLayoutDetails(Activity activity, LinearLayout linearLayout) {
        TextView tvLabel;
        TextView tvValue;

        LayoutInflater inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.wizard_form_field_pay_header, null, false);
        TextView tvHeader = (TextView) view.findViewById(R.id.pay_header);
        tvHeader.setText("Request Information");
        linearLayout.addView(view);

        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("Director Name:" + "\t:");
        tvValue.setText(((ChangeAndRemovalActivity) activity).getDirectorship().get_director().getName());
        linearLayout.addView(view);
    }

    private static void DrawRequestTrueCopyDetailsLayout(Activity activity, LinearLayout linear, ArrayList<FormField> formFields) {
        LayoutInflater inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for (FormField field : formFields) {
            if (field.isHidden()) {
                continue;
            }
            if (field.getType().equals("CUSTOMTEXT")) {

                View view = inflater.inflate(R.layout.wizard_form_field_pay_header, null, false);
                TextView tvHeader = (TextView) view.findViewById(R.id.pay_header);
                tvHeader.setText(field.getMobileLabel());
                linear.addView(view);

            } else {
                TextView tvLabel;
                TextView tvValue;
                View view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
                tvLabel = (TextView) view.findViewById(R.id.pay_title);
                tvValue = (TextView) view.findViewById(R.id.pay_text);
                tvLabel.setText(field.getMobileLabel() + "\t:");
                String stringValue = "";
                String name = field.getName();
                Field[] fields = EServices_Document_Checklist__c.class.getFields();
                if (field.getMobileLabel().equals("Account ID")) {
                    continue;
                } else if (field.getMobileLabel().equals("Company Name")) {
                    Gson gson = new Gson();
                    User user = gson.fromJson(new StoreData(activity.getApplicationContext()).getUserDataAsString(), User.class);
                    stringValue = user.get_contact().get_account().getName();
                } else {
                    for (int j = 0; j < fields.length; j++) {
                        if (name.toLowerCase().equals(fields[j].getName().toLowerCase())) {
                            try {
                                stringValue = String.valueOf(fields[j].get(((RequestTrueCopyActivity) activity).geteServices_document_checklist__c()));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    if (stringValue.equals("")) {
                        Map<String, String> parameters = ((RequestTrueCopyActivity) activity).getParameters();
                        if (parameters.containsKey(field.getTextValue())) {
                            stringValue = parameters.get(field.getTextValue());
                        }
                    }
                }
                tvValue.setText(stringValue);
                linear.addView(view);
            }
        }
    }

    private static void DrawNameReservationDetailsLayout(Activity activity, String choice1Text, String choice2Text, String choice3Text) {
        LayoutInflater inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView tvLabel;
        TextView tvValue;

        View view = inflater.inflate(R.layout.wizard_form_field_pay_header, null, false);
        TextView tvHeader = (TextView) view.findViewById(R.id.pay_header);
        tvHeader.setText("Request Information");
        linearLayout.addView(view);

        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("Choice1" + "\t:");
        tvValue.setText(choice1Text);
        linearLayout.addView(view);

        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("Choice2" + "\t:");
        tvValue.setText(choice2Text);
        linearLayout.addView(view);

        view = inflater.inflate(R.layout.wizards_form_field_details, null, false);
        tvLabel = (TextView) view.findViewById(R.id.pay_title);
        tvValue = (TextView) view.findViewById(R.id.pay_text);
        tvLabel.setText("Choice3" + "\t:");
        tvValue.setText(choice3Text);
        linearLayout.addView(view);
    }

    private static void InitializePayAndSubmitView(View convertView) {

        tvPersonName = (TextView) convertView.findViewById(R.id.personName);
        tvDate = (TextView) convertView.findViewById(R.id.date);
        tvStatus = (TextView) convertView.findViewById(R.id.status);
        tvTotalAmount = (TextView) convertView.findViewById(R.id.total_Amount);
        tvRefNumber = (TextView) convertView.findViewById(R.id.refnumber);

        lblPersonName = (TextView) convertView.findViewById(R.id.labelPerson);
        lblDate = (TextView) convertView.findViewById(R.id.labelDate);
        lblStatus = (TextView) convertView.findViewById(R.id.labelStatus);
        lblTotalAmount = (TextView) convertView.findViewById(R.id.labelTotalAmount);
        lblRefNumber = (TextView) convertView.findViewById(R.id.labelRefNumber);

        drawableService = (ImageView) convertView.findViewById(R.id.imageEmpoyeeNOC);
        tvTitle = (TextView) convertView.findViewById(R.id.title);

        lineView = convertView.findViewById(R.id.lineView);

        linearLayout = (LinearLayout) convertView.findViewById(R.id.nocDetails);
    }

    private static void ManageTopLayoutVisibillity(String personName, String RefNumber, String Date, String Status, String TotalAmount) {
        if (personName != null && !personName.equals("null")) {
            tvPersonName.setText(personName);
        } else {
            tvPersonName.setVisibility(View.GONE);
            lblPersonName.setVisibility(View.GONE);
        }

        if (RefNumber != null && !RefNumber.equals("null")) {
            tvRefNumber.setText(RefNumber);
        } else {
            lblRefNumber.setVisibility(View.GONE);
            tvRefNumber.setVisibility(View.GONE);
        }

        if (Date != null && !Date.equals("null")) {
            tvDate.setText(Date);
        } else {
            tvDate.setVisibility(View.GONE);
            lblDate.setVisibility(View.GONE);
        }

        if (Status != null && !Status.equals("null")) {
            tvStatus.setText(Status);
        } else {
            tvStatus.setVisibility(View.GONE);
            lblStatus.setVisibility(View.GONE);
        }

        if (TotalAmount != null && !TotalAmount.equals("null")) {
            if (TotalAmount.contains(",")) {
                tvTotalAmount.setText(TotalAmount + " AED.");
            } else {
                String Amount = Utilities.processAmount(TotalAmount);
                tvTotalAmount.setText(Amount + " AED.");
            }

        } else {
            tvTotalAmount.setVisibility(View.GONE);
            lblTotalAmount.setVisibility(View.GONE);
        }

        if ((personName == null || personName.equals("")) && (RefNumber == null || RefNumber.equals("")) && (Date == null || Date.equals("")) || (Status == null || Status.equals("")) || (TotalAmount == null || TotalAmount.equals(""))) {
            lineView.setVisibility(View.GONE);
        }
    }

    public static int daysBetween(Date d1, long d2) {
        return (int) ((d1.getTime() - d2) / (1000 * 60 * 60 * 24));
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        try {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (NullPointerException e) {

        }
    }

    public static String getCurrentDate() {
        long yourmilliseconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        Date resultdate = new Date(yourmilliseconds);
        return sdf.format(resultdate);
    }
}