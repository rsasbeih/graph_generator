import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.gl2.GLUT;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;

public class Driver extends Application implements GLEventListener{
	final static JFrame frame = new JFrame ("Graph"); 
	static float[] result;
	public static File f;
	public int k=0;
	public static ArrayList<Game> list = new ArrayList<>();
	public static String header;
	public static int funNum=0;
	public static void main(String[] args) { 
		final GLProfile profile = GLProfile.get(GLProfile.GL2); 
		GLCapabilities capabilities = new GLCapabilities(profile); 
		final GLCanvas glcanvas = new GLCanvas(capabilities); 
		Driver l = new Driver(); 
		glcanvas.addGLEventListener(l); 
		glcanvas.setSize(800, 400); 
		frame.getContentPane().add(glcanvas); 
		frame.setSize(frame.getContentPane().getPreferredSize()); 
		Application.launch(args); 
		float[] f= {240f,100f,100f};
		result=ColorConverter.HSV2RGB(f);
		for(int i=0;i<result.length;i++)
			System.out.println(result[i]);
	//	frame.setVisible(true); 
		
	}
	@Override
	public void display(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2(); 
		
//		drawSolidCircle(gl,0.2f,-0.7f,0.7f,360,new float[] {1,1,0});
		if(funNum==1)
		drawPieGraphRandom(gl,list);
		else if(funNum==2)
		drawBarGraphRandom(gl,list);
		else if(funNum==3)
		drawBubbleGraphRandom(gl,list);
		else if(funNum==4)
		drawColumnGraphRandom(gl,list);
		else if(funNum==5)
			drawPieGraph(gl,list);
		else if(funNum==6)
			drawBarGraph(gl,list);
		else if(funNum==7)
			drawBubbleGraph(gl,list);
		else
			drawColumnGraph(gl,list);;
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub

	}
	/**
	 * Function to draw empty circle
	 * @param gl drawing tool
	 * @param radius radius of circle
	 * @param centerX X coordinate of center
	 * @param centerY Y coordinate of center
	 * @param alpha starting angle
	 */
	public void drawCircle(GL2 gl,float radius,float centerX,float centerY, int alpha){
		gl.glBegin(GL2.GL_LINE_LOOP);
		for(int i=0;i<=alpha;i++){
			double angle=Math.PI*i/180;
			double x=Math.cos(angle)*radius;
			double y=Math.sin(angle)*radius;
			gl.glVertex2f((float)x+centerX,(float)y+centerY);
		}
		gl.glEnd();
	}
	/**
	 * Function to draw colored circle
	 * @param gl drawing tool
	 * @param radius radius of circle
	 * @param centerX X coordinate of center
	 * @param centerY Y coordinate of center
	 * @param color color of circle
	 */
	public void drawSolidCircle(GL2 gl,float radius,float centerX,float centerY, int alpha,float[] color){
		gl.glColor3f(color[0], color[1],color[2]);
		gl.glBegin (GL2.GL_LINES);
		for(int i=0;i<=alpha;i++){
			double angle=Math.PI*i/180;
			double x=Math.cos(angle)*radius;
			double y=Math.sin(angle)*radius;
			gl.glVertex3f(centerX,centerY,0);
			gl.glVertex3f((float)x+centerX,(float)y+centerY,0);
		}
		gl.glEnd();
	}
	/**
	 * Function to draw colored circle starting from specific angle
	 * @param gl drawing tool
	 * @param radius radius of circle
	 * @param centerX X coordinate of center
	 * @param centerY Y coordinate of center
	 * @param startAlpha starting angle
	 * @param endAlpha end angle
	 * @param color color of circle
	 */
	public void drawSolidCircle(GL2 gl,float radius,float centerX,float centerY,int startAlpha, int endAlpha,float[] color){
		gl.glColor3f(color[0], color[1],color[2]);
		gl.glBegin (GL2.GL_LINES);
		for(int i=startAlpha;i<=endAlpha;i++){
			double angle=Math.PI*i/180;
			double x=Math.cos(angle)*radius;
			double y=Math.sin(angle)*radius;
			gl.glVertex3f(centerX,centerY,0);
			gl.glVertex3f((float)x+centerX,(float)y+centerY,0);
		}
		gl.glEnd();
	}
	/**
	 * Function to draw pie chart of list using random colors
	 * @param gl drawing toll
	 * @param list list of objects
	 */
	public void drawPieGraphRandom(GL2 gl, ArrayList<Game> list) {
		GLUT glut2=new GLUT();
		gl.glColor3f(1f,1f,1f); 
		gl.glRasterPos2f(-0.5f, 0.8f);
		glut2.glutBitmapString(2, header);
		int sum=0;
		for(int i=0;i<list.size();i++)
			sum+=list.get(i).sales;
		//radius,centerX,centerY,angle,color
		int angle=(list.get(0).sales*360)/sum;
		Random randomGenerator = new Random();
		for(int r=0;r<3;r++)
			list.get(0).color[r]=randomGenerator.nextFloat();
		drawSolidCircle(gl,0.5f,-0.2f,0f,0,angle,new float[] {list.get(0).color[0],list.get(0).color[1],list.get(0).color[2]});
		GLUT glut=new GLUT();
		float xText=0.4f;
		float yText=0.8f;
		gl.glColor3f(1f,1f,1f); 
		gl.glRasterPos2f(xText, yText);
		glut.glutBitmapString(2, list.get(0).name);
		float x1Color=0.9f;
		float y1Color=0.8f;
		float x2Color=1f;
		float y2Color=0.8f;
		gl.glColor3f(list.get(0).color[0],list.get(0).color[1],list.get(0).color[2]); 
		gl.glBegin (GL2.GL_LINES);
		gl.glVertex2f(x1Color,y1Color);
		gl.glVertex2f(x2Color,y2Color);
		gl.glEnd();
		int startAngle=0;
		int endAngle=angle;
		for(int i=1;i<list.size();i++) {
			startAngle+=(list.get(i-1).sales*360)/sum;
			endAngle+=(list.get(i).sales*360)/sum;
			if(i==list.size()-1)
				endAngle=360;
			for(int j=0;j<list.get(i).color.length;j++)
			list.get(i).color[j]=randomGenerator.nextFloat();
			drawSolidCircle(gl,0.5f,-0.2f,0f,startAngle,endAngle,new float[] {list.get(i).color[0],list.get(i).color[1],list.get(i).color[2]});
		}  
		for(int i=1;i<list.size();i++) {
			yText-=0.1;
			gl.glColor3f(1f,1f,1f); 
			gl.glRasterPos2f(xText, yText);
			glut.glutBitmapString(2, list.get(i).name);
			y1Color-=0.1;
			y2Color-=0.1;
			gl.glColor3f(list.get(i).color[0],list.get(i).color[1],list.get(i).color[2]); 
			gl.glBegin (GL2.GL_LINES);
			gl.glVertex2f(x1Color,y1Color);
			gl.glVertex2f(x2Color,y2Color);
			gl.glEnd();
			
		}
	} 
	/**
	 * Function to draw pie chart of list using selected colors
	 * @param gl drawing toll
	 * @param list list of objects
	 */
	public void drawPieGraph(GL2 gl, ArrayList<Game> list) {
		GLUT glut2=new GLUT();
		gl.glColor3f(1f,1f,1f); 
		gl.glRasterPos2f(-0.5f, 0.8f);
		glut2.glutBitmapString(2, header);
		int sum=0;
		for(int i=0;i<list.size();i++)
			sum+=list.get(i).sales;
		//radius,centerX,centerY,angle,color
		int angle=(list.get(0).sales*360)/sum;
		drawSolidCircle(gl,0.5f,-0.2f,0f,0,angle,new float[] {list.get(0).color[0],list.get(0).color[1],list.get(0).color[2]});
		GLUT glut=new GLUT();
		float xText=0.4f;
		float yText=0.8f;
		gl.glColor3f(1f,1f,1f); 
		gl.glRasterPos2f(xText, yText);
		glut.glutBitmapString(2, list.get(0).name);
		float x1Color=0.9f;
		float y1Color=0.8f;
		float x2Color=1f;
		float y2Color=0.8f;
		gl.glColor3f(list.get(0).color[0],list.get(0).color[1],list.get(0).color[2]); 
		gl.glBegin (GL2.GL_LINES);
		gl.glVertex2f(x1Color,y1Color);
		gl.glVertex2f(x2Color,y2Color);
		gl.glEnd();
		int startAngle=0;
		int endAngle=angle;
		for(int i=1;i<list.size();i++) {
			startAngle+=(list.get(i-1).sales*360)/sum;
			endAngle+=(list.get(i).sales*360)/sum;
			if(i==list.size()-1)
				endAngle=360;
			drawSolidCircle(gl,0.5f,-0.2f,0f,startAngle,endAngle,new float[] {list.get(i).color[0],list.get(i).color[1],list.get(i).color[2]});
		}  
		for(int i=1;i<list.size();i++) {
			yText-=0.1;
			gl.glColor3f(1f,1f,1f); 
			gl.glRasterPos2f(xText, yText);
			glut.glutBitmapString(2, list.get(i).name);
			y1Color-=0.1;
			y2Color-=0.1;
			gl.glColor3f(list.get(i).color[0],list.get(i).color[1],list.get(i).color[2]); 
			gl.glBegin (GL2.GL_LINES);
			gl.glVertex2f(x1Color,y1Color);
			gl.glVertex2f(x2Color,y2Color);
			gl.glEnd();
			
		}
	} 
	/**
	 * Function to draw bar chart of list using random colors
	 * @param gl drawing toll
	 * @param list list of objects
	 */
	public void drawBarGraphRandom(GL2 gl, ArrayList<Game> list) {
		GLUT glut2=new GLUT();
		gl.glColor3f(1f,1f,1f); 
		gl.glRasterPos2f(-0.5f, 0.8f);
		glut2.glutBitmapString(2, header);
		gl.glColor3f(1f,1f,1f); 
		gl.glBegin (GL2.GL_LINES);
		gl.glVertex2f(-0.9f,-0.8f);
		gl.glVertex2f(0.9f,-0.8f);
		gl.glEnd();
		gl.glBegin (GL2.GL_LINES);
		gl.glVertex2f(-0.9f,-0.8f);
		gl.glVertex2f(-0.9f,0.8f);
		gl.glEnd();
		Random randomGenerator = new Random();
		for(int r=0;r<3;r++)
			list.get(0).color[r]=randomGenerator.nextFloat();
		float xll=-0.7f;
		float yll=-0.8f;
		float xlr=-0.6f;
		float ylr=-0.8f;
		float xul=-0.7f;
		float yul=-0.2f;
		float xur=-0.6f;
		float yur=-0.2f;
		gl.glColor3f(list.get(0).color[0],list.get(0).color[1],list.get(0).color[2]); 
		gl.glBegin (GL2.GL_QUADS);
		//lower left corner
		gl.glVertex2f(xll,yll);
		//lower right corner
		gl.glVertex2f(xlr,ylr);
		//upper left corner
		gl.glVertex2f(xul,yul);
		//upper right corner
		gl.glVertex2f(xur,yur);
		gl.glEnd();
		GLUT glut=new GLUT();
		float xText=0.4f;
		float yText=0.8f;
		gl.glColor3f(1f,1f,1f); 
		gl.glRasterPos2f(xText, yText);
		glut.glutBitmapString(2, list.get(0).name);
		float x1Color=0.9f;
		float y1Color=0.8f;
		float x2Color=1f;
		float y2Color=0.8f;
		gl.glColor3f(list.get(0).color[0],list.get(0).color[1],list.get(0).color[2]); 
		gl.glBegin (GL2.GL_LINES);
		gl.glVertex2f(x1Color,y1Color);
		gl.glVertex2f(x2Color,y2Color);
		gl.glEnd();
		for(int i=1;i<list.size();i++) {
			float tall=(float)((float)list.get(i).sales/17)/10;
			xll+=0.1;
		//	yll+=0.1;
			xlr+=0.1;
		//	ylr+=0.1;
			xul+=0.1;
			//this
			yul=tall;
			xur+=0.1;
			yur=tall;
			for(int j=0;j<list.get(i).color.length;j++)
				list.get(i).color[j]=randomGenerator.nextFloat();
			gl.glColor3f(list.get(i).color[0],list.get(i).color[1],list.get(i).color[2]); 
			gl.glBegin (GL2.GL_QUADS);
			//lower left corner
			gl.glVertex2f(xll,yll);
			//lower right corner
			gl.glVertex2f(xlr,ylr);
			//upper left corner
			gl.glVertex2f(xul,yul);
			//upper right corner
			gl.glVertex2f(xur,yur);
			gl.glEnd();
		}
		for(int i=1;i<list.size();i++) {
			yText-=0.1;
			gl.glColor3f(1f,1f,1f); 
			gl.glRasterPos2f(xText, yText);
			glut.glutBitmapString(2, list.get(i).name);
			y1Color-=0.1;
			y2Color-=0.1;
			gl.glColor3f(list.get(i).color[0],list.get(i).color[1],list.get(i).color[2]); 
			gl.glBegin (GL2.GL_LINES);
			gl.glVertex2f(x1Color,y1Color);
			gl.glVertex2f(x2Color,y2Color);
			gl.glEnd();
			
		}
	//	gl.glEnd();
	}
	/**
	 * Function to draw bar chart of list using selected colors
	 * @param gl drawing toll
	 * @param list list of objects
	 */
	public void drawBarGraph(GL2 gl, ArrayList<Game> list) {
		GLUT glut2=new GLUT();
		gl.glColor3f(1f,1f,1f); 
		gl.glRasterPos2f(-0.5f, 0.8f);
		glut2.glutBitmapString(2, header);
		gl.glColor3f(1f,1f,1f); 
		gl.glBegin (GL2.GL_LINES);
		gl.glVertex2f(-0.9f,-0.8f);
		gl.glVertex2f(0.9f,-0.8f);
		gl.glEnd();
		gl.glBegin (GL2.GL_LINES);
		gl.glVertex2f(-0.9f,-0.8f);
		gl.glVertex2f(-0.9f,0.8f);
		gl.glEnd();
		float xll=-0.7f;
		float yll=-0.8f;
		float xlr=-0.6f;
		float ylr=-0.8f;
		float xul=-0.7f;
		float yul=-0.2f;
		float xur=-0.6f;
		float yur=-0.2f;
		gl.glColor3f(list.get(0).color[0],list.get(0).color[1],list.get(0).color[2]); 
		gl.glBegin (GL2.GL_QUADS);
		//lower left corner
		gl.glVertex2f(xll,yll);
		//lower right corner
		gl.glVertex2f(xlr,ylr);
		//upper left corner
		gl.glVertex2f(xul,yul);
		//upper right corner
		gl.glVertex2f(xur,yur);
		gl.glEnd();
		GLUT glut=new GLUT();
		float xText=0.4f;
		float yText=0.8f;
		gl.glColor3f(1f,1f,1f); 
		gl.glRasterPos2f(xText, yText);
		glut.glutBitmapString(2, list.get(0).name);
		float x1Color=0.9f;
		float y1Color=0.8f;
		float x2Color=1f;
		float y2Color=0.8f;
		gl.glColor3f(list.get(0).color[0],list.get(0).color[1],list.get(0).color[2]); 
		gl.glBegin (GL2.GL_LINES);
		gl.glVertex2f(x1Color,y1Color);
		gl.glVertex2f(x2Color,y2Color);
		gl.glEnd();
		for(int i=1;i<list.size();i++) {
			float tall=(float)((float)list.get(i).sales/17)/10;
			xll+=0.1;
		//	yll+=0.1;
			xlr+=0.1;
		//	ylr+=0.1;
			xul+=0.1;
			//this
			yul=tall;
			xur+=0.1;
			yur=tall;
			gl.glColor3f(list.get(i).color[0],list.get(i).color[1],list.get(i).color[2]); 
			gl.glBegin (GL2.GL_QUADS);
			//lower left corner
			gl.glVertex2f(xll,yll);
			//lower right corner
			gl.glVertex2f(xlr,ylr);
			//upper left corner
			gl.glVertex2f(xul,yul);
			//upper right corner
			gl.glVertex2f(xur,yur);
			gl.glEnd();
		}
		for(int i=1;i<list.size();i++) {
			yText-=0.1;
			gl.glColor3f(1f,1f,1f); 
			gl.glRasterPos2f(xText, yText);
			glut.glutBitmapString(2, list.get(i).name);
			y1Color-=0.1;
			y2Color-=0.1;
			gl.glColor3f(list.get(i).color[0],list.get(i).color[1],list.get(i).color[2]); 
			gl.glBegin (GL2.GL_LINES);
			gl.glVertex2f(x1Color,y1Color);
			gl.glVertex2f(x2Color,y2Color);
			gl.glEnd();
			
		}
	//	gl.glEnd();
	}
	/**
	 * Function to draw bubble chart of list using random colors
	 * @param gl drawing toll
	 * @param list list of objects
	 */
	public void drawBubbleGraphRandom(GL2 gl, ArrayList<Game> list) {
		GLUT glut2=new GLUT();
		gl.glColor3f(1f,1f,1f); 
		gl.glRasterPos2f(-0.5f, 0.8f);
		glut2.glutBitmapString(2, header);
		gl.glColor3f(1f,1f,1f); 
		gl.glBegin (GL2.GL_LINES);
		gl.glVertex2f(-0.9f,-0.8f);
		gl.glVertex2f(0.9f,-0.8f);
		gl.glEnd();
		gl.glBegin (GL2.GL_LINES);
		gl.glVertex2f(-0.9f,-0.8f);
		gl.glVertex2f(-0.9f,0.8f);
		gl.glEnd();
		Random randomGenerator = new Random();
		for(int r=0;r<3;r++)
			list.get(0).color[r]=randomGenerator.nextFloat();
		float xCenter=-0.4f;
		float yCenter=-0.4f;
		float radius1=(float)((float)list.get(0).sales/50)/10;
		drawSolidCircle(gl,radius1,xCenter,yCenter,360,new float[] {list.get(0).color[0],list.get(0).color[1],list.get(0).color[2]});
		GLUT glut=new GLUT();
		float xText=0.4f;
		float yText=0.2f;
		gl.glColor3f(1f,1f,1f); 
		gl.glRasterPos2f(xText, yText);
		glut.glutBitmapString(2, list.get(0).name);
		float x1Color=0.9f;
		float y1Color=0.2f;
		float x2Color=1f;
		float y2Color=0.2f;
		gl.glColor3f(list.get(0).color[0],list.get(0).color[1],list.get(0).color[2]); 
		gl.glBegin (GL2.GL_LINES);
		gl.glVertex2f(x1Color,y1Color);
		gl.glVertex2f(x2Color,y2Color);
		gl.glEnd();
		for(int i=1;i<list.size();i++) {
			xCenter+=0.1;
			yCenter+=0.1;
			float radius=(float)((float)list.get(i).sales/50)/10;
			for(int j=0;j<list.get(i).color.length;j++)
				list.get(i).color[j]=randomGenerator.nextFloat();
		drawSolidCircle(gl,radius,xCenter,yCenter,360,new float[] {list.get(i).color[0],list.get(i).color[1],list.get(i).color[2]});
		}
		for(int i=1;i<list.size();i++) {
			yText-=0.1;
			gl.glColor3f(1f,1f,1f); 
			gl.glRasterPos2f(xText, yText);
			glut.glutBitmapString(2, list.get(i).name);
			y1Color-=0.1;
			y2Color-=0.1;
			gl.glColor3f(list.get(i).color[0],list.get(i).color[1],list.get(i).color[2]); 
			gl.glBegin (GL2.GL_LINES);
			gl.glVertex2f(x1Color,y1Color);
			gl.glVertex2f(x2Color,y2Color);
			gl.glEnd();
			
		}
	}
	/**
	 * Function to draw bubble chart of list using selected colors
	 * @param gl drawing toll
	 * @param list list of objects
	 */
	public void drawBubbleGraph(GL2 gl, ArrayList<Game> list) {
		GLUT glut2=new GLUT();
		gl.glColor3f(1f,1f,1f); 
		gl.glRasterPos2f(-0.5f, 0.8f);
		glut2.glutBitmapString(2, header);
		gl.glColor3f(1f,1f,1f); 
		gl.glBegin (GL2.GL_LINES);
		gl.glVertex2f(-0.9f,-0.8f);
		gl.glVertex2f(0.9f,-0.8f);
		gl.glEnd();
		gl.glBegin (GL2.GL_LINES);
		gl.glVertex2f(-0.9f,-0.8f);
		gl.glVertex2f(-0.9f,0.8f);
		gl.glEnd();
		Random randomGenerator = new Random();
		float xCenter=-0.4f;
		float yCenter=-0.4f;
		float radius1=(float)((float)list.get(0).sales/50)/10;
		drawSolidCircle(gl,radius1,xCenter,yCenter,360,new float[] {list.get(0).color[0],list.get(0).color[1],list.get(0).color[2]});
		GLUT glut=new GLUT();
		float xText=0.4f;
		float yText=0.2f;
		gl.glColor3f(1f,1f,1f); 
		gl.glRasterPos2f(xText, yText);
		glut.glutBitmapString(2, list.get(0).name);
		float x1Color=0.9f;
		float y1Color=0.2f;
		float x2Color=1f;
		float y2Color=0.2f;
		gl.glColor3f(list.get(0).color[0],list.get(0).color[1],list.get(0).color[2]); 
		gl.glBegin (GL2.GL_LINES);
		gl.glVertex2f(x1Color,y1Color);
		gl.glVertex2f(x2Color,y2Color);
		gl.glEnd();
		for(int i=1;i<list.size();i++) {
			xCenter+=0.1;
			yCenter+=0.1;
			float radius=(float)((float)list.get(i).sales/50)/10;
			for(int j=0;j<list.get(i).color.length;j++)
				list.get(i).color[j]=randomGenerator.nextFloat();
		drawSolidCircle(gl,radius,xCenter,yCenter,360,new float[] {list.get(i).color[0],list.get(i).color[1],list.get(i).color[2]});
		}
		for(int i=1;i<list.size();i++) {
			yText-=0.1;
			gl.glColor3f(1f,1f,1f); 
			gl.glRasterPos2f(xText, yText);
			glut.glutBitmapString(2, list.get(i).name);
			y1Color-=0.1;
			y2Color-=0.1;
			gl.glColor3f(list.get(i).color[0],list.get(i).color[1],list.get(i).color[2]); 
			gl.glBegin (GL2.GL_LINES);
			gl.glVertex2f(x1Color,y1Color);
			gl.glVertex2f(x2Color,y2Color);
			gl.glEnd();
			
		}
	}
	/**
	 * Function to draw column chart of list using random colors
	 * @param gl drawing toll
	 * @param list list of objects
	 */
	public void drawColumnGraphRandom(GL2 gl, ArrayList<Game> list) {
		GLUT glut2=new GLUT();
		gl.glColor3f(1f,1f,1f); 
		gl.glRasterPos2f(-0.5f, 0.8f);
		glut2.glutBitmapString(2, header);
		gl.glColor3f(1f,1f,1f); 
		gl.glBegin (GL2.GL_LINES);
		gl.glVertex2f(-0.8f,-0.9f);
		gl.glVertex2f(0.8f,-0.9f);
		gl.glEnd();
		gl.glBegin (GL2.GL_LINES);
		gl.glVertex2f(-0.8f,-0.9f);
		gl.glVertex2f(-0.8f,0.9f);
		gl.glEnd();
		Random randomGenerator = new Random();
		for(int r=0;r<3;r++)
			list.get(0).color[r]=randomGenerator.nextFloat();
		float yll=-0.7f;
		float xll=-0.8f;
		float ylr=-0.6f;
		float xlr=-0.8f;
		float yul=-0.7f;
		float xul=-0.2f;
		float yur=-0.6f;
		float xur=-0.2f;
		gl.glColor3f(list.get(0).color[0],list.get(0).color[1],list.get(0).color[2]); 
		gl.glBegin (GL2.GL_QUADS);
		//lower left corner
		gl.glVertex2f(xll,yll);
		//lower right corner
		gl.glVertex2f(xlr,ylr);
		//upper left corner
		gl.glVertex2f(xul,yul);
		//upper right corner
		gl.glVertex2f(xur,yur);
		gl.glEnd();
		GLUT glut=new GLUT();
		float xText=0.4f;
		float yText=0.8f;
		gl.glColor3f(1f,1f,1f); 
		gl.glRasterPos2f(xText, yText);
		glut.glutBitmapString(2, list.get(0).name);
		float x1Color=0.9f;
		float y1Color=0.8f;
		float x2Color=1f;
		float y2Color=0.8f;
		gl.glColor3f(list.get(0).color[0],list.get(0).color[1],list.get(0).color[2]); 
		gl.glBegin (GL2.GL_LINES);
		gl.glVertex2f(x1Color,y1Color);
		gl.glVertex2f(x2Color,y2Color);
		gl.glEnd();
		for(int i=1;i<list.size();i++) {
			float tall=(float)((float)list.get(i).sales/17)/10;
			yll+=0.1;
		//	yll+=0.1;
			ylr+=0.1;
		//	ylr+=0.1;
			yul+=0.1;
			//this
			xul=tall;
			yur+=0.1;
			xur=tall;
			for(int j=0;j<list.get(i).color.length;j++)
				list.get(i).color[j]=randomGenerator.nextFloat();
			gl.glColor3f(list.get(i).color[0],list.get(i).color[1],list.get(i).color[2]); 
			gl.glBegin (GL2.GL_QUADS);
			//lower left corner
			gl.glVertex2f(xll,yll);
			//lower right corner
			gl.glVertex2f(xlr,ylr);
			//upper left corner
			gl.glVertex2f(xul,yul);
			//upper right corner
			gl.glVertex2f(xur,yur);
			gl.glEnd();
		}
		for(int i=1;i<list.size();i++) {
			yText-=0.1;
			gl.glColor3f(1f,1f,1f); 
			gl.glRasterPos2f(xText, yText);
			glut.glutBitmapString(2, list.get(i).name);
			y1Color-=0.1;
			y2Color-=0.1;
			gl.glColor3f(list.get(i).color[0],list.get(i).color[1],list.get(i).color[2]); 
			gl.glBegin (GL2.GL_LINES);
			gl.glVertex2f(x1Color,y1Color);
			gl.glVertex2f(x2Color,y2Color);
			gl.glEnd();
			
		}
	//	gl.glEnd();
	}
	/**
	 * Function to draw column chart of list using selected colors
	 * @param gl drawing toll
	 * @param list list of objects
	 */
	public void drawColumnGraph(GL2 gl, ArrayList<Game> list) {
		GLUT glut2=new GLUT();
		gl.glColor3f(1f,1f,1f); 
		gl.glRasterPos2f(-0.5f, 0.8f);
		glut2.glutBitmapString(2, header);
		gl.glColor3f(1f,1f,1f); 
		gl.glBegin (GL2.GL_LINES);
		gl.glVertex2f(-0.8f,-0.9f);
		gl.glVertex2f(0.8f,-0.9f);
		gl.glEnd();
		gl.glBegin (GL2.GL_LINES);
		gl.glVertex2f(-0.8f,-0.9f);
		gl.glVertex2f(-0.8f,0.9f);
		gl.glEnd();
		Random randomGenerator = new Random();
		float yll=-0.7f;
		float xll=-0.8f;
		float ylr=-0.6f;
		float xlr=-0.8f;
		float yul=-0.7f;
		float xul=-0.2f;
		float yur=-0.6f;
		float xur=-0.2f;
		gl.glColor3f(list.get(0).color[0],list.get(0).color[1],list.get(0).color[2]); 
		gl.glBegin (GL2.GL_QUADS);
		//lower left corner
		gl.glVertex2f(xll,yll);
		//lower right corner
		gl.glVertex2f(xlr,ylr);
		//upper left corner
		gl.glVertex2f(xul,yul);
		//upper right corner
		gl.glVertex2f(xur,yur);
		gl.glEnd();
		GLUT glut=new GLUT();
		float xText=0.4f;
		float yText=0.8f;
		gl.glColor3f(1f,1f,1f); 
		gl.glRasterPos2f(xText, yText);
		glut.glutBitmapString(2, list.get(0).name);
		float x1Color=0.9f;
		float y1Color=0.8f;
		float x2Color=1f;
		float y2Color=0.8f;
		gl.glColor3f(list.get(0).color[0],list.get(0).color[1],list.get(0).color[2]); 
		gl.glBegin (GL2.GL_LINES);
		gl.glVertex2f(x1Color,y1Color);
		gl.glVertex2f(x2Color,y2Color);
		gl.glEnd();
		for(int i=1;i<list.size();i++) {
			float tall=(float)((float)list.get(i).sales/17)/10;
			yll+=0.1;
		//	yll+=0.1;
			ylr+=0.1;
		//	ylr+=0.1;
			yul+=0.1;
			//this
			xul=tall;
			yur+=0.1;
			xur=tall;
			gl.glColor3f(list.get(i).color[0],list.get(i).color[1],list.get(i).color[2]); 
			gl.glBegin (GL2.GL_QUADS);
			//lower left corner
			gl.glVertex2f(xll,yll);
			//lower right corner
			gl.glVertex2f(xlr,ylr);
			//upper left corner
			gl.glVertex2f(xul,yul);
			//upper right corner
			gl.glVertex2f(xur,yur);
			gl.glEnd();
		}
		for(int i=1;i<list.size();i++) {
			yText-=0.1;
			gl.glColor3f(1f,1f,1f); 
			gl.glRasterPos2f(xText, yText);
			glut.glutBitmapString(2, list.get(i).name);
			y1Color-=0.1;
			y2Color-=0.1;
			gl.glColor3f(list.get(i).color[0],list.get(i).color[1],list.get(i).color[2]); 
			gl.glBegin (GL2.GL_LINES);
			gl.glVertex2f(x1Color,y1Color);
			gl.glVertex2f(x2Color,y2Color);
			gl.glEnd();
			
		}
	//	gl.glEnd();
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		Label lblTitle=new Label("                ｡:  🎀  𝒢𝓇𝒶𝓅𝒽 𝒢𝑒𝓃𝑒𝓇𝒶𝓉♡𝓇  🎀  :｡");
		lblTitle.setFont(Font.font(17));
		Label lblInput=new Label("Input: \n\n");
		Label lblData=new Label("                      Data: ");
		Label lblCS=new Label("         Color System: ");
		Label lblRandom=new Label("     Random Colors? ");
		Label lblEmpty=new Label(" ");
		RadioButton rbRGB = new RadioButton("RGB");
		rbRGB.setSelected(true);
		rbRGB.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 14));
		rbRGB.setTextFill(Color.RED);
		RadioButton rbCMY = new RadioButton("CMY");
		rbCMY.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 14));
		rbCMY.setTextFill(Color.MEDIUMSEAGREEN);
		RadioButton rbHSV = new RadioButton("HSV");
		rbHSV.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 14));
		rbHSV.setTextFill(Color.BLUE);
		ToggleGroup group = new ToggleGroup();
		rbRGB.setToggleGroup(group);
		rbCMY.setToggleGroup(group);
		rbHSV.setToggleGroup(group);
		HBox hbColor = new HBox(25);
		hbColor.getChildren().addAll(rbRGB,rbCMY,rbHSV);
		hbColor.setTranslateX(10);
		//
		RadioButton rbYes = new RadioButton("YES");
		rbYes.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 14));
		rbYes.setTextFill(Color.GREEN);
		rbYes.setSelected(true);
		RadioButton rbNo = new RadioButton("NO");
		rbNo.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 14));
		rbNo.setTextFill(Color.RED);
		ToggleGroup group2 = new ToggleGroup();
		rbYes.setToggleGroup(group2);
		rbNo.setToggleGroup(group2);
		HBox hbRandom = new HBox(25);
		hbRandom.getChildren().addAll(rbYes,rbNo);
		hbRandom.setTranslateX(10);
		Button btBrowse=new Button("Browse...");
		Button btRun=new Button("     START     ");
		Label lblSpace=new Label("                                         ");
		Label lblConnections1=new Label("          Graph Type:    ");
		ComboBox<String> cbGraph = new ComboBox<>();
		cbGraph.getItems().addAll("Pie Graph","Bar Graph","Bubble Graph","Column Graph");
		cbGraph.setValue("Pie Graph");
		TextField txtConnect2=new TextField();
//		Label lblTable=new Label("Table: \n\n");
		txtConnect2.setEditable(false);
	//	pane.setVgap(10);
		HBox hbox1=new HBox(10);
		HBox hbox2=new HBox(10);
		HBox hbox3=new HBox(10);
		HBox hbox4=new HBox(10);
		HBox hbox5=new HBox(10);
		hbox1.getChildren().addAll(lblData,lblEmpty,btBrowse);
		hbox2.getChildren().addAll(lblCS,hbColor);
		hbox3.getChildren().addAll(lblRandom,hbRandom);
		hbox4.getChildren().addAll(lblConnections1,cbGraph);
		hbox5.getChildren().addAll(lblSpace,btRun);
		FileChooser fileChooser = new FileChooser();
		btBrowse.setOnAction(e->{
			f = fileChooser.showOpenDialog(primaryStage);
			readFile(f);
		//	frame.setVisible(true);
		});
		btRun.setOnAction(e->{
			if(cbGraph.getSelectionModel().getSelectedItem().equals("Pie Graph")&&rbYes.isSelected()) {
				funNum=1;
				frame.dispose();
				frame.setVisible(true);
			}
			
			else if(cbGraph.getSelectionModel().getSelectedItem().equals("Bar Graph")&&rbYes.isSelected()) {
				funNum=2;
				frame.dispose();
				frame.setVisible(true);
			}
			else if(cbGraph.getSelectionModel().getSelectedItem().equals("Bubble Graph")&&rbYes.isSelected()) {
				funNum=3;
				frame.dispose();
				frame.setVisible(true);
			}
			else if(cbGraph.getSelectionModel().getSelectedItem().equals("Column Graph")&&rbYes.isSelected()){
				funNum=4;
				frame.dispose();
				frame.setVisible(true);
			}
			else if(rbNo.isSelected()){
				Stage stage = new Stage();
				stage.initModality(Modality.APPLICATION_MODAL);
				GridPane pane1=new GridPane();
				pane1.setHgap(10);
				pane1.setVgap(10);
				BorderPane pane2=new BorderPane();
				Label lblTVInfo=new Label("Color of: "+list.get(k).name+" (0-255)");
				Label lblColor1=new Label();
				Label lblColor2=new Label();
				Label lblColor3=new Label();
				if(rbRGB.isSelected()) {
				lblColor1=new Label("R:");
				lblColor2=new Label("G:");
				lblColor3=new Label("B:");
				}
				if(rbCMY.isSelected()) {
					lblColor1=new Label("C:");
					lblColor2=new Label("M:");
					lblColor3=new Label("Y:");
				}
				else if(rbHSV.isSelected()){
					lblColor1=new Label("H:");
					lblColor2=new Label("S:");
					lblColor3=new Label("V:");
				}
				TextField txtColor1=new TextField();
//				txtTitle.setText(list.get(i).getTitle());
				TextField txtColor2=new TextField();
//				txtYear.setText(list.get(i).getYear()+"");
				TextField txtColor3=new TextField();
//				txtCountry.setText(list.get(i).getCountry());
//				txtLanguage.setText(list.get(i).getLanguage());
				pane1.addColumn(0,lblColor1, lblColor2,lblColor3);
				pane1.addColumn(1, txtColor1,txtColor2,txtColor3);
				Button btNext=new Button("Next");
				Button btRun2=new Button("Run");
				btRun2.setDisable(true);
				btNext.setOnAction(e1 ->{
				if(k<list.size()-1) {
					if(rbRGB.isSelected()) {
				list.get(k).color[0]=Float.parseFloat(txtColor1.getText())/255;
				list.get(k).color[1]=Float.parseFloat(txtColor2.getText())/255;
				list.get(k).color[2]=Float.parseFloat(txtColor3.getText())/255;
					}
					else if(rbCMY.isSelected()) {
						float[] f=new float[3];
						f[0]=Float.parseFloat(txtColor1.getText())/255;
						f[1]=Float.parseFloat(txtColor2.getText())/255;
						f[2]=Float.parseFloat(txtColor3.getText())/255;
						ColorConverter.CMY2RGB(f);
						list.get(k).color[0]=f[0];
						list.get(k).color[1]=f[1];
						list.get(k).color[2]=f[2];
							}
					else if(rbHSV.isSelected()) {
						float[] f=new float[3];
						f[0]=Float.parseFloat(txtColor1.getText());
						f[1]=Float.parseFloat(txtColor2.getText());
						f[2]=Float.parseFloat(txtColor3.getText());
						ColorConverter.HSV2RGB(f);
						list.get(k).color[0]=f[0];
						list.get(k).color[1]=f[1];
						list.get(k).color[2]=f[2];
							}
				for(int r=0;r<3;r++)
						if(list.get(k).color[r]>1)	{
							Random randomGenerator = new Random();
							list.get(k).color[r]=randomGenerator.nextFloat();
						}
				if(list.get(k).color[0]==0&&list.get(k).color[1]==0&&list.get(k).color[2]==0) {
					Random randomGenerator = new Random();
					for(int r=0;r<3;r++) {
						list.get(k).color[r]=randomGenerator.nextFloat();
					}
				}
				
				for(int r=0;r<3;r++)
					System.out.println(list.get(k).color[r]);
				txtColor1.clear();
				txtColor2.clear();
				txtColor3.clear();
				k++;
				
				lblTVInfo.setText("Color of: "+list.get(k).name);
				}
				if(k==list.size()-1)
					btRun2.setDisable(false);
				});
				btRun2.setOnAction(e2 ->{
					if(rbRGB.isSelected()) {
						list.get(list.size()-1).color[0]=Float.parseFloat(txtColor1.getText())/255;
						list.get(list.size()-1).color[1]=Float.parseFloat(txtColor2.getText())/255;
						list.get(list.size()-1).color[2]=Float.parseFloat(txtColor3.getText())/255;
							}
							else if(rbCMY.isSelected()) {
								float[] f=new float[3];
								f[0]=Float.parseFloat(txtColor1.getText())/255;
								f[1]=Float.parseFloat(txtColor2.getText())/255;
								f[2]=Float.parseFloat(txtColor3.getText())/255;
								ColorConverter.CMY2RGB(f);
								list.get(list.size()-1).color[0]=f[0];
								list.get(list.size()-1).color[1]=f[1];
								list.get(list.size()-1).color[2]=f[2];
									}
							else if(rbHSV.isSelected()) {
								float[] f=new float[3];
								f[0]=Float.parseFloat(txtColor1.getText());
								f[1]=Float.parseFloat(txtColor2.getText());
								f[2]=Float.parseFloat(txtColor3.getText());
								ColorConverter.HSV2RGB(f);
								list.get(list.size()-1).color[0]=f[0];
								list.get(list.size()-1).color[1]=f[1];
								list.get(list.size()-1).color[2]=f[2];
									}
						for(int r=0;r<3;r++)
								if(list.get(list.size()-1).color[r]==0||list.get(k).color[r]>1)	{
									Random randomGenerator = new Random();
									list.get(list.size()-1).color[r]=randomGenerator.nextFloat();
								}
						
					k=0;
					if(cbGraph.getSelectionModel().getSelectedItem().equals("Pie Graph")) {
					funNum=5;
					frame.dispose();
					frame.setVisible(true);
					}
					else if(cbGraph.getSelectionModel().getSelectedItem().equals("Bar Graph")) {
						funNum=6;
						frame.dispose();
						frame.setVisible(true);
						}
					if(cbGraph.getSelectionModel().getSelectedItem().equals("Bubble Graph")) {
						funNum=7;
						frame.dispose();
						frame.setVisible(true);
						}
					if(cbGraph.getSelectionModel().getSelectedItem().equals("Column Graph")) {
						funNum=8;
						frame.dispose();
						frame.setVisible(true);
						}
						});	
				
				HBox hbox=new HBox();
				hbox.setSpacing(5);
				hbox.getChildren().add(btNext);
				hbox.getChildren().add(btRun2);
				hbox.setAlignment(Pos.BASELINE_CENTER);
				lblTVInfo.setAlignment(Pos.BASELINE_LEFT);
				pane2.setTop(lblTVInfo);
				pane2.setCenter(pane1);
				pane2.setBottom(hbox);
				Scene scene=new Scene(pane2);
				stage.setTitle("Color Range (0-255)");
				stage.setScene(scene);
				stage.setResizable(false);
				stage.show();
			}
		});
	     Text text1 = new Text("     ｡:  🎀 G");
	     text1.setFill(Color.RED);
	     text1.setFont(Font.font(24));
	     Text text2 = new Text("R");
	     text2.setFill(Color.ORANGE);
	     text2.setFont(Font.font(24));
	     Text text3 = new Text("A");
	     text3.setFill(Color.YELLOW);
	     text3.setFont(Font.font(24));
	     Text text4 = new Text("P");
	     text4.setFill(Color.LAWNGREEN);
	     text4.setFont(Font.font(24));
	     Text text5 = new Text("H ");
	     text5.setFill(Color.DEEPSKYBLUE);
	     text5.setFont(Font.font(24));
	     Text text6 = new Text("G");
	     text6.setFill(Color.BLUE);
	     text6.setFont(Font.font(24));
	     Text text7 = new Text("E");
	     text7.setFill(Color.PURPLE);
	     text7.setFont(Font.font(24));
	     Text text8 = new Text("N");
	     text8.setFill(Color.RED);
	     text8.setFont(Font.font(24));
	     Text text9 = new Text("E");
	     text9.setFill(Color.ORANGE);
	     text9.setFont(Font.font(24));
	     Text text10 = new Text("R");
	     text10.setFill(Color.YELLOW);
	     text10.setFont(Font.font(24));
	     Text text11 = new Text("A");
	     text11.setFill(Color.LAWNGREEN);
	     text11.setFont(Font.font(24));
	     Text text12 = new Text("T");
	     text12.setFill(Color.DEEPSKYBLUE);
	     text12.setFont(Font.font(24));
	     Text text13 = new Text("O");
	     text13.setFill(Color.BLUE);
	     text13.setFont(Font.font(24));
	     Text text14 = new Text("R  🎀  :｡");
	     text14.setFill(Color.PURPLE);
	     text14.setFont(Font.font(24));
	     TextFlow textFlow = new TextFlow(text1, text2,text3,text4,text5,text6,text7,text8,text9,text10,text11,text12,text13,text14);
		GridPane pane=new GridPane();
		pane.setVgap(10);
	//	pane.add(lblTitle, 0, 0);
		pane.add(textFlow, 0, 0);
//		pane.add(lblInput, 0, 1);
		pane.add(hbox1, 0, 4);
		pane.add(hbox2, 0, 5);
		pane.add(hbox3, 0, 7); 
//		pane.add(lblOutput, 0, 8);
		pane.add(hbox4, 0, 9);
		pane.add(hbox5, 0, 13);
		
		Scene scene = new Scene(pane);
		primaryStage.setHeight(350);
		primaryStage.setWidth(400);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Project 1");
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}
	/**
	 * function to read data from file
	 * @param f selected file
	 */
	public static void readFile(File f) {
		Scanner scan = null;
		try {
			scan = new Scanner(f);
		} catch (FileNotFoundException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("File Not Found ");
			alert.setHeaderText("There seems to be a problem with the file you selected");
			alert.setContentText("This file does not exist.");
			alert.show();
			return;
		}
		catch(NullPointerException e) {
			return;
		}
		header=scan.nextLine();
		while(scan.hasNext()) {
			String line = scan.nextLine();
			String[] token = line.split(":");
			String name=token[0];
			int sales=Integer.parseInt(token[1].trim());
			list.add(new Game(name,sales));
		} 
		scan.close();
	}
}