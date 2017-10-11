package com.smapl_android.model;

import android.databinding.ObservableField;
import com.smapl_android.net.responses.GetCampaignListResponse;

public class CampaignVM {

    public final ObservableField<String> name = new ObservableField<>();

    public final ObservableField<String> description = new ObservableField<>();

    public final ObservableField<String> photo = new ObservableField<>();

    public final ObservableField<Integer> budget = new ObservableField<>();
    private final GetCampaignListResponse.Campaign campaign;

    public static CampaignVM forDetails(GetCampaignListResponse.Campaign c){
        return new CampaignVM(c, false);
    }

    public static CampaignVM forList(GetCampaignListResponse.Campaign c){
        return new CampaignVM(c, true);
    }

    private CampaignVM(GetCampaignListResponse.Campaign c, boolean isShort) {
        campaign = c;
        name.set(c.getName());
        description.set(c.getDescription(isShort));
        photo.set(c.getImage());
        budget.set(c.getPrice());
    }

    public GetCampaignListResponse.Campaign getCampaign() {
        return campaign;
    }
}
