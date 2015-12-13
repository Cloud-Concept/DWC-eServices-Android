package cloudconcept.dwc;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

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

    private String soqlQuery;
    private RestRequest restRequest;
    static LinearLayout linearNotifications;
    static String[] services = new String[]{"Visa Services", "NOC Services", "License Services", "Access Card Services", "Registration Services", "Leasing Services"};
    private static User _user;
    private URI theUrl;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
    }

    void updateAppWidget(final Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {

        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.quick_access_widget);
        String data = new StoreData(context).getUserDataAsString();
        Gson gson = new Gson();
        _user = gson.fromJson(data, User.class);
        CallNotificationWebService(context, views, appWidgetManager, appWidgetId);
//        "http://chart.googleapis.com/chart?cht=p3&chs=250x100&chd=t:60,40&chl=Hello|World";
//        try {
//            new ImageDownloader(views, "https://chart.googleapis.com/chart?chxt=x,x,y,y&cht=bvs&chd=s:c9ucD&chls=2.0&chs=250x125&chxl=1:|Martinis|3:|Score&chxp=1,50|3,50").execute().get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }

//        webView.setWebViewClient(new WebViewClient() {
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                Picture picture = view.capturePicture();
//                Bitmap b = Bitmap.createBitmap(
//                        picture.getWidth(), picture.getHeight(), Bitmap.Config.ARGB_8888);
//                Canvas c = new Canvas(b);
//                picture.draw(c);
//
//                FileOutputStream fos = null;
//                try {
//                    fos = new FileOutputStream("/sdcard/" + Utilities.getCurrentDate());
//                    if (fos != null) {
//                        b.compress(Bitmap.CompressFormat.JPEG, 90, fos);
//                        fos.close();
//                    }
//                    views.setImageViewBitmap(R.id.imageDashboard, b);
//                } catch (Exception e) {
//                    System.out.println("-----error--" + e);
//                }
//            }
//        });
//
//        webView.loadUrl("https://chart.googleapis.com/chart?cht=p3&chs=250x100&chd=t:60,40&chl=Hello|World");


//        new ClientManager(context, SalesforceSDKManager.getInstance().getAccountType(), SalesforceSDKManager.getInstance().getLoginOptions(), SalesforceSDKManager.getInstance().shouldLogoutWhenTokenRevoked()).getRestClient((Activity) context, new ClientManager.RestClientCallback() {
//
//            @Override
//            public void authenticatedRestClient(RestClient client) {
//                if (client == null) {
//                    return;
//                } else {
//                    String attUrl = client.getClientInfo().resolveUrl("/apex/DWCPortal_DashboardMobile").toString();
//                    String url = attUrl + "?sid=" + client.getRefreshToken() + "&accountId=" + _user.get_contact().get_account().getID() + "&authToken=" + client.getAuthToken() + "&client_id=" + "3MVG9sLbBxQYwWqsa8dlM02Kaxs1PH5oIbF_o2Y4FfeOoF.1__CEjo_6_fIBta82uOIvzvuJ9tVtR1iU9PbtA" + "&client_secret=3MVG9sLbBxQYwWqsa8dlM02Kaxs1PH5oIbF_o2Y4FfeOoF.1__CEjo_6_fIBta82uOIvzvuJ9tVtR1iU9PbtA";
//                    HttpClient tempClient = new DefaultHttpClient();
//                    try {
//                        theUrl = new URI(url);
//                    } catch (URISyntaxException e) {
//                        e.printStackTrace();
//                    }
//
//                    WebView webView = new WebView(context);
//                    webView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
//                    webView.getSettings().setSupportZoom(true);
//                    webView.getSettings().setBuiltInZoomControls(true);
//                    webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
//                    webView.setScrollbarFadingEnabled(true);
//                    webView.getSettings().setLoadsImagesAutomatically(true);
//                    final WebSettings webSettings = webView.getSettings();
//                    webSettings.setJavaScriptEnabled(true);
//                    webSettings.setAllowFileAccessFromFileURLs(true);
//                    webSettings.setDatabaseEnabled(true);
//                    webSettings.setDomStorageEnabled(true);
//
//                    webView.setWebViewClient(new WebViewClient() {
//
//                        @Override
//                        public void onPageFinished(WebView view, String url) {
//                            Picture picture = view.capturePicture();
//                            Bitmap b = Bitmap.createBitmap(
//                                    picture.getWidth(), picture.getHeight(), Bitmap.Config.ARGB_8888);
//                            Canvas c = new Canvas(b);
//                            picture.draw(c);
//
//                            FileOutputStream fos = null;
//                            try {
//                                fos = new FileOutputStream("/sdcard/" + Utilities.getCurrentDate());
//                                if (fos != null) {
//                                    b.compress(Bitmap.CompressFormat.JPEG, 90, fos);
//                                    fos.close();
//                                }
//                                views.setImageViewBitmap(R.id.imageDashboard, b);
//                            } catch (Exception e) {
//                                System.out.println("-----error--" + e);
//                            }
//                        }
//                    });
//                    try {
//                        webView.loadUrl(theUrl.toURL().toString());
//                    } catch (MalformedURLException e) {
//                        e.printStackTrace();
//                    }
//                    if (Utilities.getIsProgressLoading()) {
//                        Utilities.dismissLoadingDialog();
//                    }
//                }
//            }
//        });
    }

    private void CallNotificationWebService(final Context context, final RemoteViews views, final AppWidgetManager appWidgetManager, final int appWidgetId) {

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

    private void ConstructView(final ArrayList<NotificationManagement> notificationManagements, RemoteViews views, Context context) {

        if (notificationManagements.size() > 1) {
            DrawView(true, true, notificationManagements, views, context);
        } else if (notificationManagements.size() == 1) {
            DrawView(true, false, notificationManagements, views, context);
        } else {
            DrawView(false, false, notificationManagements, views, context);
        }
    }

    private void DrawView(boolean firstVisibility, boolean secondVisibility, ArrayList<NotificationManagement> notificationManagements, RemoteViews views, Context context) {

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


//    private class ImageDownloader extends AsyncTask<Void, Void, Void> {
//
//        RemoteViews views;
//        Bitmap bitmap;
//        String url;
//
//        public ImageDownloader(RemoteViews views, String url) {
//            this.views = views;
//            this.url = url;
//        }
//
//        @Override
//        protected Void doInBackground(Void[] params) {
////            bitmap = downloadBitmap(this.url);
//            bitmap = loadChart(this.url);
//            return null;
//        }
//
//        private Bitmap loadChart(String urlRqs) {
//            Bitmap bm = null;
//            InputStream inputStream = null;
//
//            try {
//                inputStream = OpenHttpConnection(urlRqs);
//                bm = BitmapFactory.decodeStream(inputStream);
//                inputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            return bm;
//        }
//
//        private InputStream OpenHttpConnection(String strURL) throws IOException {
//
//            InputStream is = null;
//            URL url = new URL(strURL);
//            URLConnection urlConnection = url.openConnection();
//
//            try {
//                HttpURLConnection httpConn = (HttpURLConnection) urlConnection;
//                httpConn.setRequestMethod("GET");
//                httpConn.connect();
//
//                if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                    is = httpConn.getInputStream();
//                }
//            } catch (Exception ex) {
//                Log.e("###", "", ex);
//            }
//
//            return is;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            views.setImageViewBitmap(R.id.imageDashboard, bitmap);
//        }
//
////        private Bitmap downloadBitmap(String url) {
////            final DefaultHttpClient client = new DefaultHttpClient();
////
////            //forming a HttoGet request
////            final HttpGet getRequest = new HttpGet(url);
////            try {
////                HttpResponse response = client.execute(getRequest);
////                final int statusCode = response.getStatusLine().getStatusCode();
////
////                if (statusCode != HttpStatus.SC_OK) {
////                    Log.w("ImageDownloader", "Error " + statusCode +
////                            " while retrieving bitmap from " + url);
////                    return null;
////
////                }
////
////                final HttpEntity entity = response.getEntity();
////                if (entity != null) {
////                    InputStream inputStream = null;
////                    try {
////                        // getting contents from the stream
////                        inputStream = entity.getContent();
////
////                        // decoding stream data back into image Bitmap that android understands
////                        final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
////                        return bitmap;
////                    } finally {
////                        if (inputStream != null) {
////                            inputStream.close();
////                        }
////                        entity.consumeContent();
////                    }
////                }
////            } catch (Exception e) {
////                // You Could provide a more explicit error message for IOException
////                getRequest.abort();
////                Log.e("ImageDownloader", "Something went wrong while" +
////                        " retrieving bitmap from " + url + e.toString());
////            }
////
////            return null;
////        }
//    }
}