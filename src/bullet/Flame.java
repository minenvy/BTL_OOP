package bullet;

import state.GameWorldState;
import gameobject.ParticularObject;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import dataloader.Animation;
import dataloader.DataLoader;


public class Flame extends Bullet{
	
    private Animation BulletAnim;
    
    //private long timeExist, lastAttackTime;
    
    public Flame(float x, float y, float width, float height, int damage, GameWorldState gameWorld) {
            super(x, y, width, height, 100, gameWorld);
            BulletAnim = DataLoader.getInstance().getAnimation("bullet04_enemy4");
            setSpeedX(0);
            setSpeedY(0);
            timeExist = 1000*1000000;
            lastAttackTime = System.nanoTime();
            setDamage(5);
          
            setTeamType(ENEMY_TEAM);
    }  
    
    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
    	Rectangle bound = getBoundForCollisionWithMap();
    	bound.x = (int)(getPosX() - 50);
        return bound;
    }

    @Override
    public void draw(Graphics2D g2) { 
    	if(getBlood()==0) return;
        BulletAnim.Update(System.nanoTime());
        BulletAnim.draw(dx_cam(), dy_cam(),(int) getWidth(), (int) getHeight(), g2);
    }

    public void Update() {
    	super.Update();
    	if(getBlood() == 0) setState(DEATH);
        setPosX(getPosX() + getSpeedX());
        setPosY(getPosY() + getSpeedY());
        if(System.nanoTime() - lastAttackTime > timeExist) setBlood(0);
        ParticularObject object = getGameWorld().getParticularObjectManager().getCollisionWidthEnemyObject(this);
        if(object!=null && object.getState() == ALIVE){
        	object.beHurt(getDamage());
        }
    }

    @Override
    public void attack() {}
	@Override
	public void shooting() {}

}
