import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class UILoader
{
	JFrame parent = null;
	JFrame window;
	int loadertype; //0:main, 1:ARA, 2:OTL, 3:Delivery, 4:Recommendation
	int framewidth,frameheight;
	private Point mouseclick;
	UILoader(final int loadertype)
	{
		//default setting
		this.loadertype = loadertype;
		window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension screenSize = new Dimension(800,600);
		framewidth = (int)screenSize.getWidth();
		frameheight = (int)screenSize.getHeight();
		window.setSize(framewidth,frameheight);
		window.setUndecorated(true);
		window.getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		window.getContentPane().setBackground(Color.WHITE);
		window.setLayout(null);
		window.setResizable(false);

		//top of frame. title, exit buttons ...
		JPanel moving = new JPanel();
		moving.setBackground(Color.WHITE);
		moving.setLayout(null);
		moving.setSize(framewidth,50);
		moving.setLocation(0,0);
		moving.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				mouseclick = e.getPoint(); // update the position
			}
		});
		moving.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				Point newPoint = e.getLocationOnScreen();
				newPoint.translate(-mouseclick.x, -mouseclick.y); // Moves the point by given values from its location
				window.setLocation(newPoint); // set the new location
			}
		});
		JLabel titlelabel = new JLabel("Hello@KAIST");
		titlelabel.setFont(new Font("Arial", Font.BOLD, 27));
		titlelabel.setLocation(10,0);
		titlelabel.setSize(framewidth-100,50);
		moving.add(titlelabel);

		JButton btn_min = new JButton(new ImageIcon("src/images/min.png"));
		btn_min.setLocation(framewidth - 100,0);
		btn_min.setSize(50,50);
		btn_min.setBackground(Color.WHITE);
		btn_min.setBorder(null);
		btn_min.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				window.setState(Frame.ICONIFIED);
			}
		});
		moving.add(btn_min);

		JButton btn_home = new JButton(new ImageIcon("src/images/home.png"));
		btn_home.setLocation(framewidth - 150,0);
		btn_home.setSize(50,50);
		btn_home.setBackground(Color.WHITE);
		btn_home.setBorder(null);
		btn_home.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(parent == null) return;
				window.setVisible(false);
				parent.setVisible(true);
			}
		});
		moving.add(btn_home);

		JButton btn_exit = new JButton(new ImageIcon("src/images/exit.png"));
		btn_exit.setLocation(framewidth - 50,0);
		btn_exit.setSize(50,50);
		btn_exit.setBackground(Color.WHITE);
		btn_exit.setBorder(null);
		btn_exit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(1);
			}
		});
		moving.add(btn_exit);
		window.add(moving);
		//main panel
		UIView u = new UIView(window,framewidth,frameheight);
		window.add(u.window);
		//according to loader type ...
		switch(loadertype)
		{
			case 0:
				//configuration button
				JButton btn_config = new JButton(new ImageIcon("src/images/config.png"));
				btn_config.setLocation(framewidth - 200,0);
				btn_config.setSize(50,50);
				btn_config.setBackground(Color.WHITE);
				btn_config.setBorder(null);
				window.add(btn_config);
				//main panel
				u.draw_main();
				break;
			case 1:
				titlelabel.setText("Hello@Kaist ARA");
				u.draw_ARA(0);
				break;
			case 2:
				titlelabel.setText("Hello@Kaist OTL");
				u.draw_OTL(0);
				break;
			case 3:
				titlelabel.setText("Hello@Kaist Delivery Assistant");
				u.draw_delivery(0);
				break;
			case 4:
				titlelabel.setText("Hello@Kaist Application Recommendation");
				u.draw_recommend();
				break;
		}
		window.setVisible(true);
	}

}
