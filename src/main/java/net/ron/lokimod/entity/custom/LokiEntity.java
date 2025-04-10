package net.ron.lokimod.entity.custom;

import net.minecraft.client.model.WolfModel;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.ron.lokimod.sound.ModSounds;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LokiEntity extends TamableAnimal {

    public LokiEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
//        this.getNavigation().getNodeEvaluator().setCanPassDoors(true);
//        this.setPathfindingMalus(BlockPathTypes.FENCE, 0.0F);
//        this.setPathfindingMalus(BlockPathTypes.BLOCKED, -1.0F);
//        this.setPathfindingMalus(BlockPathTypes.OPEN, 0.0F);


    }

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState sitAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    private int battleDropCount = 0;

    private int targetTimeout = 0;



    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()){
            setupAnimationStates();
        }

        if(this.getTarget() != null) {
            targetTimeout++;

            if(targetTimeout >= (20 * 15)) {
                targetTimeout = 0;
                this.setTarget((LivingEntity) null);
            }
        }

        // Debug: Print Loki's bounding box dimensions
//        if (this.tickCount % 20 == 0) { // Print every second
//            //System.out.println("Loki Bounding Box: " + this.getBoundingBox());
//            //System.out.println("tickCount:" + this.tickCount);
//        }
    }

    private void setupAnimationStates (){
        if(this.idleAnimationTimeout <= 0){
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

        if (this.isInSittingPose()) {
            this.sitAnimationState.startIfStopped(this.tickCount);
            this.walkAnimation.update(0, 0.2F);
        } else {
            this.sitAnimationState.stop();;
        }
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f;
        if (this.getPose() == Pose.STANDING) {
            f = Math.min(pPartialTick * 6f, 1f);

        } else {
            f = 0f;
        }

        this.walkAnimation.update(f, 0.2f);
    }



    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.5D, true));
        this.goalSelector.addGoal(3, new FollowOwnerGoal(this, (double)1.0F, 7.0F, 1.5F, false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, (double)1.25F));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());

        //this.setPathfindingMalus(BlockPathTypes.FENCE, 0.0F); // Stops avoiding fences
    }

    public static AttributeSupplier.Builder createAttributes() {
        return TamableAnimal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 100D)
                .add(Attributes.MOVEMENT_SPEED, (double) 0.32F)
                .add(Attributes.FOLLOW_RANGE, 24D)
                .add(Attributes.ATTACK_DAMAGE, 50D)
                .add(Attributes.ATTACK_KNOCKBACK, 1.5D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8D);


    }



    @Override
    public boolean canAttack(LivingEntity pTarget) {
//        if (pTarget instanceof LokiEntity) { // If target is a Loki
//            LokiEntity otherLoki = (LokiEntity) pTarget;
//
//            if (this.isTame() && otherLoki.isTame()){ // If both Lokis are tamed
//                if(this.getOwner() != null && this.getOwner().equals(otherLoki.getOwner())) { // If this loki has an owner, and both Lokis have the same owner
//                    return false;
//                }
//            }
//        }

        // TODO: BRING THESE TWO BACK
        if (pTarget instanceof LokiEntity) { // If target is a Loki
            LokiEntity otherLoki = (LokiEntity) pTarget;

            if (this.isTame() && otherLoki.isTame()){ // If both Lokis are tamed
                    return false;
            }
        }
        if (pTarget instanceof TamableAnimal){ // If the target is a tamableAnimal
            TamableAnimal otherAnimal = (TamableAnimal) pTarget;
            if (this.isTame() && otherAnimal.isTame()) { // If both are tamed
                if(this.getOwner() != null && this.getOwner().equals(otherAnimal.getOwner())){ // If this Loki has an owner, and both have the same owner
                    return false;
                }
            }
        }
        return super.canAttack(pTarget);
    }

    public boolean isSomethingLokiLikes (ItemStack itemStack){
        if (itemStack.is(Items.BONE) || itemStack.is(Items.BAKED_POTATO) || itemStack.is(Items.COOKED_CHICKEN) || itemStack.is(Items.CHICKEN) || itemStack.is(Items.COOKED_BEEF)){
            return true;
        } else {
            return false;
        }
    }

    public boolean isHoldingSword (ItemStack itemStack){
        return itemStack.is(Items.STONE_SWORD) || itemStack.is(Items.IRON_SWORD) || itemStack.is(Items.WOODEN_SWORD) || itemStack.is(Items.DIAMOND_SWORD) || itemStack.is(Items.NETHERITE_SWORD) || itemStack.is(Items.GOLDEN_SWORD);
    }
    @Override
    @NotNull
    public InteractionResult mobInteract(Player pPlayer, @NotNull InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        Item item = itemstack.getItem();

        if (isSomethingLokiLikes(itemstack) && !isTame()) {
            if (!pPlayer.getAbilities().instabuild) {
                itemstack.shrink(1);
            }

            if(!this.level().isClientSide) {
                this.tame(pPlayer);
                this.setOrderedToSit(true);
                this.level().broadcastEntityEvent(this, (byte)7);
            }

            return InteractionResult.SUCCESS;
        }

        if (this.isOwnedBy(pPlayer) && isTame()) {
//            if (isInSittingPose() && isHoldingSword(itemstack)) {
//                this.playSound(SoundEvents.CHICKEN_EGG);
//                this.spawnAtLocation(Items.GOLDEN_CARROT);
//                //this.spawnAtLocation(Items.EXPERIENCE_BOTTLE);
//                //this.gameEvent(GameEvent.ENTITY_PLACE);
//            }
            if (isHoldingSword(itemstack)){
                this.battleDropCount++;
                this.playSound(SoundEvents.CHICKEN_EGG);
                if (battleDropCount % 3 == 0) {
                    // TODO: Add code to spawn a splash potion here
                    ItemStack splashPotionOfHealth = PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), Potions.HEALING);
                    this.spawnAtLocation(splashPotionOfHealth);
                } else {
                    this.spawnAtLocation(Items.GOLDEN_CARROT);
                }
            } else if (itemstack.is(Items.BREAD)){
                if(!this.level().isClientSide) {
                    this.level().addFreshEntity(new ExperienceOrb(this.level(), this.getX(), this.getY(), this.getZ(), 512));
                }
                if (!pPlayer.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }

            }else if (isSomethingLokiLikes(itemstack)){
                if (this.getHealth() == this.getMaxHealth()){ // If already at max health
                    this.setOrderedToSit(!isInSittingPose()); // toggle sit
                } else {
                    float newHealth = Math.min(this.getHealth() + 4, this.getMaxHealth());
                    this.setHealth(newHealth);
                    if (!pPlayer.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }
                }
            }else {
                this.setOrderedToSit(!isInSittingPose()); // toggle sit
            }
            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(pPlayer, pHand);
    }

    @Override
    public boolean doHurtTarget(Entity pEntity) {
        boolean flag = pEntity.hurt(this.damageSources().mobAttack(this), (float) ((int) this.getAttributeValue(Attributes.ATTACK_DAMAGE)));

        if(flag) {
            this.doEnchantDamageEffects(this, pEntity);
        }

        return flag;

    }





    //    @Override
//    protected void defineSynchedData() {
//        super.defineSynchedData();
//        this.getNavigation().setSpeedModifier(0.5D);
//    }

    @NotNull
    @Override
    public EntityDimensions getDimensions(Pose pPose) {
        return EntityDimensions.scalable(0.6F, 0.85F);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        //return SoundEvents.WOLF_AMBIENT;
        int randomNum = this.random.nextInt(3);
        if (randomNum <= 1) {
            return ModSounds.LOKI_BARK_1.get();
        }
        return ModSounds.LOKI_BARK_2.get();
    }

    @Override
    public float getVoicePitch() {
        return 1.0F;
    }

    @Override
    public int getAmbientSoundInterval() {
        return (20 * 25) + this.random.nextInt(200);
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.WOLF_HURT;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.WOLF_DEATH;
    }

}
