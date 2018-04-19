package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import model.DrawableParticle;
import model.Model;
import model.ModelAbstract;

public class Canvas extends JPanel{
  ModelAbstract m; Canvas(ModelAbstract m){this.m=m;}
  @Override public void paint(Graphics gg) {
    Graphics2D g = (Graphics2D) gg;
    g.setBackground(Color.DARK_GRAY);
    g.clearRect(0, 0, getWidth(), getHeight());
    for (DrawableParticle d : m.pDraw){ d.draw(g); }
  }
  @Override public Dimension getPreferredSize(){
    return new Dimension((int) Model.size, (int) Model.size);
    }
}
