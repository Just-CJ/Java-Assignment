package miniCAD;
import java.awt.*;

public class Line extends Shape{
	Line(Point spoint, Point epoint, Color color){
		type = Type.LINE;
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
		g2d.drawLine(startpoint.x, startpoint.y, endpoint.x, endpoint.y);
		
		if(enableTag) {
			draw_tag(g,startpoint);
			draw_tag(g,endpoint);
		}
	}
	
	public Shape pick(Point p){
		if(p.x>=Math.min(endpoint.x, startpoint.x)
				&&p.x<=Math.max(endpoint.x, startpoint.x)
				&&p.y>=Math.min(endpoint.y, startpoint.y)
				&&p.y<=Math.max(endpoint.y, startpoint.y)){
			int delta_y=(endpoint.y-startpoint.y);
			int delta_x=(endpoint.x-startpoint.x);
			double k=(double)delta_y/(double)delta_x;
			double c=endpoint.y-k*endpoint.x;
			double denominator=Math.sqrt(1+k*k); //距离公式的分母
			double numerator=Math.abs(k*p.x+c-p.y);
			if(numerator/denominator<=threshold){
				return this;
			}
			else{
				return null;
			}
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
