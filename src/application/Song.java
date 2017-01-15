package application;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.sound.midi.MidiUnavailableException;



//Beat is equal to smallest note value in song - adjust it so that smallest beat is one, and multiply other note lengths accordingly
public class Song {

	private ArrayList<Part> parts;
	int numParts, numBeats, timeSig, currentBeat;
	String fileFormat;
	String title,composer;
	
	public Song(String filename) throws FileNotFoundException, MidiUnavailableException{
		parseFile(filename);
		currentBeat=1;
	
	}
	
	private void parseFile(String filename) throws FileNotFoundException, MidiUnavailableException{
		Scanner scan=new Scanner(new File(filename));
		String header=scan.nextLine();
		String[] head =header.split(", ");
		for(int i=0;i<head.length;i++)
			System.out.println(head[i]);
		Scanner headScanner=new Scanner(header);
		headScanner.useDelimiter(", ");
		this.title=head[0];
		this.composer=head[1];
		this.numBeats=Integer.parseInt(head[2]);
		this.numParts=Integer.parseInt(head[3]);
		this.timeSig=Integer.parseInt(head[4]);
		parts=new ArrayList<Part>(numParts);
		int i=0;
		while(scan.hasNext()){
			parts.add(new Part(scan.nextLine(), numBeats,i));
			i++;
			System.out.println("part added");
		}
	}
	
	/*public Song(String title, String composer, String[] parts, String version, int beatsPerMeasure, int numBeats) throws MidiUnavailableException
{
		fileFormat=version;
		timeSig=beatsPerMeasure;
		this.parts=new ArrayList<Part>();
		for(int i=0;i<parts.length;i++){
			this.parts.add(new Part(parts[i], numBeats));
		}
		this.title=title;
		this.composer=composer;
		
	}
	*/
	
	public void playNext() throws InterruptedException{
		for(int i=0;i<parts.size();i++){
			parts.get(i).playNext();

		}
		currentBeat++;
		System.out.println("currentBeat "+currentBeat%4);
	}
	
	/**
	 * @return the numParts
	 */
	public int getNumParts() {
		return numParts;
	}

	/**
	 * @param numParts the numParts to set
	 */
	public void setNumParts(int numParts) {
		this.numParts = numParts;
	}

	/**
	 * @return the numBeats
	 */
	public int getNumBeats() {
		return numBeats;
	}

	/**
	 * @param numBeats the numBeats to set
	 */
	public void setNumBeats(int numBeats) {
		this.numBeats = numBeats;
	}

	/**
	 * @return the timeSig
	 */
	public int getTimeSig() {
		return timeSig;
	}

	/**
	 * @param timeSig the timeSig to set
	 */
	public void setTimeSig(int timeSig) {
		this.timeSig = timeSig;
	}

	/**
	 * @return the fileFormat
	 */
	public String getFileFormat() {
		return fileFormat;
	}

	/**
	 * @param fileFormat the fileFormat to set
	 */
	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the composer
	 */
	public String getComposer() {
		return composer;
	}

	/**
	 * @param composer the composer to set
	 */
	public void setComposer(String composer) {
		this.composer = composer;
	}

	/**
	 * @return the currentBeat
	 */
	public int getCurrentBeat() {
		return currentBeat;
	}

	/**
	 * @param currentBeat the currentBeat to set
	 */
	public void setCurrentBeat(int currentBeat) {
		this.currentBeat = currentBeat;
	}

	public String toString(){
		return title +" by "+ composer;
	}
	
}
	

