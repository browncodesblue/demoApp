package com.ibm.browna.grit3_android.Views.Assessments;

import android.app.Fragment;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.ibm.browna.grit3_android.R;
import com.ibm.browna.grit3_android.WatsonTone.MainActivity;

import java.io.IOException;
import java.util.Random;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


/**
 * Created by browna on 2/2/2017.
 */

public class StoryFragment extends Fragment {

    ImageView mStoryRecord;
    Button mStorySave;
    Button buttonPlayLastRecordAudio;
    int count;
    String AudioSavePathInDevice = null;
    MediaRecorder mediaRecorder ;
    Random random ;
    String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    public static final int RequestPermissionCode = 1;
    MediaPlayer mediaPlayer ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_story_record, null, false);
        count = 1;
        mStoryRecord =(ImageView) v.findViewById(R.id.story_record_button);
        mStorySave = (Button) v.findViewById(R.id.story_save_button);
        mStorySave.setClickable(false);
        buttonPlayLastRecordAudio = (Button) v.findViewById(R.id.replay_story);
        random = new Random();

        setClickListeners();

        return v;
    }


    private void setClickListeners(){
        mStoryRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (count){
                    case 1:
                        mStoryRecord.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.story_pause));
                        count++;

                       /*     AudioSavePathInDevice =
                                    Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                                            CreateRandomAudioFileName(5) + "AudioRecording.3gp";

                            MediaRecorderReady();

                            try {
                                mediaRecorder.prepare();
                                mediaRecorder.start();
                            } catch (IllegalStateException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            Toast.makeText(getActivity(), "Recording started",
                                    Toast.LENGTH_LONG).show();*/

                        break;
                    case 2:
                        mStoryRecord.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.story_re_record));
                        mStorySave.setClickable(true);
                        count=3;
                      /* if(mediaPlayer!= null) mediaRecorder.stop();

                        Toast.makeText(getActivity(), "Recording Completed",
                                Toast.LENGTH_LONG).show();*/
                        break;
                    case 3:
                        mStoryRecord.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.story_record));
                        mStorySave.setClickable(false);
                        count = 1;
                }
            }
        });
        buttonPlayLastRecordAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) throws IllegalArgumentException,
                    SecurityException, IllegalStateException {



                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(AudioSavePathInDevice);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer.start();
                Toast.makeText(getActivity(), "Recording Playing",
                        Toast.LENGTH_LONG).show();
            }
        });

        mStorySave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AssessmentActivity)getActivity()).lifeButton.callOnClick();
            }
        });
    }

    public void MediaRecorderReady(){
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
    }

    public String CreateRandomAudioFileName(int string){
        StringBuilder stringBuilder = new StringBuilder( string );
        int i = 0 ;
        while(i < string ) {
            stringBuilder.append(RandomAudioFileName.
                    charAt(random.nextInt(RandomAudioFileName.length())));

            i++ ;
        }
        return stringBuilder.toString();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length> 0) {
                    boolean StoragePermission = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission) {
                        Toast.makeText(getActivity(), "Permission Granted",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(),"Permission Denied",
                                Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getActivity(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }


}
