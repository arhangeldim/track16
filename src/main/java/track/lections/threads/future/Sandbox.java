package track.lections.threads.future;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Sandbox {

    public static void main(String[] args) throws Exception {
        JFrame frame = buildFrame();

        frame.add(new ImagePanel());
    }

    private static JFrame buildFrame() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setVisible(true);
        return frame;
    }

    static class ImagePanel extends JPanel {

        private BufferedImage image;

        public ImagePanel() throws Exception {

            image = ImageIO.read(new URL("https://gic6.mycdn.me/image?t=56&bid=816871160539&id=816871160539&plc=WEB&tkn=*iaoYC50VGni4tMfp7sTdPUMsBD0"));
            Dimension dim = new Dimension();
            dim.width = image.getWidth(null);
            dim.height = image.getHeight(null);
            setPreferredSize(dim);
        }

        @Override
        public void paintComponent(Graphics gr) {
            super.paintComponent(gr);
            Graphics2D g2d = (Graphics2D) gr;
            g2d.drawImage(image, 0, 0, null);
        }
    }


}
