package netty;
import netty.PushServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author 徐飞
 * @version 2016/02/25 16:38
 */
public class TestFrame extends JFrame {

    private void hi() {
        int width = 420;
        int height = 500;
        setTitle("Server");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((int) (screenSize.getWidth() - width) / 2, (int) (screenSize.getHeight() - height) / 2, width, height);
        setLayout(null);
        setVisible(true);
        setResizable(false);


        Button initBtn = new Button("Init");
        initBtn.setBounds(10, 10, 50, 20);
        add(initBtn);
        initBtn.addActionListener((ActionEvent e) -> {
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    PushServer.start();
                }
            });
            t1.start();
        });

        Button sendBtn = new Button("Push");
        sendBtn.setBounds(180, 180, 50, 20);
        add(sendBtn);
        sendBtn.addActionListener((ActionEvent e) -> {
            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    PushServer.push("12345","来自服务器的消息");
                }
            });
            t2.start();
        });

        Button countBtn = new Button("None");
        countBtn.setBounds(180,70,50,20);
        add(countBtn);
        countBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread t3 = new Thread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
                t3.start();
            }
        });
    }

    public static void main(String[] args) {
        (new TestFrame()).hi();
    }

}
