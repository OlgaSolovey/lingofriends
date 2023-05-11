package com.tms.languageclub.domain;

import lombok.Data;

import java.sql.Date;
@Data
public class Subscription {
    private int id;
    private Date expireDate; //TODO
}
