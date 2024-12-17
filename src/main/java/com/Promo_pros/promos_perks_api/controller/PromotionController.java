package com.Promo_pros.promos_perks_api.controller;

import com.Promo_pros.promos_perks_api.entity.Promotion;
import com.Promo_pros.promos_perks_api.service.PromotionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/promotions")
public class PromotionController {
    private PromotionService promotionService;

    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    //POST
    @PostMapping
    public Promotion createPromotion(@RequestBody Promotion promotion){
        System.out.println(promotion.getDescription());
        return promotionService.createPromotion(promotion);
    }

    //GET

    //DELETE
}