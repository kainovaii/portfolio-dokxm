package fr.kainovaii.portfolio.service;

import fr.kainovaii.portfolio.model.User;
import fr.kainovaii.portfolio.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findById(Long id)
    {
        return userRepository.findById(id);
    }

    public List<User> findAll()
    {
        return userRepository.findAll();
    }

    public long count() {
        return userRepository.count();
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username)
    {
        return userRepository.findByUsername(username);
    }

    public void registerUser(User user)
    {
        String normalizedUsername = user.getUsername().toLowerCase();
        if (userRepository.existsByUsernameIgnoreCase(normalizedUsername)) {
            throw new IllegalArgumentException("Username already taken");
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setUsername(normalizedUsername);
        user.setPassword(encodedPassword);
        user.setRole("USER");
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'username : " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    public Map<Long, String> getUsernamesByIds(Set<Long> ids) {
        List<User> users = userRepository.findAllById(ids);
        return users.stream()
                .collect(Collectors.toMap(User::getId, User::getUsername)); // ou getPseudo() si c’est ton champ
    }
}

