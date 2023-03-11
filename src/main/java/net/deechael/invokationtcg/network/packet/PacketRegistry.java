package net.deechael.invokationtcg.network.packet;

import com.google.gson.JsonObject;
import net.deechael.dutil.gson.JOReader;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class PacketRegistry {

    private final static Map<String, Class<? extends Packet>> PACKET_TYPES = new HashMap<>();

    static {
        PACKET_TYPES.put("close", ClosePacket.class);
        PACKET_TYPES.put("illegal_operation", IllegalOperationPacket.class);
        PACKET_TYPES.put("normal_attack", NormalAttackPacket.class);
        PACKET_TYPES.put("elemental_skill", ElementalSkillPacket.class);
        PACKET_TYPES.put("elemental_burst", ElementalBurstPacket.class);
    }

    public static <T extends Packet> T createPacket(JsonObject object, Class<T> type) {
        try {
            return type.getConstructor(JsonObject.class).newInstance(object);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T extends Packet> T createPacket(JsonObject object) {
        return createPacket(object, getType(new JOReader(object).string("packetId")));
    }

    @SuppressWarnings("unchecked")
    public static <T extends Packet> Class<T> getType(String id) {
        return (Class<T>) PACKET_TYPES.get(id);
    }

    public static boolean hasType(String id) {
        return PACKET_TYPES.containsKey(id);
    }

}
