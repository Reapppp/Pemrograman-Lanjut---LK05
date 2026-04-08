package interfaces;

public interface Confidential {
    // 1 = PUBLIC, 2 = RESTRICTED, 3 = SECRET
    int getSecurityLevel();
    void maskSensitiveData();
}
