package RestAPI;

import android.util.Log;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import model.Account;
import model.Card_Management__c;
import model.Case;
import model.Company_Documents__c;
import model.Contact;
import model.Contract_DWC__c;
import model.Contract_Line_Item__c;
import model.Country__c;
import model.CountryofBirth;
import model.CurrentLicenseNumber;
import model.CurrentNationality;
import model.CurrentPassport;
import model.Director;
import model.Directorship;
import model.EServices_Document_Checklist__c;
import model.EmployeeRef;
import model.FormField;
import model.FreeZonePayment;
import model.Inventory_Unit__r;
import model.JobTitleAtImmigration;
import model.LegalRepresentative;
import model.LegalRepresentativeLookup;
import model.LicenseActivity;
import model.ManagementMember;
import model.Manager;
import model.MyRequest;
import model.Nationality;
import model.NotificationManagement;
import model.OriginalBusinessActivity;
import model.Party;
import model.Product_Type__r;
import model.Quote;
import model.Receipt_Template__c;
import model.RecordType;
import model.Registration_Amendments__c;
import model.ShareOwnership;
import model.Shareholder;
import model.SponsoringCompany;
import model.TenancyContractPayment;
import model.User;
import model.Visa;
import model.VisaHolder;
import model.WebForm;
import utilities.Utilities;

/**
 * Created by Abanoub Wagdy on 6/15/2015.
 */

/**
 * SFResponseManager is a factory class for parsing all salesforce requests and parse the request into indicated object
 * If no records is found, it returns an empty arralist of this object
 */
public class SFResponseManager {

    static User _user;
    private static Gson gson;

    /**
     * @param companyRestResponse:salesforce response after quering for the logged in user
     * @return user represents the logged in community user
     */
    public static User parseCompanyRestResponse(String companyRestResponse) {
        _user = new User();
        try {
            JSONObject jsonResult = new JSONObject(companyRestResponse);
            if (jsonResult.optBoolean(JSONConstants.DONE) == true) {
                JSONArray jsonRecords = jsonResult.getJSONArray(JSONConstants.RECORDS);
                for (int i = 0; i < jsonRecords.length(); i++) {
                    JSONObject jsonUser = jsonRecords.getJSONObject(i);
                    _user = parseUserObject(jsonUser);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return _user;
    }

    public static ArrayList<Card_Management__c> parseCardsData(String companyRestResponse) {
        ArrayList<Card_Management__c> _cards = new ArrayList<Card_Management__c>();
        Log.d("Card", companyRestResponse);
        Card_Management__c _card;
        try {
            JSONObject jsonResult = new JSONObject(companyRestResponse);
            if (jsonResult.optBoolean(JSONConstants.DONE) == true) {
                JSONArray jsonRecords = jsonResult.getJSONArray(JSONConstants.RECORDS);
                for (int i = 0; i < jsonRecords.length(); i++) {
                    _card = new Card_Management__c();
                    JSONObject jsonCard = jsonRecords.getJSONObject(i);
                    gson = new Gson();
                    _card = gson.fromJson(jsonCard.toString(), Card_Management__c.class);
                    _card = parseCardsObject(_card, jsonCard);
                    _cards.add(_card);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return _cards;
    }

    private static Card_Management__c parseCardsObject(Card_Management__c card, JSONObject jsonCard) {
        RecordType _recordType = new RecordType();
        Nationality _nationality = new Nationality();
        Log.d("", jsonCard.toString());

        gson = new Gson();
        try {
            JSONObject jsonRecordType = jsonCard.getJSONObject("RecordType");
            gson = new Gson();
            _recordType = gson.fromJson(jsonRecordType.toString(), RecordType.class);
            card.setRecordType(_recordType);
            JSONObject jsonNationality = jsonCard.getJSONObject("Nationality__r");
            gson = new Gson();
            _nationality = gson.fromJson(jsonNationality.toString(), Nationality.class);
            card.setNationality__r(_nationality);
            if (jsonCard.toString().contains("Account__r")) {
                gson = new Gson();
                Account account = gson.fromJson(jsonCard.getJSONObject("Account__r").toString(), Account.class);
                card.setAccount__r(account);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return card;
    }

    /**
     * @param jsonUser:Json object represents community user model
     * @return user object after parsing salesforce response
     * Note : user has contact ,contact has account that includes all details of logged in user
     */
    private static User parseUserObject(JSONObject jsonUser) {

        try {
            Log.d("result user", jsonUser.toString());
            _user.setUrl(jsonUser.getJSONObject(JSONConstants.ATTRIBUTES).getString(JSONConstants.URL));
            Utilities.contactEmail = jsonUser.getString("Email");
            _user.setContactId(jsonUser.getString(JSONConstants.CONTACTID));
            JSONObject jsonContact = jsonUser.getJSONObject(JSONConstants.CONTACT);
            Contact _contact = parseContactObject(jsonContact);
            _user.set_contact(_contact);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return _user;
    }

    /**
     * @param jsonContact:Json object represents contact model related to account object
     * @return contact object after parsing to attach to account object
     */
    private static Contact parseContactObject(JSONObject jsonContact) {
        Contact _contact = new Contact();
        try {
            gson = new Gson();
            _contact = gson.fromJson(jsonContact.toString(), Contact.class);
            JSONObject jsonAccount = jsonContact.getJSONObject(JSONConstants.ACCOUNT);
            Account _account = parseAccountObject(jsonAccount);
            _contact.set_account(_account);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return _contact;
    }

    /**
     * @param jsonAccount:Json object represents Account model related to user object
     * @return Account object after parsing to attach to user object
     */
    private static Account parseAccountObject(JSONObject jsonAccount) {
        Account _account = new Account();
        try {
//            ObjectReader or = new ObjectMapper().reader().withType(
//                    Account.class);
//            try {
//                _account = or.readValue(jsonAccount.toString());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

//            _account.setUrl(jsonAccount.getJSONObject(JSONConstants.ATTRIBUTES).getString(JSONConstants.URL));
//            _account.setID(jsonAccount.getString(JSONConstants.ID));
//            _account.setName(jsonAccount.getString(JSONConstants.NAME));
//            _account.setAccountBalance(jsonAccount.getString(JSONConstants.ACCOUNT_BALANCE));
//            _account.setPortalBalance(jsonAccount.getString(JSONConstants.PORTAL_BALANCE));
//            _account.setName(jsonAccount.getString(JSONConstants.NAME));
//            _account.setArabicAccountName(jsonAccount.getString(JSONConstants.ARABIC_ACCOUNT_NAME));
//            _account.setLicenseNumberFormula(jsonAccount.getString(JSONConstants.LICENSE_NUMBER_FORMULA));
//            _account.setBillingCity(jsonAccount.getString(JSONConstants.BILLING_CITY));
//            _account.setBillingCountry(jsonAccount.getString(JSONConstants.BILLING_COUNTRY));
//            _account.setBillingPostalCode(jsonAccount.getString(JSONConstants.BILLING_POSTAL_CODE));
//            _account.setBillingState(jsonAccount.getString(JSONConstants.BILLING_STATE));
//            _account.setBillingStreet(jsonAccount.getString(JSONConstants.BILLING_STREET));
//            _account.setRegistrationNumberValue(jsonAccount.getString(JSONConstants.REGISTRATION_NUMBER));
//            _account.setAccountBalance(jsonAccount.getString(JSONConstants.ACCOUNT_BALANCE));
//            _account.setCompany_Logo(jsonAccount.getString(JSONConstants.COMPANY_LOGO));
//            _account.setEmail(jsonAccount.getString(JSONConstants.EMAIL));
//            _account.setProEmail(jsonAccount.getString(JSONConstants.PRO_EMAIL));
//            _account.setMobile(jsonAccount.getString(JSONConstants.MOBILE));
//            _account.setProMobileNumber(jsonAccount.getString(JSONConstants.PRO_MOBILE_NUMBER));
            gson = new Gson();
            _account = gson.fromJson(jsonAccount.toString(), Account.class);
            JSONObject jsonCurentLicenseNumber = jsonAccount.getJSONObject(JSONConstants.CURRENT_LICENSE_NUMBER);
            CurrentLicenseNumber _currentLicenseNumber = parseLicenseNumberObject(jsonCurentLicenseNumber);
            _account.set_currentLicenseNumber(_currentLicenseNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return _account;
    }

    /**
     * @param jsonCurentLicenseNumber:Json object represents CurrentLicenseNumber model
     * @return CurrentLicenseNumber object after parsing response
     */
    private static CurrentLicenseNumber parseLicenseNumberObject(JSONObject jsonCurentLicenseNumber) throws JSONException {
        CurrentLicenseNumber _currentLicenseNumber = new CurrentLicenseNumber();
        // _currentLicenseNumber.setUrl(jsonCurentLicenseNumber.getJSONObject(JSONConstants.ATTRIBUTES).getString(JSONConstants.URL));
//        _currentLicenseNumber.setCommercial_Name(jsonCurentLicenseNumber.getString(JSONConstants.COMMERCIAL_NAME));
//        _currentLicenseNumber.setCommercial_Name_Arabic(jsonCurentLicenseNumber.getString(JSONConstants.COMMERCIAL_ARABIC_NAME));
//        _currentLicenseNumber.setId(jsonCurentLicenseNumber.getString(JSONConstants.ID));
//        _currentLicenseNumber.setLicense_Expiry_Date(jsonCurentLicenseNumber.getString(JSONConstants.LICENSE_EXPIRY_DATE));
//        _currentLicenseNumber.setLicense_Issue_Date(jsonCurentLicenseNumber.getString(JSONConstants.License_Issue_Date));
//        _currentLicenseNumber.setValidity_Status(jsonCurentLicenseNumber.getString(JSONConstants.VALIDITY_STATUS));
        gson = new Gson();
        _currentLicenseNumber = gson.fromJson(jsonCurentLicenseNumber.toString(), CurrentLicenseNumber.class);
        RecordType _recordType = new RecordType();
        JSONObject jsonRecordType = jsonCurentLicenseNumber.getJSONObject(JSONConstants.RECORD_TYPE);
        _recordType = parseRecordTypeobject(jsonRecordType);
        _currentLicenseNumber.set_recordType(_recordType);
        return _currentLicenseNumber;
    }

    private static RecordType parseRecordTypeobject(JSONObject jsonRecordType) throws JSONException {
        RecordType _recordType = new RecordType();
        gson = new Gson();
        _recordType = gson.fromJson(jsonRecordType.toString(), RecordType.class);
        return _recordType;
    }

    public static ArrayList<Visa> parseVisaData(String s) {
        ArrayList<Visa> _visas = new ArrayList<Visa>();
        try {
            JSONObject jsonVisas = new JSONObject(s);
            if (jsonVisas.optBoolean(JSONConstants.DONE)) {
                JSONArray jsonArrayVisas = jsonVisas.getJSONArray(JSONConstants.RECORDS);
                for (int i = 0; i < jsonArrayVisas.length(); i++) {
                    JSONObject json = jsonArrayVisas.getJSONObject(i);
                    Visa _visa = parseVisaObject(json);
                    _visas.add(_visa);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return _visas;
    }

    public static Account parseAccountData(String s) {
        ArrayList<Account> _accounts = new ArrayList<Account>();
        try {
            JSONObject jsonVisas = new JSONObject(s);
            if (jsonVisas.optBoolean(JSONConstants.DONE)) {
                JSONArray jsonArrayVisas = jsonVisas.getJSONArray(JSONConstants.RECORDS);
                for (int i = 0; i < jsonArrayVisas.length(); i++) {
                    JSONObject json = jsonArrayVisas.getJSONObject(i);
                    Account _account = parseAccountDataObject(json.toString());
                    _accounts.add(_account);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return _accounts.get(0);
    }

    private static Account parseAccountDataObject(String s) {
        Account _account = new Account();
        gson = new Gson();
        _account = gson.fromJson(s, Account.class);
        return _account;
    }

//    public static ArrayList<MyVisa> parseMyVisaData(String s) {
//        ArrayList<MyVisa> _visas = new ArrayList<MyVisa>();
//        try {
//            JSONObject jsonVisas = new JSONObject(s);
//            if (jsonVisas.optBoolean(JSONConstants.DONE)) {
//                JSONArray jsonArrayVisas = jsonVisas.getJSONArray(JSONConstants.RECORDS);
//                for (int i = 0; i < jsonArrayVisas.length(); i++) {
//                    JSONObject json = jsonArrayVisas.getJSONObject(i);
//                    MyVisa _visa = parseMyVisaObject(json);
//                    _visas.add(_visa);
//                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return _visas;
//    }

    public static JSONObject parseJsonVisaData(String s) {
        ArrayList<JSONObject> _visas = new ArrayList<JSONObject>();
        try {
            JSONObject jsonVisas = new JSONObject(s);
            if (jsonVisas.optBoolean(JSONConstants.DONE)) {
                JSONArray jsonArrayVisas = jsonVisas.getJSONArray(JSONConstants.RECORDS);
                for (int i = 0; i < jsonArrayVisas.length(); i++) {
                    JSONObject json = jsonArrayVisas.getJSONObject(i);
                    Visa _visa = parseVisaObject(json);
                    _visas.add(json);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return _visas.get(0);
    }

    public static JSONObject parseJsonCardData(String s) {
        ArrayList<JSONObject> _cards = new ArrayList<JSONObject>();
        try {
            JSONObject jsonVisas = new JSONObject(s);
            if (jsonVisas.optBoolean(JSONConstants.DONE)) {
                JSONArray jsonArrayVisas = jsonVisas.getJSONArray(JSONConstants.RECORDS);
                for (int i = 0; i < jsonArrayVisas.length(); i++) {
                    JSONObject json = jsonArrayVisas.getJSONObject(i);
                    Visa _visa = parseVisaObject(json);
                    _cards.add(json);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return _cards.get(0);
    }


    private static Visa parseVisaObject(JSONObject json) {
        Visa _visa = new Visa();
        Gson gson = new Gson();
        _visa = gson.fromJson(json.toString(), Visa.class);
//        ObjectReader or = new ObjectMapper().reader().withType(
//                Visa.class);
//        try {
//            _visa = or.readValue(json.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        Log.d("json", json.toString());
        try {
            if (json.toString().contains("Country_of_Birth__r")) {
                CountryofBirth _countryOfBirth = new CountryofBirth();
                gson = new Gson();
                _countryOfBirth = gson.fromJson(json.getJSONObject("Country_of_Birth__r").toString(), CountryofBirth.class);
                _visa.set_countryOfBirth(_countryOfBirth);
            }

            if (json.toString().contains("Current_Nationality__r")) {
                CurrentNationality _currentNationality = new CurrentNationality();
                gson = new Gson();
                _currentNationality = gson.fromJson(json.getJSONObject("Current_Nationality__r").toString(), CurrentNationality.class);
                _visa.set_currentNationality(_currentNationality);
            }

            if (json.toString().contains("Job_Title_at_Immigration__r")) {
                JobTitleAtImmigration _jobTitleImmigration = new JobTitleAtImmigration();
                gson = new Gson();
                _jobTitleImmigration = gson.fromJson(json.getJSONObject("Job_Title_at_Immigration__r").toString(), JobTitleAtImmigration.class);
                _visa.set_jobTitleImigration(_jobTitleImmigration);
            }

            if (json.toString().contains("Sponsoring_Company__r")) {
                SponsoringCompany _sponsoringCompany = new SponsoringCompany();
                gson = new Gson();
                _sponsoringCompany = gson.fromJson(json.getJSONObject("Sponsoring_Company__r").toString(), SponsoringCompany.class);
                _visa.set_sponsoringCompany(_sponsoringCompany);
            }

            if (json.toString().contains("Visa_Holder__r")) {
                VisaHolder _visaHolder = new VisaHolder();
                gson = new Gson();
                _visaHolder = gson.fromJson(json.getJSONObject("Visa_Holder__r").toString(), VisaHolder.class);
                _visa.set_visaHolder(_visaHolder);
            }

            if (json.toString().contains("Qualification__r")) {
                JobTitleAtImmigration qualification = new JobTitleAtImmigration();
                gson = new Gson();
                qualification = gson.fromJson(json.getJSONObject("Qualification__r").toString(), JobTitleAtImmigration.class);
                _visa.setQualification__r(qualification);
            }
            if (json.toString().contains("Previous_Nationality__r")) {
                gson = new Gson();
                CurrentNationality _previosNationality = gson.fromJson(json.getJSONObject("Previous_Nationality__r").toString(), CurrentNationality.class);
                _visa.setPrevious_Nationality__r(_previosNationality);
            }
            if (json.toString().contains("Passport__r")) {
                gson = new Gson();
                CurrentPassport currentPassport = gson.fromJson(json.getJSONObject("Passport__r").toString(), CurrentPassport.class);
                if (json.getJSONObject("Passport__r").toString().contains("Passport_Holder__r")) {
                    gson = new Gson();
                    Account account = gson.fromJson(json.getJSONObject("Passport__r").getJSONObject("Passport_Holder__r").toString(), Account.class);
                    currentPassport.setPassport_Holder__r(account);
                }
                _visa.setPassport__r(currentPassport);
            }
            if (json.toString().contains("Passport_Issue_Country__r")) {
                gson = new Gson();
                Country__c country__c = gson.fromJson(json.getJSONObject("Passport_Issue_Country__r").toString(), Country__c.class);
                _visa.setPassport_Country__c(country__c.getName());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return _visa;
    }

//    private static MyVisa parseMyVisaObject(JSONObject json) {
//        MyVisa _visa = new MyVisa();
//        Gson gson = new Gson();
//        _visa = gson.fromJson(json.toString(), MyVisa.class);
//
//
//        Log.d("json", json.toString());
//        try {
//            CountryofBirth _countryOfBirth = new CountryofBirth();
//            _countryOfBirth.setID(json.getJSONObject("Country_of_Birth__r").getString(JSONConstants.ID));
//            _countryOfBirth.setName(json.getJSONObject("Country_of_Birth__r").getString(JSONConstants.NAME));
//            _visa.set_countryOfBirth(_countryOfBirth);
//
//            CurrentNationality _currentNationality = new CurrentNationality();
//            _currentNationality.setID(json.getJSONObject("Current_Nationality__r").getString(JSONConstants.ID));
//            _currentNationality.setName(json.getJSONObject("Current_Nationality__r").getString(JSONConstants.NAME));
//            _visa.set_currentNationality(_currentNationality);
//
//            JobTitleAtImmigration _jobTitleImmigration = new JobTitleAtImmigration();
////            _jobTitleImmigration.setUrl(json.getJSONObject("Job_Title_at_Immigration__r").getJSONObject(JSONConstants.ATTRIBUTES).getString(JSONConstants.URL));
//            _jobTitleImmigration.setID(json.getJSONObject("Job_Title_at_Immigration__r").getString(JSONConstants.ID));
//            _jobTitleImmigration.setName(json.getJSONObject("Job_Title_at_Immigration__r").getString(JSONConstants.NAME));
//            _visa.set_jobTitleImigration(_jobTitleImmigration);
//
//            SponsoringCompany _sponsoringCompany = new SponsoringCompany();
////            _sponsoringCompany.setUrl(json.getJSONObject("Sponsoring_Company__r").getJSONObject(JSONConstants.ATTRIBUTES).getString(JSONConstants.URL));
//            _sponsoringCompany.setName(json.getJSONObject("Sponsoring_Company__r").getString(JSONConstants.NAME));
//            _visa.set_sponsoringCompany(_sponsoringCompany);
//
//            VisaHolder _visaHolder = new VisaHolder();
////            _visaHolder.setUrl(json.getJSONObject("Visa_Holder__r").getJSONObject(JSONConstants.ATTRIBUTES).getString(JSONConstants.URL));
//            _visaHolder.setID(json.getJSONObject("Visa_Holder__r").getString(JSONConstants.ID));
//            _visaHolder.setName(json.getJSONObject("Visa_Holder__r").getString(JSONConstants.NAME));
//            _visaHolder.setBillingCity(json.getJSONObject("Visa_Holder__r").getString(JSONConstants.BILLING_CITY));
//            _visa.set_visaHolder(_visaHolder);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return _visa;
//    }


    public static ArrayList<LicenseActivity> parseLicenseActivityObject(String s) throws JSONException {
        JSONObject jsonLicenseActivities = new JSONObject(s);
        ArrayList<LicenseActivity> _licenses = new ArrayList<LicenseActivity>();
        Gson gson = new Gson();
        if (jsonLicenseActivities.optBoolean(JSONConstants.DONE) == true) {

            JSONArray jRecords = jsonLicenseActivities.getJSONArray(JSONConstants.RECORDS);
            for (int i = 0; i < jRecords.length(); i++) {
                gson = new Gson();
                JSONObject jsonLicense = jRecords.getJSONObject(i);
                LicenseActivity _licenseActivity = gson.fromJson(jsonLicense.toString(), LicenseActivity.class);
                _licenseActivity.setUrl(jsonLicense.getJSONObject(JSONConstants.ATTRIBUTES).getString(JSONConstants.URL));
                gson = new Gson();
                OriginalBusinessActivity _originalBusinessActivity = gson.fromJson(jsonLicense.getJSONObject("Original_Business_Activity__r").toString(), OriginalBusinessActivity.class);
                _originalBusinessActivity.setUrl(jsonLicense.getJSONObject("Original_Business_Activity__r").getJSONObject(JSONConstants.ATTRIBUTES).getString(JSONConstants.URL));
                _licenseActivity.set_originalBusinessActivity(_originalBusinessActivity);
                _licenses.add(_licenseActivity);
            }
        }


        return _licenses;
    }

    public static String parseRenewalRequest(String s) throws JSONException {
        JSONObject jsonObject = new JSONObject(s);
        String Invoices = "";
        if (jsonObject.optBoolean(JSONConstants.DONE) == true) {
            if (jsonObject.getJSONArray(JSONConstants.RECORDS).length() > 0) {
                Invoices = "";
            } else {
                Invoices = "Found";
            }
        }

        return Invoices;
    }

    public static ArrayList<ShareOwnership> parseShareOwnerShipObject(String s) throws JSONException {

        Gson gson = new Gson();
        ShareOwnership shareOwnership = new ShareOwnership();
        ArrayList<ShareOwnership> shareOwnerships = new ArrayList<ShareOwnership>();
        JSONObject jsonObject = new JSONObject(s.toString());
        JSONArray jsonArrayRecords = jsonObject.getJSONArray(JSONConstants.RECORDS);
        for (int i = 0; i < jsonArrayRecords.length(); i++) {
            JSONObject json = jsonArrayRecords.getJSONObject(i);
            shareOwnership = gson.fromJson(json.toString(), ShareOwnership.class);
            shareOwnership.setUrl(json.getJSONObject(JSONConstants.ATTRIBUTES).getString(JSONConstants.URL));

            JSONObject jsonShareHolder = json.getJSONObject("Shareholder__r");
            Shareholder shareholder = new Shareholder();
            gson = new Gson();
            shareholder = gson.fromJson(jsonShareHolder.toString(), Shareholder.class);
            shareholder.setUrl(jsonShareHolder.getJSONObject(JSONConstants.ATTRIBUTES).getString(JSONConstants.URL));

            CurrentPassport currentPassport = new CurrentPassport();
            gson = new Gson();
            if (!jsonShareHolder.toString().contains("\"Current_Passport__r\":null")) {
                JSONObject jsonCurrentPassport = jsonShareHolder.getJSONObject("Current_Passport__r");
                currentPassport = gson.fromJson(jsonCurrentPassport.toString(), CurrentPassport.class);
                currentPassport.setUrl(jsonCurrentPassport.getJSONObject(JSONConstants.ATTRIBUTES).getString(JSONConstants.URL));
                shareholder.set_currentPassport(currentPassport);
            } else {
                shareholder.set_currentPassport(null);
            }
            shareOwnership.set_shareholder(shareholder);
            shareOwnerships.add(shareOwnership);
        }
        return shareOwnerships;
    }

    public static ArrayList<Directorship> parseDirectionshipObject(String s) {

        Gson gson = new Gson();
        Directorship directorship = new Directorship();
        ArrayList<Directorship> directorships = new ArrayList<Directorship>();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(s.toString());
            JSONArray jsonArrayRecords = jsonObject.getJSONArray(JSONConstants.RECORDS);
            for (int i = 0; i < jsonArrayRecords.length(); i++) {
                JSONObject json = jsonArrayRecords.getJSONObject(i);
                directorship = gson.fromJson(json.toString(), Directorship.class);
                directorship.setUrl(json.getJSONObject(JSONConstants.ATTRIBUTES).getString(JSONConstants.URL));

                JSONObject jsonDirector = json.getJSONObject("Director__r");
                Director director = new Director();
                gson = new Gson();
                director = gson.fromJson(jsonDirector.toString(), Director.class);
                director.setUrl(jsonDirector.getJSONObject(JSONConstants.ATTRIBUTES).getString(JSONConstants.URL));

                CurrentPassport currentPassport = new CurrentPassport();
                gson = new Gson();
                if (!jsonDirector.toString().contains("\"Current_Passport__r\":null")) {
                    JSONObject jsonCurrentPassport = jsonDirector.getJSONObject("Current_Passport__r");
                    currentPassport = gson.fromJson(jsonCurrentPassport.toString(), CurrentPassport.class);
                    currentPassport.setUrl(jsonCurrentPassport.getJSONObject(JSONConstants.ATTRIBUTES).getString(JSONConstants.URL));
                    director.set_currentPassport(currentPassport);
                } else {
                    director.set_currentPassport(null);
                }
                directorship.set_director(director);
                directorships.add(directorship);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return directorships;
    }


    public static ArrayList<ManagementMember> parseManagementMemberObject(String s) {
        Gson gson = new Gson();
        ManagementMember managementMember = new ManagementMember();
        ArrayList<ManagementMember> managementMembers = new ArrayList<ManagementMember>();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(s.toString());
            JSONArray jsonArrayRecords = jsonObject.getJSONArray(JSONConstants.RECORDS);
            for (int i = 0; i < jsonArrayRecords.length(); i++) {
                JSONObject json = jsonArrayRecords.getJSONObject(i);
                managementMember = gson.fromJson(json.toString(), ManagementMember.class);
                managementMember.setUrl(json.getJSONObject(JSONConstants.ATTRIBUTES).getString(JSONConstants.URL));

                JSONObject jsonDirector = json.getJSONObject("Manager__r");
                Manager manager = new Manager();
                gson = new Gson();
                manager = gson.fromJson(jsonDirector.toString(), Manager.class);
                manager.setUrl(jsonDirector.getJSONObject(JSONConstants.ATTRIBUTES).getString(JSONConstants.URL));

                CurrentPassport currentPassport = new CurrentPassport();
                gson = new Gson();
                if (!jsonDirector.toString().contains("\"Current_Passport__r\":null")) {
                    JSONObject jsonCurrentPassport = jsonDirector.getJSONObject("Current_Passport__r");
                    currentPassport = gson.fromJson(jsonCurrentPassport.toString(), CurrentPassport.class);
                    currentPassport.setUrl(jsonCurrentPassport.getJSONObject(JSONConstants.ATTRIBUTES).getString(JSONConstants.URL));
                    manager.setCurrentPassport(currentPassport);
                } else {
                    manager.setCurrentPassport(null);
                }
                managementMember.set_manager(manager);
                managementMembers.add(managementMember);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return managementMembers;
    }

    public static ArrayList<LegalRepresentative> parseLegalRepresentativesObject(String s) {

        Gson gson = new Gson();
        LegalRepresentative legalRepresentative = new LegalRepresentative();
        ArrayList<LegalRepresentative> legalRepresentatives = new ArrayList<LegalRepresentative>();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(s.toString());
            JSONArray jsonArrayRecords = jsonObject.getJSONArray(JSONConstants.RECORDS);
            for (int i = 0; i < jsonArrayRecords.length(); i++) {
                JSONObject json = jsonArrayRecords.getJSONObject(i);
                legalRepresentative = gson.fromJson(json.toString(), LegalRepresentative.class);
                legalRepresentative.setUrl(json.getJSONObject(JSONConstants.ATTRIBUTES).getString(JSONConstants.URL));

                JSONObject jsonLegalReprentativeLookup = json.getJSONObject("Legal_Representative__r");
                LegalRepresentativeLookup legalRepresentativeLookup = new LegalRepresentativeLookup();
                gson = new Gson();

                legalRepresentativeLookup = gson.fromJson(jsonLegalReprentativeLookup.toString(), LegalRepresentativeLookup.class);
                legalRepresentativeLookup.setUrl(jsonLegalReprentativeLookup.getJSONObject(JSONConstants.ATTRIBUTES).getString(JSONConstants.URL));

                CurrentPassport currentPassport = new CurrentPassport();
                gson = new Gson();
                if (!jsonLegalReprentativeLookup.toString().contains("\"Current_Passport__r\":null")) {
                    JSONObject jsonCurrentPassport = jsonLegalReprentativeLookup.getJSONObject("Current_Passport__r");
                    currentPassport = gson.fromJson(jsonCurrentPassport.toString(), CurrentPassport.class);
                    currentPassport.setUrl(jsonCurrentPassport.getJSONObject(JSONConstants.ATTRIBUTES).getString(JSONConstants.URL));
                    legalRepresentativeLookup.setCurrentPassport(currentPassport);
                } else {
                    legalRepresentativeLookup.setCurrentPassport(null);
                }

                legalRepresentative.setLegalRepresentativeLookup(legalRepresentativeLookup);
                legalRepresentatives.add(legalRepresentative);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return legalRepresentatives;
    }

    public static ArrayList<Receipt_Template__c> parseReceiptObjectResponse(String s) {

//        ArrayList<Receipt_Template__c> receipt_template__cs = new ArrayList<>();
//        Receipt_Template__c receipt_template__c;
//
//        JSONObject jsonFullResponse = null;
//        try {
//            jsonFullResponse = new JSONObject(s);
//            JSONArray jsonArrayRecords = jsonFullResponse.getJSONArray(JSONConstants.RECORDS);
//            for (int i = 0; i < jsonArrayRecords.length(); i++) {
//                receipt_template__c = new Receipt_Template__c();
//                JSONObject jsonObject = jsonArrayRecords.getJSONObject(i);
//                ObjectReader or = new ObjectMapper().reader().withType(
//                        Receipt_Template__c.class);
//                try {
//                    receipt_template__c = or.readValue(jsonObject.toString());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                receipt_template__cs.add(receipt_template__c);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return receipt_template__cs;

//        ArrayList<Receipt_Template__c> receipt_template__cs = new ArrayList<>();
//
//        try {
//            receipt_template__cs = new ObjectMapper().readValue(s.toString(), new TypeReference<Collection<Receipt_Template__c>>() {
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return receipt_template__cs;


        Gson gson;
        ArrayList<Receipt_Template__c> receiptTemplates = new ArrayList<Receipt_Template__c>();
        ArrayList<EServices_Document_Checklist__c> eServicesDocumentChecklistses = new ArrayList<EServices_Document_Checklist__c>();
        try {
            JSONObject jsonFullResponse = new JSONObject(s);
            JSONArray jsonArrayRecords = jsonFullResponse.getJSONArray(JSONConstants.RECORDS);

            for (int i = 0; i < jsonArrayRecords.length(); i++) {
                gson = new Gson();
                eServicesDocumentChecklistses = new ArrayList<EServices_Document_Checklist__c>();
                JSONObject jsonObject = jsonArrayRecords.getJSONObject(i);
                Receipt_Template__c receiptTemplate = gson.fromJson(jsonObject.toString(), Receipt_Template__c.class);
                Log.d("object", jsonObject.toString());
                receiptTemplate.setTotal_Amount__c(jsonObject.getDouble("Total_Amount__c"));
                receiptTemplate.setNo_of_Upload_Docs__c(jsonObject.getInt("No_of_Upload_Docs__c"));


                if (jsonObject.getString("eServices_Document_Checklists__r") != "null") {
                    JSONObject jsoneServices_Document_Checklists = jsonObject.getJSONObject("eServices_Document_Checklists__r");
                    JSONArray jArrayeServices_Document_ChecklistsRecords = jsoneServices_Document_Checklists.getJSONArray(JSONConstants.RECORDS);
                    for (int j = 0; j < jArrayeServices_Document_ChecklistsRecords.length(); j++) {
                        gson = new Gson();
                        JSONObject jsonEServicesDocumentChecklistsItem = jArrayeServices_Document_ChecklistsRecords.getJSONObject(j);
                        EServices_Document_Checklist__c eServicesDocumentChecklists = gson.fromJson(jsonEServicesDocumentChecklistsItem.toString(), EServices_Document_Checklist__c.class);
                        eServicesDocumentChecklistses.add(eServicesDocumentChecklists);
                    }
                    receiptTemplate.seteServices_document_checklist__c(eServicesDocumentChecklistses);

                }
                receiptTemplates.add(receiptTemplate);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return receiptTemplates;


    }

    public static ArrayList<Receipt_Template__c> parseReceiptObjectResponse2(String s) {

//        ArrayList<Receipt_Template__c> receipt_template__cs = new ArrayList<>();
//        Receipt_Template__c receipt_template__c;
//
//        JSONObject jsonFullResponse = null;
//        try {
//            jsonFullResponse = new JSONObject(s);
//            JSONArray jsonArrayRecords = jsonFullResponse.getJSONArray(JSONConstants.RECORDS);
//            for (int i = 0; i < jsonArrayRecords.length(); i++) {
//                receipt_template__c = new Receipt_Template__c();
//                JSONObject jsonObject = jsonArrayRecords.getJSONObject(i);
//                ObjectReader or = new ObjectMapper().reader().withType(
//                        Receipt_Template__c.class);
//                try {
//                    receipt_template__c = or.readValue(jsonObject.toString());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                receipt_template__cs.add(receipt_template__c);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return receipt_template__cs;

//        ArrayList<Receipt_Template__c> receipt_template__cs = new ArrayList<>();
//
//        try {
//            receipt_template__cs = new ObjectMapper().readValue(s.toString(), new TypeReference<Collection<Receipt_Template__c>>() {
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return receipt_template__cs;
        Gson gson;
        ArrayList<Receipt_Template__c> receiptTemplates = new ArrayList<Receipt_Template__c>();
        ArrayList<EServices_Document_Checklist__c> eServicesDocumentChecklistses = new ArrayList<EServices_Document_Checklist__c>();
        try {
            JSONObject jsonFullResponse = new JSONObject(s);
            JSONArray jsonArrayRecords = jsonFullResponse.getJSONArray(JSONConstants.RECORDS);
            for (int i = 0; i < jsonArrayRecords.length(); i++) {
                gson = new Gson();
                eServicesDocumentChecklistses = new ArrayList<EServices_Document_Checklist__c>();
                JSONObject jsonObject = jsonArrayRecords.getJSONObject(i);
                Receipt_Template__c receiptTemplate = gson.fromJson(jsonObject.toString(), Receipt_Template__c.class);
                Log.d("object", jsonObject.toString());
                receiptTemplate.setTotal_Amount__c(jsonObject.getDouble("Total_Amount__c"));
                if (!jsonObject.toString().contains("\"eServices_Document_Checklists__r\":null")) {
                    JSONObject jsoneServices_Document_Checklists = jsonObject.getJSONObject("eServices_Document_Checklists__r");
                    JSONArray jArrayeServices_Document_ChecklistsRecords = jsoneServices_Document_Checklists.getJSONArray(JSONConstants.RECORDS);
                    for (int j = 0; j < jArrayeServices_Document_ChecklistsRecords.length(); j++) {
                        gson = new Gson();
                        JSONObject jsonEServicesDocumentChecklistsItem = jArrayeServices_Document_ChecklistsRecords.getJSONObject(j);
                        EServices_Document_Checklist__c eServicesDocumentChecklists = gson.fromJson(jsonEServicesDocumentChecklistsItem.toString(), EServices_Document_Checklist__c.class);
                        eServicesDocumentChecklistses.add(eServicesDocumentChecklists);
                    }
                    receiptTemplate.seteServices_document_checklist__c(eServicesDocumentChecklistses);
                }

                receiptTemplates.add(receiptTemplate);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return receiptTemplates;
    }

    public static ArrayList<MyRequest> parseMyRequestsResponse(String s) {

        Gson gson;
        ArrayList<MyRequest> myRequests = new ArrayList<MyRequest>();
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.optBoolean(JSONConstants.DONE) == true) {
                JSONArray jArrayRecords = jsonObject.getJSONArray(JSONConstants.RECORDS);
                for (int i = 0; i < jArrayRecords.length(); i++) {
                    gson = new Gson();
                    JSONObject jsonRecord = jArrayRecords.getJSONObject(i);
                    MyRequest myRequest = new MyRequest();
                    myRequest = gson.fromJson(jsonRecord.toString(), MyRequest.class);
                    JSONObject jsonRecordType = jsonRecord.getJSONObject(JSONConstants.RECORD_TYPE);
                    gson = new Gson();
                    RecordType recordType = gson.fromJson(jsonRecordType.toString(), RecordType.class);
                    myRequest.setRecordType(recordType);
                    myRequest.setUrl(jsonRecord.getJSONObject(JSONConstants.ATTRIBUTES).getString(JSONConstants.URL));
                    if (!jsonRecord.toString().contains("\"Employee_Ref__r\":null")) {
                        JSONObject jsonEmployeeRef = jsonRecord.getJSONObject("Employee_Ref__r");
                        EmployeeRef employeeRef = new EmployeeRef();
                        gson = new Gson();
                        employeeRef = gson.fromJson(jsonEmployeeRef.toString(), EmployeeRef.class);
                        employeeRef.setUrl(jsonEmployeeRef.getJSONObject(JSONConstants.ATTRIBUTES).getString(JSONConstants.URL));
                        myRequest.setEmployee_Ref(employeeRef);
                    }
                    myRequests.add(myRequest);
                }
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return myRequests;
    }

    public static ArrayList<NotificationManagement> parseNotificationsResponse(String s) {
        Gson gson;
        ArrayList<NotificationManagement> notificationManagements = new ArrayList<NotificationManagement>();
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(s.toString());
            JSONArray jArrayRecords = jsonObject.getJSONArray(JSONConstants.RECORDS);
            for (int i = 0; i < jArrayRecords.length(); i++) {
                gson = new Gson();
                JSONObject jsonRecord = jArrayRecords.getJSONObject(i);
                NotificationManagement notificationManagement = new NotificationManagement();
                notificationManagement = gson.fromJson(jsonRecord.toString(), NotificationManagement.class);
                notificationManagement.setUrl(jsonRecord.getJSONObject(JSONConstants.ATTRIBUTES).getString(JSONConstants.URL));
                JSONObject jsonCase = jsonRecord.getJSONObject("Case__r");
                gson = new Gson();
                Case recordCase = gson.fromJson(jsonCase.toString(), Case.class);
                recordCase.setUrl(jsonCase.getJSONObject(JSONConstants.ATTRIBUTES).getString(JSONConstants.URL));
//               recordCase.setCase_Rating_Score(jsonCase.getString("Case_Rating_Score__c"));
                notificationManagement.setCaseNotification(recordCase);
                notificationManagements.add(notificationManagement);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return notificationManagements;
    }

    public static String parseNeedHelpService(String s) {
        String ID = "";
        try {
            JSONObject json = new JSONObject(s.toString());
            ID = json.getJSONArray(JSONConstants.RECORDS).getJSONObject(0).getString("Id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ID;
    }

    public static WebForm parseWebFormObject(String s) {
        WebForm webForm = new WebForm();
        Gson gson = new Gson();
        try {
            JSONObject jsonObject = new JSONObject(s.toString());
            if (jsonObject.optBoolean(JSONConstants.DONE) == true) {
                JSONArray jsonArrayRecords = jsonObject.getJSONArray(JSONConstants.RECORDS);
                for (int i = 0; i < jsonArrayRecords.length(); i++) {
                    JSONObject jsonRecord = jsonArrayRecords.getJSONObject(i);
                    webForm = gson.fromJson(jsonRecord.toString(), WebForm.class);
                    webForm.setUrl(jsonRecord.getJSONObject(JSONConstants.ATTRIBUTES).getString(JSONConstants.URL));
                    JSONObject jsonFields = jsonRecord.getJSONObject(JSONConstants.VISUALFORCE_GENERATOR_ID);
                    JSONArray jsonArrayFields = jsonFields.getJSONArray(JSONConstants.RECORDS);
                    ArrayList<FormField> formFields = new ArrayList<FormField>();
                    for (int j = 0; j < jsonArrayFields.length(); j++) {
                        JSONObject json = jsonArrayFields.getJSONObject(j);
                        gson = new Gson();
                        FormField formField = new FormField();
                        formField = gson.fromJson(json.toString(), FormField.class);
                        formFields.add(formField);
                    }
                    webForm.set_formFields(formFields);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return webForm;
    }

    public static ArrayList<Company_Documents__c> parseCompanyDocumentObjectWithGson(String s) {

        ArrayList<Company_Documents__c> company_documents = new ArrayList<Company_Documents__c>();
        JSONObject jsonObject = null;
        Gson gson;
        Company_Documents__c company_documents__c;
        try {
            jsonObject = new JSONObject(s.toString());
            Log.d("documents", s.toString());
            JSONArray jsonArrayRecords = jsonObject.getJSONArray(JSONConstants.RECORDS);
            if (jsonArrayRecords.length() > 0) {
                for (int i = 0; i < jsonArrayRecords.length(); i++) {
                    JSONObject jsonRecord = jsonArrayRecords.getJSONObject(i);
                    company_documents__c = new Company_Documents__c();
                    company_documents__c.setId(jsonRecord.getString("Id"));
                    company_documents__c.setName(jsonRecord.getString("Name"));
                    company_documents__c.setVersion__c(Double.valueOf(jsonRecord.getString("Version__c")));
                    company_documents__c.setCustomer_Document__c(jsonRecord.getBoolean("Customer_Document__c"));
                    company_documents__c.setAttachment_Id__c(jsonRecord.getString("Attachment_Id__c"));
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = null;
                    try {
                        date = format.parse(jsonRecord.getString("CreatedDate").substring(0, 10));
                        System.out.println(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    company_documents__c.setCreatedDate(calendar);
                    company_documents__c.setDocument_Type__c(jsonRecord.getString("Document_Type__c"));
                    company_documents__c.setOriginal_Verified__c(jsonRecord.getBoolean("Original_Verified__c"));
                    company_documents__c.setOriginal_Collected__c(jsonRecord.getBoolean("Original_Collected__c"));
                    company_documents__c.setRequired_Original__c(jsonRecord.getBoolean("Required_Original__c"));
                    company_documents__c.setVerified_Scan_Copy__c(jsonRecord.getBoolean("Verified_Scan_Copy__c"));
                    company_documents__c.setUploaded__c(jsonRecord.getBoolean("Uploaded__c"));
                    company_documents__c.setRequired_Scan_copy__c(jsonRecord.getBoolean("Required_Scan_copy__c"));
                    JSONObject jsonRecordType = jsonRecord.getJSONObject("RecordType");
                    RecordType recordType = new RecordType();
                    recordType.setId(jsonRecordType.getString("Id"));
                    recordType.setName(jsonRecordType.getString("Name"));
                    recordType.setDeveloperName(jsonRecordType.getString("DeveloperName"));
                    recordType.setSobjectType(jsonRecordType.getString("SobjectType"));
                    company_documents__c.setRecordType(recordType);
                    if (jsonRecord.toString().contains("Party__r")) {
                        JSONObject jsonParty = jsonRecord.getJSONObject("Party__r");
                        Party party = new Party();
                        gson = new Gson();
                        party = gson.fromJson(jsonParty.toString(), Party.class);
                        company_documents__c.setParty(party);
                    }
                    if (company_documents__c.getUploaded__c())
                        company_documents.add(company_documents__c);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return company_documents;
    }

//    public static ArrayList<Company_Documents__c> parseCompanyDocumentObject(String s) {
//
//        ArrayList<Company_Documents__c> company_documents = new ArrayList<Company_Documents__c>();
//        JSONObject jsonObject = null;
//        Company_Documents__c company_documents__c;
//        try {
//            jsonObject = new JSONObject(s.toString());
//            Log.d("documents", s.toString());
//            JSONArray jsonArrayRecords = jsonObject.getJSONArray(JSONConstants.RECORDS);
//            if (jsonArrayRecords.length() > 0) {
//                for (int i = 0; i < jsonArrayRecords.length(); i++) {
//                    JSONObject jsonRecord = jsonArrayRecords.getJSONObject(i);
//                    company_documents__c = new Company_Documents__c();
//                    ObjectReader or = new ObjectMapper().reader().withType(
//                            Company_Documents__c.class);
//                    try {
//                        company_documents__c = or.readValue(jsonRecord.toString());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    if (company_documents__c.getUploaded__c())
//                        company_documents.add(company_documents__c);
//                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return company_documents;
//    }

    public static ArrayList<Company_Documents__c> parseCompanyDocumentObject(String s, boolean t) {

        ArrayList<Company_Documents__c> company_documents = new ArrayList<Company_Documents__c>();
        JSONObject jsonObject = null;
        Company_Documents__c company_documents__c;
        try {
            jsonObject = new JSONObject(s.toString());
            JSONArray jsonArrayRecords = jsonObject.getJSONArray(JSONConstants.RECORDS);
            if (jsonArrayRecords.length() > 0) {
                for (int i = 0; i < jsonArrayRecords.length(); i++) {
                    JSONObject jsonRecord = jsonArrayRecords.getJSONObject(i);
                    company_documents__c = new Company_Documents__c();
//                    ObjectReader or = new ObjectMapper().reader().withType(
//                            Company_Documents__c.class);
//                    try {
//                        company_documents__c = or.readValue(jsonRecord.toString());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    company_documents__c.setName(jsonRecord.getString("Name"));
                    company_documents__c.setVersion__c(jsonRecord.get("Version__c") == null ? 1.0 : Double.valueOf(jsonRecord.optDouble("Version__c")));
                    company_documents__c.setOriginal_Verified__c(jsonRecord.optBoolean("Original_Verified__c"));
                    company_documents__c.setRequired_Scan_copy__c(jsonRecord.optBoolean("Required_Scan_copy__c"));
                    company_documents__c.setVerified_Scan_Copy__c(jsonRecord.optBoolean("Verified_Scan_Copy__c"));
                    company_documents__c.setOriginal_Collected__c(jsonRecord.optBoolean("Original_Collected__c"));
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                    try {
                        cal.setTime(sdf.parse(jsonRecord.getString("CreatedDate").substring(0, 10)));
                        company_documents__c.setCreatedDate(cal);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    company_documents__c.setUploaded__c(jsonRecord.optBoolean("Uploaded__c"));
                    company_documents__c.setDocument_Type__c(jsonRecord.getString("Document_Type__c"));
                    company_documents__c.setAttachment_Id__c(jsonRecord.getString("Attachment_Id__c") == null || jsonRecord.getString("Attachment_Id__c").equals("null") ? "" : jsonRecord.getString("Attachment_Id__c"));
                    company_documents__c.setCustomer_Document__c(jsonRecord.optBoolean("Customer_Document__c"));
                    company_documents__c.setRequired_Original__c(jsonRecord.optBoolean("Required_Original__c"));
                    company_documents__c.setId(jsonRecord.getString("Id"));
//                    company_documents__c.setParty(jsonRecord.getString("Party__r"));
                    company_documents.add(company_documents__c);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return company_documents;
    }

    public static String parseCompanyDocumentRecordType(String s) {

        String recordId = "";
        try {
            JSONObject jsonObject = new JSONObject(s.toString());
            if (jsonObject.optBoolean(JSONConstants.DONE) == true) {
                JSONArray jsonArrayRecords = jsonObject.getJSONArray(JSONConstants.RECORDS);
                for (int i = 0; i < jsonArrayRecords.length(); i++) {
                    JSONObject jsonRecord = jsonArrayRecords.getJSONObject(i);
                    recordId = jsonRecord.getString("Id");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return recordId;
    }

    public static ArrayList<Company_Documents__c> parseCompanyDocumentChecklist(String caseId, String accountId, String RecordTypeId, String ObjectId, String s) {

        ArrayList<Company_Documents__c> _companyDocuments = new ArrayList<Company_Documents__c>();

        try {
            JSONObject jsonObject = new JSONObject(s.toString());
            JSONArray jArrayRecords = jsonObject.getJSONArray(JSONConstants.RECORDS);
            if (jArrayRecords.length() > 0) {
                for (int i = 0; i < jArrayRecords.length(); i++) {
                    JSONObject jsonObjectRecord = jArrayRecords.getJSONObject(i);
                    Company_Documents__c company_document = new Company_Documents__c();
                    company_document.setId(jsonObjectRecord.getString("Id"));
                    company_document.setRequired_Scan_copy__c(jsonObjectRecord.getBoolean("Required_Scan_copy__c"));
                    company_document.setName(jsonObjectRecord.getString("Name"));
                    company_document.setDocument_Type__c(jsonObjectRecord.getString("Document_Type__c"));
                    company_document.setVersion__c(Double.valueOf(0));
                    company_document.setRequest__c(caseId);
                    company_document.setParty__c(accountId);
                    company_document.setCompany__c(accountId);
                    company_document.setEServices_Document__c(jsonObjectRecord.getString("Id"));
                    company_document.setRecordTypeId(RecordTypeId);
                    company_document.setNOC__c(ObjectId);
                    if (company_document.getDocument_Type__c() != null) {
                        if (company_document.getDocument_Type__c().equals("Upload")) {
                            _companyDocuments.add(company_document);
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return _companyDocuments;
    }

//    public static ArrayList<Attachment> parseAttachmentObject(String s) {
//        ArrayList<Attachment> attachments = new ArrayList<Attachment>();
//        Attachment attachment = new Attachment();
//        try {
//            JSONObject jsonObject = new JSONObject(s.toString());
//            JSONArray jArrayRecords = jsonObject.getJSONArray(JSONConstants.RECORDS);
//            for (int i = 0; i < jArrayRecords.length(); i++) {
//                JSONObject jsonRecord = jArrayRecords.getJSONObject(i);
//                ObjectReader or = new ObjectMapper().reader().withType(
//                        Attachment.class);
//                try {
//                    attachment = or.readValue(jsonRecord.toString());
//                    attachments.add(attachment);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return attachments;
//    }

    public static ArrayList<Country__c> parseCountryObject(String s) throws JSONException {
        ArrayList<Country__c> country__cs = new ArrayList<Country__c>();
        Country__c country__c = new Country__c();
        JSONObject jsonObject = new JSONObject(s.toString());
        JSONArray jArrayRecords = jsonObject.getJSONArray(JSONConstants.RECORDS);
        for (int i = 0; i < jArrayRecords.length(); i++) {
            JSONObject jsonRecord = jArrayRecords.getJSONObject(i);
//            ObjectReader or = new ObjectMapper().reader().withType(
//                    Country__c.class);
//
//            try {
//                attachment = or.readValue(jsonRecord.toString());
//                attachments.add(attachment);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            gson = new Gson();
            country__c = gson.fromJson(jsonRecord.toString(), Country__c.class);
            country__cs.add(country__c);
        }

        return country__cs;
    }

    public static ArrayList<Contract_DWC__c> parseLeasingContractResponse(String s) {
        Gson gson = new Gson();
        ArrayList<Contract_DWC__c> contract_dwc__cs = new ArrayList<>();
        Contract_DWC__c contract_dwc__c = new Contract_DWC__c();
//        JSONObject jsonObject = null;
        try {
//            jsonObject = new JSONObject(s.toString());
            JSONArray jArrayRecords = new JSONArray(s);
            for (int i = 0; i < jArrayRecords.length(); i++) {
                contract_dwc__c = new Contract_DWC__c();
                JSONObject jsonRecord = jArrayRecords.getJSONObject(i);
//                ObjectReader or = new ObjectMapper().reader().withType(
//                        Contract_DWC__c.class);
//                try {
//                    contract_dwc__c = or.readValue(jsonRecord.toString());
//                    contract_dwc__cs.add(contract_dwc__c);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                contract_dwc__c = gson.fromJson(jsonRecord.toString(), Contract_DWC__c.class);
                Contract_Line_Item__c contract_line_item__c = new Contract_Line_Item__c();
                ArrayList<Contract_Line_Item__c> contract_line_item__cs;
                JSONObject jsonContract_Line_Items__r = jsonRecord.getJSONObject("Contract_Line_Items__r");
                JSONArray jsonArrayRecords = jsonContract_Line_Items__r.getJSONArray(JSONConstants.RECORDS);
                contract_line_item__cs = new ArrayList<>();
                for (int j = 0; j < jsonArrayRecords.length(); j++) {
                    JSONObject json = jsonArrayRecords.getJSONObject(j);
                    contract_line_item__c = new Contract_Line_Item__c();
                    gson = new Gson();
                    contract_line_item__c = gson.fromJson(json.toString(), Contract_Line_Item__c.class);
                    JSONObject jsonInventory_Unit__r = json.getJSONObject("Inventory_Unit__r");
                    Inventory_Unit__r inventory_unit__r = new Inventory_Unit__r();
//                    gson = new Gson();
//                    inventory_unit__r = gson.fromJson(jsonInventory_Unit__r.toString(), Inventory_Unit__r.class);
                    inventory_unit__r.setName(jsonInventory_Unit__r.getString("Name"));

                    if (jsonInventory_Unit__r.toString().contains("Building_Number__c")) {
                        inventory_unit__r.setBuilding_Number__c(jsonInventory_Unit__r.getString("Building_Number__c"));
                    }

                    if (jsonInventory_Unit__r.toString().contains("Product_Type__c")) {
                        inventory_unit__r.setProduct_Type__c(jsonInventory_Unit__r.getString("Product_Type__c"));
                    }

                    if (jsonInventory_Unit__r.toString().contains("Area_in_sq_m__c")) {
                        inventory_unit__r.setArea_in_sq_m__c(jsonInventory_Unit__r.getString("Area_in_sq_m__c"));
                    }

                    JSONObject jsonProduct_Type__r = jsonInventory_Unit__r.getJSONObject("Product_Type__r");
                    Product_Type__r product_type__r = new Product_Type__r();
                    product_type__r.setId(jsonProduct_Type__r.getString("Id"));
                    product_type__r.setName(jsonProduct_Type__r.getString("Name"));
                    inventory_unit__r.setProduct_Type__r(product_type__r);
                    contract_line_item__c.setInventory_unit__r(inventory_unit__r);
                    contract_line_item__cs.add(contract_line_item__c);
                }

                contract_dwc__c.setContract_line_item__cs(contract_line_item__cs);
                JSONObject jsonQoute = jsonRecord.getJSONObject("Quote__r");
                Quote quote = new Quote();
                quote.setId(jsonQoute.getString("Id"));
                quote.setName(jsonQoute.getString("Name"));
                contract_dwc__c.setQuote(quote);
                if (jsonRecord.toString().contains("Tenancy_Contract_Payments__r")) {
                    JSONObject jsonUpcomingPayments = jsonRecord.getJSONObject("Tenancy_Contract_Payments__r");
                    if (jsonUpcomingPayments != null) {
                        JSONArray jsonArrayUpcomingPayments = jsonUpcomingPayments.getJSONArray(JSONConstants.RECORDS);
                        if (jsonArrayUpcomingPayments.length() > 0) {
                            ArrayList<TenancyContractPayment> tenancyContractPayments = new ArrayList<>();
                            for (int j = 0; j < jsonArrayUpcomingPayments.length(); j++) {
                                JSONObject jsonUpcomingPayment = jsonArrayUpcomingPayments.getJSONObject(0);
                                TenancyContractPayment tenancyContractPayment = new TenancyContractPayment();
                                gson = new Gson();
                                tenancyContractPayment = gson.fromJson(jsonUpcomingPayment.toString(), TenancyContractPayment.class);
                                tenancyContractPayments.add(tenancyContractPayment);
                            }
                            contract_dwc__c.setTenancyContractPayments(tenancyContractPayments);
                        }
                    }
                }
                contract_dwc__cs.add(contract_dwc__c);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return contract_dwc__cs;
    }

    public static ArrayList<Contract_DWC__c> parseLeasingContractResponse2(String s) {
        Gson gson = new Gson();
        ArrayList<Contract_DWC__c> contract_dwc__cs = new ArrayList<>();
        Contract_DWC__c contract_dwc__c = new Contract_DWC__c();
//        JSONObject jsonObject = null;
        try {
//            jsonObject = new JSONObject(s.toString());
            JSONArray jArrayRecords = new JSONArray(s);
            for (int i = 0; i < jArrayRecords.length(); i++) {
                contract_dwc__c = new Contract_DWC__c();
                JSONObject jsonRecord = jArrayRecords.getJSONObject(i);
//                ObjectReader or = new ObjectMapper().reader().withType(
//                        Contract_DWC__c.class);
//                try {
//                    contract_dwc__c = or.readValue(jsonRecord.toString());
//                    contract_dwc__cs.add(contract_dwc__c);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                contract_dwc__c = gson.fromJson(jsonRecord.toString(), Contract_DWC__c.class);
                Contract_Line_Item__c contract_line_item__c = new Contract_Line_Item__c();
                ArrayList<Contract_Line_Item__c> contract_line_item__cs;
                JSONObject jsonContract_Line_Items__r = jsonRecord.getJSONObject("Contract_Line_Items__r");
                JSONArray jsonArrayRecords = jsonContract_Line_Items__r.getJSONArray(JSONConstants.RECORDS);
                contract_line_item__cs = new ArrayList<>();
                for (int j = 0; j < jsonArrayRecords.length(); j++) {
                    JSONObject json = jsonArrayRecords.getJSONObject(j);
                    contract_line_item__c = new Contract_Line_Item__c();
                    gson = new Gson();
                    contract_line_item__c = gson.fromJson(json.toString(), Contract_Line_Item__c.class);
                    JSONObject jsonInventory_Unit__r = json.getJSONObject("Inventory_Unit__r");
                    Inventory_Unit__r inventory_unit__r = new Inventory_Unit__r();
                    gson = new Gson();
                    inventory_unit__r = gson.fromJson(jsonInventory_Unit__r.toString(), Inventory_Unit__r.class);
                    JSONObject jsonProduct_Type__r = jsonInventory_Unit__r.getJSONObject("Product_Type__r");
                    Product_Type__r product_type__r = new Product_Type__r();
                    gson = new Gson();
                    product_type__r = gson.fromJson(jsonProduct_Type__r.toString(), Product_Type__r.class);
                    inventory_unit__r.setProduct_Type__r(product_type__r);
                    contract_line_item__c.setInventory_unit__r(inventory_unit__r);
                    contract_line_item__cs.add(contract_line_item__c);
                }

                contract_dwc__c.setContract_line_item__cs(contract_line_item__cs);
                JSONObject jsonQoute = jsonRecord.getJSONObject("Quote__r");
                Quote quote = new Quote();
                quote.setId(jsonQoute.getString("Id"));
                quote.setName(jsonQoute.getString("Name"));
                contract_dwc__c.setQuote(quote);

                contract_dwc__cs.add(contract_dwc__c);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return contract_dwc__cs;
    }


    public static Case parseCaseObject(String s) {
        Gson gson = new Gson();
        Case caseObject = new Case();
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jArrayRecords = jsonObject.getJSONArray(JSONConstants.RECORDS);
            for (int i = 0; i < jArrayRecords.length(); i++) {
                JSONObject jsonRecord = jArrayRecords.getJSONObject(i);
                caseObject = gson.fromJson(jsonRecord.toString(), Case.class);
                Registration_Amendments__c registration_amendments__c = new Registration_Amendments__c();
                JSONObject jsonRegistrationAmendment = jsonRecord.getJSONObject("Registration_Amendment__r");
                gson = new Gson();
                registration_amendments__c = gson.fromJson(jsonRegistrationAmendment.toString(), Registration_Amendments__c.class);
                caseObject.setRegistration_amendments__c(registration_amendments__c);
                JSONObject jsonInvoice = jsonRecord.getJSONObject("Invoice__r");
                caseObject.setInvoice__c(jsonInvoice.getString("Amount__c"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return caseObject;
    }

    public static ArrayList<JobTitleAtImmigration> parseJobImmagrationObject(String s) throws JSONException {
        ArrayList<JobTitleAtImmigration> jobTitleAtImmigrations = new ArrayList<JobTitleAtImmigration>();
        JobTitleAtImmigration jobTitleAtImmigration = new JobTitleAtImmigration();
        JSONObject jsonObject = new JSONObject(s.toString());
        JSONArray jArrayRecords = jsonObject.getJSONArray(JSONConstants.RECORDS);
        for (int i = 0; i < jArrayRecords.length(); i++) {
            JSONObject jsonRecord = jArrayRecords.getJSONObject(i);
//            ObjectReader or = new ObjectMapper().reader().withType(
//                    JobTitleAtImmigration.class);
//
//            try {
//                attachment = or.readValue(jsonRecord.toString());
//                attachments.add(attachment);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            gson = new Gson();
            jobTitleAtImmigration = gson.fromJson(jsonRecord.toString(), JobTitleAtImmigration.class);
            jobTitleAtImmigrations.add(jobTitleAtImmigration);
        }

        return jobTitleAtImmigrations;
    }

    public static Collection<? extends FreeZonePayment> parseFreeZonePaymentResponse(String s) {
        ArrayList<FreeZonePayment> freeZonePayments = new ArrayList<>();
        Gson gson = new Gson();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(s.toString());
            JSONArray jArrayRecords = jsonObject.getJSONArray(JSONConstants.RECORDS);
            if (jArrayRecords.length() > 0) {
                for (int i = 0; i < jArrayRecords.length(); i++) {
                    JSONObject jsonObject1 = jArrayRecords.getJSONObject(i);
                    FreeZonePayment freeZonePayment = new FreeZonePayment();
                    freeZonePayment = gson.fromJson(jsonObject1.toString(), FreeZonePayment.class);
                    if (jsonObject1.getJSONObject("Request__r") != null) {
                        if (jsonObject1.getJSONObject("Request__r").toString().contains("\"Employee_Ref__r\":null")) {
                            freeZonePayment.setEmployeeName("");
                        } else {
                            if (jsonObject1.getJSONObject("Request__r").getJSONObject("Employee_Ref__r") != null) {
                                freeZonePayment.setEmployeeName(jsonObject1.getJSONObject("Request__r").getJSONObject("Employee_Ref__r").getString("Name"));
                            } else {
                                freeZonePayment.setEmployeeName("");
                            }
                        }
                    } else {
                        freeZonePayment.setEmployeeName("");
                    }

                    freeZonePayments.add(freeZonePayment);
                }
            } else {
                freeZonePayments = new ArrayList<>();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return freeZonePayments;
    }

//    public static ArrayList<EServices_Document_Checklist__c> parseEServiceDocumentChecklist(String s) throws JSONException, IOException {
//        JSONObject jsonObject = new JSONObject(s.toString());
//        JSONArray jArrayRecords = jsonObject.getJSONArray(JSONConstants.RECORDS);
//        ArrayList<EServices_Document_Checklist__c> eServices_document_checklist__cs = new ArrayList<EServices_Document_Checklist__c>();
//        EServices_Document_Checklist__c eServices_document_checklist__c;
//        if (jArrayRecords.length() > 0) {
//            for (int i = 0; i < jArrayRecords.length(); i++) {
//                JSONObject jsonRecord = jArrayRecords.getJSONObject(i);
////                gson = new Gson();
////                eServices_document_checklist__c = gson.fromJson(jsonRecord.toString(),EServices_Document_Checklist__c.class);
//                ObjectReader or = new ObjectMapper().reader().withType(
//                        EServices_Document_Checklist__c.class);
//                eServices_document_checklist__c = or.readValue(jsonRecord.toString());
//                eServices_document_checklist__cs.add(eServices_document_checklist__c);
//            }
//        }
//        return eServices_document_checklist__cs;
//    }

    public static ArrayList<EServices_Document_Checklist__c> parseEServiceDocumentChecklist2(String s) throws JSONException, IOException {
        JSONObject jsonObject = new JSONObject(s.toString());
        JSONArray jArrayRecords = jsonObject.getJSONArray(JSONConstants.RECORDS);
        ArrayList<EServices_Document_Checklist__c> eServices_document_checklist__cs = new ArrayList<EServices_Document_Checklist__c>();
        EServices_Document_Checklist__c eServices_document_checklist__c;
        if (jArrayRecords.length() > 0) {
            for (int i = 0; i < jArrayRecords.length(); i++) {
                JSONObject jsonRecord = jArrayRecords.getJSONObject(i);
                gson = new Gson();
                eServices_document_checklist__c = gson.fromJson(jsonRecord.toString(), EServices_Document_Checklist__c.class);
//                ObjectReader or = new ObjectMapper().reader().withType(
//                        EServices_Document_Checklist__c.class);
//                eServices_document_checklist__c = or.readValue(jsonRecord.toString());
                eServices_document_checklist__cs.add(eServices_document_checklist__c);
            }
        }
        return eServices_document_checklist__cs;
    }

    public static RecordType parseRecordTypeResponse(String s) throws JSONException, IOException {
        JSONObject jsonObject = new JSONObject(s.toString());
        JSONArray jArrayRecords = jsonObject.getJSONArray(JSONConstants.RECORDS);
        RecordType recordType = null;
        if (jArrayRecords.length() > 0) {
            for (int i = 0; i < jArrayRecords.length(); i++) {
                JSONObject jsonRecord = jArrayRecords.getJSONObject(i);
//                ObjectReader or = new ObjectMapper().reader().withType(
//                        RecordType.class);
//                recordType = or.readValue(jsonRecord.toString());
                gson = new Gson();
                recordType = gson.fromJson(jsonRecord.toString(), RecordType.class);
            }
        }

        return recordType;
    }
}