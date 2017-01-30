package app.com.example.althomas04.basicmalayalam;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;

    AudioManager.OnAudioFocusChangeListener afChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        // Pause playback
                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // Resume playback
                        mediaPlayer.start();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        // Stop playback
                        releaseMediaPlayer();
                    }
                }
            };

    private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<word> phrasesArrayList = new ArrayList<word>();
        phrasesArrayList.add(new word("Hello", "namaskaram", R.raw.phrase_hello));
        phrasesArrayList.add(new word("What is your name?", "perentha?", R.raw.phrase_what_is_your_name));
        phrasesArrayList.add(new word("My name is...", "ente per ... an", R.raw.phrase_my_name_is));
        phrasesArrayList.add(new word("How are you?", "enganeuntu Karyangal?", R.raw.phrase_how_are_you));
        phrasesArrayList.add(new word("I’m doing well.", "nannayirikkunnu", R.raw.phrase_im_doing_well));
        phrasesArrayList.add(new word("Can you speak in ...?", "ningal ... samsarikkumo?", R.raw.phrase_can_you_speak_in));
        phrasesArrayList.add(new word("Yes, a little.", "ah kuresha", R.raw.phrase_yes_a_little));
        phrasesArrayList.add(new word("no, I cannot (speak).", "illa samsarikarilla", R.raw.phrase_no_cannot_speak));
        phrasesArrayList.add(new word("Excuse me.", "shamikyanam", R.raw.phrase_excuse_me));
        phrasesArrayList.add(new word("Thank you.", "nanni", R.raw.phrase_thank_you));

        WordAdapter adapter = new WordAdapter(this, phrasesArrayList, R.color.category_phrases);
        ListView numbersListView = (ListView) findViewById(R.id.listView);
        numbersListView.setAdapter(adapter);

        numbersListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id){
                word item = phrasesArrayList.get(position);
                releaseMediaPlayer();
                int result = audioManager.requestAudioFocus(afChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(PhrasesActivity.this, item.getAudioResourseId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(mOnCompletionListener);
                }
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();
            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;
            //Abandons audio focus from the app when no longer needed.
            audioManager.abandonAudioFocus(afChangeListener);
        }
    }
}

