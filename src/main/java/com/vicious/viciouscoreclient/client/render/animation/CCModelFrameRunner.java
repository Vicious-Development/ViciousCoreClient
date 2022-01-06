package com.vicious.viciouscoreclient.client.render.animation;

import codechicken.lib.render.CCModel;
import codechicken.lib.vec.Rotation;
import codechicken.lib.vec.Scale;
import codechicken.lib.vec.Translation;
import com.vicious.viciouscoreclient.client.configuration.ItemTransformOverrideCFG;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public abstract class CCModelFrameRunner extends AnimationFrameRunner {
    public CCModelFrameRunner(){}
    public abstract CCModel run(CCModel model);
    //Applies models changes made in the previous frame.

    //Base level animations. Nothing really special, useful for avoiding repeat code.

    //Rotates an object at a variable rate when ran. Also works as a constant rotator if used in that way
    public static class Rotator extends CCModelFrameRunner{
        public Rotator(Supplier<Double> rotationx, Supplier<Double> rotationy, Supplier<Double> rotationz, Supplier<Double> x, Supplier<Double> y, Supplier<Double> z){
            rx=rotationx;
            ry=rotationy;
            rz=rotationz;
            this.rxf =x;
            this.rxy =y;
            this.rxz =z;
        }
        @Override
        public CCModel run(CCModel model) {
            if(!willRotate()) return model;
            model = model.copy();
            if(rxf != null) model.apply(new Rotation(rx.get(), rxf.get(),0,0));
            if(rxy != null) model.apply(new Rotation(ry.get(), 0, rxy.get(),0));
            if(rxz != null) model.apply(new Rotation(rz.get(), 0,0, rxz.get()));
            return model;
        }
        public boolean willRotate(){
            return !(rx == null && ry == null && rz == null) || !(rxf == null && rxy == null && rxz == null);
        }
    }
    //Translates an object at a variable rate when ran. Also works as a constant translator if used in that way
    public static class Translator extends CCModelFrameRunner{
        public Translator(Supplier<Double> translationx, Supplier<Double> translationy, Supplier<Double> translationz){
            tx=translationx;
            ty=translationy;
            tz=translationz;
        }
        @Override
        public CCModel run(CCModel model) {
            if(!willTranslate()) return model;
            model = model.copy();
            if(tx != null) model.apply(new Translation(tx.get(),0,0));
            if(ty != null) model.apply(new Translation(0,ty.get(),0));
            if(tz != null) model.apply(new Translation(0,0,tz.get()));
            return model;
        }
        public boolean willTranslate(){
            return !(tx == null && ty == null && tz == null);
        }
    }
    //Performs multiple frame transformations.
    public static class Multi extends CCModelFrameRunner{
        private List<CCModelFrameRunner> frames = new ArrayList<>();
        public Multi(CCModelFrameRunner... frames){
            this.frames.addAll(Arrays.asList(frames));
        }
        @Override
        public CCModel run(CCModel model) {
            for (CCModelFrameRunner frame : frames) {
                model = frame.run(model);
            }
            return model;
        }
    }

    public static class Configurate {
        private ItemTransformOverrideCFG cfg;
        private ItemCameraTransforms.TransformType type;

        public Configurate(ItemTransformOverrideCFG r, ItemCameraTransforms.TransformType type) {
            cfg = r;
            this.type=type;
        }

        public CCModel run(CCModel model, double x, double y, double z, float yaw, float partialticks) {
            if(!cfg.active.getBoolean()) return model;
            model = model.copy();
            if(type == ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND || type == ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND) {
                if (cfg.overrideRotation.getBoolean()) {
                    model.apply(new Rotation(Math.toRadians(cfg.rx.value()), 1, 0, 0));
                    model.apply(new Rotation(Math.toRadians(cfg.ry.value()), 0, 1, 0));
                    model.apply(new Rotation(Math.toRadians(cfg.rz.value()), 0, 0, 1));
                }
                if (cfg.overrideTranslation.getBoolean()) {
                    model.apply(new Translation(cfg.tx.value(), cfg.ty.value(), cfg.tz.value()));
                }
                if (cfg.overrideScale.getBoolean()) {
                    model.apply(new Scale(cfg.sx.value(), cfg.sy.value(), cfg.sz.value()));
                }
            }
            else if(type == ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND || type == ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND) {
                if (cfg.fpoverrideRotation.getBoolean()) {
                    model.apply(new Rotation(Math.toRadians(cfg.frx.value()), 1, 0, 0));
                    model.apply(new Rotation(Math.toRadians(cfg.fry.value()), 0, 1, 0));
                    model.apply(new Rotation(Math.toRadians(cfg.frz.value()), 0, 0, 1));
                }
                if (cfg.fpoverrideTranslation.getBoolean()) {
                    model.apply(new Translation(cfg.ftx.value(), cfg.fty.value(), cfg.ftz.value()));
                }
                if (cfg.fpoverrideScale.getBoolean()) {
                    model.apply(new Scale(cfg.fsx.value(), cfg.fsy.value(), cfg.fsz.value()));
                }
            }
            else if(type == ItemCameraTransforms.TransformType.GUI || type == ItemCameraTransforms.TransformType.FIXED) {
                if (cfg.guioverrideRotation.getBoolean()) {
                    model.apply(new Rotation(Math.toRadians(cfg.grx.value()), 1, 0, 0));
                    model.apply(new Rotation(Math.toRadians(cfg.gry.value()), 0, 1, 0));
                    model.apply(new Rotation(Math.toRadians(cfg.grz.value()), 0, 0, 1));
                }
                if (cfg.guioverrideTranslation.getBoolean()) {
                    model.apply(new Translation(cfg.gtx.value(), cfg.gty.value(), cfg.gtz.value()));
                }
                if (cfg.guioverrideScale.getBoolean()) {
                    model.apply(new Scale(cfg.gsx.value(), cfg.gsy.value(), cfg.gsz.value()));
                }
            }
            return model;
        }
    }
}
