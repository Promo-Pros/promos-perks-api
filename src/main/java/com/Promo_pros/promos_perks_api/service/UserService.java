package com.Promo_pros.promos_perks_api.service;

import com.Promo_pros.promos_perks_api.entity.User;

public interface UserService {
    User createUser(User user);

    User getUser(Long id);
}
