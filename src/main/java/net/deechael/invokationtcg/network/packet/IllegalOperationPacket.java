package net.deechael.invokationtcg.network.packet;

import com.google.gson.JsonObject;
import net.deechael.dutil.gson.JOBuilder;
import net.deechael.dutil.gson.JOReader;

public class IllegalOperationPacket implements Packet {

    private final static String PACKET_ID = "illegal_operation";

    private String message;

    public IllegalOperationPacket(String message) {
        this.message = message;
    }

    public IllegalOperationPacket(JsonObject object) {
        JOReader reader = new JOReader(object);
        this.message = reader.string("message");
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public JsonObject serialize() {
        return JOBuilder.of().string("packetId", PACKET_ID).string("message", this.message).build();
    }

}
