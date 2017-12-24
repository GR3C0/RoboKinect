import processing.serial.*; //Importamos la librería Serial
import org.openkinect.freenect.*; // Importamos las librerias para kinect
import org.openkinect.processing.*;

Serial port; //Nombre del puerto serie
// Para enviar datos: port.write('d');

Kinect kinect; // Declaramos el kinect

float deg;

boolean ir = false;
boolean colorDepth = false;
boolean mirror = false;


boolean status=false; //Estado del color de rect
String texto="LED OFF";//Texto del status inicial del LED

int valor;//Valor de la temperatura

void setup()
{
  println(Serial.list()); //Visualiza los puertos serie disponibles en la consola de abajo
  port = new Serial(this, Serial.list()[1], 9600); //Abre el puerto serie COM3

  size(1280, 520); //Creamos una ventana
  kinect = new Kinect(this);
  kinect.initDepth(); // Iniciamos los diferentes modos del Kinect
  kinect.initVideo();

  kinect.enableColorDepth(colorDepth);

  deg = kinect.getTilt();
}

void draw()
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
    "Framerate: " + int(frameRate), 10, 515);

  //Recibir datos temperatura del Arduino
  if(port.available() > 0) // si hay algún dato disponible en el puerto
   {
     valor=port.read();//Lee el dato y lo almacena en la variable "valor"
   }
   //Visualizamos la temperatura con un texto
   text("Temperatura =",390,200);
   text(valor, 520, 200);
   text("ºC",547,200);

}

void keyPressed()
{
  if (key == 'i') {
    ir = !ir;
    kinect.enableIR(ir);
    port.write('E');
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
