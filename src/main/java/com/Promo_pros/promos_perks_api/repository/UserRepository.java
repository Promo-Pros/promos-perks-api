package com.Promo_pros.promos_perks_api.repository;

import com.Promo_pros.promos_perks_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
