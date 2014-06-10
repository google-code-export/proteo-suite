package uk.ac.liv.proteoidviewer.interfaces;

import java.awt.event.ComponentListener;

public interface LazyLoading {
	public void load();

	public void removeComponentListener(ComponentListener componentListener);

}
