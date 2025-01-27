package com.Promo_pros.promos_perks_api.roles;

//this will determine access levels and permissions. Used for Spring Security authorization.

//implementing Role Based Access Control using Spring Security

public enum Role {
    ROLE_ADMIN,
    ROLE_CUSTOMER,
    ROLE_EMPLOYEE,
    ROLE_VETERAN
}
