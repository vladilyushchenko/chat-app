package org.softarex.chat.util;

import lombok.experimental.UtilityClass;
import org.softarex.chat.model.UserRole;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@UtilityClass
public class UserRoleUtil {

    public static List<String> mapToAuthorities(Set<UserRole> roles) {
        return roles.stream()
                .map(UserRole::name)
                .collect(toList());
    }
}