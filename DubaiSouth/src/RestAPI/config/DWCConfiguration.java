package RestAPI.config;

/**
 * Created by Abanoub Wagdy on 10/20/2015.
 */
public class DWCConfiguration {

    public static final String MOBILE_SERVICE_UTILITY_URL = "/services/apexrest/MobileServiceUtilityWebService";
    public static final String PAY_AND_SUBMIT_WEB_SERVICE = "/services/apexrest/MobilePayAndSubmitWebService";
//    public static final String SUBMIT_AND_PAY_WEB_SERVICE = "/services/apexrest/MobileSubmitAndPayRequestWebService";

    //Create Methods
    public static final String METHOD_NAME_ADDRESS_CHANGE = "CreateRequestAddressChange";
    public static final String METHOD_NAME_NAME_CHANGE = "CreateRequestNameChange";
    public static final String METHOD_NAME_CAPITAL_CHANGE = "CreateRequestCapitalChange";
    public static final String METHOD_NAME_DIRECTOR_REMOVAL = "CreateRequestDirectorRemoval";
    public static final String METHOD_NAME_ESTABLISHMENT_CARD = "CreateEstablishmentCardRequest";
    public static final String METHOD_NAME_PASSPORT_RENEWAL = "CreateRequestPassportRenewal";
    public static final String METHOD_NAME_CHANGE_LICENSE_ACTIVITY = "CreateRequestChangeLicenseActivities";
    public static final String METHOD_NAME_LICENSE_RENEWAL = "CreateRequestLicenseRenewalWithChangeLicenseActivities";
    public static final String METHOD_NAME_SHARE_TRANSFER = "CreateRequestShareTransfer";
    public static final String METHOD_NAME_NAME_RESERVATION= "validateRequestNameReservation";


    //LicenseOperation attributes
    public static final String LICENSE_OPERATION_CHANGE_LICENSE_ACTIVITY = "ChangeInLicenseActivity";
    public static final String LICENSE_OPERATION_LICENSE_RENEWAL = "licenseRenewal";


    //Submit Methods
    public static final String SUBMIT_ADDRESS_CHANGE = "SubmitRequestAddressChange";
    public static final String SUBMIT_NAME_CHANGE = "SubmitRequestNameChange";
    public static final String SUBMIT_CAPITAL_CHANGE = "SubmitRequestCapitalChange";
    public static final String SUBMIT_DIRECTOR_REMOVAL = "SubmitRequestDirectorRemoval";
    public static final String SUBMIT_ESTABLISHMENT_CARD = "SubmitRequestEstablishmentCard";
    public static final String SUBMIT_PASSPORT_RENEWAL = "SubmitRequestPassportRenewal";
    public static final String SUBMIT_CHANGE_LICENSE_ACTIVITY = "SubmitRequestChangeLicenseActivities";
    public static final String SUBMIT_LICENSE_RENEWAL = "SubmitRequestLicenseRenewalWithChangeLicenseActivities";
    public static final String SUBMIT_SHARE_TRANSFER = "SubmitRequestShareTransfer";
    public static final String SUBMIT_VISA_CANCELLATION = "SubmitRequestVisaCancellation";
    public static final String SUBMIT_NAME_RESERVATION = "SubmitRequestNameReservation";

    //Service Identifiers
    public static final String RENEW_CARD_SERVICE_IDENTIFIER = "Establishment Card Renewal Fee";
    public static final String LOST_CARD_SERVICE_IDENTIFIER = "Establishment Card Lost Fee";
    public static final String CANCEL_CARD_SERVICE_IDENTIFIER = "Establishment Card Cancellation Fee";
}
