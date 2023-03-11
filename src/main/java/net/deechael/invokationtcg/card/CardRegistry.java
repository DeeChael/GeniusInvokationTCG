package net.deechael.invokationtcg.card;

import net.deechael.invokationtcg.card.action.ActionCard;
import net.deechael.invokationtcg.card.character.Barbara;
import net.deechael.invokationtcg.card.character.CharacterCard;

import java.util.HashMap;
import java.util.Map;

public class CardRegistry {

    private final static Map<String, CharacterCard> CHARACTERS = new HashMap<>();
    private final static Map<String, ActionCard> ACTIONS = new HashMap<>();

    static {
        CHARACTERS.put("character_barbara", Barbara.INSTANCE);
    }

    public static CharacterCard getCharacterCard(String id) {
        return CHARACTERS.get(id);
    }

    public static ActionCard getActionCard(String id) {
        return ACTIONS.get(id);
    }

}
