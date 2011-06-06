/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.liv.proteosuite;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.Icon;
import java.awt.Component;
import javax.swing.JTree;

/**
 *
 * @author fgonzalez
 */
public class TreeRender extends DefaultTreeCellRenderer {
        Icon tutorialIcon;

        public TreeRender(Icon icon) {
            tutorialIcon = icon;
        }
        @Override
        public Component getTreeCellRendererComponent(
                            JTree tree,
                            Object value,
                            boolean sel,
                            boolean expanded,
                            boolean leaf,
                            int row,
                            boolean hasFocus)
        {

            super.getTreeCellRendererComponent(tree, value, sel,expanded, leaf, row, hasFocus);
            if (leaf && getNodeType(value))
            {
                setIcon(tutorialIcon);                
            } 
            else
            {
                
            }

            return this;
        }

        protected boolean getNodeType(Object value) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
            SpectrumData nodeInfo = (SpectrumData)(node.getUserObject());
            String type_data = nodeInfo.getSpecMSLevel();
            if (type_data.indexOf("MS1") >= 0) {
                return true;
            }
            return false;
        }
}