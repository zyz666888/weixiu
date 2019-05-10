package com.idisfkj.hightcopywx.find.customervisit;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.idisfkj.hightcopywx.R;
import com.idisfkj.hightcopywx.find.signin.CalendarUtil;
import com.idisfkj.hightcopywx.find.signin.LocationAdapter;
import com.idisfkj.hightcopywx.find.uploadpictureutil.Constants;
import com.idisfkj.hightcopywx.find.uploadpictureutil.GridViewAdapter;
import com.idisfkj.hightcopywx.find.uploadpictureutil.PictureSelectorConfig;
import com.idisfkj.hightcopywx.find.uploadpictureutil.PlusImageActivity;
import com.idisfkj.hightcopywx.uploadpictures.UploadHelper;
import com.idisfkj.hightcopywx.util.OKHttpUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.idisfkj.hightcopywx.util.MyProperUtil.getProperties;

public class AddCustomerVisitActivity extends Activity implements View.OnClickListener {
    MapView customervisit_bmapView = null;
    public LocationClient mLocationClient = null;
    private BaiduMap mBaiduMap;
    private LocationClientOption mOption;
    boolean isFirstLoc = true;// 是否首次定位
    private ListView customervisit_listview;
    private TextView customervisitLocation;
    private TextView customervisitDate;
    private TextView customervisitTime;
    private EditText customervisitDesc;
    private EditText visitCustomerName;
    private TextView customervisitLocationUpdate;
    private RelativeLayout rl_top;
    private RelativeLayout rl2;
    private LocationAdapter mAdapper;
    private RelativeLayout customervisit_rl_update;
    private String cur_location;
    boolean isUpdate = false;
    private ImageView return_img;
    private static final int BAIDU_READ_PHONE_STATE =100;
    private ArrayList<String> list;

    //保存上传图片的数据源
    private GridView mGridView_customervisit;
    private ArrayList<String> mPicList_customervisit = new ArrayList<>(); //上传的图片凭证的数据源
    private GridViewAdapter mGridViewAdapter_customervisit;
    private Button cancel_customervisit;
    private Button submit_customervisit;
    private Button add_customervisit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.add_customervisit);
        //图片初始化
        init();
        customervisit_bmapView = (MapView) findViewById(R.id.customervisit_bmapView);
        customervisit_listview = (ListView) findViewById(R.id.customervisit_listview);
        customervisit_rl_update = (RelativeLayout) findViewById(R.id.customervisit_rl_update);
        customervisitLocation = (TextView) findViewById(R.id.customervisit_location);
        customervisitDate = (TextView) findViewById(R.id.customervisit_date);
        customervisitTime = (TextView) findViewById(R.id.customervisit_time);
        customervisitDesc =(EditText) findViewById(R.id.customervisit_desc);
        visitCustomerName =(EditText) findViewById(R.id.visitcustomer_name);
        add_customervisit=(Button) findViewById(R.id.add_customervisit);
        add_customervisit.setVisibility(View.INVISIBLE);
        rl_top=(RelativeLayout) findViewById(R.id.rl_top);
        rl2=(RelativeLayout) findViewById(R.id.rl_2);
        customervisitLocationUpdate = (TextView) findViewById(R.id.customervisit_location_update);
        customervisitLocationUpdate.setOnClickListener(this);
        return_img = (ImageView) findViewById(R.id.return_customervisit);
        return_img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //判断是否为android6.0系统版本，如果是，需要动态添加权限
        if (Build.VERSION.SDK_INT>=23){
            showContacts();
        }else{
            initMap();//init为定位方法
        }

        //确认按钮初始化
        submit_customervisit = (Button) findViewById(R.id.confirm_customervisit);
        //取消按钮初始化
        cancel_customervisit = (Button) findViewById(R.id.cancel_customervisit);
        //取消按钮按下
        cancel_customervisit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCustomerVisitActivity.this, CustomerVisitListActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //确认按钮按下
        submit_customervisit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if(customervisitDesc.getText().toString().isEmpty()){
                    Toast.makeText(AddCustomerVisitActivity.this,"描述不能为空！", Toast.LENGTH_SHORT).show();
                    return ;
                }
                if(visitCustomerName.getText().toString().isEmpty()){
                    Toast.makeText(AddCustomerVisitActivity.this,"拜访客户名称不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(customervisitLocation.getText().toString().isEmpty()){
                    Toast.makeText(AddCustomerVisitActivity.this,"地点不能为空！", Toast.LENGTH_SHORT).show();
                    return ;
                }

                BaseVisitCustomerInfo bs = new BaseVisitCustomerInfo();
                //拜访客户描述
                bs.setRemarks(customervisitDesc.getText().toString());
                //拜访客户时间
                bs.setVisittime(customervisitDate.getText().toString()+" "+customervisitTime.getText().toString());
                //拜访客户地点
                bs.setAddress(customervisitLocation.getText().toString());
                //拜访客户名称
                bs.setCustomer(visitCustomerName.getText().toString());

                //上传图片
                //uploadPics();
                OKHttpUtil http = new OKHttpUtil(AddCustomerVisitActivity.this);
                http.insertVisitCustomerInfo(bs, getProperties(getApplicationContext()).getProperty("CUSTOMER_VISIT_SUBMIT"));
                Intent intent = new Intent(AddCustomerVisitActivity.this, CustomerVisitListActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    private void initMap() {
        mBaiduMap = customervisit_bmapView.getMap();
        infoUplate(isUpdate);

        mBaiduMap.setMyLocationEnabled(true);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(mBDLocationListener);
        mLocationClient.start();
        mLocationClient.setLocOption(getDefaultLocationClientOption());

        customervisit_bmapView.refreshDrawableState();
    }

    public void showContacts(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(),"没有权限,请手动开启定位权限",Toast.LENGTH_SHORT).show();
            // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
            ActivityCompat.requestPermissions(AddCustomerVisitActivity.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE}, BAIDU_READ_PHONE_STATE);
        }else{
            initMap();
        }
    }

    //Android6.0申请权限的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case BAIDU_READ_PHONE_STATE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                    initMap();
                } else {
                    // 没有获取到权限，做特殊处理
                    Toast.makeText(getApplicationContext(), "获取位置权限失败，请手动开启", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }


    public LocationClientOption getDefaultLocationClientOption() {

        mOption = new LocationClientOption();
        mOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        mOption.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        //mOption.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        mOption.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        mOption.setIsNeedLocationDescribe(true);//可选，设置是否需要地址描述
        mOption.setNeedDeviceDirect(false);//可选，设置是否需要设备方向结果
        mOption.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        mOption.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        mOption.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        mOption.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        mOption.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集

        return mOption;
    }

    private String cur_time;
    private BDLocationListener mBDLocationListener = new BDLocationListener() {



        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null || customervisit_bmapView == null)
                return;
            MyLocationData locData = new MyLocationData.Builder().
                    accuracy(location.getRadius()).direction(100).latitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());

                //地图标注
                BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.mine_locate);
                OverlayOptions options = new MarkerOptions().position(ll).icon(bitmapDescriptor);
                mBaiduMap.addOverlay(options);
                  /*
                   * 标绘圆
                   * */
                CircleOptions circleOptions = new CircleOptions();
                circleOptions.center(ll);//设置圆心坐标
                //circleOptions.fillColor(0Xaafaa355);//圆的填充颜色
                circleOptions.fillColor(R.color.color_blue);//圆的填充颜色
                circleOptions.radius(40);//设置半径
                circleOptions.stroke(new Stroke(2, 0xAA00FF00));//设置边框
                mBaiduMap.addOverlay(circleOptions);

                MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(ll, 18);   //设置地图中心点以及缩放级别
                //MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(u);
            }


            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                sb.append(location.getTime());
                sb.append("\nerror code : ");
                sb.append(location.getLocType());
                sb.append("\nlatitude : ");
                sb.append(location.getLatitude());
                sb.append("\nlontitude : ");
                sb.append(location.getLongitude());
                sb.append("\nradius : ");
                sb.append(location.getRadius());
                sb.append("\nCountryCode : ");
                sb.append(location.getCountryCode());
                sb.append("\nCountry : ");
                sb.append(location.getCountry());
                sb.append("\ncitycode : ");
                sb.append(location.getCityCode());
                sb.append("\ncity : ");
                sb.append(location.getCity());
                sb.append("\nDistrict : ");
                sb.append(location.getDistrict());
                sb.append("\nStreet : ");
                sb.append(location.getStreet());
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\nDescribe: ");
                sb.append(location.getLocationDescribe());
                sb.append("\nDirection(not all devices have value): ");
                sb.append(location.getDirection());
                sb.append("\nPoi: ");


                customervisitDate.setText(CalendarUtil.getCurrentDate());
                cur_time = CalendarUtil.getTime(new Date());
                customervisitTime.setText(cur_time);
                list = new ArrayList();
                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                    list.clear();
                    for (int i = 0; i < location.getPoiList().size(); i++) {
                        Poi poi = (Poi) location.getPoiList().get(i);
                        sb.append(poi.getName() + ";");
                        list.add(poi.getName());
                        if (0 == i) {   //当前位置
                            cur_location = poi.getName();
                            customervisitLocation.setText(cur_location);
                        }
                    }
                }

                mAdapper = new LocationAdapter(AddCustomerVisitActivity.this, list);
                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());// 单位：km/h
                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());
                    sb.append("\nheight : ");
                    sb.append(location.getAltitude());// 单位：米
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    // 运营商信息
                    sb.append("\noperationers : ");
                    sb.append(location.getOperators());
                    sb.append("\ndescribe : ");
                    sb.append("网络定位成功");
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ");
                    sb.append("离线定位成功，离线定位结果也是有效的");
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
                inflateMsg(sb.toString());
            }
        }

    };

    private void inflateMsg(String s) {
        Log.e("msg", s);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        customervisit_bmapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        customervisit_bmapView.onPause();
    }

    @Override
    protected void onDestroy() {
        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        customervisit_bmapView.onDestroy();
        customervisit_bmapView = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.customervisit_bmapView:
            case R.id.customervisit_location_update:
                //地点微调
                isUpdate = true;
                infoUplate(isUpdate);
                break;
        }
    }

    private void infoUplate(boolean flag) {
        UiSettings settings = mBaiduMap.getUiSettings();
        if (flag) {//微调页面
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 900);
            customervisit_bmapView.setLayoutParams(param);

            settings.setAllGesturesEnabled(true);   //开放一切手势功能
            customervisit_bmapView.showScaleControl(true);//隐藏地图上的比例尺
            customervisit_bmapView.showZoomControls(true);//显示地图上的缩放控件
            customervisit_bmapView.setVisibility(View.VISIBLE);
            customervisitLocation.setVisibility(View.GONE);
            customervisitLocationUpdate.setVisibility(View.GONE);
            customervisit_listview.setVisibility(View.VISIBLE);
            customervisit_listview.setAdapter(mAdapper);
            rl_top.setVisibility(View.GONE);
            rl2.setVisibility(View.GONE);
            customervisit_listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    customervisitLocation.setText(list.get(position));
                    customervisitLocation.setVisibility(View.VISIBLE);
                    customervisitLocationUpdate.setVisibility(View.VISIBLE);
                    customervisit_listview.setVisibility(View.GONE);
                    customervisit_bmapView.setVisibility(View.GONE);
                    rl_top.setVisibility(View.VISIBLE);
                    rl2.setVisibility(View.VISIBLE);
                }
            });
        } else {//默认页面
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 500);
            customervisit_bmapView.setLayoutParams(param);

            settings.setZoomGesturesEnabled(false);  //禁用手势缩放功能
            settings.setScrollGesturesEnabled(false);
            customervisit_bmapView.showScaleControl(false);//显示地图上的比例尺
            customervisit_bmapView.showZoomControls(false);//隐藏地图上的缩放控件
            customervisit_bmapView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(SignInMainActivity.this, "shit", Toast.LENGTH_SHORT).show();
                }
            });

            customervisit_bmapView.setVisibility(View.GONE);
            customervisit_listview.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (isUpdate) {
                isUpdate = false;
                infoUplate(isUpdate);
                return false;
            }
            finish();
//            if ((System.currentTimeMillis() - exitTime) > 2000) {
//                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
//                exitTime = System.currentTimeMillis();
//            } else {
//                finish();
//                System.exit(0);
//            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //图片选择器的回显
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    //图片选择结果的回调
                    refreshAdapter(PictureSelector.obtainMultipleResult(data));
                    //例如 LocalMedia里面返回了三种path
                    //1、media.getPath() 为原图的path
                    //2、media.getCutPath() 为裁剪后的path 需要判断media.isCut() 是否为true
                    //3、media.getCompressPath() 为压缩后的path 徐判断media.isCompressed() 是否weitrue
                    //如果裁剪并压缩了 则以取到的压缩路径为准 因为是先裁剪后压缩的
                    break;

                default:
                    break;
            }
        }
        if (requestCode == Constants.REQUEST_CODE_MAIN && resultCode == Constants.RESULT_CODE_VIEW_IMG) {
            //查看了大图界面删除了图片
            ArrayList<String> toDeletePicList = data.getStringArrayListExtra(Constants.IMG_LIST);
            mPicList_customervisit.clear();
            mPicList_customervisit.addAll(toDeletePicList);
            mGridViewAdapter_customervisit.notifyDataSetChanged();
        }
    }

    /**
     * 处理选择的照片的地址
     *
     * @param picList
     */
    private void refreshAdapter(List<LocalMedia> picList) {
        for (LocalMedia localMedia : picList) {
            //被压缩后的图片路径
            File file = new File(localMedia.getCompressPath().toString());
            String name = file.getName();
            long length = file.length();
            Log.e("PATH", name + " 所占的内存是： " + length / 1024 + "KB");
            if (localMedia.isCompressed()) {
                String compressPath = localMedia.getCompressPath();
                mPicList_customervisit.add(compressPath);
                mGridViewAdapter_customervisit.notifyDataSetChanged();
            }
        }
    }
    //图片初始化
    private void init() {
        mGridView_customervisit = (GridView) findViewById(R.id.gridView_customervisit);
        initGridView();
    }
    /**
     * 初始化GridView   图片添加
     */
    private void initGridView() {
        mGridViewAdapter_customervisit = new GridViewAdapter(AddCustomerVisitActivity.this, mPicList_customervisit);
        mGridView_customervisit.setAdapter(mGridViewAdapter_customervisit);
        //设置GridView的条目的点击事件
        mGridView_customervisit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == parent.getChildCount() - 1) {
                    //如果添加按钮是最后一张 并且添加图片的数量不超过9张
                    if (mPicList_customervisit.size() == Constants.MAX_SELECT_PIV_NUM) {
                        //最多添加9张照片
                        viewPluImg(position);
                    } else {
                        //添加照片的凭证
                        selectPic(Constants.MAX_SELECT_PIV_NUM - mPicList_customervisit.size());
                    }
                } else {
                    viewPluImg(position);
                }
            }
        });
    }
    /**
     * 添加照片
     *
     * @param num
     */
    private void selectPic(int num) {
        PictureSelectorConfig.initMultiConfig(AddCustomerVisitActivity.this, num);
    }

    /**
     * 查看大图
     *
     * @param position
     */
    private void viewPluImg(int position) {
        Intent intent = new Intent(AddCustomerVisitActivity.this, PlusImageActivity.class);
        intent.putStringArrayListExtra(Constants.IMG_LIST, mPicList_customervisit);
        intent.putExtra(Constants.POSITION, position);
        startActivityForResult(intent, Constants.REQUEST_CODE_MAIN);
    }

    //图片上传
    public void uploadPics() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                Map<String, Drawable> files = new HashMap<>();
                for (int i = 0; i < mPicList_customervisit.size(); i++) {
                    files.put(i + "", Drawable.createFromPath(mPicList_customervisit.get(i)));
                }
                UploadHelper helper = new UploadHelper(AddCustomerVisitActivity.this);
                //helper.post(CustomerVisitActivity.this, getProperties(getApplicationContext()).getProperty("uploadpicURL"), files, "4", "");
            }
        }).start();
    }
}

