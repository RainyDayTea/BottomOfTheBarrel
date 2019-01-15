package framework;

import framework.geom.Circle;

public abstract class DamageSource {
    private boolean player;
    private Circle circle;

    public DamageSource(Circle c) {
        this.circle = c;
    }
}
