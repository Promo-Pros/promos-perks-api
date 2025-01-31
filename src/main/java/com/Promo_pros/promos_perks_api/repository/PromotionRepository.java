package com.Promo_pros.promos_perks_api.repository;

import com.Promo_pros.promos_perks_api.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    Promotion findByName(String name);
    List<Promotion> findAllByStatus(String status);
}
