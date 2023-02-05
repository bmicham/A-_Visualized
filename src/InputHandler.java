import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {

	DemoWindow dw;
	
	public InputHandler(DemoWindow dw) {
		this.dw = dw;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int code = e.getKeyCode();
		
		if (code == KeyEvent.VK_M) {
			dw.ManualSearch();
		}
		else if (code == KeyEvent.VK_ENTER) {
			dw.AutoSearch();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {}

}
