package xm.game.retrosnake;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by xuming on 2016/6/18.
 */
public class SnakeControl implements KeyListener {
    SnakeModel model;

    public SnakeControl(SnakeModel model){
        this.model = model;
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (model.running){                // 运行状态下，处理的按键
            switch (keyCode) {
                case KeyEvent.VK_UP:
                    model.changeDirection(SnakeModel.UP);
                    break;
                case KeyEvent.VK_DOWN:
                    model.changeDirection(SnakeModel.DOWN);
                    break;
                case KeyEvent.VK_LEFT:
                    model.changeDirection(SnakeModel.LEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                    model.changeDirection(SnakeModel.RIGHT);
                    break;
                case KeyEvent.VK_ADD:
                case KeyEvent.VK_PAGE_UP:
                    model.speedUp();
                    break;
                case KeyEvent.VK_SUBTRACT:
                case KeyEvent.VK_PAGE_DOWN:
                    model.speedDown();
                    break;
                case KeyEvent.VK_SPACE:
                case KeyEvent.VK_P:
                    model.changePauseState();
                    break;
                default:
            }
        }

        // 任何情况下处理的按键，按键导致重新启动游戏
        if (keyCode == KeyEvent.VK_R ||
                keyCode == KeyEvent.VK_S ||
                keyCode == KeyEvent.VK_ENTER) {
            model.reset();
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }
}
