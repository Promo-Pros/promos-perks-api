package com.Promo_pros.promos_perks_api.payload;

import lombok.Data;

@Data
public class JWTAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer Token";
}
