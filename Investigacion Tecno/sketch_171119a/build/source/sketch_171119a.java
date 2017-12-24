import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.serial.*; 
import org.openkinect.freenect.*; 
import org.openkinect.processing.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class sketch_171119a extends PApplet {

// import org.openkinect.processing.*;
//
// Kinect kinect;
//
// void setup() {
//   size(512, 424, P3D);
//   kinect = new Kinect(this);
//   kinect.initDepth();
// }
//
// void draw() {
//   background(0);
//
//   PImage img = kinect.getDepthImage();
//   //image(img, 0, 0);
//
//   int skip = 20;
//   for (int x = 0; x < img.width; x+=skip) {
//     for (int y = 0; y < img.height; y+=skip) {
//       int index = x + y * img.width;
//       float b = brightness(img.pixels[index]);
//       float z = map(b, 0, 255, 250, -250);
//       fill(255-b);
//       pushMatrix();
//       translate(x, y, z);
//       rect(0, 0, skip/2, skip/2);
//       popMatrix();
//     }
//   }
// }

 //Importamos la librer\u00eda Serial
 // Importamos las librerias para kinect


Serial port; //Nombre del puerto serie

Kinect kinect; // Declaramos el kinect

float deg;

boolean ir = false;
boolean colorDepth = false;
boolean mirror = false;


boolean status=false; //Estado del color de rect
String texto="LED OFF";//Texto del status inicial del LED

int valor;//Valor de la temperatura

public void setup()
{
  println(Serial.list()); //Visualiza los puertos serie disponibles en la consola de abajo
  port = new Serial(this, Serial.list()[1], 9600); //Abre el puerto serie COM3

   //Creamos una ventana
  kinect = new Kinect(this);
  kinect.initDepth();
  kinect.initVideo();

  kinect.enableColorDepth(colorDepth);

  deg = kinect.getTilt();
}

public void draw()
{
  background(0);//Fondo de color negro
  image(kinect.getVideoImage(), 0, 0);
  image(kinect.getDepthImage(), 640, 0);
  fill(255);

  text(
    "Press 'i' to enable/disable between video image and IR image,  " +
    "Press 'c' to enable/disable between color depth and gray scale depth,  " +
    "Press 'm' to enable/diable mirror mode, "+
    "UP and DOWN to tilt camera   " +
    "Framerate: " + PApplet.parseInt(frameRate), 10, 515);

  //Recibir datos temperatura del Arduino
  if(port.available() > 0) // si hay alg\u00fan dato disponible en el puerto
   {
     valor=port.read();//Lee el dato y lo almacena en la variable "valor"
   }
   //Visualizamos la temperatura con un texto
   text("Temperatura =",390,200);
   text(valor, 520, 200);
   text("\u00baC",547,200);

}

public void keyPressed()
{
  if (key == 'i') {
    ir = !ir;
    kinect.enableIR(ir);
  } else if (key == 'c') {
    colorDepth = !colorDepth;
    kinect.enableColorDepth(colorDepth);
  }else if(key == 'm'){
    mirror = !mirror;
    kinect.enableMirror(mirror);
  } else if (key == CODED) {
    if (keyCode == UP) {
      deg++;
    } else if (keyCode == DOWN) {
      deg--;
    }
    deg = constrain(deg, 0, 30);
    kinect.setTilt(deg);
  }
}
  public void settings() {  size(1280, 520); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "sketch_171119a" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
