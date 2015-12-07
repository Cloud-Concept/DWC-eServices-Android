package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cloudconcept.dwc.R;

/**
 * Created by Abanoub Wagdy on 10/25/2015.
 */
public class GenericThankYouFragment extends Fragment {

    String caseNumber, TotalAmount, mail;
    TextView txtmsg, txtFee, txtmail;


    public static GenericThankYouFragment newInstance(String caseNumber, String TotalAmount, String mail) {
        GenericThankYouFragment fragment = new GenericThankYouFragment();
        Bundle bundle = new Bundle();
        bundle.putString("caseNumber", caseNumber);
        bundle.putString("TotalAmount", TotalAmount);
        bundle.putString("mail", mail);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thank_you, container, false);
        InitializeView(view);

        caseNumber = getArguments().getString("caseNumber");
        TotalAmount = getArguments().getString("TotalAmount");
        mail = getArguments().getString("mail");

        if (caseNumber != null && !caseNumber.equals("")) {
            txtmsg.setText(String.format(getActivity().getResources().getString(R.string.ServiceThankYouMessage), caseNumber));
        } else {
            txtmsg.setVisibility(View.GONE);
        }

        if (TotalAmount != null && !TotalAmount.equals("")) {
            txtFee.setText(String.format(getActivity().getResources().getString(R.string.ServiceThankYouMessagePayment), TotalAmount));
        } else {
            txtFee.setVisibility(View.GONE);
        }

        if (mail != null && !mail.equals("")) {
            txtmail.setText(String.format(getActivity().getResources().getString(R.string.ServiceThankYouMessageNOCNote), mail));
        } else {
            txtmail.setVisibility(View.GONE);
        }
        return view;
    }

    private void InitializeView(View view) {
        txtmsg = (TextView) view.findViewById(R.id.txtmsg);
        txtFee = (TextView) view.findViewById(R.id.txtfee);
        txtmail = (TextView) view.findViewById(R.id.txtmail);
    }
}
