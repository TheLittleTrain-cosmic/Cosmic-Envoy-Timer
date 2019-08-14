package com.TheLittleTrain_.EnvoyTimer;

import com.TheLittleTrain_.EnvoyTimer.proxy.CommonProxy;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.MINECRAFT_VERSION)
public class EnvoyTimer {

    @Instance
    public static EnvoyTimer instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
    }
}
