package com.smapl_android.net.responses;

public class GetCampaignListResponse extends ServerResponse<GetCampaignResponse.Campaign[]> {

    public GetCampaignResponse.Campaign[] getCampaigns() {
        return getResult();
    }
}
