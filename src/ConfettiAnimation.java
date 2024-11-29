import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class ConfettiAnimation extends JPanel {
    private static final int CONFETTI_COUNT = 150; // Number of confetti particles
    private static final int ANIMATION_DURATION = 10000; // Animation duration in milliseconds
    private final ArrayList<Confetti> confettiList;
    private final Timer timer; // Timer to control the animation
    private long startTime;
    private int num;

    public ConfettiAnimation(int num) {
        confettiList = new ArrayList<>();
        Random rand = new Random();
        this.num = num;
        for (int i = 0; i < CONFETTI_COUNT; i++) {
            confettiList.add(new Confetti(rand.nextInt(800), rand.nextInt(600), rand));
        }

        // Initialize the timer without referencing it in the lambda
        timer = new Timer(30, null);
        timer.addActionListener(e -> {
            repaint();
            long elapsedTime = System.currentTimeMillis() - startTime;
            if (elapsedTime > ANIMATION_DURATION) {
                ((Timer) e.getSource()).stop(); // Stop the timer when animation is done
            }
        });
    
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw "Win" text in the background
        g2d.setFont(new Font("Serif", Font.BOLD, 100));
        g2d.setColor(Color.green);
        FontMetrics fm = g2d.getFontMetrics();
        String winText = "P" + num + " WIN";
        int x = (getWidth() - fm.stringWidth(winText)) / 2;
        int y = (getHeight() / 2) + (fm.getAscent() / 4);
        g2d.drawString(winText, x, y);

        // Draw confetti particles on top
        for (Confetti confetti : confettiList) {
            confetti.move();
            g2d.setColor(confetti.color);
            g2d.fillRect(confetti.x, confetti.y, confetti.size, confetti.size);
        }
    }

    public void startAnimation() {
        startTime = System.currentTimeMillis();
        timer.start();
    }

    private static class Confetti {
        int x, y, size;
        int dx, dy;
        Color color;

        Confetti(int x, int y, Random rand) {
            this.x = x;
            this.y = y;
            this.size = rand.nextInt(5) + 5; // Confetti size between 5 and 10
            this.dx = rand.nextInt(5) - 8; // Random x movement
            this.dy = rand.nextInt(5) + 5; // Downward y movement
            this.color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)); // Random color
        }

        void move() {
            x += dx;
            y += dy;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Confetti Animation");
        ConfettiAnimation confettiPanel = new ConfettiAnimation(1);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(confettiPanel);
        frame.setVisible(true);

        confettiPanel.startAnimation();
    }
}
