package miniCAD;
import java.awt.*;

public class Circle extends Shape{
	Circle(Point spoint, Point epoint, Color color){
		type = Type.CIRCLE;
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
		g2d.drawOval(startpoint.x-Math.abs(endpoint.y-startpoint.y), 
				   startpoint.y-Math.abs(endpoint.y-startpoint.y), 
				   Math.abs(endpoint.y-startpoint.y)*2,
				   Math.abs(endpoint.y-startpoint.y)*2);
		
		if(enableTag){
			int y=startpoint.y+endpoint.y-startpoint.y;
			draw_tag(g,new Point(startpoint.x,y));
		}
	}
	
	public Shape pick(Point p){
		if(p.x>=(startpoint.x-Math.abs(endpoint.y-startpoint.y)-threshold)
				&&p.x<=(startpoint.x-Math.abs(endpoint.y-startpoint.y)+Math.abs(endpoint.y-startpoint.y)*2+threshold)
				&&p.y>=(startpoint.y-Math.abs(endpoint.y-startpoint.y)-threshold)
				&&p.y<=(startpoint.y-Math.abs(endpoint.y-startpoint.y)+Math.abs(endpoint.y-startpoint.y)*2+threshold)){
			double distance = Point.distance(startpoint.x, startpoint.y, p.x, p.y);
			double radius = Math.abs(endpoint.y-startpoint.y);
			if(Math.abs(distance-radius) < threshold)
				return this;
			else return null;
		}
		else{
			return null;
		}
	}
	
	public Point pickanchor(Point p){
		double distance = Point.distance(startpoint.x, startpoint.y+endpoint.y-startpoint.y, p.x, p.y);
		if(distance <= threshold) return endpoint;

		return null;
	}
}
