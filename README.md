# Conduct-Me
Version 0.9.0
At the moment, this project is completely nowhere near being finished - this is simply the hackathon build. I am currently in the process of rewriting the algorithm that determines when to proceed to the next note - ideally, I would like to use machine learning to process and adjust these points so that conducting feels more natural.

After I finish that, and clean up, debug/redesign the user interface, and clean up my code, I will launch an official build. 

Within the next few months, I plan on adding VR support for the Oculus Rift, either in Java, or more likely, by porting over to Unity and developing it on their platform.

In order to run this version of the code, you will need to have the JavaFX SDK installed, and the Leap Motion SDK installed. If you want the code to execute more than five lines without exiting, you'll need to have a Leap Motion controller plugged into your computer as well.

Currently, only 4/4 time signature is supported. Conducting in a marching band style works best with the current rushed hackathon algorithm - straight down for beat one, straight in for beat two, straight our for beat 3, straight in halfway, than straight up for beat four. A more naturally conducting pattern also works, but I find that the program will often double register beats because of the wrist flick many people add in.
