package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import RestAPI.RelatedServiceType;
import cloudconcept.dwc.R;
import model.Card_Management__c;
import model.FormField;
import model.NOC__c;
import utilities.Utilities;

/**
 * Created by Abanoub Wagdy on 10/26/2015.
 */
public class GenericPayAndSubmitFragment extends Fragment {

    LinearLayout linearLayout;
    RelatedServiceType relatedServiceType;
    String personName;
    String RefNumber;
    String Date;
    String Status;
    String TotalAmount;
    ArrayList<FormField> formFields;
    Card_Management__c card_management__c;
    NOC__c noc__c;

    public static GenericPayAndSubmitFragment newInstance(RelatedServiceType relatedServiceType, String personName, String RefNumber, String Date, String Status, String TotalAmount, ArrayList<FormField> formFields, Card_Management__c card_management__c,
                                                          NOC__c noc__c) {
        GenericPayAndSubmitFragment fragment = new GenericPayAndSubmitFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("relatedServiceType", relatedServiceType);
        bundle.putString("personName", personName);
        bundle.putString("RefNumber", RefNumber);
        bundle.putString("Date", Date);
        bundle.putString("Status", Status);
        bundle.putString("TotalAmount", TotalAmount);
        bundle.putSerializable("formFields", formFields);
        bundle.putSerializable("CardObject", card_management__c);
        bundle.putSerializable("NOCObject", noc__c);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.company_info, container, false);
        linearLayout = (LinearLayout) view.findViewById(R.id.linear);
        relatedServiceType = (RelatedServiceType) getArguments().get("relatedServiceType");
        personName = getArguments().getString("personName");
        RefNumber = getArguments().getString("RefNumber");
        Date = getArguments().getString("Date");
        Status = getArguments().getString("Status");
        TotalAmount = getArguments().getString("TotalAmount");
        formFields = (ArrayList<FormField>) getArguments().get("formFields");
        card_management__c = (Card_Management__c) getArguments().get("CardObject");
        noc__c = (NOC__c) getArguments().get("NOCObject");
        View inflatedView = Utilities.DrawGenericPayAndSubmitView(getActivity(), relatedServiceType, personName, RefNumber, Date, Status, TotalAmount, formFields, card_management__c, noc__c);
        linearLayout.removeAllViews();
        linearLayout.addView(inflatedView);
        return view;
    }
}
