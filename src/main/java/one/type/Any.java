package one.type;

/**
 * Represents a value (at runtime, this is not a type) which
 * can have any type, all calls are dynamically linked.
 */
public interface Any {

    /* Operations */
    Any add(Any other);
    Any sub(Any other);
    Any mul(Any other);
    Any div(Any other);
    Any shl(Any other);
    Any shr(Any other);
    Any indexGet(Any other);
    Any indexSet(Any other);
    Any memberGet(String name);
    Any memberSet(String name);
    Any memberCall(String name, Any... args);

    /* Casting */
    boolean asBool();
    byte asByte();
    char asChar();
    short asShort();
    int asInt();
    long asLong();
    float asFloat();
    double asDouble();
    <T> T as(OneClassType<T> type);
    <T> T as(Class<T> type);

}
