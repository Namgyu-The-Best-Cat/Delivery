package com.bestcat.delivery.user.entity;

import lombok.Getter;

@Getter
public enum RoleType {
    MASTER(Authority.MASTER),
    MANAGER(Authority.MANAGER),
    CUSTOMER(Authority.CUSTOMER),
    OWNER(Authority.OWNER);

    private final String authority;

    RoleType(String authority) {
        this.authority = authority;
    }

    public static class Authority {
        public static final String MASTER = "ROLE_MASTER";
        public static final String MANAGER = "ROLE_MANAGER";
        public static final String CUSTOMER = "ROLE_CUSTOMER";
        public static final String OWNER = "ROLE_OWNER";
    }
}
