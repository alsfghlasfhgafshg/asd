package sales.salesmen.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sales.salesmen.entity.User;
import sales.salesmen.repository.UserRepository;
import sales.salesmen.service.FileService;
import sales.salesmen.service.UserService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    FileService fileService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User SaveOrUpdateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void removeUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Page<User> listUserByUsernameLike(String username, Pageable pageable) {
        username = "%" + username + "%";
        return userRepository.findByUsernameLike(username, pageable);
    }

    @Override
    public String changeAvatar(MultipartFile multipartFile, UsernamePasswordAuthenticationToken principal) {
        long userid = ((User) principal.getPrincipal()).getId();
        String newAvataruri = fileService.uploadImage(multipartFile);
        User u = userRepository.findById(userid).get();
        u.setAvatar(newAvataruri);
        userRepository.save(u);
        return newAvataruri;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}
