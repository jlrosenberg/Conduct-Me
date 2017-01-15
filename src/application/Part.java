package application;
import java.util.ArrayList;
import java.util.Scanner;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class Part {
	
	private int currentNote, partNum;
	private ArrayList<Note> notes;
	private int beatsPlayed;
	Scanner scan;
	Synthesizer synth;
	MidiChannel[] chan;
	int instrumentNum;
	
	public Part(String unparsedVoice, int numBeats, int partNum) throws MidiUnavailableException{
		
		notes=new ArrayList<Note>(numBeats);
		this.partNum=partNum+3;
		parseVoice(unparsedVoice);
		beatsPlayed=0;
		synth=MidiSystem.getSynthesizer();
		synth.open();
		Instrument[] arr=synth.getAvailableInstruments();
		synth.loadInstrument(arr[instrumentNum]); 
		for(int i=0;i<arr.length;i++)
			System.out.println(arr[i].toString()+"   "+i);
		synth.loadInstrument(arr[instrumentNum]);
	//	Instrument inst=
		this.chan=synth.getChannels();
		chan[4].programChange(arr[instrumentNum].getPatch().getProgram());
	}
	
	private void parseVoice(String unparsedVoice){
		scan=new Scanner(unparsedVoice);
		scan.useDelimiter(", ");
		instrumentNum=scan.nextInt();
		while(scan.hasNext()){
			notes.add(new Note(scan.next(), scan.nextInt(),scan.nextInt()));
		}
	}
	
	public void playNext() throws InterruptedException{
		
		if(beatsPlayed==notes.get(currentNote).getNumBeats()&&beatsPlayed!=0){
			endNote(notes.get(currentNote));
			currentNote++;
			System.out.println(notes.get(currentNote).str);
			if(notes.get(currentNote).getMidiPitch()!=1000){
				playNote(notes.get(currentNote));
			}
			beatsPlayed=1;
		}else if(beatsPlayed==0){
			if(notes.get(currentNote).getMidiPitch()!=1000){
				playNote(notes.get(currentNote));
			}
				beatsPlayed=1;
		}else{
			beatsPlayed++;
			System.out.println(notes.get(currentNote).str);
		}
			
	}
	
	
	public void getPreviousNote()
	{
		
	}
	
	public void silenceNote(){
		
	}
	
	public void playNote(Note n) throws InterruptedException{
		chan[4].noteOn(n.midiPitch, n.intensity);
		
	}
	
	public void endNote(Note n){
		chan[4].noteOff(n.midiPitch);
	}
}
