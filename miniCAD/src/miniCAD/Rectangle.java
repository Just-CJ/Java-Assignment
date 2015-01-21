package miniCAD;
import java.awt.*;

public class Rectangle extends Shape{
	Rectangle(Point spoint, Point epoint, Color color){
		type = Type.RECTANGLE;
		startpoint = spoint;
		endpoint = epoint;
		this.color = color;
		enableTag = false;
	}
	
	public void draw(Graphics g){
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke((new BasicStroke(3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(color);
		g2d.drawRect(Math.min(startpoint.x,endpoint.x), 
				     Math.min(startpoint.y,endpoint.y), 
				     Math.abs(endpoint.x-startpoint.x), 
				     Math.abs(endpoint.y-startpoint.y));
		if(enableTag) {
			draw_tag(g,startpoint);
			draw_tag(g,endpoint);
		}
		
	}
	
	public Shape pick(Point p){
		if(((Math.abs(p.x-Math.min(endpoint.x, startpoint.x))<=threshold
		   ||Math.abs(p.x-Math.max(endpoint.x, startpoint.x))<=threshold)
		   &&(p.y>=Math.min(endpoint.y, startpoint.y)-threshold&&
			  p.y<=Math.max(endpoint.y, startpoint.y)+threshold))
	       ||(
		   (Math.abs(p.y-Math.min(endpoint.y, startpoint.y))<=threshold
		   ||Math.abs(p.y-Math.max(endpoint.y, startpoint.y))<=threshold)
		   &&(p.x>=Math.min(endpoint.x, startpoint.x)-threshold&&
			  p.x<=Math.max(endpoint.x, startpoint.x)+threshold))){
			return this;
		}
		else{
			return null;
		}
	}
	
	public Point pickanchor(Point p){
		double distance = Point.distance(startpoint.x, startpoint.y, p.x, p.y);
		if(distance <= threshold) return startpoint;
		distance = Point.distance(endpoint.x, endpoint.y, p.x, p.y);
		if(distance <= threshold) return endpoint;
		return null;
	}
}
