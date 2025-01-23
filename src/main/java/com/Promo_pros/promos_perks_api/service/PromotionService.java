package com.Promo_pros.promos_perks_api.service;

import com.Promo_pros.promos_perks_api.entity.Promotion;

import java.util.List;

public interface PromotionService {
    Promotion createPromotion(Promotion promotion);

    List<Promotion> getPromotionsForUser();

//    Promotion deletePromotion(String name);

//    Promotion updatePromotion(String name, Promotion promotionDetails);
}
