package net.deechael.invokationtcg.network.packet;

import com.google.gson.JsonObject;
import net.deechael.dutil.gson.JOBuilder;
import net.deechael.dutil.gson.JOReader;

public class ElementalSkillPacket implements AttackRelatedPacket {

    private final static String PACKET_ID = "elemental_skill";

    private boolean host;

    public ElementalSkillPacket(boolean host) {
    }

    public ElementalSkillPacket(JsonObject object) {
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
