#include <Stepper.h>
#define STEPS 2048

Stepper stepper(STEPS, 8, 9, 10, 11);

void setup()
{
  stepper.setSpeed(10);
}

void loop()
{
  stepper.step(2048);
}
