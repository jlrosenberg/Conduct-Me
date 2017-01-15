package application;
import java.util.HashMap;
import java.util.function.Function;

import com.leapmotion.leap.Vector;

public class Note {
	static HashMap<String, Integer> noteMap;
	int midiPitch;
	int numBeats;
	int intensity;
	String str;
	
	public Note(String note,int octave, int numBeats){
		str=note;
		if(noteMap==null){
			noteMap=new HashMap<String, Integer>(17);
			populateNotemap();
			
		}
		if(!note.equalsIgnoreCase("REST")){
			setMidiPitch(12*octave+24+noteMap.get(note));
		}else{
			setMidiPitch(1000);
		}
		setNumBeats(numBeats);
		intensity=93;
	}
	private static void populateNotemap(){
		String[] arr={"B#","C", "C#", "Db", "D", "D#", "Eb","E","E#","Fb","F","F#","Gb","G", "G#", "Ab", "A", "A#","Bb","B","Cb", "REST"};
		int[] arrNum={0,0,1,1,2,3,3,4,5,4,5,6,6,7,8,8,9,10,10,11,11,1000};
		for(int i=0;i<arr.length;i++){
			noteMap.put(arr[i],new Integer(arrNum[i]));
		}
	}
	public int getMidiPitch() {
		return midiPitch;
	}
	public void setMidiPitch(int midiPitch) {
		this.midiPitch = midiPitch;
	}
	public int getNumBeats() {
		return numBeats;
	}
	public void setNumBeats(int numBeats) {
		this.numBeats = numBeats;
	}
	public int getIntensity() {
		return intensity;
	}
	public void setIntensity(int intensity) {
		this.intensity = intensity;
		//Function<Vector, Boolean>[] arr = null;
		//arr[0] = v -> {
		//	return true;
		//};
		//arr[0].apply(null);
	}
	

}
