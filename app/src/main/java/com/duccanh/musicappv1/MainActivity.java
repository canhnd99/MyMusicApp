package com.duccanh.musicappv1;

import androidx.appcompat.app.AppCompatActivity;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int current_pos = 0;
    boolean mark_loop = false;
    boolean mark_random = false;

    ImageButton btnPlay, btnNext, btnPrevious, btnRandom, btnLoop, btnBack;
    TextView txtTimeStart, txtTimeEnd, txtSingerName, txtSongName;
    SeekBar songBar;
    ArrayList<Song> listSongs;
    MediaPlayer mediaPlayer;
    ObjectAnimator animator;
    ImageView imgNHA, imgSoobin, imgAnhQuan, imgGheQua, imgAndre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        matchComponents();
        addSongs();
        initSong(current_pos);
        getAnimation(current_pos);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                Intent intent = new Intent(MainActivity.this, ListSongActivity.class);
                startActivity(intent);
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.play);
                    animator.pause();
                }else {
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.pause);
                    getAnimation(current_pos);
                    animator.resume();
                }
                setTimeTotal();
                updateTime();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current_pos++;
                if(current_pos > listSongs.size()-1){
                    current_pos = 0;
                }
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    animator.pause();
                }
                initSong(current_pos);
                btnPlay.setImageResource(R.drawable.pause);
                mediaPlayer.start();
                getAnimation(current_pos);
                animator.resume();
                setTimeTotal();
                updateTime();
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current_pos--;
                if(current_pos < 0){
                    current_pos = 0;
                }
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    animator.pause();
                }
                initSong(current_pos);
                btnPlay.setImageResource(R.drawable.pause);
                mediaPlayer.start();
                getAnimation(current_pos);
                animator.resume();
                setTimeTotal();
                updateTime();
            }
        });

        songBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) { }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(songBar.getProgress());
            }
        });

        /*
        * Set SỰ KIỆN LẶP cho bài hát.
        * Ý tưởng: dùng một biến boolean là "mark_loop" để đánh dấu.
        * --> Khởi tạo mark_loop = false.
        * --> Kiểm tra:
        *       +) Nếu mark_loop == false --> set mark_loop == true, set lại image đồng thời
        *          tắt chế độ random bằng cách set mark_random == false nếu nó đang true hoặc
        *          để yên nếu ngược lại.
        *       +) Nếu mark_loop == true --> làm ngược lại.
        * */
        btnLoop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mark_loop == false){
                    mark_loop = true;
                    btnLoop.setImageResource(R.drawable.loop_color);
                    if(mark_random == true){
                        mark_random = false;
                        btnRandom.setImageResource(R.drawable.random);
                    }
                } else {
                    mark_loop = false;
                    btnLoop.setImageResource(R.drawable.loop);
                }
            }
        });

        /*
        * Set SỰ KIỆN RANDOM BÀI HÁT.
        * Ý tưởng: dùng biến boolean mark_random để đánh dấu.
        * --> Khởi tạo mark_random == false.
        * --> Nếu mark_random == false --> chuyển sang trạng thái random bằng cách cho mark_random == true.
        *     đồng thời set lại image và tắt chế độ lặp nếu mở.
        * --> Nếu mark_random == false --> thoát chế độ random.
        * */
        btnRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mark_random == false){
                    mark_random = true;
                    btnRandom.setImageResource(R.drawable.rand_color);
                    mark_loop = false;
                    btnLoop.setImageResource(R.drawable.loop);
                }else {
                    mark_random = false;
                    btnRandom.setImageResource(R.drawable.random);
                }
            }
        });
    }

    public void setInVisibility (ImageView img1, ImageView img2, ImageView img3, ImageView img4){
        img1.setVisibility(View.INVISIBLE);
        img2.setVisibility(View.INVISIBLE);
        img3.setVisibility(View.INVISIBLE);
        img4.setVisibility(View.INVISIBLE);
    }

    public void getAnimation(int p) {
        String singer = listSongs.get(p).getSinger();
        if(singer.equals("N.H.A")){
            animator = ObjectAnimator.ofFloat(imgNHA, "rotation", 0, 360);
            imgNHA.setVisibility(View.VISIBLE);
            setInVisibility(imgAndre, imgAnhQuan, imgGheQua, imgSoobin);
        }else if (singer.equals("Anh Quân")){
            animator = ObjectAnimator.ofFloat(imgAnhQuan, "rotation", 0, 360);
            imgAnhQuan.setVisibility(View.VISIBLE);
            setInVisibility(imgNHA, imgAndre, imgSoobin, imgGheQua);
        }else if (singer.equals("TayNguyen Music")){
            animator = ObjectAnimator.ofFloat(imgGheQua, "rotation", 0, 360);
            imgGheQua.setVisibility(View.VISIBLE);
            setInVisibility(imgNHA, imgAnhQuan, imgAndre, imgSoobin);
        }else if (singer.equals("Soobin Hoàng Sơn")){
            animator = ObjectAnimator.ofFloat(imgSoobin, "rotation", 0, 360);
            imgSoobin.setVisibility(View.VISIBLE);
            setInVisibility(imgNHA, imgAndre, imgAnhQuan, imgGheQua);
        }else if (singer.equals("ENDREA")){
            animator = ObjectAnimator.ofFloat(imgAndre, "rotation", 0, 360);
            imgAndre.setVisibility(View.VISIBLE);
            setInVisibility(imgNHA, imgAnhQuan, imgSoobin, imgGheQua);
        }
        animator.setDuration(10000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.setRepeatMode(ObjectAnimator.RESTART);
        animator.start();
        animator.pause();
    }

    public void updateTime() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat formatTime = new SimpleDateFormat("mm:ss");

                txtTimeStart.setText(formatTime.format(mediaPlayer.getCurrentPosition()));

                songBar.setProgress(mediaPlayer.getCurrentPosition());

                /* Xử lý các trường hợp khi kết thúc bài hát */
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        /*
                         * Nếu đang ở chế độ lặp lại thì gán current_pos += 0 (giữ nguyên current_pos).
                         * Nếu ko thì tự động chuyển bài bằng cách cho current_pos += 1;
                         * */
                        if (mark_loop == true){
                            current_pos += 0;
                        } else {
                            current_pos += 1;
                            if(current_pos > listSongs.size()-1){
                                current_pos = 0;
                            }
                        }
                        /*
                         * Nếu đang ở chế độ random thì random một bài trong list.
                         * Set bài đó làm bài hát hiện tại.
                         * */
                        if (mark_random == true){
                            int pos = new Random().nextInt(14);
                            current_pos = pos;
                        }

                        animator.pause();
                        initSong(current_pos);
                        btnPlay.setImageResource(R.drawable.pause);
                        mediaPlayer.start();
                        getAnimation(current_pos);
                        updateTime();
                        animator.resume();
                    }
                });
                handler.postDelayed(this, 500);
            }
        }, 100);
    }

    public void setTimeTotal() {
        SimpleDateFormat formatTime = new SimpleDateFormat("mm:ss");
        txtTimeEnd.setText(formatTime.format(mediaPlayer.getDuration()));
        songBar.setMax(mediaPlayer.getDuration());
    }

    public void initSong(int p){
        mediaPlayer = MediaPlayer.create(this, listSongs.get(p).getFile());
        txtSongName.setText(listSongs.get(p).getName());
        txtSingerName.setText(listSongs.get(p).getSinger());
        setTimeTotal();
    }

    public void addSongs(){
        listSongs = new ArrayList<>();
        listSongs.add(new Song("Cho Lần Đi Xa", "N.H.A", R.raw.cho_lan_di_xa));
        listSongs.add(new Song("Chẳng Có Gì Đẹp Đẽ Trên Đời", "N.H.A", R.raw.chang_gi_dep_de_tren_doi));
        listSongs.add(new Song("Đêm nay mưa lại rơi", "N.H.A", R.raw.dem_nay_mua_lai_roi));
        listSongs.add(new Song("Mắt Biếc", "N.H.A", R.raw.mat_biec));
        listSongs.add(new Song("Còn Gì Đau Hơn Chữ Đã Từng", "Anh Quân", R.raw.con_gi_dau_hon_chu_da_tung));
        listSongs.add(new Song("Ghé Qua","TayNguyen Music", R.raw.ghe_qua));
        listSongs.add(new Song("Giá Như Phút Chốc Anh Nắm Lấy Em","N.H.A", R.raw.gia_nhu_phut_choc_anh_nam_lay_em));
        listSongs.add(new Song("Luyến","N.H.A", R.raw.luyen));
        listSongs.add(new Song("Một Đoạn Bỏ Qua","N.H.A", R.raw.mot_doan_bo_qua));
        listSongs.add(new Song("Nếu Ngày Ấy","Soobin Hoàng Sơn", R.raw.neu_ngay_ay));
        listSongs.add(new Song("Người Từng Thương","N.H.A", R.raw.nguoi_tung_thuong));
        listSongs.add(new Song("Những Ngày Đã Qua","N.H.A", R.raw.nhung_ngay_da_qua));
        listSongs.add(new Song("Nỗi Đau Chậm Lại","N.H.A", R.raw.noi_dau_cham_lai));
        listSongs.add(new Song("Tệ Thật Anh Lại Nhớ Em Rồi","ENDREA", R.raw.te_that_anh_lai_nho_em));
    }

    public void matchComponents(){
        btnPlay = findViewById(R.id.play);
        btnNext = findViewById(R.id.next);
        btnPrevious = findViewById(R.id.previous);
        btnRandom = findViewById(R.id.random);
        btnLoop = findViewById(R.id.loop);
        btnBack = findViewById(R.id.btnBack);

        songBar = findViewById(R.id.seekBar);
        txtTimeStart = findViewById(R.id.timeStart);
        txtTimeEnd = findViewById(R.id.timeEnd);
        txtSingerName = findViewById(R.id.singerName);
        txtSongName = findViewById(R.id.songName);

        imgNHA = findViewById(R.id.imgNha);
        imgSoobin = findViewById(R.id.imgSooBin);
        imgAndre = findViewById(R.id.imgAndre);
        imgGheQua = findViewById(R.id.imgGheQua);
        imgAnhQuan = findViewById(R.id.imgAnhQuan);
    }
}
