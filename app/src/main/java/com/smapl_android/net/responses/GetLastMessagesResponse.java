package com.smapl_android.net.responses;

import com.google.gson.annotations.SerializedName;

public class GetLastMessagesResponse {

    @SerializedName("messages")
    private Message[] messages;

    public Message[] getMessages() {
        return messages;
    }

    public void setMessages(Message[] messages) {
        this.messages = messages;
    }

    public static class Message {

        @SerializedName("sender_id")
        private String senderId;

        @SerializedName("sender_name")
        private String senderName;

        @SerializedName("sender_photo")
        private String senderPhoto;

        @SerializedName("receiver_id")
        private String receiverId;

        @SerializedName("receiver_name")
        private String receiverName;

        @SerializedName("receiver_photo")
        private String receiverPhoto;

        @SerializedName("message")
        private String message;

        @SerializedName("created")
        private String created;

        public String getSenderId() {
            return senderId;
        }

        public String getSenderName() {
            return senderName;
        }

        public String getSenderPhoto() {
            return senderPhoto;
        }

        public String getReceiverId() {
            return receiverId;
        }

        public String getReceiverName() {
            return receiverName;
        }

        public String getReceiverPhoto() {
            return receiverPhoto;
        }

        public String getMessage() {
            return message;
        }

        public String getCreated() {
            return created;
        }

        public void setSenderId(String senderId) {
            this.senderId = senderId;
        }

        public void setSenderName(String senderName) {
            this.senderName = senderName;
        }

        public void setSenderPhoto(String senderPhoto) {
            this.senderPhoto = senderPhoto;
        }

        public void setReceiverId(String receiverId) {
            this.receiverId = receiverId;
        }

        public void setReceiverName(String receiverName) {
            this.receiverName = receiverName;
        }

        public void setReceiverPhoto(String receiverPhoto) {
            this.receiverPhoto = receiverPhoto;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public void setCreated(String created) {
            this.created = created;
        }
    }
}
