package com.tms.lingofriends.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionResponse {
    private Timestamp expireDate;
    private Boolean status;
}