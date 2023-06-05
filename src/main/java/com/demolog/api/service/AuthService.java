package com.demolog.api.service;

import com.demolog.api.domain.Session;
import com.demolog.api.domain.User;
import com.demolog.api.exception.InvalidSignInInformation;
import com.demolog.api.repository.UserRepository;
import com.demolog.api.request.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public String signin(Login login) {
        User user = userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(InvalidSignInInformation::new);
        Session session = user.addSession();

        return session.getAccessToken();
    }
}
