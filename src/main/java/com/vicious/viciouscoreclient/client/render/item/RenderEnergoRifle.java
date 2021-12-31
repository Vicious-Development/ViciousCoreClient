package com.vicious.viciouscoreclient.client.render.item;

import codechicken.lib.render.CCModel;
import com.vicious.viciouscoreclient.client.util.resources.ViciousClientLoader;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;

public class RenderEnergoRifle extends RenderModeledItem{
    public static CCModel defaultmodel = ViciousClientLoader.loadViciousModel("item/obj/energorifle.obj").backfacedCopy();
    @Override
    public CCModel getModel() {
        return defaultmodel;
    }

    @Override
    public void renderItem(ItemStack item, ItemCameraTransforms.TransformType transformType) {
        super.renderItem(item, transformType);
    }
    //NOTE TO SELF, IN RELATION TO PLAYER FRONT.
    // X+ rotates away from face. X- rotates towards.
    // Y+- rotates parallel to face.
    // Z+- Spins around real world y.
}