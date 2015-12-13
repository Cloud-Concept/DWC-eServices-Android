package cloudconcept.dwc;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.samsung.android.sdk.look.cocktailbar.SlookCocktailManager;
import com.samsung.android.sdk.look.cocktailbar.SlookCocktailProvider;

import cloudconcept.dwc.R;
import fragmentActivity.BaseActivityLauncher;
import fragmentActivity.CardActivity;
import fragmentActivity.CompanyNocActivity;
import utilities.AdapterConfiguration;

public class CocktailSampleProvider extends SlookCocktailProvider {
    @Override
    public void onUpdate(Context context, SlookCocktailManager cocktailBarManager, int[] cocktailIds) {
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.sample_panel);
        String str = context.getResources().getString(R.string.vertical_text);
        rv.setTextViewText(R.id.text, str);
        setPendingIntent(context, rv);
        for (int i = 0; i < cocktailIds.length; i++) {
            cocktailBarManager.updateCocktail(cocktailIds[i], rv);
        }
    }

    private void setPendingIntent(Context applicationContext, RemoteViews rv) {
        setPendingIntent(applicationContext, R.id.btn_newNOC, new Intent(applicationContext, EmployeeListActivity.class), rv);
        setPendingIntent(applicationContext, R.id.btn_newCompanyNOC, new Intent(applicationContext, CompanyNocActivity.class), rv);
        Intent intent = new Intent(applicationContext, CardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("type", "1");
        setPendingIntent(applicationContext, R.id.btn_newCard, intent, rv);

        Intent intentNotifications = new Intent(applicationContext, BaseActivityLauncher.class);
        intentNotifications.putExtra("ScreenTag", AdapterConfiguration.NOTIFICATIONS_SCREEN);
        intentNotifications.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intentNotifications.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        applicationContext.startActivity(intentNotifications);
        setPendingIntent(applicationContext, R.id.btn_notifications, intentNotifications, rv);
    }

    private void setPendingIntent(Context context, int rscId, Intent intent, RemoteViews rv) {
        PendingIntent itemClickPendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        rv.setOnClickPendingIntent(rscId, itemClickPendingIntent);
    }
}
