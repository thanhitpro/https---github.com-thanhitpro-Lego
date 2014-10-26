import java.util.ArrayList;

import remixlab.dandelion.geom.Vec;


public class BoxCollider {
	private ArrayList<Box> listBox;

	/**
	 * @return the listBox
	 */
	public ArrayList<Box> getListBox() {
		return listBox;
	}

	/**
	 * @param listBox the listBox to set
	 */
	public void setListBox(ArrayList<Box> listBox) {
		this.listBox = listBox;
	}
	
	
	
	public boolean Containt(Vec point) {
		for (int i = 0; i < listBox.size(); i++) {
			if (listBox.get(i).Container(point)) 
				return true;
		}
		return false;
	}
}
