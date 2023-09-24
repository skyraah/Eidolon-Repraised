package elucent.eidolon.codex;

import elucent.eidolon.Eidolon;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CraftingPage extends Page {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(Eidolon.MODID, "textures/gui/codex_crafting_page.png");
    final ItemStack result;
    final ItemStack[] inputs;

    public CraftingPage(ItemStack result, ItemStack... inputs) {
        super(BACKGROUND);
        this.result = result;
        this.inputs = inputs;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderIngredients(CodexGui gui, GuiGraphics mStack, int x, int y, int mouseX, int mouseY) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int index = i * 3 + j;
                if (index < inputs.length && !inputs[index].isEmpty())
                    drawItem(mStack, inputs[index], x + 36 + j * 20, y + 36 + i * 20, mouseX, mouseY);
            }
        }
        drawItem(mStack, result, x + 56, y + 112, mouseX, mouseY);
    }
}
