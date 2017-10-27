package com.smapl_android.net.responses;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class GetCampaignResponse extends ServerResponse<GetCampaignResponse.Campaign> {

    public Campaign getCampaign() {
        return getResult();
    }

    public static class Campaign implements Serializable {

        @SerializedName("name")
        private String name;
        @SerializedName("price")
        private int price;
        @SerializedName("sticker")
        private List<String> stickers;
        @SerializedName("description")
        private String description;
        @SerializedName("description_short")
        private String descriptionShort;
        @SerializedName("id")
        private int id;
        @SerializedName("logo")
        private String logo;

        public String getName() {
            return name;
        }

        public List<String> getStickers() {
            if (stickers == null) {
                return Collections.emptyList();
            }
            return stickers;
        }

        public String getDescription(boolean isShort) {
            if (isShort) {
                return descriptionShort;
            }
            return description;
        }

        public int getId() {
            return id;
        }

        public String getImage() {
            return logo;
        }

        public int getPrice() {
            return price;
        }
    }
}
