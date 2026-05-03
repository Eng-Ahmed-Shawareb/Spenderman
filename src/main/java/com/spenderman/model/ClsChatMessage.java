package com.spenderman.model;

import java.time.LocalTime;

public class ClsChatMessage {
    private String _messageText;
    private boolean _isFromUser; // true = User, false = AI
    private LocalTime _timestamp;

    public ClsChatMessage(String messageText, boolean isFromUser) {
        this._messageText = messageText;
        this._isFromUser = isFromUser;
        this._timestamp = LocalTime.now();
    }

    public String get_messageText() { return _messageText; }
    public boolean isFromUser() { return _isFromUser; }
    public LocalTime get_timestamp() { return _timestamp; }
}