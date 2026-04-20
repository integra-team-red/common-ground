package cloudflight.integra.backend.user;

import cloudflight.integra.backend.security.RegisterRequest;
import cloudflight.integra.backend.tag.model.Tag;
import cloudflight.integra.backend.user.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.username()) != null)
            throw new IllegalArgumentException("Username already exists");
        if (userRepository.findByEmail(registerRequest.email()) != null)
            throw new IllegalArgumentException("Email already exists");
        User user = new User();
        user.setUsername(registerRequest.username());
        user.setEmail(registerRequest.email());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(registerRequest.password()));
        user.setJoinedDate(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new CustomUserDetails(userRepository.findByUsername(username));
    }

    public void setTagsToUser(String username, List<Tag> tags) {
        User user = userRepository.findByUsername(username);
        user.getTags().clear();
        user.getTags().addAll(tags);
        userRepository.save(user);
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email){ return userRepository.findByEmail(email); }

    public void setMatrixAccount(String username, String accessToken) {
        User user = userRepository.findByUsername(username);
        user.setMatrixAccessToken(accessToken);
        userRepository.save(user);
    }
}
