package elucent.eidolon.codex;

import elucent.eidolon.Eidolon;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SmeltingPage extends Page {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(Eidolon.MODID, "textures/gui/codex_smelting_page.png");
    final ItemStack result;
    final ItemStack input;

    public SmeltingPage(ItemStack result, ItemStack input) {
        super(BACKGROUND);
        this.result = result;
        this.input = input;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderIngredients(CodexGui gui, GuiGraphics mStack, int x, int y, int mouseX, int mouseY) {
        drawItem(mStack, input, x + 56, y + 34, mouseX, mouseY);
        drawItem(mStack, result, x + 56, y + 107, mouseX, mouseY);
    }
}
