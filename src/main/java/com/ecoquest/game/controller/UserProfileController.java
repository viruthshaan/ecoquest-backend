package com.ecoquest.game.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ecoquest.game.model.UserProfile;
import com.ecoquest.game.repository.UserProfileRepository;
import com.ecoquest.game.service.UserProfileService;

import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin(origins = "http://localhost:3001")

@RequestMapping("/user")

public class UserProfileController {

    @Autowired
    UserProfileRepository userProfileRepository;

    @GetMapping("/getall")
    public List<UserProfile> getUserProfiles() {

        return userProfileRepository.findAll();

    }


    @GetMapping("/getuser/{userName}")
    public UserProfile getUserByName(@PathVariable String userName) {
        UserProfile userProfile = userProfileRepository.findByUserName(userName);
    
        if (userProfile == null) {
            // User not found, return the specified string
            return null;
        }
    
        // User found, return the UserProfile
        return userProfile; // Or any other representation you want to return
    }
 
    
    @SuppressWarnings("null")
    @PostMapping("/create")
    public UserProfile createUserProfile(
            @RequestBody UserProfile userProfile) {

        // Save the user profile to MongoDB (assuming you have a repository)
        return userProfileRepository.save(userProfile);

    }

    
    @Autowired
    private UserProfileService userProfileService;

    @PutMapping("/updateMarks/{userName}")
    public UserProfile updateUser(@PathVariable String userName, @RequestParam int total_marks) {
    //    UserProfile updatedProfile;
       UserProfile updatedProfile = userProfileService.updateMcqMarks(userName, total_marks);
      return updatedProfile;
    }

    @PutMapping("/updateCoins/{userName}")
    public UserProfile updateCoins(@PathVariable String userName, @RequestParam String token) {
    //    UserProfile updatedProfile;
       UserProfile updatedProfile = userProfileService.updateCoins(userName, token);
      return updatedProfile;
    }

}
