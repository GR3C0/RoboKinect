// Prueba de arduino y processing

// Enviar datos: Serial.write(variable);

int led=9;
boolean status=LOW; //Estado del led

void setup()
{
  Serial.begin(9600);
  pinMode(led,OUTPUT);
}

void loop()
{
  Serial.write(temp); //Enviamos los datos en forma de byte
  delay(100);

  if(Serial.available()>0)//Si el Arduino recibe datos a través del puerto serie
  {
    byte dato = Serial.read(); //Los almacena en la variable "dato"
    if(dato==65)  //Si recibe una "A" (en ASCII "65")
    {
      status=!status; //Cambia el estatus del led
    }
    digitalWrite(led,status);
  }
}
