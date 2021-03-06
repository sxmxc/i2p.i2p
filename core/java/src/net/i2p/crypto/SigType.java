package net.i2p.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import net.i2p.data.Hash;
import net.i2p.data.SimpleDataStructure;

/**
 * Defines the properties for various signature types
 * that I2P supports or may someday support.
 *
 * All Signatures, SigningPublicKeys, and SigningPrivateKeys have a type.
 * Note that a SigType specifies both an algorithm and parameters, so that
 * we may change primes or curves for a given algorithm.
 *
 * @since 0.9.8
 */
public enum SigType {
    /**
     *  DSA_SHA1 is the default.
     *  Pubkey 128 bytes; privkey 20 bytes; hash 20 bytes; sig 40 bytes
     *  @since 0.9.8
     */
    DSA_SHA1(0, 128, 20, 20, 40, SigAlgo.DSA, "SHA-1", "SHA1withDSA", CryptoConstants.DSA_SHA1_SPEC),
    /**  Pubkey 64 bytes; privkey 32 bytes; hash 32 bytes; sig 64 bytes */
    ECDSA_SHA256_P256(1, 64, 32, 32, 64, SigAlgo.EC, "SHA-256", "SHA256withECDSA", ECConstants.P256_SPEC),
    /**  Pubkey 96 bytes; privkey 48 bytes; hash 48 bytes; sig 96 bytes */
    ECDSA_SHA384_P384(2, 96, 48, 48, 96, SigAlgo.EC, "SHA-384", "SHA384withECDSA", ECConstants.P384_SPEC),
    /**  Pubkey 132 bytes; privkey 66 bytes; hash 64 bytes; sig 132 bytes */
    ECDSA_SHA512_P521(3, 132, 66, 64, 132, SigAlgo.EC, "SHA-512", "SHA512withECDSA", ECConstants.P521_SPEC),

    /**  Pubkey 256 bytes; privkey 512 bytes; hash 32 bytes; sig 256 bytes */
    RSA_SHA256_2048(4, 256, 512, 32, 256, SigAlgo.RSA, "SHA-256", "SHA256withRSA", RSAConstants.F4_2048_SPEC),
    /**  Pubkey 384 bytes; privkey 768 bytes; hash 48 bytes; sig 384 bytes */
    RSA_SHA384_3072(5, 384, 768, 48, 384, SigAlgo.RSA, "SHA-384", "SHA384withRSA", RSAConstants.F4_3072_SPEC),
    /**  Pubkey 512 bytes; privkey 1024 bytes; hash 64 bytes; sig 512 bytes */
    RSA_SHA512_4096(6, 512, 1024, 64, 512, SigAlgo.RSA, "SHA-512", "SHA512withRSA", RSAConstants.F4_4096_SPEC),



    // TESTING....................



    // others..........

    // EC mix and match
    //ECDSA_SHA256_P192(5, 48, 24, 32, 48, SigAlgo.EC, "SHA-256", "SHA256withECDSA", ECConstants.P192_SPEC),
    //ECDSA_SHA256_P384(6, 96, 48, 32, 96, SigAlgo.EC, "SHA-256", "SHA256withECDSA", ECConstants.P384_SPEC),
    //ECDSA_SHA256_P521(7, 132, 66, 32, 132, SigAlgo.EC, "SHA-256", "SHA256withECDSA", ECConstants.P521_SPEC),
    //ECDSA_SHA384_P256(8, 64, 32, 48, 64, SigAlgo.EC, "SHA-384", "SHA384withECDSA", ECConstants.P256_SPEC),
    //ECDSA_SHA384_P521(9, 132, 66, 48, 132, SigAlgo.EC, "SHA-384", "SHA384withECDSA", ECConstants.P521_SPEC),
    //ECDSA_SHA512_P256(10, 64, 32, 64, 64, SigAlgo.EC, "SHA-512", "SHA512withECDSA", ECConstants.P256_SPEC),
    //ECDSA_SHA512_P384(11, 96, 48, 64, 96, SigAlgo.EC, "SHA-512", "SHA512withECDSA", ECConstants.P384_SPEC),

    // Koblitz
    //ECDSA_SHA256_K163(12, 42, 21, 32, 42, SigAlgo.EC, "SHA-256", "SHA256withECDSA", ECConstants.K163_SPEC),
    //ECDSA_SHA256_K233(13, 60, 30, 32, 60, SigAlgo.EC, "SHA-256", "SHA256withECDSA", ECConstants.K233_SPEC),
    //ECDSA_SHA256_K283(14, 72, 36, 32, 72, SigAlgo.EC, "SHA-256", "SHA256withECDSA", ECConstants.K283_SPEC),
    //ECDSA_SHA256_K409(15, 104, 52, 32, 104, SigAlgo.EC, "SHA-256", "SHA256withECDSA", ECConstants.K409_SPEC),
    //ECDSA_SHA256_K571(16, 144, 72, 32, 144, SigAlgo.EC, "SHA-256", "SHA256withECDSA", ECConstants.K571_SPEC),

    // too short..............
    /**  Pubkey 48 bytes; privkey 24 bytes; hash 20 bytes; sig 48 bytes */
    //ECDSA_SHA1_P192(1, 48, 24, 20, 48, SigAlgo.EC, "SHA-1", "SHA1withECDSA", ECConstants.P192_SPEC),
    //RSA_SHA1(17, 128, 256, 20, 128, SigAlgo.RSA, "SHA-1", "SHA1withRSA", RSAConstants.F4_1024_SPEC),
    //MD5
    //RSA_SHA1

    //ELGAMAL_SHA256
    //DSA_2048_224(2, 256, 28, 32, 56, "SHA-256"),
    // Nonstandard, used by Syndie.
    // Pubkey 128 bytes; privkey 20 bytes; hash 32 bytes; sig 40 bytes
    //DSA_1024_160_SHA256(1, 128, 20, 32, 40, "SHA-256", "?"),
    // Pubkey 256 bytes; privkey 32 bytes; hash 32 bytes; sig 64 bytes
    //DSA_2048_256(2, 256, 32, 32, 64, "SHA-256", "?"),
    // Pubkey 384 bytes; privkey 32 bytes; hash 32 bytes; sig 64 bytes
    //DSA_3072_256(3, 384, 32, 32, 64, "SHA-256", "?"),

    ;   

    private final int code, pubkeyLen, privkeyLen, hashLen, sigLen;
    private final SigAlgo base;
    private final String digestName, algoName;
    private final AlgorithmParameterSpec params;

    SigType(int cod, int pubLen, int privLen, int hLen, int sLen, SigAlgo baseAlgo,
            String mdName, String aName, AlgorithmParameterSpec pSpec) {
        code = cod;
        pubkeyLen = pubLen;
        privkeyLen = privLen;
        hashLen = hLen;
        sigLen = sLen;
        base = baseAlgo;
        digestName = mdName;
        algoName = aName;
        params = pSpec;
    }

    /** the unique identifier for this type */
    public int getCode() { return code; }
    /** the length of the public key, in bytes */
    public int getPubkeyLen() { return pubkeyLen; }
    /** the length of the private key, in bytes */
    public int getPrivkeyLen() { return privkeyLen; }
    /** the length of the hash, in bytes */
    public int getHashLen() { return hashLen; }
    /** the length of the signature, in bytes */
    public int getSigLen() { return sigLen; }
    /** the standard base algorithm name used for the Java crypto factories */
    public SigAlgo getBaseAlgorithm() { return base; }
    /** the standard name used for the Java crypto factories */
    public String getAlgorithmName() { return algoName; }
    /**
     *  The elliptic curve ECParameterSpec for ECDSA; DSAParameterSpec for DSA
     *  @throws InvalidParameterSpecException if the algorithm is not available on this JVM.
     */
    public AlgorithmParameterSpec getParams() throws InvalidParameterSpecException {
        if (params == null)
            throw new InvalidParameterSpecException(toString() + " is not available in this JVM");
        return params;
    }

    /** @throws UnsupportedOperationException if not supported */
    public MessageDigest getDigestInstance() {
        if (digestName.equals("SHA-1"))
            return SHA1.getInstance();
        if (digestName.equals("SHA-256"))
            return SHA256Generator.getDigestInstance();
        try {
            return MessageDigest.getInstance(digestName);
        } catch (NoSuchAlgorithmException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    /**
     *  @since 0.9.9
     *  @throws UnsupportedOperationException if not supported
     */
    public SimpleDataStructure getHashInstance() {
        switch (getHashLen()) {
            case 20:
                return new SHA1Hash();
            case 32:
                return new Hash();
            case 48:
                return new Hash384();
            case 64:
                return new Hash512();
            default:
                throw new UnsupportedOperationException("Unsupported hash length: " + getHashLen());
        }
    }

    /**
     *  @since 0.9.12
     *  @return true if supported in this JVM
     */
    public boolean isAvailable() {
        if (DSA_SHA1 == this)
            return true;
        try {
            getParams();
            Signature.getInstance(getAlgorithmName());
            getDigestInstance();
            getHashInstance();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private static final Map<Integer, SigType> BY_CODE = new HashMap<Integer, SigType>();

    static {
        for (SigType type : SigType.values()) {
            BY_CODE.put(Integer.valueOf(type.getCode()), type);
        }
    }

    /** @return null if not supported */
    public static SigType getByCode(int code) {
        return BY_CODE.get(Integer.valueOf(code));
    }

    /**
     *  Convenience for user apps
     *
     *  @param stype number or name
     *  @return null if not found
     *  @since 0.9.9 moved from SU3File in 0.9.12
     */
    public static SigType parseSigType(String stype) {
        try {
            return valueOf(stype.toUpperCase(Locale.US));
        } catch (IllegalArgumentException iae) {
            try {
                int code = Integer.parseInt(stype);
                return getByCode(code);
            } catch (NumberFormatException nfe) {
                return null;
             }
        }
    }
}
