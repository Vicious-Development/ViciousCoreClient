package com.vicious.viciouscoreclient.client.registries;

import com.vicious.viciouscoreclient.client.render.entity.living.RenderGenericHumanoidEntity;
import com.vicious.viciouscoreclient.client.render.projectile.RenderOrbProjectile;
import com.vicious.viciouscore.common.entity.living.GenericHumanoidEntity;
import com.vicious.viciouscore.common.entity.projectile.OrbProjectile;
import com.vicious.viciouscore.common.registries.Registrator;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderRegistry extends Registrator {
    public static void register(){
        //Entities
        RenderingRegistry.registerEntityRenderingHandler(OrbProjectile.class, RenderOrbProjectile::new);
        RenderingRegistry.registerEntityRenderingHandler(GenericHumanoidEntity.class, RenderGenericHumanoidEntity::new);
    }
}
