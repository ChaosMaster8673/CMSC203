import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
public class CryptoManagerTestStudent {
    private static final String OOB = "The selected string is not in bounds, Try again.";
    /* isStringInBounds */
    @Test public void inBoundsTrue()    { assertTrue(CryptoManager.isStringInBounds("MONTGOMERY 123")); }
    @Test public void inBoundsTrueSym() { assertTrue(CryptoManager.isStringInBounds("JACEN CHESKIN")); }
    @Test public void inBoundsFalse()   { assertFalse(CryptoManager.isStringInBounds("abc")); }
    @Test public void inBoundsFalseBq() { assertFalse(CryptoManager.isStringInBounds("A`B")); }
    /* Caesar */
    @Test public void caesarShift()        { assertEquals("TVU[NVTLY ", CryptoManager.caesarEncryption("MONTGOMERY", 7)); }
    @Test public void caesarWrapUp()       { assertEquals("]<\"", CryptoManager.caesarEncryption("Z9_", 3)); }
    @Test public void caesarWrap63()       { assertEquals("@", CryptoManager.caesarEncryption("A", 63)); }
    @Test public void caesarWrap64()       { assertEquals("A", CryptoManager.caesarEncryption("A", 64)); }
    @Test public void caesarOOB()          { assertEquals(OOB, CryptoManager.caesarEncryption("abc", 5)); }
    @Test public void caesarRoundTrip()    {
        assertEquals("JACEN CHESKIN 2026",
            CryptoManager.caesarDecryption(CryptoManager.caesarEncryption("JACEN CHESKIN 2026", 13), 13));
    }
    /* Vigenere */
    @Test public void vigShift()     { assertEquals(".\"7&N[SV", CryptoManager.vigenereEncryption("DATA 123", "JACEN")); }
    @Test public void vigOOBKey()    { assertEquals(OOB, CryptoManager.vigenereEncryption("WORLD", "a")); }
    @Test public void vigLongKey()   { assertEquals("B:", CryptoManager.vigenereEncryption("XY", "JACEN")); }
    @Test public void vigRoundTrip() {
        assertEquals("JACEN CHESKIN 2026",
            CryptoManager.vigenereDecryption(CryptoManager.vigenereEncryption("JACEN CHESKIN 2026", "MONTG"), "MONTG"));
    }
    @Test public void vigRoundTrip2() {
        assertEquals("XY",
            CryptoManager.vigenereDecryption(CryptoManager.vigenereEncryption("XY", "JACEN"), "JACEN"));
    }
    /* Playfair */
    @Test public void playfairMontgomery() { assertEquals("ORRGMNK3", CryptoManager.playfairEncryption("MEETYOU", "MONTGOMERY")); }
    @Test public void playfairOOB()        { assertEquals(OOB, CryptoManager.playfairEncryption("abc", "JACEN")); }
    @Test public void playfairRoundTrip()  {
        assertEquals("CRYPTO 2026",
            CryptoManager.playfairDecryption(CryptoManager.playfairEncryption("CRYPTO 2026", "MONTG"), "MONTG"));
    }
    @Test public void playfairRoundTrip2() {
        assertEquals("WORLD",
            CryptoManager.playfairDecryption(CryptoManager.playfairEncryption("WORLD", "JACEN"), "JACEN"));
    }
    @Test public void playfairDigits()     {
        assertEquals("123456",
            CryptoManager.playfairDecryption(CryptoManager.playfairEncryption("123456", "MONTGOMERY"), "MONTGOMERY"));
    }
}