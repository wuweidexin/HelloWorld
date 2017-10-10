package com.chen.javase.thinkinginjava.bookexample.containerindepth.hash;
/**
 * @author chen
 */
public class Rectangle {
	private float width;
	private float height;
	
	public Rectangle() {
		super();
	}
	
	public Rectangle(float width, float height) {
		super();
		this.width = width;
		this.height = height;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
	
	@Override
	public int hashCode() {
		final float prime = 31;  
		float result = 1;  
        result = prime * result + width;  
        result = prime * result + height;  
        return (int)result; 
	}
	@Override
	public boolean equals(Object obj) {
		if(this == obj)  
            return true;  
        if(obj == null)  
            return false;  
        if(getClass() != obj.getClass())  
            return false;  
        final Rectangle other = (Rectangle)obj;  
        if(width != other.width){  
            return false;  
        }  
        if(height != other.height){  
            return false;  
        }  
        return true;  
	}
}
