package spreadSheet;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class SpreadSheet extends JFrame{
	
	private JTable table;
	private DefaultTableModel tablemodel;
	
	public SpreadSheet(){
		setLayout(new BorderLayout()); //JFrame的布局
		
		makeMenubar();	
		
		tablemodel = new DefaultTableModel(10, 6);
		table = new JTable(tablemodel); 

		table.setCellSelectionEnabled(true);
		//table.setTableHeader(null);
		table.setRowHeight(25);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		//System.out.println(table.getColumnModel().getColumnCount());
		//for(int i=0 ;i<table.getColumnModel().getColumnCount(); i++){
			//table.getColumnModel().getColumn(i).setPreferredWidth(100);
		//}
		
		JScrollPane scrollPane = new JScrollPane(table); 

		add(scrollPane, BorderLayout.CENTER);
	}

	
	private void makeMenubar(){
		JMenuBar menubar = new JMenuBar();
		setJMenuBar(menubar);
		
		JMenu fileMenu = new JMenu("File");
		JMenu EditMenu = new JMenu("Edit");
		menubar.add(fileMenu);
		menubar.add(EditMenu);
		JMenuItem openItem = new JMenuItem("Open");
		openItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//to do
				openCSV();
			}
		});
		fileMenu.add(openItem);
		JMenuItem saveItem = new JMenuItem("Save");
		saveItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//to do
				saveCSV();
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
		
		JMenuItem addRowItem = new JMenuItem("Add a row");
		addRowItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tablemodel.addRow(new Vector<String>());
			}
		});
		EditMenu.add(addRowItem);
		
		JMenuItem addColumnItem = new JMenuItem("Add a column");
		addColumnItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tablemodel.addColumn(String.valueOf((char)('A'+tablemodel.getColumnCount())));
			}
		});
		EditMenu.add(addColumnItem);
		
	}
	
	protected void saveCSV() {
		// TODO Auto-generated method stub
		JFileChooser chooser=new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter("CSV file", "csv"));
		int ret = chooser.showSaveDialog(this);
		if(ret == JFileChooser.APPROVE_OPTION){
			try{
				FileWriter fw = new FileWriter(chooser.getSelectedFile()+".csv");
				int max_row=0, max_column=0;
				for(int i=0; i<table.getRowCount(); i++){
					for(int j=0; j<table.getColumnCount(); j++){
						System.out.println(""+i+" "+j); 
						System.out.println(table.getValueAt(i, j));
						if(table.getValueAt(i, j)!=null){
							if(!table.getValueAt(i, j).toString().isEmpty()){
								if(i>max_row) max_row = i;
								if(j>max_column) max_column = j;
							}
						}
					}
				}
				
				System.out.println(max_row);
				System.out.println(max_column);
				
				for(int i=0; i<max_row+1; i++){
					String line = "";
					for(int j=0; j<max_column; j++){
						if(table.getValueAt(i, j)!=null)
							line+=table.getValueAt(i, j).toString()+",";
						else line+=",";
					}
					if(table.getValueAt(i, max_column)!=null)
						line+=table.getValueAt(i, max_column).toString()+"\n";
					else line+="\n";
					fw.write(line);
				}
				
				fw.close();
				
			}catch(IOException e){
				//JOptionPane.showMessageDialog(this, "保存失败");
			}
		}
	}


	protected  void openCSV() {
		// TODO Auto-generated method stub
		JFileChooser chooser=new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter("CSV file", "csv"));
		int ret = chooser.showOpenDialog(this);
		if(ret == JFileChooser.APPROVE_OPTION){
			try{
				File CSVfile = chooser.getSelectedFile();
				
				tablemodel.setColumnCount(10);
		    	tablemodel.setRowCount(300);
		    	
		    	for(int i=0; i<table.getRowCount(); i++){
					for(int j=0; j<table.getColumnCount(); j++){
						tablemodel.setValueAt(null, i, j);
					}
				}
				
				FileInputStream fis=new FileInputStream(CSVfile);
			    InputStreamReader isw = new InputStreamReader(fis, "GBK");  
			    BufferedReader br = new BufferedReader(isw); 
			    String line = br.readLine();
			    int index_line = 0;
			    while(line != null){
			    	String[] words = line.split(",");
			    	
			    	while(tablemodel.getColumnCount() < words.length){
			    		tablemodel.addColumn(String.valueOf((char)('A'+tablemodel.getColumnCount())));
			    	}
			    	for(int i=0; i<words.length; i++){
			    		table.setValueAt(words[i], index_line, i);
			    		//System.out.print(""+i+". "+words[i]+" ");
			    	}
			    	//System.out.println("");
			    	line = br.readLine();
			    	index_line++;
			      }
			      
			    br.close();
			}catch(IOException e){
				JOptionPane.showMessageDialog(this, "打开失败");
			}
		}
		
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpreadSheet spreadsheet = new SpreadSheet();
		spreadsheet.setTitle("SpreadSheet");
		spreadsheet.setSize(800, 600);
		spreadsheet.setLocationRelativeTo(null); // Center the frame
		spreadsheet.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		spreadsheet.setVisible(true);
	}

}
