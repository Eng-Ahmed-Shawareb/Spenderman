package com.spenderman.model;

import java.time.LocalTime;

/**
 * Class representing ClsChatMessage.
 *
 * @author Spenderman Team
 * @version 1.0
 */
public class ClsChatMessage {

    private String _messageText;

    private boolean _isFromUser;

    private LocalTime _timestamp;

    public ClsChatMessage(String messageText, boolean isFromUser) {
        this._messageText = messageText;
        this._isFromUser = isFromUser;
        this._timestamp = LocalTime.now();
    }

    /**
     * Method to get_messageText.
     *
     * @return the String
     */
    public String get_messageText() {
        return _messageText;
    }

    /**
     * Method to isFromUser.
     *
     * @return the boolean
     */
    public boolean isFromUser() {
        return _isFromUser;
    }

    /**
     * Method to get_timestamp.
     *
     * @return the LocalTime
     */
    public LocalTime get_timestamp() {
        return _timestamp;
    }
}
