package train.client.gui;

import java.io.IOException;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import train.common.api.AbstractTrains;
import train.common.core.network.PacketPaintbrushColor;
import train.common.Traincraft;

public class GuiPaintbrush extends GuiScreen {
	private final AbstractTrains train;

	public GuiPaintbrush(AbstractTrains train) {
		this.train = train;
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void initGui() {
		this.buttonList.clear();
		if (train.acceptedColors == null || train.acceptedColors.isEmpty()) return;
		int n = train.acceptedColors.size();
		for (int i = 0; i < n; i++) {
			int c = train.acceptedColors.get(i) & 0xFF;
			int col = i % 2;
			int row = i / 2;
			int bx = this.width / 2 - 105 + col * 105;
			int by = 40 + row * 22;
			this.buttonList.add(new GuiButton(c, bx, by, 100, 20, AbstractTrains.getColorAsString(c)));
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, "Choose a colour", this.width / 2, 15, 0xFFFFFF);
		if (train.acceptedColors == null || train.acceptedColors.isEmpty()) {
			this.drawCenteredString(this.fontRenderer, "This one cannot be painted", this.width / 2, 45, 0xFFAAAA);
		}
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	public void actionPerformed(GuiButton button) throws IOException {
		Traincraft.modChannel.sendToServer(new PacketPaintbrushColor(train.getEntityId(), button.id));
		this.mc.displayGuiScreen(null);
	}
}
