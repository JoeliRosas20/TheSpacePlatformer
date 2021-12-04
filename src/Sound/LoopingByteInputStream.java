package Sound;

import java.io.*;

public class LoopingByteInputStream extends ByteArrayInputStream{//To help keep the music running infinitely

    private boolean closed;

    public LoopingByteInputStream(byte[] buf) {
        super(buf);
        closed = false;
    }

    public int read(byte[] buffer, int offset, int length){
        if (closed){
            return -1;
        }
        int totalBytesRead = 0;
        while (totalBytesRead < length){
            int numBytesRead = super.read(buffer, offset + totalBytesRead, length - totalBytesRead);
            if (numBytesRead > 0){
                totalBytesRead += numBytesRead;
            }
            else{
                reset();
            }
        }
        return totalBytesRead;
    }

    public void close() throws IOException{
        super.close();
        closed = true;
    }
}
