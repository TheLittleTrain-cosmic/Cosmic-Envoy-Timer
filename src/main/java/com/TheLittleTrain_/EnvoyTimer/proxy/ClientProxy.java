package com.TheLittleTrain_.EnvoyTimer.proxy;

import com.TheLittleTrain_.EnvoyTimer.EnvoyGui;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy implements CommonProxy {
    @Override
    public void init() {
        MinecraftForge.EVENT_BUS.register(new EnvoyGui());
    }
}
