package net.deechael.invokationtcg.network.packet;

import com.google.gson.JsonObject;
import net.deechael.dutil.gson.JOBuilder;
import net.deechael.dutil.gson.JOReader;

public class NormalAttackPacket implements AttackRelatedPacket {

    private final static String PACKET_ID = "normal_attack";

    private boolean host;

    public NormalAttackPacket(boolean host) {
    }

    public NormalAttackPacket(JsonObject object) {
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
