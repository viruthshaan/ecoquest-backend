package com.ecoquest.game.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ecoquest.game.model.UserProfile;

public interface UserProfileRepository extends MongoRepository<UserProfile, Long>{
    
    UserProfile findByUserName(String userName);

}
