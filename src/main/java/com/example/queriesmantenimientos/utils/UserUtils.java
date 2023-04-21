package com.example.queriesmantenimientos.utils;

import com.example.queriesmantenimientos.model.Role;
import com.example.queriesmantenimientos.model.User;

import java.util.Objects;
import java.util.Set;

public class UserUtils {
    public static void checkUserRole(User user, RoleValues role) throws Exception {
        if (user.getRoles().stream().noneMatch(x -> Objects.equals(x.getName(), role.toString())))
            throw new Exception("you do not have permission to authorize queries");
    }
    public static void hasRolePermission(User user,Set<String> roles) throws Exception {
        boolean hasRolePermission = user.getRoles().stream().map(Role::getName).anyMatch(
                roles::contains
        );
        if (!hasRolePermission) {
            throw new Exception("You do not have enough permissions");
        }
    }
}
