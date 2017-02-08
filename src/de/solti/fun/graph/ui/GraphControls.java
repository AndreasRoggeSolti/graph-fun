package de.solti.fun.graph.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.solti.fun.graph.layout.GraphLayouter;

public class GraphControls<V,E> extends JPanel implements ActionListener, Observer, KeyListener {
	private static final long serialVersionUID = -3009657102649320650L;

	private static final String SCORE_TEXT = "sum percentage error: ";
	
	private static final String ANGLE_TEXT = "angle: ";
	
	private JButton playPause;
	private JButton step;
	private JButton restart;
	
	private JLabel percentageError;
	
	private JTextField pointsAngleText;
	private JLabel pointsAngle;

	private GraphLayouter<V, E> layouter;
		
	public GraphControls(GraphLayouter<V, E> layouter){
		this.layouter = layouter;
		layouter.addObserver(this);
		
		this.playPause = new JButton("play");
		this.playPause.addActionListener(this);
		this.step = new JButton("step");
		this.step.addActionListener(this);
		this.restart = new JButton("restart");
		this.restart.addActionListener(this);
		this.percentageError = new JLabel(SCORE_TEXT);
		
		this.pointsAngleText = new JTextField("x1,x2,x3");
		this.pointsAngleText.addKeyListener(this);
		this.pointsAngle = new JLabel("angle: ");
		
		this.add(playPause);
		this.add(step);
		this.add(restart);
		this.add(percentageError);
		this.add(pointsAngleText);
		this.add(pointsAngle);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(playPause)){
			boolean pause = layouter.togglePause();
			if (pause){
				this.playPause.setText("play");
			} else {
				this.playPause.setText("pause");
			}
		} else if (e.getSource().equals(step)){
			this.layouter.step();
		} else if (e.getSource().equals(restart)){
			layouter.restart();
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		this.percentageError.setText(SCORE_TEXT+layouter.getPercentageError());
		this.pointsAngle.setText(ANGLE_TEXT+layouter.getAngle());
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(this.pointsAngleText.getText().split(",").length == 3){
			layouter.setPointsAngles(this.pointsAngleText.getText().split(","));
		}
	}
}
