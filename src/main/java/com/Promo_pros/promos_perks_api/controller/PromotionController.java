package com.Promo_pros.promos_perks_api.controller;

import com.Promo_pros.promos_perks_api.entity.Promotion;
import com.Promo_pros.promos_perks_api.service.PromotionService;
import org.springframework.security.access.prepost.PreAuthorize;
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


    //this may have to change from a POST to GET because this may be used with login???
    //POST
    //authorization based on the user's role
    @PreAuthorize("hasAuthority('employee') or hasAuthority('veteran')")
    @PostMapping
    public Promotion verifyPromotion(@RequestBody Promotion promotion) {
        //this may need fixing to verifyPromotion. might have to add something to impl
        return promotionService.createPromotion(promotion);
    }

    //GET
    @GetMapping
    public List<Promotion> getAllPromotions() {
        return promotionService.getAllPromotions();

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