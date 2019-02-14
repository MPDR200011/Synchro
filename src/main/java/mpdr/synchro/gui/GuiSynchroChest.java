package mpdr.synchro.gui;


import mpdr.synchro.Main;
import mpdr.synchro.gui.container.ContainerSynchroChest;
import mpdr.synchro.tileentities.TileEntitySynchroChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiSynchroChest extends GuiContainer{
    private IInventory playerInv;
    private TileEntitySynchroChest te;

    public GuiSynchroChest(IInventory playerInv, TileEntitySynchroChest te) {
        super(new ContainerSynchroChest(playerInv, te));

        this.playerInv = playerInv;
        this.te = te;

        xSize = 176;
        ySize = 222;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(new ResourceLocation(Main.id, "textures/gui/container/synchro_chest_container.png"));
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s = this.te.getDisplayName().getUnformattedText();
        this.fontRendererObj.drawString(s, 8, 6, 4210752);            //#404040
        this.fontRendererObj.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, 128, 4210752);      //#404040

    }
}
