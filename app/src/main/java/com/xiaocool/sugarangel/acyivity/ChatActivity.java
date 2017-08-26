package com.xiaocool.sugarangel.acyivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mylibrary.Chat.ChatType;
import com.example.mylibrary.KeyBoardHeightTool.KeyBoardAndViewManage;
import com.example.mylibrary.RecordAudioView.QRecordAudioView;
import com.example.mylibrary.RecordAudioView.RecordInterfaceListener;
import com.example.mylibrary.photoListView.PictureDataBean;
import com.example.mylibrary.photoListView.QPhotoListener;
import com.example.mylibrary.photoListView.QPhotoView;
import com.xiaocool.sugarangel.R;
import com.xiaocool.sugarangel.adapter.ChatListAdapter;
import com.xiaocool.sugarangel.bean.ChatDataItemBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.mylibrary.Chat.ChatType.pictureLeft;
import static com.example.mylibrary.Chat.ChatType.pictureRight;
import static com.example.mylibrary.Chat.ChatType.textLeft;
import static com.example.mylibrary.Chat.ChatType.textRight;
import static com.example.mylibrary.Chat.ChatType.voiceLeft;
import static com.example.mylibrary.Chat.ChatType.voiceRight;

public class ChatActivity extends BaseActivity {
    private final static int showingTextCorlor = Color.parseColor("#ff99cc00");
    private final static int noShowingTextCorlor = Color.parseColor("#ff33b5e5");
    private final static int noShowingBkResource = R.drawable.iv_trigger_emotion_keyboard;
    private final static int showingBkResource = R.drawable.iv_green_emotion_keyboard;
    @BindView(R.id.recylerView)
    RecyclerView recyclerView;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.custom_btn)
    ImageView customBtn;
    @BindView(R.id.custom_text_btn)
    TextView customTextBtn;
    @BindView(R.id.common_app_bar)
    RelativeLayout commonAppBar;
    @BindView(R.id.et_face_text_emotion)
    EditText etFaceTextEmotion;
    @BindView(R.id.btn_send_msg)
    Button btnSendMsg;
    @BindView(R.id.emotion)
    TextView emotion;
    @BindView(R.id.voice)
    TextView voice;
    @BindView(R.id.picture)
    TextView picture;
    @BindView(R.id.selectEmotionView)
    TextView selectEmotionView;
    @BindView(R.id.recordVoiceView)
    QRecordAudioView recordVoiceView;
    @BindView(R.id.selectPictureView)
    QPhotoView selectPictureView;
    private ChatListAdapter adapter;
    private Context context;
    private List<ChatDataItemBean> list;
    private KeyBoardAndViewManage manager;

    @Override
    public int getContentViewId() {
        return R.layout.activity_chat;
    }

    private List<KeyBoardAndViewManage.Bean> viewList;
    private String[] a = {"http://nyjob.xiaocool.net/uploads/microblog/1502950842800.png", "http://nyjob.xiaocool.net/uploads/microblog/1503109435882.png", "http://nyjob.xiaocool.net/uploads/microblog/1.png", "http://nyjob.xiaocool.net/uploads/microblog/2.png", "http://nyjob.xiaocool.net/uploads/microblog/3.png", "http://nyjob.xiaocool.net/uploads/microblog/4.png", "http://nyjob.xiaocool.net/uploads/microblog/5.png", "http://nyjob.xiaocool.net/uploads/microblog/6.png", "http://nyjob.xiaocool.net/uploads/microblog/7.png", "http://nyjob.xiaocool.net/uploads/microblog/8.png", "http://nyjob.xiaocool.net/uploads/microblog/9.png", "http://nyjob.xiaocool.net/uploads/microblog/10.png", "http://nyjob.xiaocool.net/uploads/microblog/1.jpg", "http://nyjob.xiaocool.net/uploads/microblog/2.jpg", "http://nyjob.xiaocool.net/uploads/microblog/3.jpg", "http://nyjob.xiaocool.net/uploads/microblog/4.jpg", "http://nyjob.xiaocool.net/uploads/microblog/5.jpg", "http://nyjob.xiaocool.net/uploads/microblog/6.jpg", "http://nyjob.xiaocool.net/uploads/microblog/7.jpg", "http://nyjob.xiaocool.net/uploads/microblog/8.jpg", "http://nyjob.xiaocool.net/uploads/microblog/9.jpg", "http://nyjob.xiaocool.net/uploads/microblog/10.jpg", "http://nyjob.xiaocool.net/uploads/microblog/avatar20170815211643378.png"};
    private String[] b = {"你好?", "吃了吗", "你能收到吗", "你怎么了", "hello", "放假了", "开学了", "作业做完了吗", "你喜欢我吗", "见过我的小熊吗", "你怎么不俗", "你信宗教吗", "高数过来撒", "德玛西亚", "我还有梦i想", "你过来我打你", "你来我走", "这是第几条", "还好啦", "哈哈哈", "做梦", "且", "还有梦想？", "指鹿为马，还抱着梦想呢", "行i行吧", "我还没吃早饭", "你真的行i型号吧", "怎么活该没", "赞啦", "叫爸爸", "哈佛大学也不去", "真是服了你", "够菜了", "呵呵", "如意十大打撒", "啊实打实大了扩大", "伊斯兰", "基督教", "山东纵波", "淄博", "沂源县", "心动刚", "哇狙击", "中国人"};
    private String[] c;
    private String[] d = {"张飞", "赵云", "李四", "诸葛亮", "刘备", "亚瑟", "蓝令我", "天的撒娇啊是的", "我家兴", "赵自强", "和恶化", "你猜", "没有id", "指尖的温柔", "咖啡猫", "青苔你住"};
    private ChatType[] e = {textLeft, textRight, pictureLeft, pictureRight, voiceLeft, voiceRight};

    @Override
    public void initView() {
        context = this;
        list = new ArrayList<>();
        c = new String[120];
        for (int o = 0; o < 120; o++) {
            c[o] = "" + o;
        }
        Random r = new Random();
        for (int o = 0; o < 100; o++) {
             /* String myPhoto, String hisPhoto, String picturePath, String text, String durtation*/
            list.add(new ChatDataItemBean(e[r.nextInt(e.length)], a[1], a[r.nextInt(a.length)], a[r.nextInt(a.length)], b[r.nextInt(b.length)], c[r.nextInt(c.length)], d[r.nextInt(d.length)]));
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter = new ChatListAdapter(list, context, R.layout.chat_item_layout));
        selectPictureView.setQPhotoListener(new QPhotoListener() {
            @Override
            public void onAlbumClicked() {
                Log.i("看QPhotoListener","去相册");
            }

            @Override
            public void OnSendBtnClicked(int picCount, List<PictureDataBean> selectedPicList) {
                Log.i("看QPhotoListener","发送" + picCount +"张图片");
            }

            @Override
            public void OnCropClicked(String picPath) {
                Log.i("看QPhotoListener","裁剪图片地址：" +picPath);
            }
        });
        manager = new KeyBoardAndViewManage(ChatActivity.this, recyclerView, etFaceTextEmotion);
        viewList = new ArrayList<>();
        viewList.add(new KeyBoardAndViewManage.Bean(emotion, selectEmotionView));
        viewList.add(new KeyBoardAndViewManage.Bean(voice, recordVoiceView));
        viewList.add(new KeyBoardAndViewManage.Bean(picture, selectPictureView));
        manager.setViewList(viewList);
        etFaceTextEmotion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!etFaceTextEmotion.getText().toString().equals("")) {
                    btnSendMsg.setEnabled(true);
                } else {
                    btnSendMsg.setEnabled(false);
                }
            }
        });
        recordVoiceView.setRecordInterfaceListener(recordInterfaceListener);
        manager.setOnViewChangeShowStateListener(new KeyBoardAndViewManage.OnViewChangeShowStateListener() {
            @Override
            public void OnViewChangeShowState(int position, boolean isShowing) {
                if (isShowing) {
                    switch (position) {
                        case 0:
                            emotion.setBackgroundResource(showingBkResource);
                            emotion.setTextColor(showingTextCorlor);
                            break;
                        case 1:
                            voice.setBackgroundResource(showingBkResource);
                            voice.setTextColor(showingTextCorlor);
                            break;
                        case 2:
                            picture.setBackgroundResource(showingBkResource);
                            picture.setTextColor(showingTextCorlor);
                            break;
                    }
//                     viewList.get(position).getTouchView().setBackgroundResource(R.drawable.iv_green_emotion_keyboard);
//                    TextView textView = (TextView) viewList.get(position).getTouchView();
//                    textView.setTextColor(Color.parseColor("#ff99cc00"));
                } else {
                    switch (position) {
                        case 0:
                            emotion.setBackgroundResource(noShowingBkResource);
                            emotion.setTextColor(noShowingTextCorlor);
                            break;
                        case 1:
                            voice.setBackgroundResource(noShowingBkResource);
                            voice.setTextColor(noShowingTextCorlor);
                            break;
                        case 2:
                            picture.setBackgroundResource(noShowingBkResource);
                            picture.setTextColor(noShowingTextCorlor);
                            break;
                    }
//                    viewList.get(position).getTouchView().setBackgroundResource(R.drawable.iv_trigger_emotion_keyboard);
//                    TextView textView = (TextView) viewList.get(position).getTouchView();
//                    textView.setTextColor(Color.parseColor("#ff33b5e5"));
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

    }

    @Override
    public void onBackPressed() {
        if (!manager.interceptBackPressed()) {
            super.onBackPressed();
        }
    }

    @OnClick({R.id.back, R.id.btn_send_msg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btn_send_msg:
                break;
        }
    }

    private RecordInterfaceListener recordInterfaceListener = new RecordInterfaceListener() {
        @Override
        public void OnStartRecord(View ClickView) {

        }

        @Override
        public void OnDeleteRecordSeleted() {

        }

        @Override
        public void OnPlayRecordSeleted() {

        }

        @Override
        public void OnRecordFinish(int Duration) {

        }
    };
}
