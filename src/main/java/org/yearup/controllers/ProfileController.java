package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.models.Profile;
import org.yearup.models.User;
import org.yearup.service.ProfileService;
import org.yearup.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/profile")
@CrossOrigin
@PreAuthorize("isAuthenticated()") // Only logged-in users can access their profile
public class ProfileController {

    private ProfileService profileService;
    private UserService userService;

    @Autowired
    public ProfileController(ProfileService profileService, UserService userService) {
        this.profileService = profileService;
        this.userService = userService;
    }

    @GetMapping
    public Profile getProfile(Principal principal) {
        // Get the username of the person logged in
        String username = principal.getName();
        // Find their User object to get their ID
        User user = userService.getByUserName(username);
        // Return their specific profile
        return profileService.getProfileByUserId(user.getId());
    }

    @PutMapping
    public Profile updateProfile(Principal principal, @RequestBody Profile profile) {
        // Get the username and User object
        String username = principal.getName();
        User user = userService.getByUserName(username);

        // Lock the profile's User ID to the logged-in user so they can't hack someone else's profile
        profile.setUserId(user.getId());
        return profileService.updateProfile(profile);
    }
}