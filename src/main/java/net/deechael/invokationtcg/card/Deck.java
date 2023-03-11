package net.deechael.invokationtcg.card;

import net.deechael.invokationtcg.card.action.ActionCard;
import net.deechael.invokationtcg.card.character.CharacterCard;

import java.util.ArrayList;
import java.util.List;

public class Deck {

    private CharacterCard characterLeft;
    private CharacterCard characterMiddle;
    private CharacterCard characterRight;

    private final List<ActionCard> actionCards = new ArrayList<>();

    public void setCharacterLeft(CharacterCard characterLeft) {
        this.characterLeft = characterLeft;
    }

    public void setCharacterMiddle(CharacterCard characterMiddle) {
        this.characterMiddle = characterMiddle;
    }

    public void setCharacterRight(CharacterCard characterRight) {
        this.characterRight = characterRight;
    }

    public CharacterCard getCharacterLeft() {
        return characterLeft;
    }

    public CharacterCard getCharacterMiddle() {
        return characterMiddle;
    }

    public CharacterCard getCharacterRight() {
        return characterRight;
    }

    public List<ActionCard> getActionCards() {
        return new ArrayList<>(this.actionCards);
    }

    public void addActionCard(ActionCard actionCard) {
        if (this.isActionCardsMaxed())
            return;
        this.actionCards.add(actionCard);
    }

    public void removeActionCard(ActionCard actionCard) {
        this.actionCards.remove(actionCard);
    }

    public boolean isActionCardsMaxed() {
        return this.actionCards.size() >= 30;
    }

    public boolean isPlayable() {
        return this.isActionCardsMaxed() && this.characterLeft != null && this.characterMiddle != null && this.characterRight != null;
    }

}
