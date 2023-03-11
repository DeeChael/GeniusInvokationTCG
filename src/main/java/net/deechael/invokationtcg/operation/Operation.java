package net.deechael.invokationtcg.operation;

import net.deechael.invokationtcg.CharacterCardIndex;
import net.deechael.invokationtcg.Player;
import net.deechael.invokationtcg.RoundIndex;
import net.deechael.invokationtcg.User;
import net.deechael.invokationtcg.card.character.CharacterCard;
import net.deechael.invokationtcg.character.elementalburst.ElementalBurst;
import net.deechael.invokationtcg.operation.card.ElementalBurstOperation;
import net.deechael.invokationtcg.operation.info.MatchStartOperation;
import net.deechael.invokationtcg.operation.info.RoundEndOperation;
import net.deechael.invokationtcg.operation.info.RoundStartOperation;

public interface Operation {

    class Info {

        public static Operation matchStart(User host, User guest, RoundIndex startIndex) {
            return new MatchStartOperation(host, guest, startIndex);
        }

        public static Operation roundStart(int index) {
            return new RoundStartOperation(index);
        }

        public static Operation roundEnd(int index) {
            return new RoundEndOperation(index);
        }

    }

    class Card {

        public static Operation elementalBurst(Player who, CharacterCardIndex index, CharacterCard card, ElementalBurst burst) {
            return new ElementalBurstOperation(who, index, card, burst);
        }

    }

}
