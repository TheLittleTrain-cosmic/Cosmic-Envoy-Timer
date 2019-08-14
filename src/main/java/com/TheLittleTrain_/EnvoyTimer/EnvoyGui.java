package com.TheLittleTrain_.EnvoyTimer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.apache.logging.log4j.Logger;


public class EnvoyGui extends Gui {
    private DateTime lastEnvoy;
    public DateTime lastNaturalEnvoy;

    private int msgsSinceEnvoy = 0;
    private boolean listenForEnvoySummoner = false;
    private IChatComponent lastMessage;
    public static Logger myLog = FMLLog.getLogger();


    @SubscribeEvent
    public void renderOverlay(RenderGameOverlayEvent event) {
        if (event.type == RenderGameOverlayEvent.ElementType.TEXT) {
            Minecraft mc = Minecraft.getMinecraft();
            ScaledResolution scaled = new ScaledResolution(mc);

            Period period = new Period(lastNaturalEnvoy, new DateTime());

            PeriodFormatter formatter = new PeriodFormatterBuilder()
                    .appendMinutes().appendSuffix("m ")
                    .appendSeconds().appendSuffix("s ago")
                    .toFormatter();

            String envoyString = EnumChatFormatting.BOLD + "Last Envoy:";

            mc.fontRendererObj.drawString(envoyString, scaled.getScaledWidth() / 6, 10, 11141290, true);

            String timeString = String.format("%s%s%s", EnumChatFormatting.RESET, EnumChatFormatting.WHITE, formatter.print(period));

            mc.fontRendererObj.drawString((lastNaturalEnvoy != null) ? timeString : "", (scaled.getScaledWidth() / 6) + 72, 10, 11141290, false);
        }
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {

        if (listenForEnvoySummoner) {
            msgsSinceEnvoy++;
            if (msgsSinceEnvoy == 2) {
                if (!event.message.getUnformattedText().contains("Summoned by")) {
                    lastNaturalEnvoy = lastEnvoy;
                }
                listenForEnvoySummoner = false;
                msgsSinceEnvoy = 0;
            }
        }

        String envoyText = "A Cosmic Envoy has appeared underneath the main /spawn and all PvP /warps, supply crates can be";

        if (event.message.getUnformattedText().contains(envoyText)) {
            lastEnvoy = new DateTime();
            listenForEnvoySummoner = true;
        }
        lastMessage = event.message;
    }

    @SubscribeEvent
    public void clientDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        this.lastEnvoy = null;
        this.lastNaturalEnvoy = null;
    }
}
