package com.idisfkj.hightcopywx.find.friendscircle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.find.uploadpictureutil.Constants;
import com.idisfkj.hightcopywx.find.uploadpictureutil.PlusImageActivity;

import java.util.ArrayList;
import java.util.List;

public class ListViewExampleActivity extends Activity {

    private ListView mListView;
    //private NineGridTestAdapter mAdapter;
    private NineGridTestFriendCircleAdapter mAdapter;
    private List<FriendsCircleBean> fList=new ArrayList<>();
    private String[] mUrls = new String[]{"http://d.hiphotos.baidu.com/image/h%3D200/sign=201258cbcd80653864eaa313a7dca115/ca1349540923dd54e54f7aedd609b3de9c824873.jpg",
            "http://img3.fengniao.com/forum/attachpics/537/165/21472986.jpg",
            "http://d.hiphotos.baidu.com/image/h%3D200/sign=ea218b2c5566d01661199928a729d498/a08b87d6277f9e2fd4f215e91830e924b999f308.jpg",
            "http://img4.imgtn.bdimg.com/it/u=3445377427,2645691367&fm=21&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=2644422079,4250545639&fm=21&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=1444023808,3753293381&fm=21&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=882039601,2636712663&fm=21&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=4119861953,350096499&fm=21&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=2437456944,1135705439&fm=21&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=3251359643,4211266111&fm=21&gp=0.jpg",
            "http://img4.duitang.com/uploads/item/201506/11/20150611000809_yFe5Z.jpeg",
            "http://img5.imgtn.bdimg.com/it/u=1717647885,4193212272&fm=21&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=2024625579,507531332&fm=21&gp=0.jpg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_example);
        initData();
        initView();
    }
    private void initView() {
        mListView = (ListView) findViewById(R.id.lv_bbs);
        mAdapter = new NineGridTestFriendCircleAdapter(this);
        mAdapter.setList(fList);
        mListView.setAdapter(mAdapter);
//        //设置GridView的条目的点击事件
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                viewPluImg(position);
//            }
//        });
    }

    private void initData() {
        initListData();
    }
    private void initListData() {
        FriendsCircleBean f1=new FriendsCircleBean();
        f1.setDesc("我是图一");
        f1.setImg(String.valueOf(R.drawable.bad));
        f1.setName("老大");
        f1.setTime("2011-01-11 01:01:11");
        NineGridTestModel model1 = new NineGridTestModel();
        for (int i = 0; i < 9; i++) {
            model1.urlList.add(mUrls[i]);
        }
        model1.isShowAll = true;
        f1.setUrllist(model1);
        fList.add(f1);

        FriendsCircleBean f2=new FriendsCircleBean();
        f2.setDesc("我是图二");
        f2.setImg(String.valueOf(R.drawable.baifang));
        f2.setName("老二");
        f2.setTime("2012-02-12 02:02:12");
        NineGridTestModel model2 = new NineGridTestModel();
        for (int i = 0; i < 4; i++) {
            model2.urlList.add(mUrls[i]);
        }
        model2.isShowAll = true;
        f2.setUrllist(model2);
        fList.add(f2);

        FriendsCircleBean f3=new FriendsCircleBean();
        f3.setDesc("我是图三");
        f3.setImg(String.valueOf(R.drawable.find));
        f3.setName("老三");
        f3.setTime("2013-03-13 03:03:13");
        NineGridTestModel model3 = new NineGridTestModel();
        for (int i = 0; i < 4; i++) {
            model3.urlList.add(mUrls[i]);
        }
        model3.isShowAll = true;
        f3.setUrllist(model3);
        fList.add(f3);

        FriendsCircleBean f4=new FriendsCircleBean();
        f4.setDesc("我是图四");
        f4.setImg(String.valueOf(R.drawable.install));
        f4.setName("老四");
        f4.setTime("2014-04-14 04:04:14");
        NineGridTestModel model4 = new NineGridTestModel();
        for (int i = 0; i < 6; i++) {
            model4.urlList.add(mUrls[i]);
        }
        model4.isShowAll = true;
        f4.setUrllist(model4);
        fList.add(f4);

    }

    /**
     * 查看大图
     *
     * @param position
     */
    private void viewPluImg(int position) {
        Intent intent = new Intent(ListViewExampleActivity.this, PlusImageActivity.class);
        intent.putStringArrayListExtra(Constants.IMG_LIST, (ArrayList<String>) fList.get(position).getUrllist().urlList);
        intent.putExtra(Constants.POSITION, position);
        startActivityForResult(intent, Constants.REQUEST_CODE_MAIN);
        finish();
    }
}
