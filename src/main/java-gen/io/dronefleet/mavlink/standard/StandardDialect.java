package io.dronefleet.mavlink.standard;

import io.dronefleet.mavlink.AbstractMavlinkDialect;
import io.dronefleet.mavlink.MavlinkDialect;
import io.dronefleet.mavlink.common.CommonDialect;
import java.lang.Class;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class StandardDialect extends AbstractMavlinkDialect {
    /**
     * A list of all message types supported by this dialect.
     */
    private static final Map<Integer, Class> messages = Collections.emptyMap();

    /**
     * A list of all of the dependencies of this dialect.
     */
    private static final List<MavlinkDialect> dependencies = new ArrayList<>();

    static {
        dependencies.add(new CommonDialect());
    }

    public StandardDialect() {
        super("standard", dependencies, messages);
    }
}
