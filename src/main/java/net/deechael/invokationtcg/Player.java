package net.deechael.invokationtcg;

import net.deechael.invokationtcg.card.CostType;
import net.deechael.invokationtcg.card.Costable;
import net.deechael.invokationtcg.card.Deck;
import net.deechael.invokationtcg.card.action.ActionCard;
import net.deechael.invokationtcg.network.Connection;
import net.deechael.invokationtcg.network.packet.Packet;
import net.deechael.invokationtcg.triggerable.summon.Summon;
import net.deechael.invokationtcg.triggerable.support.Support;

import java.util.*;

public class Player {

    private final static Random RANDOM = new Random();

    private final User user;

    private final Character characterLeft;
    private final Character characterMiddle;
    private final Character characterRight;

    private final List<ActionCard> drawPile = new ArrayList<>();
    private final List<ActionCard> hand = new ArrayList<>();

    private final List<ElementalDice> elementalDices = new ArrayList<>();

    private final LostReason lostReason = LostReason.NOT_END;

    private CharacterCardIndex characterIndex = CharacterCardIndex.LEFT;

    private final List<Support> supports = new ArrayList<>();
    private final List<Summon> summons = new ArrayList<>();

    public Player(User user) {
        this.user = user;
        Deck currentDeck = user.getCurrentDeck();
        this.characterLeft = currentDeck.getCharacterLeft().createCharacter();
        this.characterMiddle = currentDeck.getCharacterMiddle().createCharacter();
        this.characterRight = currentDeck.getCharacterRight().createCharacter();
        this.drawPile.addAll(currentDeck.getActionCards());
    }

    public void switchCharacter(CharacterCardIndex characterIndex) {
        this.characterIndex = characterIndex;
    }

    public void drawCard(int amount) {
        if (this.hand.size() >= 10)
            return;
        int actualAmount = this.hand.size() + amount > 10 ? 10 - this.hand.size() : amount;
        for (int i = 0; i < actualAmount; i++) {
            hand.add(drawPile.remove(RANDOM.nextInt(drawPile.size())));
        }
    }

    public void redraw(ActionCard[] cards) {
        for (ActionCard card : cards) {
            this.drawPile.add(this.hand.remove(this.hand.indexOf(card)));
        }
        drawCard(cards.length);
    }

    public void redraw() {

    }

    public void roll() {
        for (int i = 0; i < 8; i++) {
            this.elementalDices.add(ElementalDice.values()[RANDOM.nextInt(8)]);
        }
    }

    public void clearDices() {
        this.elementalDices.clear();
    }

    public void addElementalDices(ElementalDice[] dices) {
        this.elementalDices.addAll(Arrays.asList(dices).subList(0, Math.min(dices.length, 16 - this.elementalDices.size())));
    }

    public void reroll(ElementalDice[] rerolling) {
        for (ElementalDice dice : rerolling) {
            this.elementalDices.remove(dice);
        }
        for (int i = 0; i < rerolling.length; i++)
            this.elementalDices.add(ElementalDice.values()[RANDOM.nextInt(8)]);
    }

    public void reroll() {

    }

    public void addSupport(Support support) {
        if (this.supports.size() >= 4)
            return;
        this.supports.add(support);
    }

    public void removeSupport(Support support) {
        this.supports.remove(support);
    }

    public List<Support> getSupports() {
        return new ArrayList<>(this.supports);
    }

    public void cleanSupports() {
        List<Support> toBeRemoved = new ArrayList<>();
        for (Support support : this.supports)
            if (!support.isUsable())
                toBeRemoved.add(support);
        this.supports.removeAll(toBeRemoved);
    }

    public void addSummon(Summon summon) {
        if (this.summons.size() >= 4)
            return;
        this.summons.add(summon);
    }

    public void removeSummon(Summon summon) {
        this.summons.remove(summon);
    }

    public List<Summon> getSummons() {
        return new ArrayList<>(summons);
    }

    public void cleanSummons() {
        List<Summon> toBeRemoved = new ArrayList<>();
        for (Summon summon : this.summons)
            if (!summon.isUsable())
                toBeRemoved.add(summon);
        this.summons.removeAll(toBeRemoved);
    }

    public User getUser() {
        return user;
    }

    public boolean isEnded() {
        return this.characterLeft.isDead() && this.characterMiddle.isDead() && this.characterRight.isDead() && this.user.isConnected();
    }

    public boolean canAfford(Costable costable) {
        return true;
    }

    public Character getCharacterLeft() {
        return characterLeft;
    }

    public Character getCharacterMiddle() {
        return characterMiddle;
    }

    public Character getCharacterRight() {
        return characterRight;
    }

    public CharacterCardIndex getCurrentCharacterIndex() {
        return characterIndex;
    }

    public Character getCurrentCharacter() {
        return this.getCharacter(this.characterIndex);
    }

    public Map<CostType, Integer> getActualCost(Costable card) {
        Map<CostType, Integer> cost = card.getCost();
        // TODO
        return cost;
    }

    public Character getCharacter(CharacterCardIndex index) {
        if (index == CharacterCardIndex.MIDDLE)
            return this.characterMiddle;
        else if (index == CharacterCardIndex.RIGHT)
            return this.characterRight;
        return this.characterLeft;
    }

    public LostReason getLostReason() {
        return this.lostReason;
    }

    public Packet waitForPacket() {
        Connection connection = this.user.getConnection();
        // TODO
        return null;
    }

}
