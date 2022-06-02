package org.softarex.chat.service;

import lombok.RequiredArgsConstructor;
import org.softarex.chat.exception.EntityNotFoundException;
import org.softarex.chat.exception.UserAlreadyExistsException;
import org.softarex.chat.model.User;
import org.softarex.chat.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.lang.String.format;
import static org.softarex.chat.constants.ErrorConstants.USER_WITH_USERNAME_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User createUser(User user) {
        if (existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException(user.getUsername());
        }
        return repository.save(user);
    }

    private boolean existsByUsername(String username) {
        return repository.findFirstByUsername(username).isPresent();
    }

    public User getByUserName(String username) {
        return repository.findFirstByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(format(USER_WITH_USERNAME_NOT_FOUND, username)));
    }

    public void deleteByUsername(String username) {
        User user = repository.findFirstByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(format(USER_WITH_USERNAME_NOT_FOUND, username)));
        repository.deleteById(user.getId());
    }

    public List<User> getAllUsers() {
        return newArrayList(repository.findAll());
    }
}