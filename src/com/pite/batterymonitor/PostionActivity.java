package com.pite.batterymonitor;

import com.ab.activity.AbActivity;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.maps2d.overlay.DrivingRouteOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.dynamic.RouteSearchWrapper;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.RouteSearch.DriveRouteQuery;
import com.amap.api.services.route.RouteSearch.OnRouteSearchListener;
import com.amap.api.services.route.WalkRouteResult;
import com.umeng.message.PushAgent;

import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 2D地图显示 ,RouteSearch.OnRouteSearchListener
 */
public class PostionActivity extends AbActivity implements LocationSource, OnRouteSearchListener {
	private View noView = null;
	private MapView mapView = null; // 自定义地图View
	private AMap aMap;
	private OnLocationChangedListener mListener;
	private LatLonPoint startPoint;
	private LatLonPoint endPoint;

	private AMapLocationClient mlocationClient = null;
	private AMapLocationClientOption mLocationOption;


	// 路线规划
	private RouteSearch routeSearch;
	private RouteSearch.FromAndTo fromAndTo;// 起始点和终点的经纬度
	// 地图的经纬度
	private String longitude = null;
	private String latitude = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(mInflater.inflate(R.layout.activity_postion, null));
		mapView = (MapView) this.findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		noView = LayoutInflater.from(this).inflate(R.layout.no_data, null);
		PushAgent.getInstance(PostionActivity.this).onAppStart();
		this.setTitleText(R.string.Battery_postion);
		this.setLogo(R.drawable.button_selector_back);
		this.setTitleLayoutBackground(R.drawable.top_bg);
		this.setTitleTextMargin(10, 0, 0, 0);
		this.setLogoLine(R.drawable.line);
		View view = mInflater.inflate(R.layout.home_btn, null);// 标题栏右边
		this.addRightView(view);
		Button btnHome = (Button) this.findViewById(R.id.homeBtn);
		// 取值
		// 经度
		longitude = getIntent().getStringExtra("longitude");
		// 纬度
		latitude = getIntent().getStringExtra("latitude");
		endPoint = new LatLonPoint(Double.valueOf(latitude), Double.valueOf(longitude));
		//endPoint = new LatLonPoint(22.490282,113.900734);
		init();
		btnHome.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(PostionActivity.this, MenuActivity.class));
				PostionActivity.this.finish();
			}
		});
	}

	/**
	 * 初始化AMap对象
	 */
	private void init() {
		if (aMap == null) {
			aMap = mapView.getMap();
		}
		MyLocationStyle myLocationStyle = new MyLocationStyle();
		myLocationStyle.strokeColor(Color.BLUE);// 设置圆形的边框颜色
		myLocationStyle.radiusFillColor(Color.argb(50,0,0,180));// 设置圆形的填充颜色argb(100, 0, 0, 0)
		myLocationStyle.strokeWidth(0.5f);// 设置圆形的边框粗细
		aMap.setMyLocationStyle(myLocationStyle);
		aMap.setLocationSource(this);// 设置定位监听
		aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
		aMap.getUiSettings().setScaleControlsEnabled(true); //设置比例尺功能是否可用
		//aMap.getUiSettings().setCompassEnabled(true);// 设置指南针
		aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
		//moveToPostion(mapView, new LatLng(Double.valueOf(latitude), Double.valueOf(longitude)));
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
		deactivate();

	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onDestroy() {
		Log.e("tag", "onDestroy");
		mapView.onDestroy();
		super.onDestroy();
	}

	/**
	 * 移动到指定位置
	 */
	public void moveToPostion(View v, LatLng postion) {
		aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(postion, 10));
		aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f).position(postion).draggable(true)
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.detail)));
		aMap.invalidate();// 刷新地图
	}

	// 定位成功后回调函数
	AMapLocationListener amaplocationlistener = new AMapLocationListener() {
			@Override
			public void onLocationChanged(AMapLocation amapLocation) {
				if (mListener != null && amapLocation != null) {
					if (amapLocation != null && amapLocation.getErrorCode() == 0) {
						aMap.moveCamera(CameraUpdateFactory.zoomTo(14));
						// 定位成功回调信息，设置相关消息
						amapLocation.getLocationType();// 获取当前定位结果来源，如网络定位结果，详见定位类型表
						amapLocation.getLatitude();// 获取纬度
						amapLocation.getLongitude();// 获取经度
						aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude())));
						startPoint = new LatLonPoint(amapLocation.getLatitude(), amapLocation.getLongitude());
						mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
						fromAndTo = new RouteSearch.FromAndTo(startPoint, endPoint);// 实例化FromAndTo
						Log.e("tag", amapLocation.getLatitude() +"*********"+amapLocation.getLongitude());
						RouteDesign();
						mlocationClient.unRegisterLocationListener(amaplocationlistener);
					} else {
						Log.e("AmapErr", "Location ERR:" + amapLocation.getErrorCode());
					}
			   }
		 }
	};
	@Override
	public void activate(OnLocationChangedListener listener) {
		mListener = listener;
		if (mlocationClient == null) {
			mlocationClient = new AMapLocationClient(getApplicationContext());
			mLocationOption = new AMapLocationClientOption();
			// 设置定位监听 //设置定位回调监听
			mlocationClient.setLocationListener(amaplocationlistener);
			// 设置为高精度定位模式
			mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
			 mLocationOption.setOnceLocation(false);
			 mLocationOption.setMockEnable(false);
			// 设置定位参数
			mlocationClient.setLocationOption(mLocationOption);
			// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
			// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
			// 在定位结束后，在合适的生命周期调用onDestroy()方法
			// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
			Log.e("tag", "-------");
			mlocationClient.startLocation();// 开启定位
		}
	}
	//行车路线设计
	private void RouteDesign() {
		routeSearch = new RouteSearch(this);
		routeSearch.setRouteSearchListener(this);
		// fromAndTo包含路径规划的起点和终点，drivingMode表示驾车模式
		// 第三个参数表示途经点（最多支持16个），第四个参数表示避让区域（最多支持32个），第五个参数表示避让道路
		DriveRouteQuery query = new DriveRouteQuery(fromAndTo, RouteSearch.DrivingShortDistance, null, null, "");
		routeSearch.calculateDriveRouteAsyn(query);//计算线路
	}

	@Override
	public void deactivate() {
		mListener = null;
		if (mlocationClient != null) {
			mlocationClient.stopLocation();
			mlocationClient.onDestroy();
		}
		mlocationClient = null;
	}
	//公交路线回调函数
	@Override
	public void onBusRouteSearched(BusRouteResult arg0, int arg1) {

	}
	//驾车路线回调函数
	@Override
	public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {
		DrivePath drivePath = driveRouteResult.getPaths().get(0);//取其中一个路线
		aMap.clear();

		DrivingRouteOverlay routeOverlay = new DrivingRouteOverlay(this, aMap,
				drivePath, driveRouteResult.getStartPos(),
				driveRouteResult.getTargetPos());
		routeOverlay.removeFromMap();
		routeOverlay.addToMap();
		routeOverlay.setNodeIconVisibility(false);
		routeOverlay.setThroughPointIconVisibility(false);
		routeOverlay.zoomToSpan();
	}
	//步行路线回调函数
	@Override
	public void onWalkRouteSearched(WalkRouteResult arg0, int arg1) {

	}
}
