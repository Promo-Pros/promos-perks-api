package com.Promo_pros.promos_perks_api.repository;
import java.util.List;
import com.Promo_pros.promos_perks_api.entity.Promotion;
import com.Promo_pros.promos_perks_api.roles.AccountTypes;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    List<Promotion> findByStatus(AccountTypes status);
    Promotion findByNameAndUserEmail(String name, String email);
}
