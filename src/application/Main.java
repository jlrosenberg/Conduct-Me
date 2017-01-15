package application;
	
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.sound.midi.MidiUnavailableException;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.HandList;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.Vector;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application {
	
	 private DoubleProperty centerY = new SimpleDoubleProperty(0);
	 private DoubleProperty centerX = new SimpleDoubleProperty(0);
	 private DoubleProperty radius = new SimpleDoubleProperty(10);
	 public Text beatNum=new Text();
	 
	 public Text getText(){
		 return beatNum;
	 }
	    
	public DoubleProperty centerX() {
	   return centerX;
	}
	    
	public DoubleProperty centerY() {
	   return centerY;
	}
	    
	public DoubleProperty radius () {
	    return radius;
	}
	
 public void start(Stage primaryStage) throws FileNotFoundException {
        //Circle circle = new Circle(20);
        //circle.setFill(Paint.valueOf("green"));
        //circle.translateXProperty().bind(centerX);
        //circle.translateYProperty().bind(centerY);
       // circle.radiusProperty().bind(radius);
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
        
        beatNum.setFont(new Font(20));
        beatNum.setText("Beat Number: 1");
        StackPane root = new StackPane();
        root.setId("pane");
       // root.getChildren().add(circle);
        
        Font ft=new Font(20);
        beatNum.setFill(Paint.valueOf("white"));
        
        Scene scene = new Scene(root, 1080, 1920);
        scene.getStylesheets().addAll(this.getClass().getResource("application.css").toExternalForm());
        Image image = new Image(new FileInputStream("ConductorsHands.png"));  //pass in the image path
        //scene.setCursor(new ImageCursor(image));
        ImageView img=new ImageView();
        img.setImage(image);
        img.setScaleX(4);
        img.setScaleY(4);
        img.translateXProperty().bind(centerX);
        img.translateYProperty().bind(centerY);
        root.getChildren().add(img);
        root.getChildren().add(beatNum);
        beatNum.setX(100);
        beatNum.setY(900);
        beatNum.setTranslateX(-700);
        beatNum.setTranslateY(-450);
      
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
         c = new Controller();
         listener = new SampleListener(this);
        c.addListener(listener);
    }
    Listener listener;
 Controller c;
    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}

class SampleListener extends Listener {

	private Main app;
	public SampleListener(Main main) {
	        this.app = main;
    }
	int currentBeat=-1;
	HandList hands;
	Hand hand;
    public void onConnect(Controller controller) {
        System.out.println("Connected");
    }

    boolean first=true;
    float previousX, previousY;
    int count=0;
    int antiCount=0;
    Song sng;
    Vector previousVelocity, previousPosition;
    long prevTime = System.currentTimeMillis();;
    
    @SuppressWarnings("null")
	public void onFrame(Controller controller) {

       
    	
    	
    	Frame frame = controller.frame();
        
        
        	hands=frame.hands();
        	hand=hands.leftmost();
        	
        	if (!hands.isEmpty()) {
                Hand hand2 = hands.get(0);
                final float x = hand2.palmPosition().getX();
                float y = hand2.palmPosition().getY();
                float z = hand2.palmPosition().getX();
                Platform.runLater(() -> {
                    app.centerX().set((x)*2);
                    app.centerY().set((500-y)*2);
                    //app.radius().set(50.-y/5);
                });
            }
        	
        Vector velocity = hand.palmVelocity();
      

       
        if(!first){
        	//If we already have a previous value instantiated, then we can proceed normally, otherwise, we instantiate previous, and instantiate the song being played
        	if(sng.getCurrentBeat()%4==1||sng.getCurrentBeat()%4==0){
        		//System.out.println("Here");
        		//System.out.println(hand.palmPosition().getY()-previousPosition.getY());
	        	if(velocity.getY()/previousVelocity.getY()<=0){

		        	if(Math.abs(velocity.getY())>200&&System.currentTimeMillis()-prevTime>250){
		        		if(sng.getCurrentBeat()<2||Math.abs(hand.palmPosition().getY()-previousPosition.getY())>80){
		        			//System.out.println("Next Note");
			        		previousVelocity=velocity;
			        		previousPosition=hand.palmPosition();
			        		//app.beatNum.setText("Beat Number: "+sng.getCurrentBeat());
			        		try {
			        			//System.out.println("Callin");
								nextNote();
								
								prevTime = System.currentTimeMillis();
							
							} catch (MidiUnavailableException | InterruptedException e) {
								e.printStackTrace();
							}
			        		
		        		}
		        	}
	        	}else{
	        		previousVelocity=velocity;	
	        	}
			}else{
	        	if(velocity.getX()/previousVelocity.getX()<=0){
		        	if(Math.abs(velocity.getX())>200&&System.currentTimeMillis()-prevTime>250){
		        		if(sng.getCurrentBeat()<2||Math.abs(hand.palmPosition().getX()-previousPosition.getX())>80){
		        			
			        		System.out.println("Next note");
			        		previousVelocity=velocity;
			        		previousPosition=hand.palmPosition();
			        		//app.beatNum.setText("Beat Number: "+sng.getCurrentBeat());
			        		try {
								nextNote();
								prevTime = System.currentTimeMillis();
								
							} catch (MidiUnavailableException | InterruptedException e) {
								e.printStackTrace();
							}
			        		
		        		}
		        	}
	        	}else{
	        		previousVelocity=velocity;	
	        		
	        	}
			}
        }else{
        	previousVelocity=velocity;
        	first=false;
        	prevTime = System.currentTimeMillis();
        	
        	try {
				sng=new Song("tetris_quartet.txt");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MidiUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
        
			}
		}
    }

    

    
    public void nextNote() throws MidiUnavailableException, InterruptedException
    {
    	sng.playNext();

    	/**
    	Synthesizer synth=MidiSystem.getSynthesizer();
    	synth.open();
    	MidiChannel chan[]=synth. getChannels();
    	int[] notes={51,49,47,49,51,51,51,49,49,49,51,54,54, 51, 49, 47, 49, 51,51,51,51,49,49,51,49,47};
    	if(currentBeat!=0){
    		chan[4].noteOff(notes[currentBeat-1],1000);
    	}
    	chan[4].noteOn(notes[currentBeat], 1000);
    	currentBeat++;
    	**/
    }
}
