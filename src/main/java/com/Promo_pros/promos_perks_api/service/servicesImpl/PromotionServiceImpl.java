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
}
