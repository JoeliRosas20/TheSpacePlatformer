package Sound;

import java.io.*;
import javax.sound.sampled.*;

public class SimpleSoundPlayer {

    public static void main(String[] args){
        System.out.println("Running");
        SimpleSoundPlayer sound = new SimpleSoundPlayer("Sound//Cyberpunk Moonlight Sonata.wav");
        LoopingByteInputStream stream = new LoopingByteInputStream(sound.getSamples());
        sound.play(stream);
        System.exit(0);
    }

    private AudioFormat format;
    private byte[] samples;

    //Open a sound from a file
    public SimpleSoundPlayer(String filename){
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(filename));
            format = stream.getFormat();
            samples = getSamples(stream);
        } catch (UnsupportedAudioFileException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    //Gets the sample of this sound as a byte array
    public byte[] getSamples() {
        return samples;
    }

    //Gets the samples from an AudioInputStream as an array of bytes
    private byte[] getSamples(AudioInputStream stream){
        int length = (int) (stream.getFrameLength() * format.getFrameSize());
        byte[] samples = new byte[length];
        DataInputStream is = new DataInputStream(stream);
        try {
            is.readFully(samples);
        } catch (IOException e){
            e.printStackTrace();
        }
        return samples;
    }

    //Plays a stream. This method blocks (doesn't return) until the sound is finished playing
    public void play(InputStream source){
        int bufferSize = format.getFrameSize() * Math.round(format.getSampleRate() / 10);
        byte[] buffer = new byte[bufferSize];
        //Create a line to play to
        SourceDataLine line;
        try {
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format, bufferSize);
        }catch (LineUnavailableException e){
            e.printStackTrace();
            return;
        }
        //start the line
        line.start();
        //copy data to the line
        try {
            int numBytesRead = 0;
            while (numBytesRead != 1){
                numBytesRead = source.read(buffer, 0, buffer.length);
                if (numBytesRead != -1){
                    line.write(buffer, 0, numBytesRead);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        //wait until all data is played
        line.drain();
        //close the line
        line.close();
    }
}
