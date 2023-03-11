package net.deechael.invokationtcg.operation.action;

import net.deechael.invokationtcg.CharacterCardIndex;

public interface CharacterActionOperation extends ActionOperation {

    CharacterCardIndex getCharacterCardIndex();

    boolean isOnSelf();

}
