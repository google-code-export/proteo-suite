/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.liv.proteosuite;

/**
 *
 * @author faviel
 */
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class TreeListener {

  public static void main(String[] a) {
    JFrame f = new JFrame();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.add(new JTreeEvents());
    f.setSize(500, 500);
    f.setVisible(true);
  }
}

class JTreeEvents extends JPanel {
  JTree tree;

  JTextField jtf;

  public JTreeEvents() {
    setLayout(new BorderLayout());
    DefaultMutableTreeNode top = new DefaultMutableTreeNode("Options");

    DefaultMutableTreeNode a = new DefaultMutableTreeNode("A");
    top.add(a);

    a.add(new DefaultMutableTreeNode("A1"));
    a.add(new DefaultMutableTreeNode("A2"));

    DefaultMutableTreeNode b = new DefaultMutableTreeNode("B");
    top.add(b);

    b.add(new DefaultMutableTreeNode("B1"));
    b.add(new DefaultMutableTreeNode("B2"));
    b.add(new DefaultMutableTreeNode("B3"));

    tree = new JTree(top);

    int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
    int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
    JScrollPane jsp = new JScrollPane(tree, v, h);

    add(jsp, BorderLayout.CENTER);

    jtf = new JTextField("", 20);
    add(jtf, BorderLayout.SOUTH);

    tree.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent me) {
        doMouseClicked(me);
      }
    });
  }

  void doMouseClicked(MouseEvent me) {
    TreePath tp = tree.getPathForLocation(me.getX(), me.getY());
    if (tp != null)
      jtf.setText(tp.toString());
    else
      jtf.setText("");
  }
}