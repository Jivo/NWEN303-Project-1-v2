package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import model.Model;
import model.SequentialModel;

public class Canvas extends JPanel{
  Model m; Canvas(Model m){this.m=m;}
  @Override public void paint(Graphics gg) {
    Graphics2D g = (Graphics2D) gg;
    g.setBackground(Color.DARK_GRAY);
    g.clearRect(0, 0, getWidth(), getHeight());
    for (int i=0;i<m.pDraw.size();i++){
      m.pDraw.get(i).draw(g);
    }
  }
  @Override public Dimension getPreferredSize(){
    return new Dimension((int) SequentialModel.size, (int) SequentialModel.size);
    }
}