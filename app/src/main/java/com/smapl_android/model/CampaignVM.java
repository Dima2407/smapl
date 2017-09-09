package com.smapl_android.model;

import android.databinding.ObservableField;
import com.smapl_android.net.responses.GetCampaignListResponse;

public class CampaignVM {

    public final ObservableField<String> name = new ObservableField<>();

    public final ObservableField<String> description = new ObservableField<>();

    public final ObservableField<String> photo = new ObservableField<>();
    private final GetCampaignListResponse.Campaign campaign;

    public CampaignVM(GetCampaignListResponse.Campaign c) {
        campaign = c;
        name.set(c.getName());
        description.set(c.getDescription());
        photo.set(c.getImage());
    }

    public GetCampaignListResponse.Campaign getCampaign() {
        return campaign;
    }
}
