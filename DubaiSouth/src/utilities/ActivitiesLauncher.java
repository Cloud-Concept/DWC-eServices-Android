package utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;

import java.util.ArrayList;

import cloudconcept.dwc.AccessCardShowDetailsActivity;
import cloudconcept.dwc.CompanyDocumentsActivity;
import cloudconcept.dwc.DirectorShowDetailsActivity;
import cloudconcept.dwc.EmployeeListActivity;
import cloudconcept.dwc.LegalRepresentativesShowDetailsActivity;
import cloudconcept.dwc.RenewContractServiceActivity;
import cloudconcept.dwc.ReportsActivity;
import cloudconcept.dwc.ShowDetailsMyRequestsActivity;
import cloudconcept.dwc.ThankYouActivity;
import cloudconcept.dwc.ViewStatementShowDetails;
import cloudconcept.dwc.HomepageActivity;
import fragmentActivity.BaseActivityLauncher;
import fragmentActivity.CardActivity;
import fragmentActivity.ChangeAndRemovalActivity;
import fragmentActivity.CompanyNocActivity;
import fragmentActivity.HomeCompanyDocumentsActivity;
import fragmentActivity.LeasingShowDetailsActivity;
import fragmentActivity.LicenseActivity;
import fragmentActivity.LicenseCancellationActivity;
import fragmentActivity.NocActivity;
import fragmentActivity.NameReservationActivity;
import fragmentActivity.PreviewActivity;
import fragmentActivity.RequestTrueCopyActivity;
import fragmentActivity.ShareHolderActivity;
import fragmentActivity.ShowDetailsActivity;
import fragmentActivity.VisaActivity;
import model.Card_Management__c;
import model.Company_Documents__c;
import model.Contract_DWC__c;
import model.Directorship;
import model.EServices_Document_Checklist__c;
import model.FreeZonePayment;
import model.LegalRepresentative;
import model.ManagementMember;
import model.MyRequest;
import model.ShareOwnership;
import model.User;
import model.Visa;

/**
 * Created by Abanoub Wagdy on 6/14/2015.
 */

/**
 * ActivitiesLauncher is class that represents laucher of any activity inside the application
 */
public class ActivitiesLauncher {

    private static Intent intent;
    private static Gson gson;

    /**
     * @param applicationContext:context of current application
     *                                   Open Dashboard Screen
     */
    public static void openDashboardActivity(Context applicationContext) {
        intent = new Intent(applicationContext, BaseActivityLauncher.class);
        intent.putExtra("ScreenTag", AdapterConfiguration.DASHBOARD_SCREEN);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        applicationContext.startActivity(intent);

    }

    /**
     * @param applicationContext:context of current application
     *                                   Open MyRequests Screen
     */
    public static void openMyRequestsActivity(Context applicationContext) {
        intent = new Intent(applicationContext, BaseActivityLauncher.class);
        intent.putExtra("ScreenTag", AdapterConfiguration.MYREQUESTS_SCREEN);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        applicationContext.startActivity(intent);

    }

    /**
     * @param applicationContext:context of current application
     *                                   Open Notifications Screen
     */
    public static void openNotificationsActivity(Context applicationContext) {
        intent = new Intent(applicationContext, BaseActivityLauncher.class);
        intent.putExtra("ScreenTag", AdapterConfiguration.NOTIFICATIONS_SCREEN);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        applicationContext.startActivity(intent);
    }

    /**
     * @param applicationContext:context of current application
     *                                   Open Company Info Screen
     */
    public static void openCompanyInfoActivity(Context applicationContext) {
        intent = new Intent(applicationContext, BaseActivityLauncher.class);
        intent.putExtra("ScreenTag", AdapterConfiguration.COMPANY_INFO_SCREEN);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        applicationContext.startActivity(intent);

    }

    /**
     * @param applicationContext:context of current application
     *                                   Open reports Screen
     */
    public static void openReportsActivity(Context applicationContext) {
        intent = new Intent(applicationContext, ReportsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        applicationContext.startActivity(intent);

    }

    /**
     * @param applicationContext:context of current application
     *                                   Open need help Screen
     */
    public static void openNeedHelpActivity(Context applicationContext) {
        intent = new Intent(applicationContext, BaseActivityLauncher.class);
        intent.putExtra("ScreenTag", AdapterConfiguration.NEED_HELP_SCREEN);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        applicationContext.startActivity(intent);

    }

    /**
     * @param applicationContext:context of current application
     *                                   Open company documents Screen fired from company documents button in homepage or sliding menu
     */
    public static void openCompanyDocumentsActivity(Context applicationContext) {
        intent = new Intent(applicationContext, CompanyDocumentsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        applicationContext.startActivity(intent);

    }

    /**
     * @param applicationContext:context of current application
     *                                   Open company documents Screen fired from attachment screen for any service
     */
    public static void openHomeCompanyDocumentsActivity(Context applicationContext) {
        intent = new Intent(applicationContext, HomeCompanyDocumentsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        applicationContext.startActivity(intent);

    }

    /**
     * @param applicationContext:context of current application
     *                                   Open Quick Access Screen
     */
    public static void openQuickAccessActivity(Context applicationContext) {
        intent = new Intent(applicationContext, BaseActivityLauncher.class);
        intent.putExtra("ScreenTag", AdapterConfiguration.QUICK_ACCESS_SCREEN);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        applicationContext.startActivity(intent);

    }

    /**
     * @param applicationContext:context of current application
     *                                   Open Visas and cards Screen
     */
    public static void openVisasAndCardsActivity(Context applicationContext) {
        intent = new Intent(applicationContext, BaseActivityLauncher.class);
        intent.putExtra("ScreenTag", AdapterConfiguration.VISAS_AND_CARDS_SCREEN);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        applicationContext.startActivity(intent);

    }

    /**
     * @param applicationContext:context of current application
     *                                   Open homepage Screen
     */
    public static void openHomePageActivity(Context applicationContext) {
        intent = new Intent(applicationContext, HomepageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        applicationContext.startActivity(intent);
    }

    /**
     * @param applicationContext:context of current application
     * @param objectAsString:converted   object by gson
     * @param ObjectType:object          type (Visa ,User)
     *                                   Open noc wizard Screen
     */
    public static void openNOCActivity(Context applicationContext, String objectAsString, String ObjectType) {
        intent = new Intent(applicationContext, NocActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("objectAsString", objectAsString);
//        Visa v=new Gson().fromJson(objectAsString,Visa.class);
        intent.putExtra("ObjectType", ObjectType);
//        intent.putExtra("object",v);
        applicationContext.startActivity(intent);
    }

    /**
     * @param applicationContext:context of current application
     * @param title:Title                displated in next activity
     * @param objectAsString:converted   object by gson
     * @param objectType:object          type (Visa ,User)
     *                                   Open visa show details screen
     */
    public static void openShowDetailsActivity(Context applicationContext, String title, String objectAsString, String objectType) {
        intent = new Intent(applicationContext, ShowDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("objectAsString", objectAsString);
        intent.putExtra("title", "Show Details");
        intent.putExtra("objectType", objectType);
        applicationContext.startActivity(intent);
    }

    /**
     * @param applicationContext:context of current application
     *                                   Open Employee list Screen fired from Employee NOC (Quick Access Screen)
     */
    public static void openEmployeeListActivity(Context applicationContext) {
        intent = new Intent(applicationContext, EmployeeListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        applicationContext.startActivity(intent);
    }

    /**
     * @param activity              :activity          that is currently displaying
     * @param caseNumber:caseNumber if exists
     */
    public static void openThankYouActivity(Activity activity, String caseNumber) {
        intent = new Intent(activity.getApplicationContext(), ThankYouActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.putExtra("Message", Message);
        intent.putExtra("caseNumber", caseNumber);
        activity.getApplicationContext().startActivity(intent);
        activity.finish();
    }

    /**
     * @param applicationContext:context of current application
     *                                   Open Company NOC Screen
     */
    public static void openCompanyNocActivity(Context applicationContext) {
        intent = new Intent(applicationContext, CompanyNocActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        applicationContext.startActivity(intent);
    }

    /**
     * @param applicationContext:context of current application
     * @param type:type                  of used card service ,it can -1(New Card) -2(Cancel Card) -3(Renew Card) -4(Replace Card)
     *                                   Open Card Screen
     */
    public static void openNewCardActivity(Context applicationContext, String type) {
        intent = new Intent(applicationContext, CardActivity.class);
        intent.putExtra("type", type);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        applicationContext.startActivity(intent);
    }

    /**
     * @param applicationContext:context of current application
     * @param card:chosen                card to make process (cancel ,renew ...etc)
     * @param type:type                  of used card service ,it can -1(New Card) -2(Cancel Card) -3(Renew Card) -4(Replace Card)
     *                                   Open Card Screen
     */
    public static void openCardActivity(Context applicationContext, Card_Management__c card, String type) {
        intent = new Intent(applicationContext, CardActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("card", card);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        applicationContext.startActivity(intent);
    }

    /**
     * @param applicationContext:context of current application
     * @param visa:chosen                visa to make process
     * @param type:type                  of used card service ,it can -renew(Renew Visa) -Cancel(Cancel Visa) -3Passport(Renew passport)
     *                                   Open generic visa Screen
     */
    public static void openVisaActivity(Context applicationContext, Visa visa, String type) {
        intent = new Intent(applicationContext, VisaActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("visa", visa);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        applicationContext.startActivity(intent);
    }

    /**
     * @param applicationContext:context of current application
     * @param contract_dwc__c:contract   object
     *                                   Open contract show details Screen
     */
    public static void openShowContractDetailsActivity(Context applicationContext, Contract_DWC__c contract_dwc__c) {
        intent = new Intent(applicationContext, LeasingShowDetailsActivity.class);
        intent.putExtra("contract", contract_dwc__c);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        applicationContext.startActivity(intent);
    }

    /**
     * @param applicationContext:context of current application
     *                                   Open name reservation Screen service
     */
    public static void openNameReservationActivity(Context applicationContext) {
        intent = new Intent(applicationContext, NameReservationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        applicationContext.startActivity(intent);

    }

    /**
     * @param applicationContext :context of current application
     * @param MethodType         :method          type sent to salesforce web service and displayed as title
     * @param object             :object              can be Directionship ot User
     * @param i
     */
    public static void openGenericChangeAndRemovalActivity(Context applicationContext, String MethodType, Object object, Long i) {
        intent = new Intent(applicationContext, ChangeAndRemovalActivity.class);
        if (object instanceof Directorship) {
            Directorship directorship = (Directorship) object;
            Gson gson = new Gson();
            intent.putExtra("object", gson.toJson(directorship));
        } else if (object instanceof User) {
            User user = (User) object;
            if (user.get_contact().get_account().getEstablishmentCard() != null) {
                intent.putExtra("Current_Establishment_Card__c", user.get_contact().get_account().getEstablishmentCard().getCurrent_Establishment_Card__c());
                intent.putExtra("CardNumber", user.get_contact().get_account().getEstablishmentCard().getEstablishment_Card_Number__c());
                intent.putExtra("LicenseNumber", user.get_contact().get_account().get_currentLicenseNumber().getLicense_Number_Value());
                intent.putExtra("IssueDate", user.get_contact().get_account().getEstablishmentCard().getIssue_Date__c());
                intent.putExtra("ExpiryDate", user.get_contact().get_account().getEstablishmentCard().getExpiry_Date__c());
                intent.putExtra("Status", user.get_contact().get_account().getEstablishmentCard().getStatus());
            }
        }
        intent.putExtra("MethodType", MethodType);
        intent.putExtra("numberofShares", i);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        applicationContext.startActivity(intent);
    }

    /**
     * @param applicationContext:context of current application
     *                                   Open view statement Screen
     */
    public static void openViewStatementActivity(Context applicationContext) {
        intent = new Intent(applicationContext, BaseActivityLauncher.class);
        intent.putExtra("ScreenTag", AdapterConfiguration.VIEW_STATEMENT_SCREEN);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        applicationContext.startActivity(intent);
    }

    /**
     * @param applicationContext:context      of current application
     * @param freeZonePayment:freeZonePayment object to display details
     *                                        Open view statement show details Screen
     */
    public static void openViewStatementShowDetails(Context applicationContext, FreeZonePayment freeZonePayment) {
        Gson gson = new Gson();
        String str = gson.toJson(freeZonePayment);
        intent = new Intent(applicationContext, ViewStatementShowDetails.class);
        intent.putExtra("str", str);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        applicationContext.startActivity(intent);
    }

    /**
     * @param applicationContext:context of current application
     * @param shareOwnership:shareholder object
     * @param objects:objects            displayed for service
     *                                   Open share transfer service Screen
     */
    public static void openShareHolderActivity(Context applicationContext, ShareOwnership shareOwnership, String type, ArrayList<Object> objects) {
        intent = new Intent(applicationContext, ShareHolderActivity.class);
        intent.putExtra("type", "1");
        intent.putExtra("share", shareOwnership);
        intent.putExtra("shareHolders", objects);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        applicationContext.startActivity(intent);
    }

    /**
     * @param applicationContext:context                                      of current application
     * @param eServices_document_checklist__c:EServices_Document_Checklist__c object
     *                                                                        Open share transfer service Screen
     */
    public static void openCompanyDocumentsRequestTrueCopyActivity(Context applicationContext, EServices_Document_Checklist__c eServices_document_checklist__c) {
        Gson gson = new Gson();
        String str = gson.toJson(eServices_document_checklist__c);
        intent = new Intent(applicationContext, RequestTrueCopyActivity.class);
        intent.putExtra("object", str);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        applicationContext.startActivity(intent);
    }

//    public static void openCustomerDocumentsEditActivity(Context context, Company_Documents__c company_documents__c) {
//        Gson gson = new Gson();
//        String str = gson.toJson(company_documents__c);
//        intent = new Intent(context, CustomerDocumentEditActivity.class);
//        intent.putExtra("object", str);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        context.startActivity(intent);
//    }

    /**
     * @param applicationContext:context of current application
     * @param object:can                 be Company_Documents__c or EServices_Document_Checklist__c
     *                                   Open Preview Screen
     */
    public static void openCustomerDocumentsPreviewActivity(Context applicationContext, Object object) {
        Gson gson = new Gson();
        String str = gson.toJson(object);
        intent = new Intent(applicationContext, PreviewActivity.class);
        intent.putExtra("object", str);
        if (object instanceof Company_Documents__c) {
            intent.putExtra("type", "Company_Documents__c");
        } else {
            intent.putExtra("type", "EServices_Document_Checklist__c");
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        applicationContext.startActivity(intent);

    }

    /**
     * @param applicationContext:context of current application
     *                                   Open Cancel License Screen
     */
    public static void openLicenseCancellationActivity(Context applicationContext) {
        intent = new Intent(applicationContext, LicenseCancellationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        applicationContext.startActivity(intent);
    }

    /**
     * @param applicationContext:context of current application
     * @param _user:user                 object of current logged in user
     * @param type:it                    can be (Change License Activity,License Renewal,Renewal License)
     *                                   Open license activity services Screen
     */
    public static void openChangeAndRemovalLicenceActivity(Context applicationContext, User _user, String type) {
        intent = new Intent(applicationContext, LicenseActivity.class);
        intent.putExtra("user", _user);
        intent.putExtra("type", type);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        applicationContext.startActivity(intent);
    }

    /**
     * @param applicationContext:context of current application
     * @param myRequest:my               request object
     *                                   Open My Requests show details Screen
     */
    public static void openMyRequestsShowDetailsActivity(Context applicationContext, MyRequest myRequest) {
        intent = new Intent(applicationContext, ShowDetailsMyRequestsActivity.class);
        gson = new Gson();
        String ObjectAsStr = gson.toJson(myRequest);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("object", ObjectAsStr);
        applicationContext.startActivity(intent);
    }

    /**
     * @param applicationContext:context of current application
     * @param directorship:directionship object
     *                                   Open directionship show details Screen
     */
    public static void openDirectorShowDetailsActivity(Context applicationContext, Directorship directorship) {
        intent = new Intent(applicationContext, DirectorShowDetailsActivity.class);
        gson = new Gson();
        String ObjectAsStr = gson.toJson(directorship);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("object", ObjectAsStr);
        applicationContext.startActivity(intent);
    }

    /**
     * @param applicationContext:context of current application
     * @param card:card                  object from access card screen
     *                                   Open Access card show details Screen
     */
    public static void openAccessCardShowDetailsActivity(Context applicationContext, Card_Management__c card) {
        intent = new Intent(applicationContext, AccessCardShowDetailsActivity.class);
        gson = new Gson();
        String ObjectAsStr = gson.toJson(card);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("object", ObjectAsStr);
        applicationContext.startActivity(intent);
    }

    /**
     * @param applicationContext:context              of current application
     * @param legalRepresentative:legalRepresentative object
     *                                                Open show details Screen for (LegalRepresentatives)
     */
    public static void openLegalRepresentativesShowDetailsActivity(Context applicationContext, LegalRepresentative legalRepresentative) {
        intent = new Intent(applicationContext, LegalRepresentativesShowDetailsActivity.class);
        gson = new Gson();
        String ObjectAsStr = gson.toJson(legalRepresentative);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("object", ObjectAsStr);
        intent.putExtra("objectType", "LegalRepresentative");
        applicationContext.startActivity(intent);
    }

    /**
     * @param applicationContext:context           of current application
     * @param managementMember:legalRepresentative object
     *                                             Open show details Screen for (GeneralManagers)
     */
    public static void openGeneralManagersShowDetailsActivity(Context applicationContext, ManagementMember managementMember) {
        intent = new Intent(applicationContext, LegalRepresentativesShowDetailsActivity.class);
        gson = new Gson();
        String ObjectAsStr = gson.toJson(managementMember);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("object", ObjectAsStr);
        intent.putExtra("objectType", "ManagementMember");
        applicationContext.startActivity(intent);
    }

    /**
     * @param applicationContext:context of current application
     * @param shareHolder:ShareOwnership object
     *                                   Open show details Screen for (Shareholders)
     */
    public static void openShareHolderShowDetailsActivity(Context applicationContext, ShareOwnership shareHolder) {
        intent = new Intent(applicationContext, LegalRepresentativesShowDetailsActivity.class);
        gson = new Gson();
        String ObjectAsStr = gson.toJson(shareHolder);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("object", ObjectAsStr);
        intent.putExtra("objectType", "ShareHolder");
        applicationContext.startActivity(intent);
    }

    /**
     * @param applicationContext:context of current application
     * @param contract_dwc__c:contract   object
     *                                   Open show details Screen for (contracts) in leasing info screen
     */
    public static void openLeasingShowDetailsActivity(Context applicationContext, Contract_DWC__c contract_dwc__c) {
        intent = new Intent(applicationContext, LeasingShowDetailsActivity.class);
        gson = new Gson();
        String ObjectAsStr = gson.toJson(contract_dwc__c);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("object", ObjectAsStr);
        applicationContext.startActivity(intent);
    }

    public static void openRenewContractServiceActivity(Context applicationContext,Contract_DWC__c contract_dwc__c) {
        intent = new Intent(applicationContext, RenewContractServiceActivity.class);
        gson = new Gson();
        String ObjectAsStr = gson.toJson(contract_dwc__c);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("object", ObjectAsStr);
        applicationContext.startActivity(intent);

    }
}
