package io.dronefleet.mavlink;

import java.util.*;

public class AbstractMavlinkDialect implements MavlinkDialect {

    private final String name;
    private final List<MavlinkDialect> dependencies;
    private final Map<Integer, Class> messages;

    public AbstractMavlinkDialect(
            String name,
            List<MavlinkDialect> dependencies, Map<Integer, Class> messages) {
        this.name = name;
        this.dependencies = dependencies;
        this.messages = messages;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Class resolve(int messageId) {
        if (messages.containsKey(messageId)) {
            return messages.get(messageId);
        } else {
            for (MavlinkDialect dependency : dependencies) {
                Class result = dependency.resolve(messageId);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    @Override
    public boolean supports(int messageId) {
        if (messages.containsKey(messageId)) {
            return true;
        } else {
            for (MavlinkDialect dependency : dependencies) {
                if (dependency.supports(messageId)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<Class> messageTypes() {
        Set<Class> messageTypes = new HashSet<>();
        messageTypes.addAll(messages.values());
        for (MavlinkDialect dep : dependencies) {
            messageTypes.addAll(dep.messageTypes());
        }
        List<Class> messageTypesList = new ArrayList<>(messageTypes);
        return messageTypesList;
    }
}
