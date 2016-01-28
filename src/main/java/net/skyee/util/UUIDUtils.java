package net.skyee.util;


import java.util.UUID;

public class UUIDUtils {
    public static String newUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
