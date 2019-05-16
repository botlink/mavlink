package io.dronefleet.mavlink.ualberta;

import io.dronefleet.mavlink.AbstractMavlinkDialect;
import io.dronefleet.mavlink.MavlinkDialect;
import io.dronefleet.mavlink.common.CommonDialect;
import io.dronefleet.mavlink.util.UnmodifiableMapBuilder;
import java.lang.Class;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class UalbertaDialect extends AbstractMavlinkDialect {
    /**
     * A list of all message types supported by this dialect.
     */
    private static final Map<Integer, Class> messages = new UnmodifiableMapBuilder<Integer, Class>()
            .put(220, NavFilterBias.class)
            .put(221, RadioCalibration.class)
            .put(222, UalbertaSysStatus.class)
            .build();

    /**
     * A list of all of the dependencies of this dialect.
     */
    private static final List<MavlinkDialect> dependencies = new ArrayList<>();

    static {
        dependencies.add(new CommonDialect());
    }

    public UalbertaDialect() {
        super("ualberta", dependencies, messages);
    }
}
