package main.java.repo;

import com.mpatric.mp3agic.Mp3File;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

import java.io.File;

public class AudioFiles {
    private String fileTitle;
    private String fileArtist;
    private String fileLength;
    private final String filePath;
    private Mp3File mp3File;
    private double frame;

    public AudioFiles(String path){
        this.filePath = path;
        try {
            mp3File = new Mp3File(path);
            frame = (double) mp3File.getFrameCount() / mp3File.getLengthInMilliseconds();
            fileLength = covertToSongLengthFormat();

            AudioFile audioFile = AudioFileIO.read(new File(path));
            Tag tag = audioFile.getTag();
            if (tag != null){
                this.fileTitle = tag.getFirst(FieldKey.TITLE);
                this.fileArtist = tag.getFirst(FieldKey.ARTIST);
            }else {
                this.fileTitle = "Unknown";
                this.fileArtist = "Unknown";
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String covertToSongLengthFormat(){
        long mint = mp3File.getLengthInSeconds() / 60;
        long sec = mp3File.getLengthInSeconds() % 60;
        return String.format("%02d:%02d",mint,sec);
    }


    public String getSongTitle() {
        return fileTitle;
    }

    public String getSongArtist() {
        return fileArtist;
    }

    public String getFileLength() {
        return fileLength;
    }

    public String getSongPath() {
        return filePath;
    }

    public Mp3File getMp3File() {
        return mp3File;
    }

    public double getFrame() {
        return frame;
    }
}
