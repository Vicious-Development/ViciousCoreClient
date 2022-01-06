package com.vicious.viciouscoreclient.client.render.animation;

import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.vec.Matrix4;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Used to create animations easily.
 */
@SideOnly(Side.CLIENT)
@SuppressWarnings("unchecked")
public class Animation  {
    public final AnimationFrameRunner[] frames;
    public Animation(int frameCount){
        frames = new AnimationFrameRunner[frameCount];
    }
    public static <T extends Animation> T newSingleFrame(AnimationFrameRunner runner){
        Animation a = new Animation(1);
        a.addFrame(0,runner);
        return (T)a;
    }

    public static Animation empty() {
        return new Animation(0);
    }

    public CCModel runModelFrame(CCModel model, int frame){
        //Empty animation, this is usually when getAnimation is not overriden.
        if(frames.length == 0) return model;
        if(frames[frame] == null) return model;
        else{
            return ((CCModelFrameRunner)frames[frame]).run(model);
        }
    }
    public void runModelFrameAndRender(CCModel model, int frame, CCRenderState rs, Matrix4 mat){
        runModelFrame(model,frame).render(rs,mat);
    }

    public AnimationFrameRunner addFrame(int frame, AnimationFrameRunner in){
        frames[frame] = in;
        return in;
    }
    public void addFrameInRange(int start, int end, AnimationFrameRunner frame) {
        for (int i = start; i < end; i++) {
            frames[i]=frame;
        }
    }
}
