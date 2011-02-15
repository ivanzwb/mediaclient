package com.whyang.test;

import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXTree;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JXFrame frame = new JXFrame("test", false);
		frame.setSize(500, 500);
		JXButton button = new JXButton("button");
		JXTree tree = new JXTree();
		button.setSize(20, 20);
		frame.add(tree);
		frame.show();
	}

}
