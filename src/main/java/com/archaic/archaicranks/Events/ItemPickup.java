package com.archaic.archaicranks.Events;

import com.archaic.archaicranks.Ranks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemPickup {
    @SubscribeEvent
    public void onItemPickup(EntityItemPickupEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        ItemStack pickedUpItem = event.getItem().getItem();

        ResourceLocation registryName = pickedUpItem.getItem().getRegistryName();

        int metadata = pickedUpItem.getMetadata();

        String internalName = registryName.toString() + ":" + metadata;

        System.out.println("Picked up item with internal name: " + internalName);
        Ranks.validate_item(player, internalName);
    }
}

