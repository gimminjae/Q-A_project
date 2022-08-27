package com.qna_project.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void create(String username, String password, String email) {
        SiteUser siteUser = new SiteUser();
        siteUser.setUsername(username);
        siteUser.setEmail(email);

        siteUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(siteUser);
    }
}
