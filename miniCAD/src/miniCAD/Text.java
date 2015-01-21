package miniCAD;
import java.awt.*;

import javax.swing.*;

public class Text extends Shape{
	JTextArea text;
	
	Text(Point spoint, Point epoint, Color color){
		type = Type.TEXT;
		startpoint = spoint;
		endpoint = epoint;
		this.color = color;
		enableTag = false;
		text=new JTextArea();
		
		text.setLocation(startpoint.x,startpoint.y);
		text.setSize(endpoint.x-startpoint.x,endpoint.y-startpoint.y);
		text.setBorder(BorderFactory.createLoweredBevelBorder());
		text.setVisible(true);
		text.setEnabled(true);
		text.setBackground(new Color(240,240,240));
		
	}
	
	public void draw(Graphics g){
		text.setLocation(startpoint);
		//text.setBorder(BorderFactory.createLoweredBevelBorder());
		//text.setBorder(BorderFactory.createEmptyBorder());
		text.setSize(endpoint.x-startpoint.x,endpoint.y-startpoint.y);
		text.setForeground(color);
		//text.setOpaque(true);
		//text.setEnabled(false);
		
		if(enableTag) {
			draw_tag(g,startpoint);
			draw_tag(g,endpoint);
		}
		
	}
	
	public Shape pick(Point p){
		if((p.x>=Math.min(endpoint.x, startpoint.x)-threshold)
		 &&(p.x<=Math.max(endpoint.x, startpoint.x)+threshold)
		 &&(p.y>=Math.min(endpoint.y, startpoint.y)-threshold)
		 &&(p.y<=Math.max(endpoint.y, startpoint.y)+threshold)){
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
