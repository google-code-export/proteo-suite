package uk.ac.liv.proteoidviewer.listener;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import uk.ac.liv.proteoidviewer.interfaces.LazyLoading;

public class LazyLoadingComponentListener implements
		ComponentListener {

	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentShown(ComponentEvent e) {
		LazyLoading lazyLoadable = (LazyLoading) e.getSource();
		lazyLoadable.removeComponentListener(this);
		
		new Thread(lazyLoadable.getClass().getName() + " LazyLoader") {
			@Override
			public void run() {
				lazyLoadable.load();
			}
		}.start();

	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

}
