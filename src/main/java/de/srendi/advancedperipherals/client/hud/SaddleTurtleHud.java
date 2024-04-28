package de.srendi.advancedperipherals.client.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class SaddleTurtleHud extends GuiComponent implements IGuiOverlay {
    public static final String ID = "saddle_turtle_hud";

    private ForgeGui gui;
    private int screenWidth = 0;
    private int screenHeight = 0;

    private int fuelLevel = 0;
    private int fuelLimit = 0;
    private int barColor = 0;

    public SaddleTurtleHud() {}

    public Font getFont() {
        return this.gui.getMinecraft().font;
    }

    public boolean shouldRender() {
        return this.fuelLimit > 0;
    }

    public void hide() {
        this.setFuelLimit(0);
    }

    public void setFuelLevel(int level) {
        if (level < 0) {
            level = 0;
        }
        this.fuelLevel = level;
    }

    public void setFuelLimit(int limit) {
        this.fuelLimit = limit;
    }

    public void setBarColor(int color) {
        this.barColor = color;
    }

    private void renderFuelBar(PoseStack stack, int left) {
        if (!shouldRender()) {
            return;
        }

        RenderSystem.setShaderTexture(0, GuiComponent.GUI_ICONS_LOCATION);
        int width = 182;
        int top = this.screenHeight - 32 + 3;
        this.blit(stack, left, top, 0, 64, width, 5);
        if (fuelLevel > 0) {
            int progWidth = fuelLevel * width / fuelLimit;
            this.blit(stack, left, top, 0, 69, progWidth, 5);
        }

        String text = String.format("%d / %d", fuelLevel, fuelLimit);
        int x = (this.screenWidth - getFont().width(text)) / 2;
        int y = this.screenHeight - 31;
        getFont().draw(stack, text, (float)(x + 1), (float) y, 0);
        getFont().draw(stack, text, (float)(x - 1), (float) y, 0);
        getFont().draw(stack, text, (float) x, (float)(y + 1), 0);
        getFont().draw(stack, text, (float) x, (float)(y - 1), 0);
        getFont().draw(stack, text, (float) x, (float) y, 8453920);
    }

    public void render(ForgeGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
        this.gui = gui;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        this.renderFuelBar(poseStack, this.screenWidth / 2 - 91);
    }
}
