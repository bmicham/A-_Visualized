import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class DemoWindow extends JPanel{
	
	// Screen Settings
	final int m_cols = 15;
	final int m_rows = 15;
	final int randomNodeAmount = 50;
	final int nodeSize = 70;
	final int screenWidth = nodeSize * m_cols;
	final int screenHeight = nodeSize * m_rows;
	boolean displayNodeCost = false;
	
	//Node Settings
	Node[][] node = new Node [m_cols][m_rows];
	Node startNode;
	Node goalNode;
	Node currentNode;
	ArrayList<Node> openNodes = new ArrayList<>();
	Random rand = new Random();
	
	boolean goalReached = false;
	int searchLimit = 0;
	
	public DemoWindow() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setLayout(new GridLayout(m_rows, m_cols));
		this.addKeyListener(new InputHandler(this));
		this.setFocusable(true);
		
		
		// Placing nodes on screen
		int col = 0;
		int row = 0;
		
		while (col < m_cols && row < m_rows) {
			node[col][row] = new Node(col,row);
			this.add(node[col][row]);
			
			col++;
			if (col == m_cols) {
				col = 0;
				row++;
			}
		}
		
		//Placing Set and Start Nodes
		SetStart(0,0);
		SetGoal(14,14);
		
		//Placing Random Solid Nodes
		int x = 0;
		while (x <= randomNodeAmount) {
			int num = rand.nextInt(14);
			int num2 = rand.nextInt(14);
			if ((num == 0 && num2 == 0) || (num == 14 && num2 == 14)) {
				continue;
			}
			x++;
			SetSolid(num, num2);		
			}
		
		DisplayCostOnNodes();
		
		}
	
	
	
	private void SetStart(int col, int row) {
		
		node[col][row].SetStartNode();
		startNode = node[col][row];
		currentNode = startNode;
	}
	
	private void SetGoal(int col, int row) {
		
		node[col][row].SetGoalNode();
		goalNode = node[col][row];
	}
	
	private void SetSolid(int col, int row) {
		
		node[col][row].SetSolidNode();
	}
	
	private void DisplayCostOnNodes() {
		int col = 0;
		int row = 0;
		
		while(col < m_cols && row < m_rows) {
			GetCost(node[col][row]);
			col++;
			
			if (col == m_cols) {
				col = 0;
				row++;
			}
		}
	}
	
	private void GetCost(Node node) 
	{
		//Calculating Nodes GCost (Distance from start node to current node)
		int xDistance = Math.abs(node.col - startNode.col);
		int yDistance = Math.abs(node.row - startNode.row);
		node.gCost = xDistance + yDistance;
		
		//Calculating Nodes HCost (Distance from goal node to current node)
		xDistance = Math.abs(node.col - goalNode.col);
		yDistance = Math.abs(node.row - goalNode.row);
		node.hCost = xDistance + yDistance;
		
		//Calculating Nodes FCost (Final cost, sum of g and h cost)
		node.fCost = node.gCost + node.hCost;
		
		//Display text of costs if enabled. Using HTML tags because line breaks are not supported
		if (node != startNode && node != goalNode && displayNodeCost == true) {
			node.setText("<html>F:" + node.fCost + "<br>G:" + node.gCost + "</html>");
		}
	}
	
	public void AutoSearch() {
		
		while (goalReached == false && searchLimit < 300) {
			
			int col = currentNode.col;
			int row = currentNode.row;
			
			currentNode.SetCheckedNode();
			openNodes.remove(currentNode);
			
			if (row -1 >= 0) {
				OpenNode(node[col][row-1]);
			}
			if (col -1 >= 0) {
				OpenNode(node[col-1][row]);
			}
			if (row +1 < m_rows) {
				OpenNode(node[col][row+1]);
			}
			if (col +1 < m_cols) {
				OpenNode(node[col+1][row]);
			}
			
			int bestNodeIndex = 0;
			int bestNodefCost = 999;
			
			for (int i = 0; i < openNodes.size(); i++) {
				
				if (openNodes.get(i).fCost < bestNodefCost) {
					bestNodeIndex = i;
					bestNodefCost = openNodes.get(i).fCost;
				}
				else if (openNodes.get(i).fCost == bestNodefCost) {
					if (openNodes.get(i).gCost < openNodes.get(bestNodeIndex).gCost) {
						bestNodeIndex = i;
					}
				}
			}
			
			currentNode = openNodes.get(bestNodeIndex);
			
			if (currentNode == goalNode) {
				goalReached = true;
				TrackPath();
			}
			searchLimit++;
		}
	}
	
	public void ManualSearch() {
		
		if (goalReached == false) {
			
			int col = currentNode.col;
			int row = currentNode.row;
			
			currentNode.SetCheckedNode();
			openNodes.remove(currentNode);
			
			if (row -1 >= 0) {
				OpenNode(node[col][row - 1]);
			}
			if (col -1 >= 0) {
				OpenNode(node[col - 1][row]);
			}
			if (row +1 >= 0) {
				OpenNode(node[col][row + 1]);
			}
			if (col +1 >= 0) {
				OpenNode(node[col + 1][row]);
			}
			
			int bestNodeIndex = 0;
			int bestNodefCost = 999;
			
			for (int i = 0; i < openNodes.size(); i++) {
				
				if (openNodes.get(i).fCost < bestNodefCost) {
					bestNodeIndex = i;
					bestNodefCost = openNodes.get(i).fCost;
				}
				else if (openNodes.get(i).fCost == bestNodefCost) {
					if (openNodes.get(i).gCost < openNodes.get(bestNodeIndex).gCost) {
						bestNodeIndex = i;
					}
				}
			}
			
			currentNode = openNodes.get(bestNodeIndex);
			
			if (currentNode == goalNode) {
				goalReached = true;
			}
		}
	}
	
	private void OpenNode (Node node) {
		if (node.open == false && node.checked == false && node.solid == false) {
			
			node.SetOpenNode();
			node.parent = currentNode;
			openNodes.add(node);
		}
	}
	
	private void TrackPath () {
		Node current = goalNode;
		while (current != startNode) {
			
			current = current.parent;
			
			if (current != startNode) {
				current.SetPath();
			}
		}
	}
}
