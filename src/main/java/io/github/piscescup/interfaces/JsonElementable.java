package io.github.piscescup.interfaces;

import com.google.gson.JsonElement;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an object that can be serialized to and deserialized from
 * a {@link com.google.gson.JsonElement}.
 *
 * <p>Implementations of this interface provide bidirectional conversion
 * between Java objects and their JSON representation using the
 * Gson data model.
 *
 * <p>This interface is typically used in serialization frameworks,
 * configuration systems, and data exchange layers where JSON is
 * the primary data format.
 *
 * <p>Implementations should ensure that:
 * <ul>
 *   <li>{@link #fromJson(JsonElement)} correctly restores the internal state</li>
 *   <li>{@link #toJson()} produces a valid and consistent JSON structure</li>
 * </ul>
 *
 * <p>Unless otherwise specified, implementations are not required
 * to be thread-safe.
 *
 * @author REN YuanTong
 * @since 1.0.0
 */
public interface JsonElementable {

    /**
     * Populates this object from the given JSON element.
     *
     * <p>If the provided {@code jsonElement} is {@code null},
     * the implementation may choose to reset the internal state,
     * ignore the input, or throw an exception, depending on
     * its design.
     *
     * @param jsonElement the JSON element to deserialize from,
     *                    may be {@code null}
     */
    void fromJson(@Nullable JsonElement jsonElement);

    /**
     * Converts this object into its JSON representation.
     *
     * <p>The returned {@link JsonElement} should fully represent
     * the current internal state of this object.
     *
     * <p>If this object has no meaningful JSON representation,
     * this method may return {@code null}.
     *
     * @return the JSON representation of this object,
     *         or {@code null} if unavailable
     */
    @Nullable
    JsonElement toJson();
}
