package com.Promo_pros.promos_perks_api.controller;

import com.Promo_pros.promos_perks_api.entity.Promotion;
import com.Promo_pros.promos_perks_api.service.PromotionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/promotions")
public class PromotionController {
    private PromotionService promotionService;

    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    //POST
    @PostMapping
    public Promotion createPromotion(@RequestBody Promotion promotion) {
        System.out.println(promotion.getDescription());
        return promotionService.createPromotion(promotion);
    }

    //GET
    @GetMapping
    public List<Promotion> getAllPromotions() {
        return promotionService.getPromotionsForUser();

    }

    //DELETE
    @DeleteMapping("/{name}")
    public Promotion deletePromotion(@PathVariable String name) {
        return promotionService.deletePromotion(name);
    }

    //PUT
    @PutMapping("/{name}")
    public Promotion updatePromotion(@PathVariable String name, @RequestBody Promotion promotionDetails) {
        return promotionService.updatePromotion(name, promotionDetails);
    }

}