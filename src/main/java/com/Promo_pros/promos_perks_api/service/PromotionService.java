package com.Promo_pros.promos_perks_api.service;

import com.Promo_pros.promos_perks_api.entity.Promotion;
import com.Promo_pros.promos_perks_api.roles.Role;

import java.util.List;

public interface PromotionService {
    Promotion createPromotion(Promotion promotion);

    List<Promotion> getAllPromotions();

    Promotion deletePromotion(String name);

    Promotion updatePromotion(String name, Promotion promotionDetails);

    List<Promotion> getPromotionsByRole(Role role);

}

