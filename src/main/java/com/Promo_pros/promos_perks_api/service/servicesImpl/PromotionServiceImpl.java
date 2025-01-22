package com.Promo_pros.promos_perks_api.service.servicesImpl;

import com.Promo_pros.promos_perks_api.entity.Promotion;
import com.Promo_pros.promos_perks_api.entity.User;  // Ensure User is imported
import com.Promo_pros.promos_perks_api.repository.PromotionRepository;
import com.Promo_pros.promos_perks_api.roles.AccountTypes;
import com.Promo_pros.promos_perks_api.service.PromotionService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;

    public PromotionServiceImpl(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    @Override
    public Promotion createPromotion(Promotion promotion) {
        return promotionRepository.save(promotion);
    }

    @Override
    public List<Promotion> getPromotionsForUser() {
        // Retrieve the email from SecurityContext
        User user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        AccountTypes userStatus = user.getStatus();
        return promotionRepository.findByStatus(userStatus);
    }

    @Override
    public Promotion deletePromotion(String name) {
        // Retrieve the email from SecurityContext
        String email = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail();

        // Find the promotion related to the user (assuming 'name' is sufficient to find the correct promotion)
        Promotion promotion = promotionRepository.findByNameAndUserEmail(name, email);

        if (promotion != null) {
            promotionRepository.deleteById(promotion.getId());
            return promotion;
        }
        return null;
    }

    @Override
    public Promotion updatePromotion(String name, Promotion promotionDetails) {
        // Retrieve the email from SecurityContext
        String email = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail();

        // Find the promotion related to the user (assuming 'name' is sufficient to find the correct promotion)
        Promotion existingPromotion = promotionRepository.findByNameAndUserEmail(name, email);

        if (existingPromotion != null) {
            // Update the promotion's details
            existingPromotion.setName(promotionDetails.getName());
            existingPromotion.setDescription(promotionDetails.getDescription());
            existingPromotion.setStatus(promotionDetails.getStatus());
            return promotionRepository.save(existingPromotion);
        }
        return null;
    }
}
