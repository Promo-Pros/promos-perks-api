package com.Promo_pros.promos_perks_api.service.servicesImpl;

import com.Promo_pros.promos_perks_api.entity.Promotion;
import com.Promo_pros.promos_perks_api.repository.PromotionRepository;
import com.Promo_pros.promos_perks_api.roles.Role;
import com.Promo_pros.promos_perks_api.service.PromotionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromotionServiceImpl implements PromotionService {

    private PromotionRepository promotionRepository;

    public PromotionServiceImpl(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    @Override
    public Promotion createPromotion(Promotion promotion) {
        return promotionRepository.save(promotion);
    }

    @Override
    public List<Promotion> getAllPromotions() {
        return promotionRepository.findAll();
    }

    @Override
    public Promotion deletePromotion(String name) {
        Promotion newPromotion = promotionRepository.findByName(name);
        if(newPromotion != null){
            promotionRepository.deleteById(newPromotion.getId());
            return newPromotion;
        }
        return  null;
    }

    @Override
    public Promotion updatePromotion(String name, Promotion promotionDetails) {
        Promotion existingPromotion = promotionRepository.findByName(name);
        if(existingPromotion != null) {
            existingPromotion.setName(promotionDetails.getName());
            existingPromotion.setDescription(promotionDetails.getDescription());
            existingPromotion.setEligibleRoles(promotionDetails.getEligibleRoles()); //Update eligible Roles
            return promotionRepository.save(existingPromotion);
        }
        return null;
    }

    @Override
    public List getPromotionsByRole(Role role) {
        return promotionRepository.findAll()
                .stream()
                .filter(promotion -> promotion.getEligibleRoles().contains(role))
                .collect(Collectors.toList()); }
}
