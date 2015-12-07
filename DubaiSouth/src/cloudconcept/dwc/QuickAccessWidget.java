package cloudconcept.dwc;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.salesforce.androidsdk.app.SalesforceSDKManager;
import com.salesforce.androidsdk.rest.ClientManager;
import com.salesforce.androidsdk.rest.RestClient;
import com.salesforce.androidsdk.rest.RestRequest;
import com.salesforce.androidsdk.rest.RestResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import RestAPI.SFResponseManager;
import RestAPI.SoqlStatements;
import fragmentActivity.CardActivity;
import fragmentActivity.CompanyNocActivity;
import model.NotificationManagement;
import model.User;
import utilities.CallType;
import utilities.StoreData;
import utilities.Utilities;


/**
 * Implementation of Quick Access Widget functionality.
 */
public class QuickAccessWidget extends AppWidgetProvider {

    private static String soqlQuery;
    private static RestRequest restRequest;
    static LinearLayout linearNotifications;
    static String[] services = new String[]{"Visa Services", "NOC Services", "License Services", "Access Card Services", "Registration Services", "Leasing Services"};
    private static User _user;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.quick_access_widget);
        String data = new StoreData(context).getUserDataAsString();
        Gson gson = new Gson();
        _user = gson.fromJson(data, User.class);
        CallNotificationWebService(context, views, appWidgetManager, appWidgetId);
    }

    private static void CallNotificationWebService(final Context context, final RemoteViews views, final AppWidgetManager appWidgetManager, final int appWidgetId) {

        if (!new StoreData(context).getUserDataAsString().equals("")) {
            soqlQuery = SoqlStatements.getInstance().constructNotificationsServiceQuery(_user.get_contact().get_account().getID(), 2, 0);
            new ClientManager(context, SalesforceSDKManager.getInstance().getAccountType(), SalesforceSDKManager.getInstance().getLoginOptions(), SalesforceSDKManager.getInstance().shouldLogoutWhenTokenRevoked()).getRestClient((Activity) context, new ClientManager.RestClientCallback() {
                @Override
                public void authenticatedRestClient(RestClient client) {
                    if (client == null) {
                        SalesforceSDKManager.getInstance().logout((Activity) context);
                        return;
                    } else {
                        try {
                            restRequest = RestRequest.getRequestForQuery(
                                    context.getString(R.string.api_version), soqlQuery);
                            client.sendAsync(restRequest, new RestClient.AsyncRequestCallback() {
                                @Override
                                public void onSuccess(RestRequest request, RestResponse result) {
                                    ArrayList<NotificationManagement> notificationManagements = SFResponseManager.parseNotificationsResponse(result.toString());
                                    views.setViewVisibility(R.id.tvLoading, View.GONE);
                                    ConstructView(notificationManagements, views, context);

                                    Intent NewEmployeeNOCIntent = new Intent(context, EmployeeListActivity.class);
                                    PendingIntent pendingIntentEmployeeNOC = PendingIntent.getActivity(context, 0, NewEmployeeNOCIntent, 0);
                                    views.setOnClickPendingIntent(R.id.action_newEmployeeNOc, pendingIntentEmployeeNOC);

                                    Intent NewCompanyNOCIntent = new Intent(context, CompanyNocActivity.class);
                                    PendingIntent pendingIntentCompanyNOC = PendingIntent.getActivity(context, 1, NewCompanyNOCIntent, 0);
                                    views.setOnClickPendingIntent(R.id.action_newCompanyNOc, pendingIntentCompanyNOC);

                                    Intent NewCardIntent = new Intent(context, CardActivity.class);
                                    NewCardIntent.putExtra("type", "1");
                                    PendingIntent pendingIntentNewCard = PendingIntent.getActivity(context, 2, NewCardIntent, 0);
                                    views.setOnClickPendingIntent(R.id.action_newCard, pendingIntentNewCard);

                                    // Instruct the widget manager to update the widget
                                    appWidgetManager.updateAppWidget(appWidgetId, views);
                                }

                                @Override
                                public void onError(Exception exception) {

                                }
                            });
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    private static void ConstructView(final ArrayList<NotificationManagement> notificationManagements, RemoteViews views, Context context) {

        if (notificationManagements.size() > 1) {
            DrawView(true, true, notificationManagements, views, context);
        } else if (notificationManagements.size() == 1) {
            DrawView(true, false, notificationManagements, views, context);
        } else {
            DrawView(false, false, notificationManagements, views, context);
        }
    }

    private static void DrawView(boolean firstVisibility, boolean secondVisibility, ArrayList<NotificationManagement> notificationManagements, RemoteViews views, Context context) {

        if (firstVisibility) {
            views.setTextViewText(R.id.tvNotificationMessage, notificationManagements.get(0).getMobile_Compiled_Message());
            String pattern = "yyyy-MM-dd'T'HH:mm:ss";
            SimpleDateFormat dtf = new SimpleDateFormat(pattern);
            try {
                Date dateTime = dtf.parse(Utilities.stringNotNull(notificationManagements.get(0).getCreatedDate()));
                pattern = "dd-MMM-yyyy hh:mm a";
                dtf = new SimpleDateFormat(pattern);
                views.setTextViewText(R.id.tvDate, dtf.format(GMTToCVT(dateTime)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (notificationManagements.get(0).getCase_Process_Name().equals(services[0])) {
                views.setImageViewResource(R.id.imageNotification1, R.mipmap.notification_visa);
            } else if (notificationManagements.get(0).getCase_Process_Name().equals(services[1])) {
                views.setImageViewResource(R.id.imageNotification1, R.mipmap.notification_noc);
            } else if (notificationManagements.get(0).getCase_Process_Name().equals(services[2])) {
                views.setImageViewResource(R.id.imageNotification1, R.mipmap.renew_license);
            } else if (notificationManagements.get(0).getCase_Process_Name().equals(services[3])) {
                views.setImageViewResource(R.id.imageNotification1, R.mipmap.notification_card_icon);
            } else if (notificationManagements.get(0).getCase_Process_Name().equals(services[4])) {
                views.setImageViewResource(R.id.imageNotification1, R.mipmap.notification_registration);
            } else if (notificationManagements.get(0).getCase_Process_Name().equals(services[5])) {
                views.setImageViewResource(R.id.imageNotification1, R.mipmap.notification_leasing);
            }
        } else {
            views.setViewVisibility(R.id.tvNotificationMessage, View.GONE);
            views.setViewVisibility(R.id.tvDate, View.GONE);
            views.setViewVisibility(R.id.imageNotification1, View.GONE);
        }

        if (secondVisibility) {
            views.setTextViewText(R.id.tvNotificationMessage2, notificationManagements.get(1).getMobile_Compiled_Message());
            String pattern = "yyyy-MM-dd'T'HH:mm:ss";
            SimpleDateFormat dtf = new SimpleDateFormat(pattern);
            try {
                Date dateTime = dtf.parse(Utilities.stringNotNull(notificationManagements.get(1).getCreatedDate()));
                pattern = "dd-MMM-yyyy hh:mm a";
                dtf = new SimpleDateFormat(pattern);
                views.setTextViewText(R.id.tvDate2, dtf.format(GMTToCVT(dateTime)));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (notificationManagements.get(1).getCase_Process_Name().equals(services[0])) {
                views.setImageViewResource(R.id.imageNotification2, R.mipmap.notification_visa);
            } else if (notificationManagements.get(1).getCase_Process_Name().equals(services[1])) {
                views.setImageViewResource(R.id.imageNotification2, R.mipmap.notification_noc);
            } else if (notificationManagements.get(1).getCase_Process_Name().equals(services[2])) {
                views.setImageViewResource(R.id.imageNotification2, R.mipmap.renew_license);
            } else if (notificationManagements.get(1).getCase_Process_Name().equals(services[3])) {
                views.setImageViewResource(R.id.imageNotification2, R.mipmap.notification_card_icon);
            } else if (notificationManagements.get(1).getCase_Process_Name().equals(services[4])) {
                views.setImageViewResource(R.id.imageNotification2, R.mipmap.notification_registration);
            } else if (notificationManagements.get(1).getCase_Process_Name().equals(services[5])) {
                views.setImageViewResource(R.id.imageNotification2, R.mipmap.notification_leasing);
            }
        } else {
            views.setViewVisibility(R.id.tvNotificationMessage2, View.GONE);
            views.setViewVisibility(R.id.tvDate2, View.GONE);
            views.setViewVisibility(R.id.imageNotification2, View.GONE);
        }
    }

    private static Date GMTToCVT(Date date) {
        TimeZone tz = TimeZone.getDefault();
        Date ret = new Date(date.getTime() + tz.getRawOffset());
        if (tz.inDaylightTime(ret)) {
            Date dstDate = new Date(ret.getTime() + tz.getDSTSavings());
            if (tz.inDaylightTime(dstDate)) {
                ret = dstDate;
            }
        }
        return ret;
    }
}