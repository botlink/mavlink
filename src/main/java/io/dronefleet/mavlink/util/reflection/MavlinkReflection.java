package io.dronefleet.mavlink.util.reflection;

import io.dronefleet.mavlink.annotations.MavlinkEntryInfo;
import io.dronefleet.mavlink.annotations.MavlinkMessageInfo;

import java.lang.reflect.Field;

public class MavlinkReflection {

    public static int getEnumValue(Enum entry) {
        MavlinkEntryInfo entryInfo = getEnumEntry(entry);
        if(entryInfo == null) {
            throw new IllegalArgumentException("The specified entry is not annotated with @MavlinkEntryInfo");
        }
        return entryInfo.value();
    }

    public static MavlinkEntryInfo getEnumEntry(Enum entry) {
        Field[] fields = entry.getDeclaringClass().getFields();
        for(Field field: fields) {
            if(!field.isEnumConstant()) {
                continue;
            }
            if(!field.isAnnotationPresent(MavlinkEntryInfo.class)) {
                continue;
            }
            try {
                if(entry.equals(field.get(null))) {
                    return field.getAnnotation(MavlinkEntryInfo.class);
                }
            } catch (IllegalAccessException e) {

            }
        }
        return null;
    }

    public static <T extends Enum> T getEntryByValue(Class<T> enumType, int value) {
        Field[] fields = enumType.getFields();
        for(Field field: fields) {
            if(!field.isEnumConstant()) {
                continue;
            }
            if(!field.isAnnotationPresent(MavlinkEntryInfo.class)) {
                continue;
            }
            try {
                if (field.getAnnotation(MavlinkEntryInfo.class).value() == value) {
                    return enumType.cast(field.get(null));
                }
            } catch (IllegalAccessException e) {

            }
        }
        return null;
    }

    public static MavlinkMessageInfo getMessageInfo(Object message) {
        return message.getClass().getAnnotation(MavlinkMessageInfo.class);
    }

    public static boolean isMavlinkMessage(Object o) {
        return getMessageInfo(o) != null;
    }
}
