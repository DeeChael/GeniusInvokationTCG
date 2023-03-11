package net.deechael.invokationtcg;

import net.deechael.invokationtcg.card.Costable;
import net.deechael.invokationtcg.character.elementalburst.ElementalBurst;
import net.deechael.invokationtcg.character.elementalskill.ElementalSkill;
import net.deechael.invokationtcg.character.normalattack.NormalAttack;
import net.deechael.invokationtcg.network.packet.*;
import net.deechael.invokationtcg.operation.Operation;
import net.deechael.invokationtcg.triggerable.TriggerAt;
import net.deechael.invokationtcg.triggerable.summon.Summon;
import net.deechael.invokationtcg.triggerable.support.Support;
import net.deechael.invokationtcg.triggerable.support.type.SupportType;
import net.deechael.invokationtcg.util.function.SupportTrigger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Match {

    private final static Random RANDOM = new Random();

    private int roundIndex = 1;
    private final Player host;
    private final Player guest;
    private RoundIndex next;
    private final List<Operation> operations = new ArrayList<>();

    public Match(Player host, Player guest) {
        this.host = host;
        this.guest = guest;
        this.next = RANDOM.nextBoolean() ? RoundIndex.HOST : RoundIndex.GUEST;
        this.operations.add(Operation.Info.matchStart(this.host.getUser(), this.guest.getUser(), this.next));
    }

    private void triggerSummons(TriggerAt at, Round round, Player player) {
        for (Summon summon : player.getSummons())
            if (summon.getSummonType().getTriggerAt() == at)
                summon.getSummonType().trigger(this, round, round.getNextOperator(), round.getLastOperator());
    }

    public void start() {
        while (!this.host.isEnded() && !this.guest.isEnded()) {
            Round round = new Round(this.host, this.guest, this.roundIndex, this.next);
            this.operations.add(Operation.Info.roundStart(this.roundIndex));
            triggerSummons(TriggerAt.TURN_START, round, round.getNextOperator());
            triggerSummons(TriggerAt.TURN_START, round, round.getLastOperator());
            while (!round.isEnded()) {
                for (Support support : round.getNextOperator().getSupports())
                    support.getSupportType().onRoundStart(this, round, round.getNextOperator(), round.getLastOperator());
                for (Support support : round.getLastOperator().getSupports())
                    support.getSupportType().onRoundStart(this, round, round.getLastOperator(), round.getNextOperator());
                Player nextPlayer = round.getNextOperator();
                while (true) {
                    Packet packet = nextPlayer.waitForPacket();
                    if (packet instanceof AttackRelatedPacket) {
                        AttackRelatedPacket attackRelatedPacket = (AttackRelatedPacket) packet;
                        Costable costable;
                        SupportTrigger<?> selfTrigger;
                        SupportTrigger<?> enemyTrigger;
                        if (packet instanceof NormalAttackPacket) {
                            costable = nextPlayer.getCurrentCharacter().getCharacterCard().getNormalAttack();
                            SupportTrigger<NormalAttack> selfNormalAttackTrigger = SupportType::onSelfNormalAttack;
                            SupportTrigger<NormalAttack> enemyNormalAttackTrigger = SupportType::onEnemyNormalAttack;
                            selfTrigger = selfNormalAttackTrigger;
                            enemyTrigger = enemyNormalAttackTrigger;
                        } else if (packet instanceof ElementalSkillPacket) {
                            costable = nextPlayer.getCurrentCharacter().getCharacterCard().getElementalSkill();
                            SupportTrigger<ElementalSkill> selfElementalSkillTrigger = SupportType::onSelfElementalSkill;
                            SupportTrigger<ElementalSkill> enemyElementalSkillTrigger = SupportType::onEnemyElementalSkill;
                            selfTrigger = selfElementalSkillTrigger;
                            enemyTrigger = enemyElementalSkillTrigger;
                        } else if (packet instanceof ElementalBurstPacket) {
                            costable = nextPlayer.getCurrentCharacter().getCharacterCard().getElementalBurst();
                            SupportTrigger<ElementalBurst> selfElementalBurstTrigger = SupportType::onSelfElementalBurst;
                            SupportTrigger<ElementalBurst> enemyElementalBurstTrigger = SupportType::onEnemyElementalBurst;
                            selfTrigger = selfElementalBurstTrigger;
                            enemyTrigger = enemyElementalBurstTrigger;
                        } else {
                            nextPlayer.getUser().getConnection().sendPacket(new IllegalOperationPacket("Bad attack packet received!"));
                            continue;
                        }
                        if (((attackRelatedPacket.isHost() && this.host == nextPlayer)) ||
                                (!attackRelatedPacket.isHost() && this.guest == nextPlayer)) {
                            if (!nextPlayer.canAfford(costable)) {
                                nextPlayer.getUser().getConnection().sendPacket(new IllegalOperationPacket("You can't afford it"));
                                continue;
                            }
                            round.getLastOperator().getUser().getConnection().sendPacket(attackRelatedPacket); // Used for playing animation in frontend
                            for (Support support : round.getNextOperator().getSupports())
                                selfTrigger.castAndTrigger(support.getSupportType(), this, round, round.getNextOperator(), round.getLastOperator(), round.getNextOperator().getCurrentCharacter(), costable);
                            for (Support support : round.getLastOperator().getSupports())
                                enemyTrigger.castAndTrigger(support.getSupportType(), this, round, round.getLastOperator(), round.getNextOperator(), round.getLastOperator().getCurrentCharacter(), costable);
                            break;
                        } else {
                            nextPlayer.getUser().getConnection().sendPacket(new IllegalOperationPacket("It's not your turn"));
                        }
                    }
                }
                for (Support support : round.getNextOperator().getSupports())
                    support.getSupportType().onSelfTurnEnd(this, round, round.getNextOperator(), round.getLastOperator());
                for (Support support : round.getLastOperator().getSupports())
                    support.getSupportType().onEnemyTurnEnd(this, round, round.getLastOperator(), round.getNextOperator());
                // TODO send update information packet
            }
            triggerSummons(TriggerAt.TURN_END, round, round.getNextOperator());
            triggerSummons(TriggerAt.TURN_END, round, round.getLastOperator());
            for (Support support : round.getNextOperator().getSupports())
                support.getSupportType().onRoundEnd(this, round, round.getNextOperator(), round.getLastOperator());
            for (Support support : round.getLastOperator().getSupports())
                support.getSupportType().onRoundEnd(this, round, round.getLastOperator(), round.getNextOperator());
            this.operations.add(Operation.Info.roundEnd(this.roundIndex));
            this.roundIndex++;
            this.next = round.getNextRoundIndex();
        }
    }

    public void addOperation(Operation operation) {
        this.operations.add(operation);
    }

    public int getRoundIndex() {
        return roundIndex;
    }

    public Player getGuest() {
        return guest;
    }

    public Player getHost() {
        return host;
    }

    public RoundIndex getNext() {
        return next;
    }

    public List<Operation> getOperations() {
        return new ArrayList<>(this.operations);
    }

}
