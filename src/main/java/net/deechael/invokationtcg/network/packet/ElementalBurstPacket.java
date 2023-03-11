package net.deechael.invokationtcg.network.packet;

import com.google.gson.JsonObject;
import net.deechael.dutil.gson.JOBuilder;
import net.deechael.dutil.gson.JOReader;

public class ElementalBurstPacket implements AttackRelatedPacket {

    private final static String PACKET_ID = "elemental_burst";

    private boolean host;

    public ElementalBurstPacket(boolean host) {
    }

    public ElementalBurstPacket(JsonObject object) {
        JOReader reader = new JOReader(object);
        this.host = reader.bool("host");
    }

    public boolean isHost() {
        return host;
    }

    @Override
    public JsonObject serialize() {
        return JOBuilder.of().string("packetId", PACKET_ID)
                .bool("host", this.host)
                .build();
    }

}
