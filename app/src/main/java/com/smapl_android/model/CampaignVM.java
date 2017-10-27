package com.smapl_android.model;

import android.databinding.ObservableField;
import com.smapl_android.net.responses.GetCampaignListResponse;
import com.smapl_android.net.responses.GetCampaignResponse;

public class CampaignVM {

    public final ObservableField<String> name = new ObservableField<>();

    public final ObservableField<String> description = new ObservableField<>();

    public final ObservableField<String> photo = new ObservableField<>();

    public final ObservableField<Integer> budget = new ObservableField<>();
    private final GetCampaignResponse.Campaign campaign;

    public static CampaignVM forDetails(GetCampaignResponse.Campaign c){
        return new CampaignVM(c, false);
    }

    public static CampaignVM forList(GetCampaignResponse.Campaign c){
        return new CampaignVM(c, true);
    }

    private CampaignVM(GetCampaignResponse.Campaign c, boolean isShort) {
        campaign = c;
        name.set(c.getName());
        description.set(c.getDescription(isShort));
        photo.set(c.getImage());
        budget.set(c.getPrice());
    }

    public GetCampaignResponse.Campaign getCampaign() {
        return campaign;
    }
}
