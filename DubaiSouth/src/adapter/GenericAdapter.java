package adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cloudconcept.dwc.R;
import custom.DWCRoundedImageView;
import custom.ExpandableLayoutItem;
import custom.HorizontalListView;
import utilities.StoreData;
import model.Card_Management__c;
import model.Contract_DWC__c;
import model.Directorship;
import model.LegalRepresentative;
import model.ManagementMember;
import model.ServiceItem;
import model.ShareOwnership;
import model.User;
import model.Visa;
import utilities.AdapterConfiguration;
import utilities.Utilities;

/**
 * Created by Abanoub Wagdy on 10/20/2015.
 */
public class GenericAdapter extends BaseAdapter {

    private Activity activity;
    private List<?> objects;
    String ScreenTag;

    public GenericAdapter(Activity activity, List<?> objects, String ScreenTAG) {
        this.activity = activity;
        this.objects = objects;
        this.ScreenTag = ScreenTAG;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        VisaViewHolder holder = null;
        AccessCardViewHolder accessCardViewHolder = null;
        DirectorViewHolder directorViewHolder = null;
        GeneralManagerViewHolder generalManagerViewHolder = null;
        LegalRepresentativeViewHolder legalRepresentativeViewHolder = null;
        ShareHoldersViewHolder shareHoldersViewHolder = null;
        ContractViewHolder contractViewHolder = null;

        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (ScreenTag.equals(AdapterConfiguration.Permanent_Employee_TAG) || ScreenTag.equals(AdapterConfiguration.VISIT_VISA_TAG)) {
                holder = new VisaViewHolder();
                convertView = li.inflate(R.layout.item_row_permanent_employee, parent, false);
                holder.item = (ExpandableLayoutItem) convertView.findViewById(R.id.expandableLayoutListView);
                RelativeLayout relativeHeader = holder.item.getHeaderLayout();
                holder.tvFullName = (TextView) relativeHeader.findViewById(R.id.tvFullName);
                holder.tvVisaExpiry = (TextView) relativeHeader.findViewById(R.id.tvVisaExpiry);
                holder.tvPassportNumber = (TextView) relativeHeader.findViewById(R.id.tvpassportNumber);
                holder.tvStatus = (TextView) relativeHeader.findViewById(R.id.tvStatus);
                holder.tvVisaExpiryLabel = (TextView) relativeHeader.findViewById(R.id.tvVisaExpiryLabel);
                holder._smartEmployeeImage = (DWCRoundedImageView) relativeHeader.findViewById(R.id.view);
                RelativeLayout relativeContent = holder.item.getContentLayout();
                holder._horizontalListView = (HorizontalListView) relativeContent.findViewById(R.id.horizontalServices);
                convertView.setTag(holder);
            } else if (ScreenTag.equals(AdapterConfiguration.Access_Card_TAG)) {
                accessCardViewHolder = new AccessCardViewHolder();
                convertView = li.inflate(R.layout.item_row_access_card, parent, false);
                accessCardViewHolder.item = (ExpandableLayoutItem) convertView.findViewById(R.id.expandableLayoutListView);
                RelativeLayout relativeHeader = accessCardViewHolder.item.getHeaderLayout();
                accessCardViewHolder.tvFullName = (TextView) relativeHeader.findViewById(R.id.tvFullName);
                accessCardViewHolder.tvType = (TextView) relativeHeader.findViewById(R.id.tvType);
                accessCardViewHolder.tvPassportNumber = (TextView) relativeHeader.findViewById(R.id.tvpassportNumber);
                accessCardViewHolder.tvStatus = (TextView) relativeHeader.findViewById(R.id.tvStatus);
                accessCardViewHolder.tvCardExpiry = (TextView) relativeHeader.findViewById(R.id.tvCardExpiry);
                accessCardViewHolder._smartEmployeeImage = (DWCRoundedImageView) relativeHeader.findViewById(R.id.view);
                RelativeLayout relativeContent = accessCardViewHolder.item.getContentLayout();
                accessCardViewHolder._horizontalListView = (HorizontalListView) relativeContent.findViewById(R.id.horizontalServices);
                convertView.setTag(accessCardViewHolder);
            } else if (ScreenTag.equals(AdapterConfiguration.DIRECTORS_TAG)) {
                directorViewHolder = new DirectorViewHolder();
                convertView = li.inflate(R.layout.directors_item, parent, false);
                directorViewHolder.item = (ExpandableLayoutItem) convertView.findViewById(R.id.expandableLayoutListView);
                RelativeLayout relativeHeader = directorViewHolder.item.getHeaderLayout();
                directorViewHolder.tvFullName = (TextView) relativeHeader.findViewById(R.id.tvFullName);
                directorViewHolder.tvNationality = (TextView) relativeHeader.findViewById(R.id.tvNationality);
                directorViewHolder.tvPassportNumber = (TextView) relativeHeader.findViewById(R.id.tvpassportNumber);
                directorViewHolder.tvRole = (TextView) relativeHeader.findViewById(R.id.tvRole);
                directorViewHolder.tvStartDate = (TextView) relativeHeader.findViewById(R.id.tvStartDate);
                directorViewHolder._smartEmployeeImage = (DWCRoundedImageView) relativeHeader.findViewById(R.id.view);
                RelativeLayout relativeContent = directorViewHolder.item.getContentLayout();
                directorViewHolder._horizontalListView = (HorizontalListView) relativeContent.findViewById(R.id.horizontalServices);
                convertView.setTag(directorViewHolder);
            } else if (ScreenTag.equals(AdapterConfiguration.GENERAL_MANAGERS_TAG)) {
                generalManagerViewHolder = new GeneralManagerViewHolder();
                convertView = li.inflate(R.layout.general_managers_whole_item, parent, false);
                generalManagerViewHolder.item = (ExpandableLayoutItem) convertView.findViewById(R.id.expandableLayoutListView);
                RelativeLayout relativeHeader = generalManagerViewHolder.item.getHeaderLayout();
                generalManagerViewHolder.tvFullName = (TextView) relativeHeader.findViewById(R.id.tvFullName);
                generalManagerViewHolder.tvNationality = (TextView) relativeHeader.findViewById(R.id.tvNationality);
                generalManagerViewHolder.tvPassportNumber = (TextView) relativeHeader.findViewById(R.id.tvpassportNumber);
                generalManagerViewHolder.tvRole = (TextView) relativeHeader.findViewById(R.id.tvRole);
                generalManagerViewHolder.tvStartDate = (TextView) relativeHeader.findViewById(R.id.tvStartDate);
                generalManagerViewHolder._smartEmployeeImage = (DWCRoundedImageView) relativeHeader.findViewById(R.id.view);
                RelativeLayout relativeContent = generalManagerViewHolder.item.getContentLayout();
                generalManagerViewHolder.horizontalListView = (HorizontalListView) relativeContent.findViewById(R.id.horizontalServices);
                convertView.setTag(generalManagerViewHolder);
            } else if (ScreenTag.equals(AdapterConfiguration.LEGAL_REPRESENTATIVES_TAG)) {
                legalRepresentativeViewHolder = new LegalRepresentativeViewHolder();
                convertView = li.inflate(R.layout.legal_representatives_item, parent, false);
                legalRepresentativeViewHolder.item = (ExpandableLayoutItem) convertView.findViewById(R.id.expandableLayoutListView);
                RelativeLayout relativeHeader = legalRepresentativeViewHolder.item.getHeaderLayout();
                legalRepresentativeViewHolder.tvFullName = (TextView) relativeHeader.findViewById(R.id.tvFullName);
                legalRepresentativeViewHolder.tvNationality = (TextView) relativeHeader.findViewById(R.id.tvNationality);
                legalRepresentativeViewHolder.tvPassportNumber = (TextView) relativeHeader.findViewById(R.id.tvpassportNumber);
                legalRepresentativeViewHolder.tvRole = (TextView) relativeHeader.findViewById(R.id.tvRole);
                legalRepresentativeViewHolder.tvStartDate = (TextView) relativeHeader.findViewById(R.id.tvStartDate);
                legalRepresentativeViewHolder._smartEmployeeImage = (DWCRoundedImageView) relativeHeader.findViewById(R.id.view);
                RelativeLayout relativeContent = legalRepresentativeViewHolder.item.getContentLayout();
                legalRepresentativeViewHolder._horizontalServices = (HorizontalListView) relativeContent.findViewById(R.id.horizontalServices);
                convertView.setTag(legalRepresentativeViewHolder);
            } else if (ScreenTag.equals(AdapterConfiguration.SHAREHOLDERS_Employee_TAG)) {
                shareHoldersViewHolder = new ShareHoldersViewHolder();
                convertView = li.inflate(R.layout.shareholder_item, parent, false);
                shareHoldersViewHolder.item = (ExpandableLayoutItem) convertView.findViewById(R.id.expandableLayoutListView);
                RelativeLayout relativeHeader = shareHoldersViewHolder.item.getHeaderLayout();
                shareHoldersViewHolder.tvFullName = (TextView) relativeHeader.findViewById(R.id.tvFullName);
                shareHoldersViewHolder.tvNationality = (TextView) relativeHeader.findViewById(R.id.tvNationality);
                shareHoldersViewHolder.tvPassportNumber = (TextView) relativeHeader.findViewById(R.id.tvpassportNumber);
                shareHoldersViewHolder.tvOwnerShip = (TextView) relativeHeader.findViewById(R.id.tvOwnership);
                shareHoldersViewHolder.tvNumberOfShares = (TextView) relativeHeader.findViewById(R.id.tvNumberOfShares);
                shareHoldersViewHolder.tvStartDate = (TextView) relativeHeader.findViewById(R.id.tvStartDate);
                shareHoldersViewHolder._smartEmployeeImage = (DWCRoundedImageView) relativeHeader.findViewById(R.id.view);
                RelativeLayout relativeContent = shareHoldersViewHolder.item.getContentLayout();
                shareHoldersViewHolder._horizontalListView = (HorizontalListView) relativeContent.findViewById(R.id.horizontalServices);
                convertView.setTag(shareHoldersViewHolder);
            } else if (ScreenTag.equals(AdapterConfiguration.LEASING_TAG)) {
                contractViewHolder = new ContractViewHolder();
                convertView = li.inflate(R.layout.leasing_info_item, parent, false);
                contractViewHolder.item = (ExpandableLayoutItem) convertView.findViewById(R.id.expandableLayoutListView);
                RelativeLayout relativeHeader = contractViewHolder.item.getHeaderLayout();
                contractViewHolder.tvContractName = (TextView) relativeHeader.findViewById(R.id.tvContractName);
                contractViewHolder.tvUnitName = (TextView) relativeHeader.findViewById(R.id.tvUnitName);
                contractViewHolder.tvContractType = (TextView) relativeHeader.findViewById(R.id.tvContractType);
                contractViewHolder.tvExpiryDate = (TextView) relativeHeader.findViewById(R.id.tvExpiryDate);
                contractViewHolder.tvStatus = (TextView) relativeHeader.findViewById(R.id.tvStatus);
                contractViewHolder._smartEmployeeImage = (DWCRoundedImageView) relativeHeader.findViewById(R.id.imageView2);
                RelativeLayout relativeContent = contractViewHolder.item.getContentLayout();
                contractViewHolder._horizontalListView = (HorizontalListView) relativeContent.findViewById(R.id.horizontalServices);
                convertView.setTag(contractViewHolder);
            }
        } else {
            if (ScreenTag.equals(AdapterConfiguration.Permanent_Employee_TAG) || ScreenTag.equals(AdapterConfiguration.VISIT_VISA_TAG)) {
                holder = (VisaViewHolder) convertView.getTag();
            } else if (ScreenTag.equals(AdapterConfiguration.Access_Card_TAG)) {
                accessCardViewHolder = (AccessCardViewHolder) convertView.getTag();
            } else if (ScreenTag.equals(AdapterConfiguration.DIRECTORS_TAG)) {
                directorViewHolder = (DirectorViewHolder) convertView.getTag();
            } else if (ScreenTag.equals(AdapterConfiguration.GENERAL_MANAGERS_TAG)) {
                generalManagerViewHolder = (GeneralManagerViewHolder) convertView.getTag();
            } else if (ScreenTag.equals(AdapterConfiguration.LEGAL_REPRESENTATIVES_TAG)) {
                legalRepresentativeViewHolder = (LegalRepresentativeViewHolder) convertView.getTag();
            } else if (ScreenTag.equals(AdapterConfiguration.SHAREHOLDERS_Employee_TAG)) {
                shareHoldersViewHolder = (ShareHoldersViewHolder) convertView.getTag();
            } else if (ScreenTag.equals(AdapterConfiguration.LEASING_TAG)) {
                contractViewHolder = (ContractViewHolder) convertView.getTag();
            }
        }

        if (ScreenTag.equals(AdapterConfiguration.Permanent_Employee_TAG) || ScreenTag.equals(AdapterConfiguration.VISIT_VISA_TAG)) {
            Visa mo = (Visa) objects.get(position);

            holder.tvFullName.setText(mo.getApplicant_Full_Name__c());
            holder.tvStatus.setText(mo.getVisa_Validity_Status__c());
            holder.tvPassportNumber.setText(mo.getPassport_Number__c());
            if (mo.getPersonal_Photo__c() != null && !mo.getPersonal_Photo__c().equals(""))
                Utilities.setUserPhoto(activity, mo.getPersonal_Photo__c(), holder._smartEmployeeImage);

            ArrayList<ServiceItem> _items = new ArrayList<ServiceItem>();

            if (ScreenTag.equals(AdapterConfiguration.Permanent_Employee_TAG)) {

                if (mo.getVisa_Validity_Status__c().equals("Issued")) {
                    holder.tvVisaExpiry.setText(Utilities.formatVisitVisaDate(mo.getVisa_Expiry_Date__c()));
                    _items.add(new ServiceItem("New NOC", R.mipmap.noc_service_image));
                } else {
                    holder.tvVisaExpiry.setVisibility(View.GONE);
                    holder.tvVisaExpiryLabel.setVisibility(View.GONE);
                }

                User user = new Gson().fromJson(new StoreData(activity.getApplicationContext()).getUserDataAsString(), User.class);
                boolean manager = mo.getVisa_Holder__c().equals(user.get_contact().get_account().getID());
                if ((mo.getVisa_Validity_Status__c().equals("Issued") || mo.getVisa_Validity_Status__c().equals("Expired"))
                        &&
                        (mo.getVisa_Type__c().equals("Employment") || mo.getVisa_Type__c().equals("Transfer - Internal") || mo.getVisa_Type__c().equals("Transfer - External"))) {
                    _items.add(new ServiceItem("Renew Visa", R.mipmap.renew_visa));
                }

                if (mo.getVisa_Validity_Status__c().equals("Issued")) {
                    _items.add(new ServiceItem("Renew Passport", R.mipmap.noc_service_image));
                }

                if ((mo.getVisa_Validity_Status__c().equals("Issued") || mo.getVisa_Validity_Status__c().equals("Under Process") || mo.getVisa_Validity_Status__c().equals("Under Renewal")) && (mo.getVisa_Type__c().equals("Employment") || mo.getVisa_Type__c().equals("Transfer - Internal") || mo.getVisa_Type__c().equals("Transfer - External")) && !manager) {
                    _items.add(new ServiceItem("Cancel Visa", R.mipmap.cancel_visa));
                }

                _items.add(new ServiceItem("Show Details", R.mipmap.service_show_details));

            } else if (ScreenTag.equals(AdapterConfiguration.VISIT_VISA_TAG)) {
                User user = new Gson().fromJson(new StoreData(activity.getApplicationContext()).getUserDataAsString(), User.class);
                boolean manager = mo.getVisa_Holder__c().equals(user.get_contact().get_account().getID());
                if ((mo.getVisa_Validity_Status__c().equals("Issued") || mo.getVisa_Validity_Status__c().equals("Under Process") || mo.getVisa_Validity_Status__c().equals("Under Renewal")) && (mo.getVisa_Type__c().equals("Employment") || mo.getVisa_Type__c().equals("Visit") || mo.getVisa_Type__c().equals("Transfer - Internal") || mo.getVisa_Type__c().equals("Transfer - External")) && !manager) {
                    _items.add(new ServiceItem("Cancel Visa", R.mipmap.cancel_visa));
                }
                _items.add(new ServiceItem("Show Details", R.mipmap.service_show_details));
            }

            if (_items.size() > 0) {
                holder._horizontalListView.setAdapter(new HorizontalListViewAdapter(mo, activity, activity.getApplicationContext(), _items));
            }
        } else if (ScreenTag.equals(AdapterConfiguration.Access_Card_TAG)) {
            Card_Management__c _cardManagement = (Card_Management__c) objects.get(position);

            accessCardViewHolder.tvFullName.setText(Utilities.stringNotNull(_cardManagement.getFull_Name__c()));
            accessCardViewHolder.tvType.setText(Utilities.stringNotNull(_cardManagement.getCard_Type__c()));
            accessCardViewHolder.tvPassportNumber.setText(Utilities.stringNotNull(_cardManagement.getPassport_Number__c()));
            accessCardViewHolder.tvStatus.setText(Utilities.stringNotNull(_cardManagement.getStatus__c()));
            accessCardViewHolder.tvCardExpiry.setText(_cardManagement.getCard_Expiry_Date__c() == null ? "" : Utilities.formatVisitVisaDate(_cardManagement.getCard_Expiry_Date__c()));
            if (_cardManagement.getPersonal_Photo__c() != null && !_cardManagement.getPersonal_Photo__c().equals(""))
                Utilities.setUserPhoto(activity, _cardManagement.getPersonal_Photo__c(), accessCardViewHolder._smartEmployeeImage);

            ArrayList<ServiceItem> _items = new ArrayList<ServiceItem>();

            Calendar _calendar = Calendar.getInstance();
            if (_cardManagement.getCard_Expiry_Date__c() != null && !_cardManagement.getCard_Expiry_Date__c().equals("")) {
                int DaysToExpire = (int) Utilities.daysDifference(_cardManagement.getCard_Expiry_Date__c());

                if (_cardManagement.getStatus__c().equals("Expired") || (_cardManagement.getStatus__c().equals("Active") && DaysToExpire < 7)) {
                    _items.add(new ServiceItem("Renew Card", R.mipmap.renew_card));
                }
            }

            if (_cardManagement.getStatus__c().equals("Active")) {
                _items.add(new ServiceItem("Cancel Card", R.mipmap.cancel_card));
                _items.add(new ServiceItem("Replace Card", R.mipmap.replace_card));
            }
            _items.add(new ServiceItem("Show Details", R.mipmap.service_show_details));
            accessCardViewHolder._horizontalListView.setAdapter(new HorizontalListViewAdapter(_cardManagement, activity, activity.getApplicationContext(), _items));
        } else if (ScreenTag.equals(AdapterConfiguration.DIRECTORS_TAG)) {
            Directorship _directorship = (Directorship) objects.get(position);
            directorViewHolder.tvFullName.setText(_directorship.get_director().getName() == null ? "" : _directorship.get_director().getName());
            directorViewHolder.tvNationality.setText(_directorship.get_director().getNationality() == null ? "" : _directorship.get_director().getNationality());
            directorViewHolder.tvPassportNumber.setText(_directorship.get_director().get_currentPassport() == null ? "" : _directorship.get_director().get_currentPassport().getName());
            directorViewHolder.tvRole.setText(_directorship.getRoles() == null ? "" : _directorship.getRoles());
            directorViewHolder.tvStartDate.setText(_directorship.getDirectorship_Start_Date() == null ? "" : Utilities.formatVisitVisaDate(_directorship.getDirectorship_Start_Date()));

            ArrayList<ServiceItem> _items = new ArrayList<ServiceItem>();
            _items.add(new ServiceItem("Show Details", R.mipmap.service_show_details));
            if (objects != null && objects.size() > 1) {
                if (!directorViewHolder.tvRole.getText().toString().equals("")) {
                    if (directorViewHolder.tvRole.getText().toString().toLowerCase().contains("director")) {
                        _items.add(new ServiceItem("Remove Director", R.mipmap.remove_directory));
                    }
                }
            }
            Utilities.setUserPhoto(activity, Utilities.stringNotNull(_directorship.get_director().getPersonal_Photo()), directorViewHolder._smartEmployeeImage);
            directorViewHolder._horizontalListView.setAdapter(new HorizontalListViewAdapter(_directorship, activity, activity.getApplicationContext(), _items));
        } else if (ScreenTag.equals(AdapterConfiguration.GENERAL_MANAGERS_TAG)) {
            ManagementMember managementMember = (ManagementMember) objects.get(position);
            generalManagerViewHolder.tvFullName.setText(managementMember.get_manager().getName() == null ? "" : managementMember.get_manager().getName());
            generalManagerViewHolder.tvNationality.setText(managementMember.get_manager().getNationality() == null ? "" : managementMember.get_manager().getNationality());
            generalManagerViewHolder.tvPassportNumber.setText(managementMember.get_manager().getCurrentPassport() == null ? "" : managementMember.get_manager().getCurrentPassport().getName());
            generalManagerViewHolder.tvRole.setText(managementMember.getRole() == null ? "" : managementMember.getRole());
            generalManagerViewHolder.tvStartDate.setText(managementMember.getManager_Start_Date() == null ? "" : Utilities.formatVisitVisaDate(managementMember.getManager_Start_Date()));
            Utilities.setUserPhoto(activity, Utilities.stringNotNull(managementMember.get_manager().getPersonal_Photo()), generalManagerViewHolder._smartEmployeeImage);
            ArrayList<ServiceItem> _items = new ArrayList<ServiceItem>();
            _items.add(new ServiceItem("Show Details", R.mipmap.service_show_details));
            generalManagerViewHolder.horizontalListView.setAdapter(new HorizontalListViewAdapter(managementMember, activity, activity.getApplicationContext(), _items));
        } else if (ScreenTag.equals(AdapterConfiguration.LEGAL_REPRESENTATIVES_TAG)) {
            LegalRepresentative legalRepresentative = (LegalRepresentative) objects.get(position);
            legalRepresentativeViewHolder.tvFullName.setText(legalRepresentative.getLegalRepresentativeLookup().getName() == null ? "" : legalRepresentative.getLegalRepresentativeLookup().getName());
            legalRepresentativeViewHolder.tvNationality.setText(legalRepresentative.getLegalRepresentativeLookup().getNationality() == null ? "" : legalRepresentative.getLegalRepresentativeLookup().getNationality());
            legalRepresentativeViewHolder.tvPassportNumber.setText(legalRepresentative.getLegalRepresentativeLookup().getCurrentPassport() == null ? "" : legalRepresentative.getLegalRepresentativeLookup().getCurrentPassport().getName());
            legalRepresentativeViewHolder.tvRole.setText(legalRepresentative.getRole() == null ? "" : legalRepresentative.getRole());
            legalRepresentativeViewHolder.tvStartDate.setText(legalRepresentative.getLegal_Representative_Start_Date() == null ? "" : Utilities.formatVisitVisaDate(legalRepresentative.getLegal_Representative_Start_Date()));
            Utilities.setUserPhoto(activity, Utilities.stringNotNull(legalRepresentative.getLegalRepresentativeLookup().getPersonal_Photo()), legalRepresentativeViewHolder._smartEmployeeImage);
            ArrayList<ServiceItem> _items = new ArrayList<ServiceItem>();
            _items.add(new ServiceItem("Show Details", R.mipmap.service_show_details));
            legalRepresentativeViewHolder._horizontalServices.setAdapter(new HorizontalListViewAdapter(legalRepresentative, activity, activity.getApplicationContext(), _items));
        } else if (ScreenTag.equals(AdapterConfiguration.SHAREHOLDERS_Employee_TAG)) {
            ShareOwnership _ShareHolder = (ShareOwnership) objects.get(position);
            shareHoldersViewHolder.tvFullName.setText(_ShareHolder.get_shareholder().getName() == null ? "" : _ShareHolder.get_shareholder().getName());
            shareHoldersViewHolder.tvNationality.setText(_ShareHolder.get_shareholder().getNationality() == null ? "" : _ShareHolder.get_shareholder().getNationality());
            shareHoldersViewHolder.tvPassportNumber.setText(_ShareHolder.get_shareholder().get_currentPassport() == null ? "" : _ShareHolder.get_shareholder().get_currentPassport().getName());
            shareHoldersViewHolder.tvOwnerShip.setText(_ShareHolder.getOwnership_of_Share__c() == null ? "" : _ShareHolder.getOwnership_of_Share__c());
            shareHoldersViewHolder.tvNumberOfShares.setText(_ShareHolder.getNo_of_Shares__c() == null ? "" : _ShareHolder.getNo_of_Shares__c());
            shareHoldersViewHolder.tvStartDate.setText(_ShareHolder.getOwnership_Start_Date__c() == null ? "" : Utilities.formatVisitVisaDate(_ShareHolder.getOwnership_Start_Date__c()));
            Utilities.setUserPhoto(activity, Utilities.stringNotNull(_ShareHolder.get_shareholder().getPersonalPhoto()), shareHoldersViewHolder._smartEmployeeImage);
            ArrayList<ServiceItem> _items = new ArrayList<ServiceItem>();
            _items.add(new ServiceItem("Show Details", R.mipmap.service_show_details));
            _items.add(new ServiceItem("Share Transfer", R.mipmap.share_transfer, (ArrayList<Object>) objects));
            shareHoldersViewHolder._horizontalListView.setAdapter(new HorizontalListViewAdapter(_ShareHolder, activity, activity.getApplicationContext(), _items));
        } else if (ScreenTag.equals(AdapterConfiguration.LEASING_TAG)) {

            Contract_DWC__c mo = (Contract_DWC__c) objects.get(position);

            contractViewHolder.tvContractName.setText(Utilities.stringNotNull(mo.getName()));
            contractViewHolder.tvUnitName.setText(mo.getContract_line_item__cs().get(0).getInventory_unit__r().getName());
            contractViewHolder.tvContractType.setText(Utilities.stringNotNull(mo.getContract_Type__c()));
            contractViewHolder.tvStatus.setText(Utilities.stringNotNull(mo.getStatus__c()));
            contractViewHolder.tvExpiryDate.setText(Utilities.formatVisitVisaDate(Utilities.stringNotNull(mo.getContract_Expiry_Date__c().toString())));
            ArrayList<ServiceItem> _items = new ArrayList<ServiceItem>();
            if (mo.getContract_Expiry_Date__c() != null && !mo.getContract_Expiry_Date__c().equals("")) {
                if (Utilities.daysDifference(mo.getContract_Expiry_Date__c()) < 60) {
                    _items.add(new ServiceItem("Renew Contract", R.mipmap.renew_contract));
                }
            }

            _items.add(new ServiceItem("Show Details", R.mipmap.service_show_details));

            if (mo.IS_BC_Contract__c()) {
                contractViewHolder._smartEmployeeImage.setImageResource(R.mipmap.lease_bc_contract);
            } else {
                contractViewHolder._smartEmployeeImage.setImageResource(R.mipmap.lease_dlc_contract);
            }

            contractViewHolder._horizontalListView.setAdapter(new HorizontalListViewAdapter(mo, activity, activity.getApplicationContext(), _items));
        }

        return convertView;
    }


    static class VisaViewHolder {

        TextView tvFullName, tvVisaExpiry, tvPassportNumber, tvStatus;
        DWCRoundedImageView _smartEmployeeImage;
        ExpandableLayoutItem item;
        HorizontalListView _horizontalListView;
        TextView tvVisaExpiryLabel;
    }

    static class AccessCardViewHolder {

        TextView tvFullName, tvType, tvPassportNumber, tvStatus, tvCardExpiry;
        DWCRoundedImageView _smartEmployeeImage;
        ExpandableLayoutItem item;
        HorizontalListView _horizontalListView;
    }

    static class DirectorViewHolder {

        TextView tvFullName, tvNationality, tvPassportNumber, tvRole, tvStartDate;
        DWCRoundedImageView _smartEmployeeImage;
        ExpandableLayoutItem item;
        HorizontalListView _horizontalListView;
    }

    static class GeneralManagerViewHolder {

        TextView tvFullName, tvNationality, tvPassportNumber, tvRole, tvStartDate;
        DWCRoundedImageView _smartEmployeeImage;
        HorizontalListView horizontalListView;
        ExpandableLayoutItem item;
    }

    static class LegalRepresentativeViewHolder {

        TextView tvFullName, tvNationality, tvPassportNumber, tvRole, tvStartDate;
        DWCRoundedImageView _smartEmployeeImage;
        HorizontalListView _horizontalServices;
        ExpandableLayoutItem item;
    }

    static class ShareHoldersViewHolder {

        TextView tvFullName, tvNationality, tvPassportNumber, tvOwnerShip, tvNumberOfShares, tvStartDate;
        DWCRoundedImageView _smartEmployeeImage;
        ExpandableLayoutItem item;
        HorizontalListView _horizontalListView;
    }

    static class ContractViewHolder {

        TextView tvContractName, tvUnitName, tvContractType, tvExpiryDate, tvStatus;
        DWCRoundedImageView _smartEmployeeImage;
        ExpandableLayoutItem item;
        HorizontalListView _horizontalListView;
    }
}