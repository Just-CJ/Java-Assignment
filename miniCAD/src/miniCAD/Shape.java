package miniCAD;

import java.awt.*;

public class Shape {
	enum Type{
		LINE, CIRCLE, RECTANGLE, TEXT
	}
	
	Point startpoint;
	Point endpoint;
	Color color;
	Type type;
	
	boolean enableTag;
	
	static double threshold=10;
	
	void draw_tag(Graphics g,Point p){
		g.setColor(new Color(0,0,0));
		g.fillRect(p.x-5, p.y-5, 10, 10);
	}
 	
	public void draw(Graphics g){}
	public Shape pick(Point p){return null;}
	public Point pickanchor(Point p){return null;}
}
