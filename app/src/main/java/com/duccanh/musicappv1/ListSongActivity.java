package com.duccanh.musicappv1;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class ListSongActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    ArrayList<Song> listSongs = new ArrayList<>();
    ListView listView;
    ImageButton btnPlay, btnPrevious, btnNext, btnRandom, btnLoop;

    int pos = 0;

    String[] songs = {
            "Cho lần đi xa",
            "Chẳng có gì đẹp đẽ trên đời mãi",
            "Đêm nay lại mưa rồi em",
            "Mắt biếc",
            "Còn Gì Đau Hơn Chữ Đã Từng",
            "Ghé Qua",
            "Giá Như Phút Chốc Anh Nắm Lấy Em",
            "Luyến",
            "Một Đoạn Bỏ Qua",
            "Nếu Ngày Ấy",
            "Người Từng Thương",
            "Những Ngày Đã Qua",
            "Nỗi Đau Chậm Lại",
            "Tệ Thật Anh Lại Nhớ Em Rồi"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_song);

        matchComponents();
        addSongs();
        initSong(pos);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, songs);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListSongActivity.this, songs[position], Toast.LENGTH_SHORT).show();
                pos = position;
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                initSong(pos);
                mediaPlayer.start();
            }
        });
    }

    public void initSong(int p){
        mediaPlayer = MediaPlayer.create(this, listSongs.get(p).getFile());
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

    private void matchComponents(){
        listView = findViewById(R.id.listSongs);

        btnPlay = findViewById(R.id.play);
        btnNext = findViewById(R.id.next);
        btnPrevious = findViewById(R.id.previous);
        btnLoop = findViewById(R.id.loop);
        btnRandom = findViewById(R.id.random);
    }

}
