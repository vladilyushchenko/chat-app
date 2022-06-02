package org.softarex.chat.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.Set;

import static org.softarex.chat.model.UserRole.CHAT_ROLE;

@Data
@RedisHash("users")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    @Indexed
    private String username;

    private Set<UserRole> roles = Set.of(CHAT_ROLE);
}