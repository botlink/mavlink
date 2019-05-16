package io.dronefleet.mavlink.util;

import io.dronefleet.mavlink.util.reflection.MavlinkReflection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class EnumValue<T extends Enum> {
    public static <T extends Enum> EnumValue<T> of(T entry) {
        int value = MavlinkReflection.getEnumValue(entry);
        return new EnumValue<>(value, entry);
    }

    public static <T extends Enum> EnumValue<T> create(Enum... flags) {
        return create(Arrays.asList(flags));
    }

    public static <T extends Enum> EnumValue<T> create(Collection<Enum> flags) {
        int value = 0;
        for(Enum flag:flags) {
            int bitmask = MavlinkReflection.getEnumValue(flag);
            value |= bitmask;
        }

        return create(value);
    }

    public static <T extends Enum> EnumValue<T> create(int value) {
        return new EnumValue<>(value, null);
    }

    public static <T extends Enum> EnumValue<T> create(Class<T> enumType, int value) {
        if(MavlinkReflection.getEntryByValue(enumType, value) == null) {
            return null;
        }
        return new EnumValue<>(value, MavlinkReflection.getEntryByValue(enumType, value));
    }

    private final int value;
    private final T entry;

    private EnumValue(int value, T entry) {
        this.value = value;
        this.entry = entry;
    }

    public int value() {
        return value;
    }

    public T entry() {
        return entry;
    }

    public boolean flagsEnabled(Enum... flags) {
        List<Integer> flagList = new ArrayList<>();
        for(Enum flag:flags) {
            Integer value = MavlinkReflection.getEnumValue(flag);
            if(value != null) {
                flagList.add(value);
            }
        }

        int[] enabledFlags = new int[flagList.size()];
        for(int i=0;i<enabledFlags.length;i++) {
            enabledFlags[i] = flagList.get(i).intValue();
        }

        return flagsEnabled(enabledFlags);
    }

    public boolean flagsEnabled(int... flags) {
        for(int i=0;i<flags.length;i++) {
            if(flags[i] != (value & flags[i])) {
                return false;
            }

        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EnumValue<?> enumValue = (EnumValue<?>) o;

        if (value != enumValue.value) return false;
        return entry != null ? entry.equals(enumValue.entry) : enumValue.entry == null;
    }

    @Override
    public int hashCode() {
        int result = value;
        result = 31 * result + (entry != null ? entry.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EnumValue{" +
                "value=" + value +
                ", entry=" + entry +
                '}';
    }
//    public static <T extends Enum> EnumValue<T> of(T entry) {
//        int value = MavlinkReflection.getEnumValue(entry);
//        return new EnumValue<>(value, entry);
//    }
//
//    public static <T extends Enum> EnumValue<T> create(Enum... flags) {
//        return create(Arrays.asList(flags));
//    }
//
//    public static <T extends Enum> EnumValue<T> create(Collection<Enum> flags) {
//        int value = 0;
//        for(Enum flag: flags) {
//            int bitmask = MavlinkReflection.getEnumValue(flag);
//            value |= bitmask;
//        }
//        return create(value);
//    }
//
//    public static <T extends Enum> EnumValue<T> create(int value) {
//        return new EnumValue<>(value, null);
//    }
//
//    public static <T extends Enum> EnumValue<T> create(Class<T> enumType, int value) {
//        return new EnumValue<>(
//                value,
//                MavlinkReflection.getEntryByValue(enumType, value));
//    }
//
//    private final int value;
//    private final T entry;
//
//    private EnumValue(int value, T entry) {
//        this.value = value;
//        this.entry = entry;
//    }
//
//    public int value() {
//        return value;
//    }
//
//    public T entry() {
//        return entry;
//    }
//
//    public boolean flagsEnabled(Enum... flags) {
//        int[] flagsEnabled = new int[flags.length];
//        for(int i=0;i<flags.length;i++){
//            flagsEnabled[i] = MavlinkReflection.getEnumValue(flags[i]);
//        }
//        return flagsEnabled(flagsEnabled);
//    }
//
//    public boolean flagsEnabled(int... flags) {
//        for(int i=0; i<flags.length;i++) {
//            if(flags[i] == this.value) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        EnumValue<?> enumValue = (EnumValue<?>) o;
//
//        if (value != enumValue.value) return false;
//        return entry != null ? entry.equals(enumValue.entry) : enumValue.entry == null;
//    }
//
//    @Override
//    public int hashCode() {
//        int result = value;
//        result = 31 * result + (entry != null ? entry.hashCode() : 0);
//        return result;
//    }
//
//    @Override
//    public String toString() {
//        return "EnumValue{" +
//                "value=" + value +
//                ", entry=" + entry +
//                '}';
//    }
}
