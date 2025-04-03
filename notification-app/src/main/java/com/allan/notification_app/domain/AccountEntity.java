package com.allan.notification_app.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountEntity {

    private Long id;

    private String name;

    private String surName;

    private String cpf;

    private String phone;

    private Double income;

}
