package base;

public class TestFlags {
    public static boolean shouldKeepAccount() {
        String prop = System.getProperty("KEEP_ACCOUNT", System.getenv("KEEP_ACCOUNT"));
        if (prop == null) return true; // default: keep accounts to stabilize reruns
        return prop.equalsIgnoreCase("true") || prop.equals("1");
    }

}


