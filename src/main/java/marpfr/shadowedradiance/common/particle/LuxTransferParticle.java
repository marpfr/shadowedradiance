package marpfr.shadowedradiance.common.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class LuxTransferParticle extends SimpleAnimatedParticle {

    protected LuxTransferParticle(ClientLevel leve, double x, double y, double z, SpriteSet spriteSet,
                                  double xSpeed, double ySpeed, double zSpeed) {
        super(leve, x, y, z, spriteSet, 0.0f);

        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;
        this.quadSize *= 0.50F;
        this.lifetime = 10 + this.random.nextInt(6);
        //this.setFadeColor(0xffffff);
        this.setSpriteFromAge(sprites);
    }

    @Override
    public void move(double x, double y, double z) {
        this.setBoundingBox(this.getBoundingBox().move(x, y, z));
        this.setLocationFromBoundingbox();
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType particleType, @NonNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, RandomSource random) {
            return new LuxTransferParticle(level, x, y, z, this.spriteSet, xSpeed, ySpeed, zSpeed);
        }
    }
}
