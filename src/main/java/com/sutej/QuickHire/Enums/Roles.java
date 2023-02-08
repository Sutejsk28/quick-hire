package com.sutej.QuickHire.Enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Roles {
    USER("USER"),WORKER("WORKER"),ADMIN("ADMIN");
    private final String name;
}
