package elucent.eidolon.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import elucent.eidolon.Eidolon;
import elucent.eidolon.capability.IPlayerData;
import elucent.eidolon.capability.ISoul;
import elucent.eidolon.client.model.*;
import elucent.eidolon.client.renderer.*;
import elucent.eidolon.common.item.IManaRelatedItem;
import elucent.eidolon.common.item.IWingsItem;
import elucent.eidolon.common.item.curio.RavenCloakRenderer;
import elucent.eidolon.common.item.curio.SanguineAmuletItem;
import elucent.eidolon.common.item.model.*;
import elucent.eidolon.common.tile.CrucibleTileRenderer;
import elucent.eidolon.event.ClientEvents;
import elucent.eidolon.registries.EidolonEntities;
import elucent.eidolon.registries.EidolonPotions;
import elucent.eidolon.registries.Registry;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Random;

@Mod.EventBusSubscriber(modid = Eidolon.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistry {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerTooltip(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(SanguineAmuletItem.SanguineAmuletTooltipInfo.class, SanguineAmuletItem.SanguineAmuletTooltipComponent::new);
    }

    public static final ModelLayerLocation SILVER_ARMOR_LAYER = new ModelLayerLocation(new ResourceLocation(Eidolon.MODID, "silver_armor"), "main");
    public static final ModelLayerLocation WARLOCK_ARMOR_LAYER = new ModelLayerLocation(new ResourceLocation(Eidolon.MODID, "warlock_armor"), "main");
    public static final ModelLayerLocation BONELORD_ARMOR_LAYER = new ModelLayerLocation(new ResourceLocation(Eidolon.MODID, "bonelord_armor"), "main");
    public static final ModelLayerLocation TOP_HAT_LAYER = new ModelLayerLocation(new ResourceLocation(Eidolon.MODID, "top_hat"), "main");
    public static final ModelLayerLocation RAVEN_LAYER = new ModelLayerLocation(new ResourceLocation(Eidolon.MODID, "raven"), "main");
    public static final ModelLayerLocation NECROMANCER_LAYER = new ModelLayerLocation(new ResourceLocation(Eidolon.MODID, "necromancer"), "main");
    public static final ModelLayerLocation WRAITH_LAYER = new ModelLayerLocation(new ResourceLocation(Eidolon.MODID, "wraith"), "main");
    public static final ModelLayerLocation ZOMBIE_BRUTE_LAYER = new ModelLayerLocation(new ResourceLocation(Eidolon.MODID, "zombie_brute"), "main");
    public static final ModelLayerLocation SLUG_LAYER = new ModelLayerLocation(new ResourceLocation(Eidolon.MODID, "slimy_slug"), "main");
    public static final ModelLayerLocation GIANT_SKEL_LAYER = new ModelLayerLocation(new ResourceLocation(Eidolon.MODID, "giant_skeleton"), "main");

    public static final ModelLayerLocation CRUCIBLE_STIRRER_LAYER = new ModelLayerLocation(new ResourceLocation(Eidolon.MODID, "crucible_stirrer"), "main");
    public static final ModelLayerLocation RAVEN_CLOAK_LAYER = new ModelLayerLocation(new ResourceLocation(Eidolon.MODID, "raven_cloak"), "main");

    public static WarlockArmorModel WARLOCK_ARMOR_MODEL = null;
    public static BonelordArmorModel BONELORD_ARMOR_MODEL = null;
    public static TopHatModel TOP_HAT_MODEL = null;
    public static SilverArmorModel SILVER_ARMOR_MODEL = null;
    public static ZombieBruteModel ZOMBIE_BRUTE_MODEL = null;
    public static BruteSkeletonModel GIANT_SKEL_MODEL = null;
    public static WraithModel WRAITH_MODEL = null;
    public static RavenModel RAVEN_MODEL = null;
    public static NecromancerModel NECROMANCER_MODEL = null;
    public static SlimySlugModel SLUG_MODEL = null;

    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(WARLOCK_ARMOR_LAYER, WarlockArmorModel::createBodyLayer);
        event.registerLayerDefinition(BONELORD_ARMOR_LAYER, BonelordArmorModel::createBodyLayer);
        event.registerLayerDefinition(TOP_HAT_LAYER, TopHatModel::createBodyLayer);
        event.registerLayerDefinition(SILVER_ARMOR_LAYER, SilverArmorModel::createBodyLayer);
        event.registerLayerDefinition(RAVEN_CLOAK_LAYER, RavenCloakModel::createBodyLayer);

        event.registerLayerDefinition(RAVEN_LAYER, RavenModel::createBodyLayer);
        event.registerLayerDefinition(ZOMBIE_BRUTE_LAYER, ZombieBruteModel::createBodyLayer);
        event.registerLayerDefinition(WRAITH_LAYER, WraithModel::createBodyLayer);
        event.registerLayerDefinition(NECROMANCER_LAYER, NecromancerModel::createBodyLayer);
        event.registerLayerDefinition(SLUG_LAYER, SlimySlugModel::createBodyLayer);
        event.registerLayerDefinition(GIANT_SKEL_LAYER, BruteSkeletonModel::createBodyLayer);

        event.registerLayerDefinition(CRUCIBLE_STIRRER_LAYER, CrucibleTileRenderer::createModelLayer);
    }

    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.AddLayers event) {
        WARLOCK_ARMOR_MODEL = new WarlockArmorModel(event.getEntityModels().bakeLayer(WARLOCK_ARMOR_LAYER));
        BONELORD_ARMOR_MODEL = new BonelordArmorModel(event.getEntityModels().bakeLayer(BONELORD_ARMOR_LAYER));
        TOP_HAT_MODEL = new TopHatModel(event.getEntityModels().bakeLayer(TOP_HAT_LAYER));
        SILVER_ARMOR_MODEL = new SilverArmorModel(event.getEntityModels().bakeLayer(SILVER_ARMOR_LAYER));

        RAVEN_MODEL = new RavenModel(event.getEntityModels().bakeLayer(RAVEN_LAYER));
        ZOMBIE_BRUTE_MODEL = new ZombieBruteModel(event.getEntityModels().bakeLayer(ZOMBIE_BRUTE_LAYER));
        GIANT_SKEL_MODEL = new BruteSkeletonModel(event.getEntityModels().bakeLayer(GIANT_SKEL_LAYER));
        WRAITH_MODEL = new WraithModel(event.getEntityModels().bakeLayer(WRAITH_LAYER));
        NECROMANCER_MODEL = new NecromancerModel(event.getEntityModels().bakeLayer(NECROMANCER_LAYER));
        SLUG_MODEL = new SlimySlugModel(event.getEntityModels().bakeLayer(SLUG_LAYER));
    }

    @SubscribeEvent
    public static void onRegisterEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
        EntityRenderers.register(EidolonEntities.ZOMBIE_BRUTE.get(), ZombieBruteRenderer::new);
        EntityRenderers.register(EidolonEntities.GIANT_SKEL.get(), GiantSkeletonRenderer::new);
        EntityRenderers.register(EidolonEntities.WRAITH.get(), WraithRenderer::new);
        EntityRenderers.register(EidolonEntities.NECROMANCER.get(), NecromancerRenderer::new);
        EntityRenderers.register(EidolonEntities.SOULFIRE_PROJECTILE.get(), NoopRenderer::new);
        EntityRenderers.register(EidolonEntities.BONECHILL_PROJECTILE.get(), NoopRenderer::new);
        EntityRenderers.register(EidolonEntities.NECROMANCER_SPELL.get(), NoopRenderer::new);
        EntityRenderers.register(EidolonEntities.CHANT_CASTER.get(), ChantCasterRenderer::new);
        EntityRenderers.register(EidolonEntities.RAVEN.get(), RavenRenderer::new);
        EntityRenderers.register(EidolonEntities.SLIMY_SLUG.get(), SlimySlugRenderer::new);
    }

    public static ShaderInstance GLOWING_SHADER, GLOWING_SPRITE_SHADER, GLOWING_PARTICLE_SHADER, VAPOR_SHADER, GLOWING_ENTITY_SHADER, SPRITE_PARTICLE_SHADER;

    public static ShaderInstance getGlowingShader() {
        return GLOWING_SHADER;
    }

    public static ShaderInstance getGlowingSpriteShader() {
        return GLOWING_SPRITE_SHADER;
    }

    public static ShaderInstance getGlowingParticleShader() {
        return GLOWING_PARTICLE_SHADER;
    }

    public static ShaderInstance getGlowingEntityShader() {
        return GLOWING_ENTITY_SHADER;
    }

    public static ShaderInstance getVaporShader() {
        return VAPOR_SHADER;
    }

    public static ShaderInstance getSpriteParticleShader() {
        return SPRITE_PARTICLE_SHADER;
    }

    @SubscribeEvent
    public static void shaderRegistry(RegisterShadersEvent event) throws IOException {
        event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation("eidolon:glowing"), DefaultVertexFormat.POSITION_COLOR),
                shader -> GLOWING_SHADER = shader);
        event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation("eidolon:glowing_sprite"), DefaultVertexFormat.POSITION_TEX_COLOR),
                shader -> GLOWING_SPRITE_SHADER = shader);
        event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation("eidolon:glowing_particle"), DefaultVertexFormat.PARTICLE),
                shader -> GLOWING_PARTICLE_SHADER = shader);
        event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation("eidolon:glowing_entity"), DefaultVertexFormat.NEW_ENTITY),
                shader -> GLOWING_ENTITY_SHADER = shader);
        event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation("eidolon:vapor"), DefaultVertexFormat.BLOCK),
                shader -> VAPOR_SHADER = shader);
        event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation("eidolon:sprite_particle"), DefaultVertexFormat.PARTICLE),
                shader -> SPRITE_PARTICLE_SHADER = shader);
    }

    public static void initCurios() {
        CuriosRendererRegistry.register(Registry.RAVEN_CLOAK.get(), RavenCloakRenderer::new);
    }

    protected static final ResourceLocation ICONS_TEXTURE = new ResourceLocation(Eidolon.MODID, "textures/gui/icons.png");
    protected static final ResourceLocation MANA_BAR_TEXTURE = new ResourceLocation(Eidolon.MODID, "textures/gui/mana_bar.png");

    public static class EidolonManaBar implements IGuiOverlay {
        int xPos() {
            String origin = ClientConfig.MANA_BAR_POSITION.get();
            if (origin.equals(ClientConfig.Positions.BOTTOM_LEFT)
                || origin.equals(ClientConfig.Positions.LEFT)
                || origin.equals(ClientConfig.Positions.TOP_LEFT))
                return -1;
            if (origin.equals(ClientConfig.Positions.BOTTOM_RIGHT)
                || origin.equals(ClientConfig.Positions.RIGHT)
                || origin.equals(ClientConfig.Positions.TOP_RIGHT))
                return 1;
            return 0;
        }

        int yPos() {
            String origin = ClientConfig.MANA_BAR_POSITION.get();
            if (origin.equals(ClientConfig.Positions.TOP)
                || origin.equals(ClientConfig.Positions.TOP_LEFT))
                return -1;
            if (origin.equals(ClientConfig.Positions.BOTTOM_LEFT)
                || origin.equals(ClientConfig.Positions.BOTTOM_RIGHT))
                return 1;
            return 0;
        }

        boolean horiz() {
            String orient = ClientConfig.MANA_BAR_ORIENTATION.get();
            String origin = ClientConfig.MANA_BAR_POSITION.get();
            if (orient.equals(ClientConfig.Orientations.HORIZONTAL)) return true;
            else if (orient.equals(ClientConfig.Orientations.VERTICAL)) return false;
            else return !origin.equals(ClientConfig.Positions.LEFT)
                        && !origin.equals(ClientConfig.Positions.RIGHT);
        }

        @Override
        public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTicks, int width, int height) {
            Minecraft mc = Minecraft.getInstance();
            LocalPlayer player = mc.player;
            var mStack = guiGraphics.pose();
            if (player == null) return;
            int xp = xPos(), yp = yPos();
            boolean isHoriz = horiz();

            int w = isHoriz ? 120 : 28, h = isHoriz ? 28 : 120;

            int ox = width / 2 - w / 2;
            int oy = height / 2 - h / 2;
            if (isHoriz) {
                if (yp == -1) oy = 4;
                else if (yp == 1) oy = height + 4 - h;
                if (xp == -1) ox = 8;
                else if (xp == 1) ox = width - 4 - w;
            } else {
                if (yp == -1) oy = -8;
                else if (yp == 1) oy = height - 20 - h;
                if (xp == -1) ox = 4;
                else if (xp == 1) ox = width + 4 - w;
            }

            final int barlength = 114;
            float magic = 0, maxMagic = 0;
            try {
                ISoul soul = player.getCapability(ISoul.INSTANCE).resolve().get();
                magic = soul.getMagic();
                maxMagic = soul.getMaxMagic();
            } catch (NoSuchElementException e) {
                //
            }
            if (maxMagic == 0) return;
            if (!(player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof IManaRelatedItem)
                && !(player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof IManaRelatedItem))
                return;

            int length = Mth.ceil(barlength * magic / maxMagic);

            int iconU = 48, iconV = 48;

            mStack.pushPose();
            mStack.translate(0, 0, 0.01);
            if (isHoriz) {
                ox -= 4;
                guiGraphics.blit(MANA_BAR_TEXTURE, ox, oy, 2, length == 0 ? 6 : 38, 6, 20);
                if (xp > 0) {
                    guiGraphics.blit(MANA_BAR_TEXTURE, ox - 23, oy - 2, 0, 64, 24, 24);
                    guiGraphics.blit(MANA_BAR_TEXTURE, ox - 18, oy + 4, iconU, iconV, 12, 12);
                }
                ox += 6;

                int firstSegment = Math.min(8, length);
                length -= firstSegment;
                guiGraphics.blit(MANA_BAR_TEXTURE, ox, oy, 8, 38, firstSegment, 20);
                ox += firstSegment;
                if (firstSegment < 8) {
                    guiGraphics.blit(MANA_BAR_TEXTURE, ox, oy, 8 + firstSegment, 6, 8 - firstSegment, 20);
                    ox += 8 - firstSegment;
                }

                for (int i = 0; i < 6; i++) {
                    int segment = Math.min(16, length);
                    length -= segment;
                    guiGraphics.blit(MANA_BAR_TEXTURE, ox, oy, 16, 38, segment, 20);
                    ox += segment;
                    if (segment < 16) {
                        guiGraphics.blit(MANA_BAR_TEXTURE, ox, oy, 16 + segment, 6, 16 - segment, 20);
                        ox += 16 - segment;
                    }
                }

                int lastSegment = Math.min(8, length);
                length -= lastSegment;
                guiGraphics.blit(MANA_BAR_TEXTURE, ox, oy, 32, 38, lastSegment, 20);
                ox += lastSegment;
                if (lastSegment < 8) {
                    guiGraphics.blit(MANA_BAR_TEXTURE, ox, oy, 32 + lastSegment, 6, 8 - lastSegment, 20);
                    ox += 8 - lastSegment;
                }

                guiGraphics.blit(MANA_BAR_TEXTURE, ox, oy, 40, Mth.ceil(barlength * magic / maxMagic) == barlength ? 6 : 38, 7, 20);
                if (xp <= 0) {
                    guiGraphics.blit(MANA_BAR_TEXTURE, ox + 5, oy - 2, 32, 64, 24, 24);
                    guiGraphics.blit(MANA_BAR_TEXTURE, ox + 12, oy + 4, iconU, iconV, 12, 12);
                }
            } else {
                oy += 16;
                oy += barlength;
                guiGraphics.blit(MANA_BAR_TEXTURE, ox, oy, length == 0 ? 54 : 86, 40, 20, 6);
                if (yp < 0) {
                    guiGraphics.blit(MANA_BAR_TEXTURE, ox - 2, oy + 5, 32, 96, 24, 24);
                    guiGraphics.blit(MANA_BAR_TEXTURE, ox + 4, oy + 12, iconU, iconV, 12, 12);
                }

                int firstSegment = Math.min(8, length);
                length -= firstSegment;
                oy -= firstSegment;
                guiGraphics.blit(MANA_BAR_TEXTURE, ox, oy, 86, 32, 20, firstSegment);
                if (firstSegment < 8) {
                    oy -= 8 - firstSegment;
                    guiGraphics.blit(MANA_BAR_TEXTURE, ox, oy, 54, 32 + firstSegment, 20, 8 - firstSegment);
                }

                for (int i = 0; i < 6; i++) {
                    int segment = Math.min(16, length);
                    length -= segment;
                    oy -= segment;
                    guiGraphics.blit(MANA_BAR_TEXTURE, ox, oy, 86, 16, 20, segment);
                    if (segment < 16) {
                        oy -= 16 - segment;
                        guiGraphics.blit(MANA_BAR_TEXTURE, ox, oy, 54, 16 + segment, 20, 16 - segment);
                    }
                }

                int lastSegment = Math.min(8, length);
                length -= lastSegment;
                oy -= lastSegment;
                guiGraphics.blit(MANA_BAR_TEXTURE, ox, oy, 86, 8, 20, lastSegment);
                if (lastSegment < 8) {
                    oy -= 8 - lastSegment;
                    guiGraphics.blit(MANA_BAR_TEXTURE, ox, oy, 54, 8 + lastSegment, 20, 8 - lastSegment);
                }

                oy -= 6;
                guiGraphics.blit(MANA_BAR_TEXTURE, ox, oy, Mth.ceil(barlength * magic / maxMagic) == barlength ? 54 : 86, 2, 20, 6);
                if (yp >= 0) {
                    guiGraphics.blit(MANA_BAR_TEXTURE, ox - 2, oy - 23, 0, 96, 24, 24);
                    guiGraphics.blit(MANA_BAR_TEXTURE, ox + 4, oy - 18, iconU, iconV, 12, 12);
                }
            }

            mStack.popPose();
        }
    }

    public static class EidolonHearts implements IGuiOverlay {
        float lastEtherealHealth = 0;
        long healthBlinkTime = 0;
        long lastHealthTime = 0;

        @Override
        public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTicks, int width, int height) {
            PoseStack mStack = guiGraphics.pose();
            Minecraft mc = Minecraft.getInstance();
            LocalPlayer player = mc.player;
            if (!gui.shouldDrawSurvivalElements() || player == null) return;
            mStack.pushPose();
            mStack.translate(0, 0, 0.01);

            int health = Mth.ceil(player.getHealth());
            float absorb = Mth.ceil(player.getAbsorptionAmount());
            AttributeInstance attrMaxHealth = player.getAttribute(Attributes.MAX_HEALTH);
            float healthMax = (float) attrMaxHealth.getValue();

            float etherealHealth = 0, etherealMax = 0;
            try {
                ISoul cap = player.getCapability(ISoul.INSTANCE).resolve().get();
                etherealHealth = cap.getEtherealHealth();
                etherealMax = cap.getMaxEtherealHealth();
            } catch (NoSuchElementException e) {
                // ignore empty optional
            }

            int ticks = gui.getGuiTicks();
            boolean highlight = healthBlinkTime > (long) ticks && (healthBlinkTime - (long) ticks) / 3L % 2L == 1L;

            if (etherealHealth < this.lastEtherealHealth && player.invulnerableTime > 0) {
                this.lastHealthTime = Util.getMillis();
                this.healthBlinkTime = ticks + 20;
            } else if (etherealHealth > this.lastEtherealHealth) {
                this.lastHealthTime = Util.getMillis();
                this.healthBlinkTime = ticks + 10;
            }
            if (Util.getMillis() - this.lastHealthTime > 1000L) {
                lastEtherealHealth = health;
                lastHealthTime = Util.getMillis();
            }

            lastEtherealHealth = etherealHealth;

            float f = Math.max((float) player.getAttributeValue(Attributes.MAX_HEALTH), (float) health);
            int regen = -1;
            if (player.hasEffect(MobEffects.REGENERATION)) regen = ticks % Mth.ceil(f + 5.0F);

            Random rand = new Random();
            rand.setSeed(ticks * 312871L);

            int absorptionHearts = Mth.ceil(absorb / 2.0f) - 1;
            int hearts = Mth.ceil(healthMax / 2.0f) - 1;
            int ethHearts = Mth.ceil(etherealMax / 2.0f);
            int healthRows = Mth.ceil((healthMax + absorb) / 2.0F / 10.0F);
            int totalHealthRows = Mth.ceil((healthMax + absorb + etherealMax) / 2.0F / 10.0F);
            int rowHeight = Math.max(10 - (healthRows - 2), 3);
            int extraHealthRows = totalHealthRows - healthRows;
            int extraRowHeight = Mth.clamp(10 - (healthRows - 2), 3, 10);

            int left = width / 2 - 91;
            int top = height - ((ForgeGui) Minecraft.getInstance().gui).leftHeight + healthRows * rowHeight;
            if (rowHeight != 10) top += 10 - rowHeight;

            gui.leftHeight += extraHealthRows * extraRowHeight;

            for (int i = absorptionHearts + hearts + ethHearts; i > absorptionHearts + hearts; --i) {
                int row = (i + 1) / 10;
                int heart = (i + 1) % 10;
                int x = left + heart * 8;
                int y = top - extraRowHeight * Math.max(0, row - healthRows + 1) - rowHeight * Math.min(row, healthRows - 1);
                guiGraphics.blit(ICONS_TEXTURE, x, y, highlight ? 9 : 0, 18, 9, 9);
            }
            for (int i = absorptionHearts + hearts + ethHearts; i > absorptionHearts + hearts; --i) {
                int row = (i + 1) / 10;
                int heart = (i + 1) % 10;
                int x = left + heart * 8;
                int y = top - extraRowHeight * Math.max(0, row - healthRows + 1) - rowHeight * Math.min(row, healthRows - 1);
                int i2 = i - (Mth.ceil((healthMax + absorb) / 2.0f) - 1);
                if (i2 * 2 + 1 < etherealHealth)
                    guiGraphics.blit(ICONS_TEXTURE, x, y, 0, 9, 9, 9);
                else if (i2 * 2 + 1 == etherealHealth)
                    guiGraphics.blit(ICONS_TEXTURE, x, y, 9, 9, 9, 9);
            }
            for (int i = Mth.ceil((healthMax + absorb) / 2.0F) - 1; i >= 0; --i) {
                int row = i / 10;
                int heart = i % 10;
                int x = left + heart * 8;
                int y = top - row * rowHeight;

                if (health <= 4) y += rand.nextInt(2);
                if (i == regen) y -= 2;

                RenderSystem.enableBlend();
                if (player.hasEffect(EidolonPotions.CHILLED_EFFECT.get()) && i <= Mth.ceil(healthMax / 2.0f) - 1) {
                    if (i * 2 + 1 < health)
                        guiGraphics.blit(ICONS_TEXTURE, x, y, 0, 0, 9, 9);
                    else if (i * 2 + 1 == health)
                        guiGraphics.blit(ICONS_TEXTURE, x, y, 9, 0, 9, 9);
                }
                RenderSystem.disableBlend();
            }
            mStack.popPose();
        }
    }

    public static class EidolonRavenCharge implements IGuiOverlay {
        protected static final ResourceLocation GUI_ICONS_LOCATION = new ResourceLocation("textures/gui/icons.png");

        @Override
        public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
            PoseStack mStack = guiGraphics.pose();
            Minecraft mc = gui.getMinecraft();
            LocalPlayer player = mc.player;

            if (!gui.shouldDrawSurvivalElements() || player == null || player.onGround()) return;
            player.getCapability(IPlayerData.INSTANCE).ifPresent(d -> {

                ItemStack wings = d.getWingsItem(player);
                if (!(wings.getItem() instanceof IWingsItem wing)) return;

                int remainingFlaps = d.getWingCharges(player);

                //TODO render an icon
                //renders the number of remaining flaps
                String s = "" + remainingFlaps;
                int i1 = (screenWidth - gui.getFont().width(s)) / 2;
                int j1 = screenHeight - 46;
                guiGraphics.drawString(gui.getFont(), s, i1 + 1, j1, 0, false);
                guiGraphics.drawString(gui.getFont(), s, i1 - 1, j1, 0, false);
                guiGraphics.drawString(gui.getFont(), s, i1, j1 + 1, 0, false);
                guiGraphics.drawString(gui.getFont(), s, i1, j1 - 1, 0, false);
                guiGraphics.drawString(gui.getFont(), s, i1, j1, 6505166, false);

                if (ClientEvents.jumpTicks >= 5) {
                    gui.setupOverlayRenderState(false, false);
                    var x = screenWidth / 2 - 91;
                    guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
                    //RenderSystem.disableBlend();

                    mc.getProfiler().push("ravenJumpBar");
                    float f = (ClientEvents.jumpTicks - 5 + Minecraft.getInstance().getFrameTime()) / 15.0f;
                    int i = 182;
                    int j = (int) (f * 183.0F);
                    int k = guiGraphics.guiHeight() - 32 + 3;
                    guiGraphics.blit(GUI_ICONS_LOCATION, x, k, 0, 84, 182, 5);
                    if (j > 0) {
                        guiGraphics.blit(GUI_ICONS_LOCATION, x, k, 0, 89, j, 5);
                    }

                    mc.getProfiler().pop();

                    RenderSystem.enableBlend();
                    gui.getMinecraft().getProfiler().pop();
                    guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
                }
            });
        }
    }

}
