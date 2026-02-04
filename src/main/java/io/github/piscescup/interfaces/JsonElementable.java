package io.github.piscescup.interfaces;

import com.google.gson.JsonElement;
import org.jetbrains.annotations.Nullable;

/**
 * @author REN YuanTong
 * @since 1.0.0
 */
public interface JsonElementable {
    void fromJson(@Nullable JsonElement jsonElement);

    @Nullable
    JsonElement toJson();
}
