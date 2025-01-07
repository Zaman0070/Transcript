package main.java.repo;//package repo;
//import javazoom.jl.player.advanced.AdvancedPlayer;
//import javazoom.jl.player.advanced.PlaybackEvent;
//import javazoom.jl.player.advanced.PlaybackListener;
//
//import java.io.*;
//import java.util.ArrayList;
//
//public class MusicPlayer extends PlaybackListener {
//
//    private static final  Object playSignal = new Object();
//    private MusicPlayerGuis gui;
//    private AudioFiles audioFile;
//    public AudioFiles getSong() {
//        return audioFile;
//    }
//    private ArrayList<AudioFiles> playList;
//    private int playListIndex;
//    private AdvancedPlayer player;
//    private boolean isPaused;
//    private boolean songEnded;
//    private boolean pressedNext, pressedPrevious;
//    private int currentFrame;
//    public void setCurrentFrame(int currentFrame) {
//        this.currentFrame = currentFrame;
//    }
//    private int currentTimeMillis;
//
//    public void setCurrentTimeMillis(int currentTimeMillis) {
//        this.currentTimeMillis = currentTimeMillis;
//    }
//
//    public MusicPlayer(MusicPlayerGuis gui){
//        this.gui = gui;
//    }
//
//    public void loadSong(AudioFiles loadSong){
//        audioFile = loadSong;
//        playList = null;
//        if (!songEnded)
//            stopSong();
//        if (audioFile!=null){
//            gui.getPlaybackPanel().togglePlayPause(true);
//            gui.getSongInfoPanel().updateSongInfo(audioFile);
//            gui.getPlaybackPanel().updatePlaybackSlider(song);
//            currentTimeMillis = 0;
//            currentFrame = 0;
//            playCurrentSong();
//
//        }
//
//    }
//
//
//
//
//
//    public void playCurrentSong(){
//        if(audioFile==null)return;
//        try {
//            FileInputStream fileInputStream = new FileInputStream(song.getSongPath());
//            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
//            player = new AdvancedPlayer(bufferedInputStream);
//            player.setPlayBackListener(this);
//
//            startMusicThread();
//            startPlaybackSliderThread();
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    public void pauseSong(){
//        if(player !=null){
//            isPaused = true;
//            stopSong();
//        }
//    }
//
//    public void stopSong(){
//        if (player!=null){
//            player.stop();
//            player.close();
//            player =null;
//        }
//    }
//
//
//
//
//
//    private void startMusicThread(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    if(isPaused){
//                        synchronized (playSignal){
//                            isPaused = false;
//                            playSignal.notify();
//                        }
//
//                        player.play(currentFrame,Integer.MAX_VALUE);
//                    }
//                    else {
//                        player.play();
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//
//            }
//        }).start();
//    }
//
//    private void startPlaybackSliderThread() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                if(isPaused){
//                    try {
//                        synchronized (playSignal){
//                            playSignal.wait();
//                        }
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//                while (!isPaused && !songEnded && !pressedNext && !pressedPrevious){
//                   try {
//                       currentTimeMillis++;
//                       int calculatedFrame = (int) ((double) currentTimeMillis * 2.08 *song.getFrame());
//                       gui.getPlaybackPanel().setPlaybackSliderValue(calculatedFrame);
//                          Thread.sleep(1);
//                   }catch (Exception e){
//                       e.printStackTrace();
//                   }
//
//                }
//            }
//        }).start();
//
//    }
//
//    @Override
//    public void playbackStarted(PlaybackEvent evt) {
//        System.out.println("Playback Start");
//        songEnded = false;
//        pressedNext = false;
//        pressedPrevious = false;
//    }
//
//    @Override
//    public void playbackFinished(PlaybackEvent evt) {
//        System.out.println("Playback Finish");
//        System.out.println("Frame: "+evt.getFrame());
//        if (isPaused){
//            currentFrame +=(int) ((double) evt.getFrame() * song.getFrame());
//        }else {
//            // if next or previous button is pressed we don't want to play the next song
//            if (pressedNext || pressedPrevious) return;
//            songEnded = true;
//            if (playList==null){
//                gui.getPlaybackPanel().togglePlayPause(false);
//            }else {
//
//            }
//        }
//    }
//}
