package io.github.mjaroslav.sharedjava.tuple.triplet;

import io.github.mjaroslav.sharedjava.tuple.Triplet;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.ToIntFunction;

/**
 * Implementation of {@link Triplet}, allows you delegating hashCode, equals and toString by functions.
 * Can contain null values and sets it by default constructor.
 *
 * @param <X> type of x (first) value
 * @param <Y> type of y (second) value
 * @param <Z> type of z (third) value
 * @author MJaroslav
 * @since 1.0.0
 */
@NoArgsConstructor
public @Data class DelegatingTriplet<X, Y, Z> implements Triplet<X, Y, Z> {
    /**
     * x (first) value, use setter and getter for it.
     *
     * @since 1.0.0
     */
    protected X x;
    /**
     * y (second) value, use setter and getter for it.
     *
     * @since 1.0.0
     */
    protected Y y;
    /**
     * z (third) value, use setter and getter for it.
     *
     * @since 1.0.0
     */
    protected Z z;

    /**
     * Delegating function for toString; Will use default behavior if null.
     * Use setter and getter for it.
     *
     * @since 1.0.0
     */
    protected @Nullable Function<Triplet<X, Y, Z>, String> toStringFunc;
    /**
     * Delegating function for hashCode; Will use default behavior if null.
     * Use setter and getter for it.
     *
     * @since 1.0.0
     */
    protected @Nullable ToIntFunction<Triplet<X, Y, Z>> hashCodeFunc;
    /**
     * Delegating function for equals; Will use default behavior if null.
     * Use setter and getter for it.
     *
     * @since 1.0.0
     */
    protected @Nullable BiPredicate<Triplet<X, Y, Z>, Object> equalsFunc;

    public DelegatingTriplet(X x, Y y, Z z) {
        set(x, y, z);
    }

    /**
     * Sets or remove (null value) toString delegating function.
     *
     * @param toStringFunc function for toString delegating; use null for default behavior
     * @return this Unit object
     * @since 1.0.0
     */
    @SuppressWarnings("UnusedReturnValue")
    @Contract("_ -> this")
    public DelegatingTriplet<X, Y, Z> setToStringFunc(@Nullable Function<Triplet<X, Y, Z>, String> toStringFunc) {
        this.toStringFunc = toStringFunc;
        return this;
    }

    /**
     * Sets or remove (null value) hashCode delegating function.
     *
     * @param hashCodeFunc function for hashCode delegating; use null for default behavior
     * @return this Unit object
     * @since 1.0.0
     */
    @SuppressWarnings("UnusedReturnValue")
    @Contract("_ -> this")
    public DelegatingTriplet<X, Y, Z> setHashCodeFunc(@Nullable ToIntFunction<Triplet<X, Y, Z>> hashCodeFunc) {
        this.hashCodeFunc = hashCodeFunc;
        return this;
    }

    /**
     * Sets or remove (null value) equals delegating function.
     *
     * @param equalsFunc function for equals delegating; use null for default behavior
     * @return this Unit object
     * @since 1.0.0
     */
    @SuppressWarnings("UnusedReturnValue")
    @Contract("_ -> this")
    public DelegatingTriplet<X, Y, Z> setEqualsFunc(@Nullable BiPredicate<Triplet<X, Y, Z>, Object> equalsFunc) {
        this.equalsFunc = equalsFunc;
        return this;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        val func = getEqualsFunc();
        return func != null ? func.test(this, obj) : this == obj || obj instanceof Triplet<?, ?, ?> triplet &&
            Objects.equals(getX(), triplet.getX()) && Objects.equals(getY(), triplet.getY()) &&
            Objects.equals(getZ(), triplet.getZ());
    }

    @Override
    public int hashCode() {
        val func = getHashCodeFunc();
        return func != null ? func.applyAsInt(this) : Objects.hash(getX(), getY(), getZ());
    }

    @Override
    public String toString() {
        val func = getToStringFunc();
        return func != null ? func.apply(this) : "DelegatingTriplet(x=" + this.getX() + ", y=" + this.getY() + ", z=" + this.getZ() + ")";
    }
}
