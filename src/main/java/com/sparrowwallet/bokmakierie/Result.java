package com.sparrowwallet.bokmakierie;

public class Result {
    private final String message;
    private final byte[] rawBytes;
    private final int version;

    public Result(String message, byte[] rawBytes, int version) {
        this.message = message;
        this.rawBytes = rawBytes;
        this.version = version;
    }

    public String getMessage() {
        return message;
    }

    public byte[] getRawBytes() {
        return rawBytes;
    }

    public int getVersion() {
        return version;
    }
}
