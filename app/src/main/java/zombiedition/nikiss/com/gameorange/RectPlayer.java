package zombiedition.nikiss.com.gameorange;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;

import zombiedition.nikiss.com.gameorange.utils.Constants;

/**
 * Created by Issouf on 22/08/2018.
 */

public class RectPlayer implements GameObject{

    private Rect rectangle;
    private int playerType;

    private  Animation idle;
    private  Animation walkRight;
    private  Animation walkLeft;
    private  AnimationManager animManager;

    private ModelPlayerManager modelPlayerManager = new ModelPlayerManager();



    /**
     * ici on choisira l'image de notre jeux
     * @param rectangle
     *
     */
    public RectPlayer(Rect rectangle){

        this.rectangle = rectangle;



        BitmapFactory bf = new BitmapFactory();
        Bitmap idleImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.alienblue);
        Bitmap walk1= bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.alienblue_walk1);
        Bitmap walk2= bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.alienblue_walk2);

        idle = new Animation(new Bitmap[] {idleImg},2);
        walkRight = new Animation(new Bitmap[] {walk1,walk2},0.5f);

        Matrix m = new Matrix();
        m.preScale(1,1);

        walk1 = Bitmap.createBitmap(walk1,0,0,walk1.getWidth(),walk1.getHeight(),m,false);
        walk2 = Bitmap.createBitmap(walk2,0,0,walk2.getWidth(),walk2.getHeight(),m,false);

        walkLeft = new Animation(new Bitmap[] {walk1,walk2},0.5f);

        animManager = new AnimationManager(new Animation[]{idle,walkRight,walkLeft});

    }



    /**
     *
     * @param rectangle
     * @param playerType donner la cle de ceux que On veux avoir comme joeur
     */
    public RectPlayer(Rect rectangle, int playerType) {
        this.rectangle = rectangle;
        this.playerType = playerType;


        int intIdle =modelPlayerManager.getPlayerHashMap().get(playerType).getIdleImg();
        int intWalk1=modelPlayerManager.getPlayerHashMap().get(playerType).getWalk1();
        int intWalk2=modelPlayerManager.getPlayerHashMap().get(playerType).getWalk2();

        BitmapFactory bf = new BitmapFactory();
        Bitmap idleImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),intIdle);
        Bitmap walk1= bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),intWalk1);
        Bitmap walk2= bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),intWalk2);

        idle = new Animation(new Bitmap[] {idleImg},2);
        walkRight = new Animation(new Bitmap[] {walk1,walk2},0.5f);

        Matrix m = new Matrix();
        m.preScale(1,1);

        walk1 = Bitmap.createBitmap(walk1,0,0,walk1.getWidth(),walk1.getHeight(),m,false);
        walk2 = Bitmap.createBitmap(walk2,0,0,walk2.getWidth(),walk2.getHeight(),m,false);

        walkLeft = new Animation(new Bitmap[] {walk1,walk2},0.5f);

        animManager = new AnimationManager(new Animation[]{idle,walkRight,walkLeft});


    }

    @Override
    public void draw(Canvas canvas) {

        //Paint paint = new Paint();
        //paint.setColor(color);
        //canvas.drawRect(rectangle,paint);

        animManager.draw(canvas,rectangle);
    }

    @Override
    public void update() {

        animManager.update();

    }

    /**
     * point est le coordonné du coin touché , il est utiliser pour redessiner l'objet ,
     * rectangle est l'objet et sera dessine en fonction de point , il aura pour centre le coin clique
     * dans le jeux.
     * @param point
     */
    public void update(Point point) {
        //l,t,r,b
        float oldLeft = rectangle.left;
        rectangle.set(point.x-rectangle.width()/2,point.y - rectangle.height()/2,point.x+rectangle.width()/2,point.y + rectangle.height()/2);

        int state = 0;
        if (rectangle.left - oldLeft > 5 ){
            state =1;
        }else if (rectangle.left - oldLeft <  -5)
            state=2;

        animManager.playAnim(state);
        animManager.update();

    }

    public Rect getRectangle() {
        return rectangle;
    }
}
