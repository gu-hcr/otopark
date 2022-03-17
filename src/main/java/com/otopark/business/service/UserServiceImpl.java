package com.otopark.business.service;

import com.otopark.business.model.User;
import com.otopark.business.repository.UserJPARepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl extends AbstractServiceImpl<User> implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserJPARepository userJPARepository;

    public UserServiceImpl(@Lazy PasswordEncoder passwordEncoder, UserJPARepository userRepository) {
        super(userRepository);
        this.passwordEncoder = passwordEncoder;
        this.userJPARepository = userRepository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userJPARepository.findByUsername(username);
    }

    @Override
    public boolean isUsernameExist(String username) {
        Optional optionalUser = findByUsername(username);
        return optionalUser.isPresent();
    }

    @Override
    public User save(User user) {
        if (user.getId() == 0) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userJPARepository.save(user);
    }

    @Override
    public User setPassword(User user, String rawPassword) {
        User storedUser = this.findById(user.getId());
        storedUser.setPassword(passwordEncoder.encode(user.getPassword()));
        return userJPARepository.save(storedUser);
    }
}
