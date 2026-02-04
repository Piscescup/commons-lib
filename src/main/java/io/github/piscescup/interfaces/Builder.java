package io.github.piscescup.interfaces;


import io.github.piscescup.exception.IllegalBuilderPatternConfigurationException;

/**
 * A functional interface for building and producing objects of type {@code T}.
 *
 * <p>This interface represents the terminal form of the
 * <em>Builder Pattern</em>, where all required configuration has
 * been provided and the {@link #build()} method creates the final result.
 *
 * <p>Implementations are expected to:
 * <ul>
 *   <li>Validate all required state before building</li>
 *   <li>Produce a fully-initialized and consistent instance of {@code T}</li>
 *   <li>Throw an {@link IllegalBuilderPatternConfigurationException} if the pre-configuration failed</li>
 * </ul>
 *
 * <p>As a {@link FunctionalInterface}, this type may be implemented using
 * lambda expressions or method references when the construction logic
 * is simple or stateless.
 *
 * @param <T> the type of object produced by this builder
 *
 * @author REN YuanTong
 * @since 1.0.0
 */
@FunctionalInterface
public interface Builder<T> {

    /**
     * Builds and returns an instance of {@code T}.
     *
     * <p>This method represents the terminal operation of the builder.
     * It should be safe to call exactly once after all required
     * configuration has been completed.
     *
     * @return a newly built instance of {@code T}
     *
     * @throws IllegalBuilderPatternConfigurationException if the object cannot be built due to
     *         invalid state or configuration
     */
    T build() throws IllegalBuilderPatternConfigurationException;


}
