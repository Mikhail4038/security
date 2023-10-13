package com.keiko.securityapp.service.common.impl;

import com.keiko.securityapp.entity.security.User;
import com.keiko.securityapp.repository.common.UserRepository;
import com.keiko.securityapp.service.common.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DefaultUserService extends DefaultCrudService<User>
        implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Set<User> findUserByRole (String role) {
        return userRepository.findByRoles_nameLikeIgnoreCase (role);
    }

    @Override
    public User findUserByEmail (String email) {
        return userRepository.findUserByEmail (email).orElseThrow ();
    }

    @Override
    public void deleteByEmail (String email) {
        userRepository.deleteByEmail (email);
    }

    @Override
    public User save (User user) {
        final String presentedPassword = user.getPassword ();
        final String encodePassword = passwordEncoder.encode (presentedPassword);
        user.setPassword (encodePassword);
        return userRepository.save (user);
    }
}
