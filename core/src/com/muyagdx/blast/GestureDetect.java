package com.muyagdx.blast;

import com.badlogic.gdx.input.GestureDetector;
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.math.Vector3;

public class GestureDetect extends GestureDetector {
	public interface DirectionListener {
		
		void onLeft();

		void onRight();

		void onUp();

		void onDown();
		
		
	}

	public GestureDetect(DirectionListener directionListener) {
		super(new DirectionGestureListener(directionListener));
	}
	
	public static class DirectionGestureListener extends GestureAdapter{
		DirectionListener directionListener;
		public DirectionGestureListener(DirectionListener directionListener){
			this.directionListener = directionListener;
		}
		
		@Override
        public boolean fling(float velocityX, float velocityY, int button) {
			if(Math.abs(velocityX)>Math.abs(velocityY)){
				if(velocityX>0){
						directionListener.onRight();
				}else{
						directionListener.onLeft();
				}
			}else{
				if(velocityY>0){
						directionListener.onDown();
				}else{                                  
						directionListener.onUp();
				}
			}
			return super.fling(velocityX, velocityY, button);
        }
		
//		 @Override
//		   public boolean touchDown (float x, float y, int pointer, int button) {
//		      return false;
//		   }
////		  
//		    public boolean touchUp(float x, float y, int pointer, int button) {
//		        this.
//		    	return true;
//		    }
//
//		   @Override
//		   public boolean tap (float x, float y, int count, int button) {
//		      return false;
//		   }
//
//		   @Override
//		   public boolean longPress (float x, float y) {
//		      return false;
//		   }
//
//
////		   @Override
////		   public boolean pan (float x, float y, float deltaX, float deltaY) {
////			          
////							directionListener.onPan();
////					setDeltaXY(new Vector3(deltaX, deltaY, 0));
////				return super.pan(x, y, deltaX, deltaY);
////		   }
//
//		   @Override
//		   public boolean zoom (float originalDistance, float currentDistance) {
//		      return false;
//		   }
//
//		   @Override
//		   public boolean pinch (Vector2 initialFirstPointer, Vector2 initialSecondPointer, Vector2 firstPointer, Vector2 secondPointer) {
//		      return false;
//		   }

	

	}

}
