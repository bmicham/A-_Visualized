import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;

public class Node extends JButton implements ActionListener{
	
	int clickCount;
	Node parent;
	int col;
	int row;
	int gCost;
	int hCost;
	int fCost;
	boolean start;
	boolean goal;
	boolean solid;
	boolean open;
	boolean checked;
	
	public Node(int col, int row) {
		
		this.col = col;
		this.row = row;
		
		setBackground(Color.white);
		setForeground(Color.black);
		
		addActionListener(this);
	}
	
	public void SetStartNode() {
		setBackground(Color.green);
		setForeground(Color.white);
		setText("Start");
		start = true;
	}
	
	public void SetGoalNode() {
		setBackground(Color.orange);
		setForeground(Color.white);
		setText("Goal");
		goal = true;
	}
	
	public void SetSolidNode() {
		setBackground(Color.black);
		setForeground(Color.black);
		solid = true;
	}
	
	public void SetOpenNode() {
		open = true;
	}
	
	public void SetCheckedNode(){
		if (start == false && goal == false) {
			setBackground(Color.cyan);
			setForeground(Color.black);
			checked = true;
		}	
	}
	
	public void SetPath(){
		setBackground(Color.green);
		setForeground(Color.black);
	}
	
	public void actionPerformed(ActionEvent e) {
		clickCount++;
		setBackground(Color.black);
	}

}
