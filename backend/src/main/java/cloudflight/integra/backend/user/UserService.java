package cloudflight.integra.backend.user;

import cloudflight.integra.backend.tag.model.Tag;
import cloudflight.integra.backend.user.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null)
            throw new IllegalArgumentException("Username already exists");
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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

    public void addTagsToUser(String username, List<Tag> tags) {
        User user = userRepository.findByUsername(username);
        user.getTags().addAll(tags);
        userRepository.save(user);
    }
}
