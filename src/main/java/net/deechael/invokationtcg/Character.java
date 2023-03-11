package net.deechael.invokationtcg;

import net.deechael.invokationtcg.card.character.CharacterCard;
import net.deechael.invokationtcg.triggerable.status.Status;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Character {

    private final CharacterCard characterCard;
    private int health = 10;
    private final int energy = 0;
    private final Set<ElementType> elementalApplication = new HashSet<>();
    private final List<Status> statuses = new ArrayList<>();

    public Character(CharacterCard card) {
        this.characterCard = card;

    }

    public void reduceHealth(int amount) {
        if (amount < 0)
            return;
        this.health = Math.max(0, this.health - amount);
    }

    public void reduceHealth(int amount, ElementType elementType) {
        if (amount < 0)
            return;
        // TODO elemental reaction
        this.health = Math.max(0, this.health - amount);
    }

    public void healHealth(int amount) {
        if (amount < 0)
            return;
        this.health = Math.min(10, this.health + amount);
    }

    public void addElementalApplication(ElementType type) {
        this.elementalApplication.add(type);
    }

    public void removeElementalApplication(ElementType type) {
        this.elementalApplication.remove(type);
    }

    public Set<ElementType> getElementalApplication() {
        return new HashSet<>(this.elementalApplication);
    }

    public CharacterCard getCharacterCard() {
        return characterCard;
    }

    public int getHealth() {
        return health;
    }

    public boolean isDead() {
        return health == 0;
    }

    public void setHealth(int health) {
        this.health = health;
    }

}
