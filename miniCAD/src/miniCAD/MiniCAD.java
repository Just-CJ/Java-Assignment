package miniCAD;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MiniCAD extends JFrame  implements MouseMotionListener,MouseListener{
	enum mode{
		SELECT,
		SCALE,
		LINE,
		CIRCLE,
		RECTANGLE,
		TEXT,
	}
	
	private mode curmode;
	private NewPanel CADpanel;
	Image image;
	ArrayList<Shape> shapes;
	private Shape curshape, lastshape;
	private Shape colorshape;
	private String text;
	private Point startpoint, endpoint, curpos;
	private Point anchorpoint;
	boolean enablescale;
	
	class NewPanel extends JPanel{
		public PopupMenu changecolor;
		public NewPanel(){
			changecolor = new PopupMenu();
			MenuItem menuItem1 = new MenuItem("Change Color");
			
			menuItem1.addActionListener(new ActionListener(){ //菜单1的事件监听
				public void actionPerformed(ActionEvent e) {
				menuItem1_actionPerformed(e); //菜单事件函数
				}
				});
			changecolor.add(menuItem1);
			add(changecolor);
		}
		protected void paintComponent(Graphics g){
			super.paintComponent(g);
			if(image!=null){
	        	g.drawImage(image, 0,0,image.getWidth(this), image.getHeight(this), this);
	        }
			
			for(Shape shape : shapes){
				shape.draw(g);
			}
		}
		
		public void menuItem1_actionPerformed(ActionEvent e){
			new JColorChooser();
			Color choose=JColorChooser.showDialog(this, "颜色选择", Color.black);
			colorshape.color = choose;
			CADpanel.repaint();
		}
	}
	
	
	
	public MiniCAD(){
		super.setLayout(new BorderLayout()); //JFrame的布局
		
		curmode = mode.SELECT;
		enablescale = false;
		shapes = new ArrayList<Shape>();
		
		makeMenubar();		
		makeToolbar();
						
		CADpanel = new NewPanel(); //主绘制界面
		CADpanel.setBackground(Color.WHITE);
		//CADpanel.setForeground(Color.WHITE);
		//CADpanel.setBorder(BorderFactory.createLoweredBevelBorder());
		CADpanel.addMouseListener(this);
		CADpanel.addMouseMotionListener(this);
		add(CADpanel, BorderLayout.CENTER);
	}

	private void makeMenubar(){
		JMenuBar menubar = new JMenuBar();
		setJMenuBar(menubar);
		
		JMenu fileMenu = new JMenu("File");
		menubar.add(fileMenu);
		JMenuItem openItem = new JMenuItem("Open");
		openItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JFileChooser choose=new JFileChooser();	
				int n = choose.showOpenDialog(null);
				if (n == JFileChooser.APPROVE_OPTION) {
					
					Image tmpimage;
					try {
						tmpimage = ImageIO.read(new File(choose.getSelectedFile().toString()));
						
						Graphics g=CADpanel.getGraphics();
						
				
					g.drawImage(tmpimage, 0, 0, tmpimage.getWidth(CADpanel),tmpimage.getHeight(CADpanel), CADpanel);
					JOptionPane.showMessageDialog(CADpanel, "打开成功");
					image=tmpimage;
					shapes.clear();
					
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(CADpanel, "打开失败");

					}
				}
			}
		});
		fileMenu.add(openItem);
		JMenuItem saveItem = new JMenuItem("Save");
		saveItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JFileChooser save=new JFileChooser();	
				int n = save.showSaveDialog(null);
				if (n == JFileChooser.APPROVE_OPTION) {

					BufferedImage image =(BufferedImage)CADpanel.createImage(CADpanel.getWidth(), CADpanel.getHeight()); 
					CADpanel.paint(image.getGraphics());
	
	
					try {
						ImageIO.write(image, "jpg",  new java.io.File(save.getSelectedFile().toString()+".jpg"));
						JOptionPane.showMessageDialog(CADpanel, "保存成功");
		
						}catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(CADpanel, "保存失败");
						}
				}
			}
		});
		fileMenu.add(saveItem);
		JMenuItem quitItem = new JMenuItem("Quit");
		quitItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		fileMenu.add(quitItem);
		
	}
	
	private void makeToolbar(){
		JPanel toolPanel = new JPanel(); //左侧工具栏
		BoxLayout boxLayout = new BoxLayout(toolPanel, BoxLayout.Y_AXIS);
		toolPanel.setLayout(boxLayout);
		
		final ImageIcon selectIcon = new ImageIcon("Images\\cursor.png");
		final ImageIcon selectpressedIcon = new ImageIcon("Images\\cursor.jpg");
		final ImageButton jbtSelect = new ImageButton(selectpressedIcon);
		jbtSelect.setPressedIcon(selectpressedIcon);
		
		final ImageIcon scaleIcon = new ImageIcon("Images\\scale.png");
		final ImageIcon scalepressedIcon = new ImageIcon("Images\\scale.jpg");
		final ImageButton jbtScale = new ImageButton(scaleIcon);
		jbtScale.setPressedIcon(scalepressedIcon);
		
		final ImageIcon lineIcon = new ImageIcon("Images\\line.png");
		final ImageIcon linepressedIcon = new ImageIcon("Images\\line.jpg");
		final ImageButton jbtLine = new ImageButton(lineIcon);
		jbtLine.setPressedIcon(linepressedIcon);
		

		final ImageIcon rectangleIcon = new ImageIcon("Images\\rectangle.png");
		final ImageIcon rectanglepressedIcon = new ImageIcon("Images\\rectangle.jpg");
		final ImageButton jbtRectangle = new ImageButton(rectangleIcon);
		jbtRectangle.setPressedIcon(rectanglepressedIcon);
		
		final ImageIcon circleIcon = new ImageIcon("Images\\circle.png");
		final ImageIcon circlepressedIcon = new ImageIcon("Images\\circle.jpg");
		final ImageButton jbtCircle = new ImageButton(circleIcon);
		jbtCircle.setPressedIcon(circlepressedIcon);
		
		final ImageIcon textIcon = new ImageIcon("Images\\text.png");
		final ImageIcon textpressedIcon = new ImageIcon("Images\\text.jpg");
		final ImageButton jbtText = new ImageButton(textIcon);
		jbtText.setPressedIcon(textpressedIcon);
		
		jbtSelect.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent event) {  
				//System.out.println("test");
				if(curmode != mode.SELECT){
					jbtSelect.setIcon(selectpressedIcon);
					jbtScale.setIcon(scaleIcon);
					jbtLine.setIcon(lineIcon);
					jbtCircle.setIcon(circleIcon);
					jbtRectangle.setIcon(rectangleIcon);
					jbtText.setIcon(textIcon);

					curmode = mode.SELECT;
					System.out.println(curmode);
				}
            }  
		});
		
		jbtScale.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent event) {  
				//System.out.println("test");
				if(curmode != mode.SCALE){
					jbtSelect.setIcon(selectIcon);
					jbtScale.setIcon(scalepressedIcon);
					jbtLine.setIcon(lineIcon);
					jbtCircle.setIcon(circleIcon);
					jbtRectangle.setIcon(rectangleIcon);
					jbtText.setIcon(textIcon);

					curmode = mode.SCALE;
					System.out.println(curmode);
				}
            }  
		});
		
		jbtLine.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent event) {  
				//System.out.println("test");
				if(curmode != mode.LINE){
					jbtSelect.setIcon(selectIcon);
					jbtScale.setIcon(scaleIcon);
					jbtLine.setIcon(linepressedIcon);
					jbtCircle.setIcon(circleIcon);
					jbtRectangle.setIcon(rectangleIcon);
					jbtText.setIcon(textIcon);

					curmode = mode.LINE;
					System.out.println(curmode);
				}
            }  
		});
		
		jbtCircle.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent event) {  
				//System.out.println("test");
				if(curmode != mode.CIRCLE){
					jbtSelect.setIcon(selectIcon);
					jbtScale.setIcon(scaleIcon);
					jbtLine.setIcon(lineIcon);
					jbtCircle.setIcon(circlepressedIcon);
					jbtRectangle.setIcon(rectangleIcon);
					jbtText.setIcon(textIcon);

					curmode = mode.CIRCLE;
					System.out.println(curmode);
				}
            }  
		});
		
		jbtRectangle.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent event) {  
				//System.out.println("test");
				if(curmode != mode.RECTANGLE){
					jbtSelect.setIcon(selectIcon);
					jbtScale.setIcon(scaleIcon);
					jbtLine.setIcon(lineIcon);
					jbtCircle.setIcon(circleIcon);
					jbtRectangle.setIcon(rectanglepressedIcon);
					jbtText.setIcon(textIcon);

					curmode = mode.RECTANGLE;
					System.out.println(curmode);
				}
            }  
		});
		
		jbtText.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent event) {  
				//System.out.println("test");
				if(curmode != mode.TEXT){
					jbtSelect.setIcon(selectIcon);
					jbtScale.setIcon(scaleIcon);
					jbtLine.setIcon(lineIcon);
					jbtCircle.setIcon(circleIcon);
					jbtRectangle.setIcon(rectangleIcon);
					jbtText.setIcon(textpressedIcon);

					curmode = mode.TEXT;
				}
            }  
		});
		
		
		toolPanel.add(jbtSelect);
		toolPanel.add(jbtScale);
		toolPanel.add(jbtLine);
		toolPanel.add(jbtRectangle);
		toolPanel.add(jbtCircle);
		toolPanel.add(jbtText);
		add(toolPanel, BorderLayout.WEST);
	}
	
	public Shape getshape(){
		for(Shape shape: shapes){
			if(shape.pick(curpos) != null)
				return shape;
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MiniCAD frame = new MiniCAD();
		frame.setTitle("miniCAD");
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null); // Center the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		startpoint = e.getPoint();
		endpoint = e.getPoint();
		
		switch(curmode){
		case CIRCLE:{
			if(curshape instanceof Text){
				((Text)curshape).text.setOpaque(false);
				((Text)curshape).text.setBorder(BorderFactory.createEmptyBorder());
				((Text)curshape).text.setEditable(false);
				repaint();
			}
			curshape = new Circle(startpoint, endpoint, Color.BLACK);
			shapes.add(curshape);
			}break;
		case LINE:{
			if(curshape instanceof Text){
				((Text)curshape).text.setOpaque(false);
				((Text)curshape).text.setBorder(BorderFactory.createEmptyBorder());
				((Text)curshape).text.setEditable(false);
				repaint();
			}
			curshape = new Line(startpoint, endpoint, Color.BLACK);
			shapes.add(curshape);
			}break;
		case SELECT:{
			if(lastshape instanceof Text){	
				((Text)lastshape).text.setOpaque(false);
				((Text)lastshape).text.setBorder(BorderFactory.createEmptyBorder());
				((Text)lastshape).text.setEditable(false);
				repaint();
			}
			if(curshape != null) lastshape = curshape;
			curpos = e.getPoint();
			curshape = getshape();
			}break;
		case SCALE:{
			if(curshape != null) lastshape = curshape;
			curpos = e.getPoint();
			curshape = getshape();
			if(curshape!=null){
				anchorpoint = curshape.pickanchor(curpos);
				if( anchorpoint != null){
					enablescale = true;
					System.out.println("test");
				}
			}
			}break;
		case TEXT:{
			if(curshape instanceof Text){	
				((Text)curshape).text.setOpaque(false);
				((Text)curshape).text.setBorder(BorderFactory.createEmptyBorder());
				((Text)curshape).text.setEditable(false);
				repaint();
			}
			curshape = new Text(startpoint, endpoint, Color.BLACK);
			CADpanel.add(((Text)curshape).text);
			shapes.add(curshape);
			}break;
		case RECTANGLE:{
			if(curshape instanceof Text){
				((Text)curshape).text.setOpaque(false);
				((Text)curshape).text.setBorder(BorderFactory.createEmptyBorder());
				((Text)curshape).text.setEditable(false);
				repaint();
			}
			curshape = new Rectangle(startpoint, endpoint, Color.BLACK);
			shapes.add(curshape);
			}break;
		default:
			break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if(curmode == mode.SELECT && e.getButton() == MouseEvent.BUTTON3 && curshape!= null){
			colorshape = curshape;
			CADpanel.changecolor.show(CADpanel, e.getX(), e.getY());
		}
		if(curmode == mode.SELECT && curshape instanceof Text){
			((Text)curshape).text.setEditable(true);
			((Text)curshape).text.setOpaque(true);
			((Text)curshape).text.setBorder(BorderFactory.createLoweredBevelBorder());
			repaint();
		}
		if(curmode == mode.SCALE){
			enablescale = false;
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Entered");
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Exit");
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		endpoint = e.getPoint();
		Graphics g = CADpanel.getGraphics();
		//CADpanel.update(g);
		
		switch(curmode){
		case CIRCLE:{
			curshape.endpoint = endpoint;
			repaint();
			}break;
		case LINE:{
			curshape.endpoint = endpoint;
			repaint();
			}break;
		case SELECT:{
			if(curshape != null){
				curshape.startpoint.x+=endpoint.x-startpoint.x;
				curshape.startpoint.y+=endpoint.y-startpoint.y;
				curshape.endpoint.x+=endpoint.x-startpoint.x;
				curshape.endpoint.y+=endpoint.y-startpoint.y;
				startpoint = endpoint;
				
				repaint();
			}
			}break;
		case SCALE:{
			if(enablescale){
				anchorpoint.x = endpoint.x;
				anchorpoint.y = endpoint.y;
				System.out.println(anchorpoint);
				repaint();
			}
			}break;
		case TEXT:{
			curshape.endpoint = endpoint;
			repaint();
			}break;
		case RECTANGLE:{
			curshape.endpoint = endpoint;
			repaint();
			}break;
		default:
			break;
		}
		
		
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		if(curmode == mode.SELECT || curmode == mode.SCALE){
			curpos = e.getPoint();
			if(curshape != null) lastshape = curshape;
			curshape = getshape();
			if(curshape!=null) curshape.enableTag = true;
			if(lastshape!=null && !lastshape.equals(curshape)) lastshape.enableTag = false;
			repaint();
		}
		//System.out.println(e.getPoint());
		
	}

}



class ImageButton extends JButton {
	 
	public ImageButton(ImageIcon icon){
		setSize(icon.getImage().getWidth(null),
				icon.getImage().getHeight(null));
		setIcon(icon);
		setMargin(new Insets(0,0,0,0));//将边框外的上下左右空间设置为0
		setIconTextGap(0);//将标签中显示的文本和图标之间的间隔量设置为0
		//setBorderPainted(false);//不打印边框
		//setBorder(null);//除去边框
		setText(null);//除去按钮的默认名称
		//setFocusPainted(false);//除去焦点的框
		//setContentAreaFilled(false);//除去默认的背景填充
	}
}