package dev.callumherr.modding.ducky_lib.book;

import cpw.mods.modlauncher.api.IEnvironment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class BookScreen extends Screen {
    private final ResourceLocation BOOK_TEXTURE;

    protected BookScreen(Component title, ResourceLocation bookTexture) {
        super(title);
        BOOK_TEXTURE = bookTexture;
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        renderBg(guiGraphics);
    }

    public void renderBg(GuiGraphics guiGraphics) {

    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 37 || keyCode == 39) return true;
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
